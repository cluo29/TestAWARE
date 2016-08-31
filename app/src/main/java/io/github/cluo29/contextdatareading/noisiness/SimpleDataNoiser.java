package io.github.cluo29.contextdatareading.noisiness;

import io.github.cluo29.contextdatareading.table.*;


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

public final class SimpleDataNoiser implements DataNoiser {

    public SimpleDataNoiser() {
    }

    public Noiser<Accelerometer> accelerometer() {
        return new Noiser<Accelerometer>() {
            public Accelerometer apply(Accelerometer rv) {
                return rv;
            }
        };
    }

    public Noiser<Battery> battery() {
        return new Noiser<Battery>() {
            public Battery apply(Battery rv) {
                return rv;
            }
        };
    }

    public Noiser<Light> light() {
        return new Noiser<Light>() {
            public Light apply(Light rv) {
                return rv;
            }
        };
    }

    public Noiser<Locations> locations() {
        return new Noiser<Locations>() {
            public Locations apply(Locations rv) {
                return rv;
            }
        };
    }
}
