
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

import android.content.Intent;

import java.util.UUID;

/**
 * All events share {@link AbstractEvent#id()}, {@link AbstractEvent#timestamp()}
 * and {@link AbstractEvent#device()}. {@link AwareSimulator.EventsHandler} uses
 * these fields to correctly schedule events of all concrete types.
 */
public interface AbstractEvent {

    /** This field is unique only among events of the same concrete type. */
    int id();

    /** Milliseconds since Epoch */
    long timestamp();

    /** Unique ID of a device that generated this event. */
    UUID device();

    //you know
    Intent sendIntent();

}
