package io.github.cluo29.contextdatareading;

import io.github.cluo29.contextdatareading.table.*;

import java.util.List;
import java.util.UUID;

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

public interface DataSourceLocal {

        /**
         * Simulates a passable function object (Java lacks these). This
         * function reads next {@link T}s from {@link DataSource}.
         * @param <T>
         */
        interface Source<T extends AbstractEvent> {
            /**

             * @param withIdGreaterThan              {@link AbstractEvent#id()}s of returned events will be
             *                                       strictly greater than this param.
             * @param withTimestampGreaterEqualTo    {@link AbstractEvent#timestamp()} will be greater-equal to
             *                                       this param.
             * @param number                         this many events satisfying above conditions (with the
             *                                       smallest {@link AbstractEvent#id()} possible) will be returned.
             * @return                               a {@link List} of events of type {@link T}.
             */
            List<T> apply(int withIdGreaterThan, long withTimestampGreaterEqualTo, int number);
        }

        public Source<Accelerometer> accelerometer();

        public Source<Battery> battery();
        public Source<Light> light();
        //public Source<Locations> locations();
}


