package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 13/10/16.
 */

import io.github.cluo29.contextdatareading.noisiness.*;

import io.github.cluo29.contextdatareading.providers.Accelerometer_Provider;
import io.github.cluo29.contextdatareading.semantization.parser.*;
import io.github.cluo29.contextdatareading.table.Battery;
import io.github.cluo29.contextdatareading.providers.Battery_Provider.*;
//get an acceleration one
import io.github.cluo29.contextdatareading.table.Accelerometer;

//get an acceleration provider
import io.github.cluo29.contextdatareading.providers.Audio_Provider.Audio_Result;
import io.github.cluo29.contextdatareading.providers.Accelerometer_Provider.*;
import io.github.cluo29.contextdatareading.providers.Light_Provider.Light_Data;
import io.github.cluo29.contextdatareading.table.Event;
import io.github.cluo29.contextdatareading.table.Light;
import io.github.cluo29.contextdatareading.providers.Event_Provider.Event_Data;
import io.github.cluo29.contextdatareading.providers.ESM_Provider.ESM_Result;
import io.github.cluo29.contextdatareading.providers.Screen_Provider.Screen_Result;

import android.app.Service;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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

public class OutputMatchingTest extends Service {

    ArrayList<String> sensorList;
    @Override
    public void onCreate() {

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        IntentFilter command_filter = new IntentFilter();
        command_filter.addAction("ACTION_TESTAWARE_START");
        command_filter.addAction("ACTION_TESTAWARE_STOP");

        registerReceiver(commandListener, command_filter);

        //setSpeed(1000d);
        //start();

        /*
        Cursor cursor = getContentResolver().query(ESM_Result.CONTENT_URI, null, null, null, ESM_Result.TIMESTAMP + " ASC LIMIT 1");

        if (cursor != null && cursor.moveToFirst()) {
            Long thisTimestamp = cursor.getLong(cursor.getColumnIndex(ESM_Result.TIMESTAMP));
            if(thisTimestamp < startTimestamp)
            {
                startTimestamp = thisTimestamp;
            }
            Log.d("UNLOCK", "startTimestamp = " + startTimestamp);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
*/
        /*
        Cursor cursor = getContentResolver().query(Screen_Result.CONTENT_URI, null, null, null, Screen_Result.TIMESTAMP + " ASC LIMIT 1");

        if (cursor != null && cursor.moveToFirst()) {
            Long thisTimestamp = cursor.getLong(cursor.getColumnIndex(Screen_Result.TIMESTAMP));
            if(thisTimestamp < startTimestamp)
            {
                startTimestamp = thisTimestamp;
            }
            Log.d("UNLOCK", "startTimestamp = " + startTimestamp);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
*/


    }

    private CommandListener commandListener = new CommandListener();

