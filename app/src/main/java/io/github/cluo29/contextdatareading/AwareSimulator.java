
/* https://github.com/cluo29 Chu reused their code
 * Copyright 2014 Szymon Bielak <bielakszym@gmail.com> and
 *     Michał Rus <https://michalrus.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.cluo29.contextdatareading;

import android.util.Log;

import io.github.cluo29.contextdatareading.table.*;
import io.github.cluo29.contextdatareading.noisiness.DataNoiser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Main simulation class. You need to have one instance of
 * {@link AwareSimulator} for each simulation you want to run.
 */
public final class AwareSimulator {

    /**
     * An user-provided Listener that might be registered/added
     * to any {@link EventsHandler} using {@link EventsHandler#addListener(Listener)}.
     *
     * @param <T>    the type of event to be listened for.
     */
    public interface Listener<T extends AbstractEvent> {
        public void onEvent(final T event);
    }

    /**
     * A handler for each type of supported event {@link T}.
     *
     * @param <T>     the type of event.
     */
    public final class EventsHandler<T extends AbstractEvent> {
        private static final int MIN_BUFFER_SIZE = 3;
        private static final int REFILL_BUFFER_WITH = 20;

        /**
         * @param source    A {@link DataSource.Source} of events of type {@link T}.
         */
        public EventsHandler(final DataSource.Source<T> source, final DataNoiser.Noiser<T> noiser) {

            this.source = source;
            this.noiser = noiser;
            allHandlers.add(this);
        }

        /** Temporarily turns off all {@link Listener}s added to this handler. */
        public void enable() { enabled.set(true); }
        /** Turns back on all {@link Listener}s added to this handler. */
        public void disable() { enabled.set(false); }
        /** Registers a {@link Listener} that will be notified if this handler is enabled. */
        public void addListener(final Listener<T> l) { listeners.add(l); }

        private void refill() throws InterruptedException {
            if (buffer.size() < Math.max(1, MIN_BUFFER_SIZE)) {
                final List<T> nexts = source.apply(device, lastId.get(), startTimestamp, Math.max(REFILL_BUFFER_WITH, Math.max(MIN_BUFFER_SIZE, 1)));
                for (final T next : nexts)
                    buffer.put(noiser.apply(next));
            }
        }

        private void scheduleNext(final long currentTimestamp) {
            try {


                refill();
                final T next = buffer.poll(0L, TimeUnit.MILLISECONDS);

                final long msSim = Math.max(0L, next.timestamp() - currentTimestamp);
                final long msReal = Math.round(msSim / Math.abs(speed.get()));

                scheduler.schedule(new Runnable() {
                    public void run() {
                        publish(next);
                    }
                }, msReal, TimeUnit.MILLISECONDS);

                lastId.set(next.id());
            } catch (InterruptedException ignored) {}
        }

        private void publish(final T event) {


            if (enabled.get())
                for (final Listener<T> l : listeners)
                    l.onEvent(event);
            scheduleNext(event.timestamp());
        }

        private final AtomicBoolean enabled = new AtomicBoolean(true);
        private final List<Listener<T>> listeners = Collections.synchronizedList(new ArrayList<Listener<T>>());
        private final DataSource.Source<T> source;
        private final DataNoiser.Noiser<T> noiser;
        private final LinkedBlockingQueue<T> buffer = new LinkedBlockingQueue<T>();
        private final AtomicInteger lastId = new AtomicInteger(0);
    }

    public final EventsHandler<Accelerometer> accelerometer;
    //public final EventsHandler<Battery> battery;
    //public final EventsHandler<Light> light;
    //location joke!

    /**
     * @param dataSource        A {@link DataSource} for all the events.
     * @param startTimestamp    A time [ms] at which the user wants to start their simulation.
     *                          If set to, say, 10000 and the first event with its
     *                          {@link AbstractEvent#timestamp()}
     *                          greater than 10000 has it set to 11000, then all registered
     *                          listeners of this type will be called one second after
     *                          calling {@link AwareSimulator#start()}. (That is if simulation
     *                          speed is set to 1.0, see {@link AwareSimulator#setSpeed(double)}.)
     * @param device            A {@link UUID} of the device the user wants to simulate.
     */
    public AwareSimulator(final DataSource dataSource, final DataNoiser dataNoiser, final long startTimestamp, final UUID device) {
        this.startTimestamp = startTimestamp;
        this.device = device;

        this.accelerometer = new EventsHandler<Accelerometer>(dataSource.accelerometer(), dataNoiser.accelerometer());
        //this.battery = new EventsHandler<Battery>(dataSource.battery(), dataNoiser.battery());
        //this.light = new EventsHandler<Light>(dataSource.light(), dataNoiser.light());

    }

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final List<EventsHandler<? extends AbstractEvent>> allHandlers = new ArrayList<EventsHandler<? extends AbstractEvent>>();

    /**
     * Starts the simulation. Should be called once only, all
     * subsequent calls will be ignored (with no exception thrown).
     *
     * See the documentation for
     * for details about timing, start time etc.
     */
    public void start() {
        if (hasStarted.compareAndSet(false, true)) {
            for (final EventsHandler<? extends AbstractEvent> h : allHandlers)
                scheduler.schedule(new Runnable() {
                    public void run() {
                        h.scheduleNext(startTimestamp);
                    }
                }, 0, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Stops the simulation. If you call {@link AwareSimulator#start()}, nothing will happen.
     */
    public void stop() {
        scheduler.shutdownNow();
    }

    /**
     * Speeds up / slows down the frequency of publishing of events.
     * E.g. if two events are 1 s apart and the speed factor is 1000.0,
     * the events will be published with 1 ms distance.
     *
     * May be called during simulation run.
     *
     * @param sp    new speed factor.
     */
    public void setSpeed(final double sp) { speed.set(sp); }

    final private long startTimestamp;
    final private UUID device;
    final private AtomicReference<Double> speed = new AtomicReference<Double>(1.0);
    final private AtomicBoolean hasStarted = new AtomicBoolean(false);

}