    public class CommandListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACTION_TESTAWARE_STOP")) {
                Log.d("UNLOCK", "Over the hills and far away!");
                stop();
            }

            if (intent.getAction().equals("ACTION_TESTAWARE_START")) {
                Log.d("UNLOCK", "Over the hills and far away2!");

                sensorList = new ArrayList<String>();
                sensorList = intent.getStringArrayListExtra("sensorList");

                DataSourceLocal dataSource = new SQLiteDataSource();

                accelerometer = new EventsHandler<Accelerometer>("Accelerometer", dataSource.accelerometer());
                battery = new EventsHandler<Battery>("Battery", dataSource.battery());
                light = new EventsHandler<Light>("Light", dataSource.light());

                //check sensor list to judge timestamp

                if(sensorList.contains("Accelerometer")) {
                    //power sensor name
                    //
                    //sensing delay, 2 rows!


                    //query data base   getApplicationContext().getContentResolver().query
                    Cursor cursor = getContentResolver().query(Accelerometer_Data.CONTENT_URI, null, null, null, Accelerometer_Data.TIMESTAMP + " ASC LIMIT 1");

                    if (cursor != null && cursor.moveToFirst()) {
                        Long thisTimestamp = cursor.getLong(cursor.getColumnIndex(Accelerometer_Data.TIMESTAMP));
                        if(thisTimestamp < startTimestamp)
                        {
                            startTimestamp = thisTimestamp;
                        }
                        Log.d("UNLOCK", "startTimestamp = " + startTimestamp);
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
                if(sensorList.contains("Battery")) {
                    //query data base   getApplicationContext().getContentResolver().query
                    Cursor cursor = getContentResolver().query(Battery_Data.CONTENT_URI, null, null, null, Battery_Data.TIMESTAMP + " ASC LIMIT 1");

                    if (cursor != null && cursor.moveToFirst()) {
                        Long thisTimestamp = cursor.getLong(cursor.getColumnIndex(Battery_Data.TIMESTAMP));
                        if(thisTimestamp < startTimestamp)
                        {
                            startTimestamp = thisTimestamp;
                        }
                        Log.d("UNLOCK", "startTimestamp = " + startTimestamp);
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
                if(sensorList.contains("Light")) {
                    //query data base   getApplicationContext().getContentResolver().query
                    Cursor cursor = getContentResolver().query(Light_Data.CONTENT_URI, null, null, null, Light_Data.TIMESTAMP + " ASC LIMIT 1");

                    if (cursor != null && cursor.moveToFirst()) {
                        Long thisTimestamp = cursor.getLong(cursor.getColumnIndex(Light_Data.TIMESTAMP));
                        if(thisTimestamp < startTimestamp)
                        {
                            startTimestamp = thisTimestamp;
                        }
                        Log.d("UNLOCK", "startTimestamp = " + startTimestamp);
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }


                setSpeed(intent.getDoubleExtra("speed", 1.0d));
                start();
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(commandListener);

    }



    public final class EventsHandler<T extends AbstractEvent> {
        private static final int MIN_BUFFER_SIZE = 3;
        private static final int REFILL_BUFFER_WITH = 20;
        public DataSourceLocal.Source<T> source;
        public String myType;

        public EventsHandler(String type, DataSourceLocal.Source<T> source ) {
            this.myType = type;
            this.source = source;
            //sensor type, first letter Capital, for scheduling
        }

        private void refill() throws InterruptedException {
            if (buffer.size() < Math.max(1, MIN_BUFFER_SIZE)) {

                // wont work via this.getClass();

                int number = Math.max(REFILL_BUFFER_WITH, Math.max(MIN_BUFFER_SIZE, 1));

                //get all sensor
                final List<T> nexts = source.apply(lastId.get(), startTimestamp, number);

                for (final T next : nexts) {
                    buffer.put(next);
                }


            }
        }

        private void scheduleNext(final long currentTimestamp) {
            try {
                refill();

                //type
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

        //type
        private void publish(final T event) {
            if (enabled.get())
            {
                //send data by intent, this is the way AWARE plugin gets it.
                sendBroadcast(event.sendIntent());

            }
            scheduleNext(event.timestamp());
        }

        private final AtomicBoolean enabled = new AtomicBoolean(true);

        //type
        private final LinkedBlockingQueue<T> buffer = new LinkedBlockingQueue<T>();
        private final AtomicInteger lastId = new AtomicInteger(0);
    }

    public EventsHandler<Accelerometer> accelerometer;
    public EventsHandler<Battery> battery;
    public EventsHandler<Light> light;
    public EventsHandler<Event> event;

    static private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(12);//newSingleThreadScheduledExecutor();
    private List<EventsHandler<? extends AbstractEvent>> allHandlers = new ArrayList<EventsHandler<? extends AbstractEvent>>();

    public void start() {


        //check sensor list
        if(sensorList.contains("Accelerometer"))
        {
            allHandlers.add(accelerometer);
        }

        if(sensorList.contains("Battery"))
        {
            allHandlers.add(battery);
        }

        if(sensorList.contains("Light"))
        {
            allHandlers.add(light);
        }
        if(sensorList.contains("Event"))
        {
            allHandlers.add(event);
        }

        if (hasStarted.compareAndSet(false, true)) {
            for (final EventsHandler<? extends AbstractEvent> h : allHandlers) {
                scheduler.schedule(new Runnable() {
                    public void run() {

                        h.scheduleNext(startTimestamp);
                    }
                }, 0, TimeUnit.MILLISECONDS);
            }
        }
    }
    public void setSpeed(final double sp) { speed.set(sp); }

    public void stop() {
        scheduler.shutdownNow();
        scheduler = Executors.newScheduledThreadPool(12);//newSingleThreadScheduledExecutor();
        allHandlers = new ArrayList<EventsHandler<? extends AbstractEvent>>();
        hasStarted = new AtomicBoolean(false);
    }

    public long startTimestamp = Long.MAX_VALUE;
    private AtomicReference<Double> speed = new AtomicReference<Double>(1.0);
    private AtomicBoolean hasStarted = new AtomicBoolean(false);

    //how to make getContentResolver work.... so i copy all shit here.

    public class SQLiteDataSource implements DataSourceLocal {
        public SQLiteDataSource() {
        }

        public Source<Battery> battery() {
            return new Source<Battery>() {
                public List<Battery> apply(int withIdGreaterThan, long withTimestampGreaterEqualTo, int number) {
                    List<Battery> rv = new ArrayList<Battery>();
                    Cursor cursor = getContentResolver().query(Battery_Data.CONTENT_URI, null,
                            Battery_Data._ID + " > " + withIdGreaterThan + " AND " + Battery_Data.TIMESTAMP + " >= " + withTimestampGreaterEqualTo,
                            null, Battery_Data._ID + " ASC LIMIT " + number);

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            rv.add(new Battery(
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data._ID)),
                                    cursor.getLong(cursor.getColumnIndex(Battery_Data.TIMESTAMP)),
                                    UUID.fromString(cursor.getString(cursor.getColumnIndex(Battery_Data.DEVICE_ID))),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.STATUS)),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.LEVEL)),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.SCALE)),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.VOLTAGE)),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.TEMPERATURE)),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.PLUG_ADAPTOR)),
                                    cursor.getInt(cursor.getColumnIndex(Battery_Data.HEALTH)),
                                    cursor.getString(cursor.getColumnIndex(Battery_Data.TECHNOLOGY))));
                        }
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return rv;
                }

            };
        }

        public Source<Accelerometer> accelerometer() {
            return new Source<Accelerometer>() {
                public List<Accelerometer> apply(int withIdGreaterThan, long withTimestampGreaterEqualTo, int number) {
                    List<Accelerometer> rv = new ArrayList<Accelerometer>();
                    Cursor cursor = getContentResolver().query(Accelerometer_Data.CONTENT_URI, null,
                            Accelerometer_Data._ID + " > " + withIdGreaterThan + " AND " + Accelerometer_Data.TIMESTAMP + " >= " + withTimestampGreaterEqualTo,
                            null, Accelerometer_Data._ID + " ASC LIMIT " + number);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            rv.add(new Accelerometer(
                                    cursor.getInt(cursor.getColumnIndex(Accelerometer_Data._ID)),
                                    cursor.getLong(cursor.getColumnIndex(Accelerometer_Data.TIMESTAMP)),
                                    UUID.fromString(cursor.getString(cursor.getColumnIndex(Accelerometer_Data.DEVICE_ID))),
                                    cursor.getDouble(cursor.getColumnIndex(Accelerometer_Data.VALUES_0)),
                                    cursor.getDouble(cursor.getColumnIndex(Accelerometer_Data.VALUES_1)),
                                    cursor.getDouble(cursor.getColumnIndex(Accelerometer_Data.VALUES_2)),
                                    cursor.getInt(cursor.getColumnIndex(Accelerometer_Data.ACCURACY)),
                                    cursor.getString(cursor.getColumnIndex(Accelerometer_Data.LABEL))));
                        }
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return rv;
                }
            };
        }

        public Source<Light> light() {
            return new Source<Light>() {
                public List<Light> apply(int withIdGreaterThan, long withTimestampGreaterEqualTo, int number) {
                    List<Light> rv = new ArrayList<Light>();
                    Cursor cursor = getContentResolver().query(Light_Data.CONTENT_URI, null,
                            Light_Data._ID + " > " + withIdGreaterThan + " AND " + Light_Data.TIMESTAMP + " >= " + withTimestampGreaterEqualTo,
                            null, Light_Data._ID + " ASC LIMIT " + number);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            rv.add(new Light(
                                    cursor.getInt(cursor.getColumnIndex(Light_Data._ID)),
                                    cursor.getLong(cursor.getColumnIndex(Light_Data.TIMESTAMP)),
                                    UUID.fromString(cursor.getString(cursor.getColumnIndex(Light_Data.DEVICE_ID))),
                                    cursor.getDouble(cursor.getColumnIndex(Light_Data.LIGHT_LUX)),
                                    cursor.getInt(cursor.getColumnIndex(Light_Data.ACCURACY)),
                                    cursor.getString(cursor.getColumnIndex(Light_Data.LABEL))));
                        }
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return rv;
                }
            };
        }

        public Source<Event> event() {
            return new Source<Event>() {
                public List<Event> apply(int withIdGreaterThan, long withTimestampGreaterEqualTo, int number) {
                    List<Event> rv = new ArrayList<Event>();
                    Cursor cursor = getContentResolver().query(Event_Data.CONTENT_URI, null,
                            Event_Data._ID + " > " + withIdGreaterThan + " AND " + Event_Data.TIMESTAMP + " >= " + withTimestampGreaterEqualTo,
                            null, Event_Data._ID + " ASC LIMIT " + number);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            rv.add(new Event(
                                    cursor.getInt(cursor.getColumnIndex(Event_Data._ID)),
                                    cursor.getLong(cursor.getColumnIndex(Event_Data.TIMESTAMP)),
                                    UUID.fromString("1"),
                                    cursor.getString(cursor.getColumnIndex(Event_Data.EVENT))));
                        }
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return rv;
                }
            };
        }
    }


}