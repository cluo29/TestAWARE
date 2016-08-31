package io.github.cluo29.contextdatareading.table;


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

import android.content.ContentValues;
import android.content.Intent;

import io.github.cluo29.contextdatareading.AbstractEvent;
//import io.github.cluo29.contextdatareading.providers.;

import java.util.UUID;

public class Locations implements AbstractEvent {

    public int id() { return _id; }
    public long timestamp() { return _timestamp; }
    public UUID device() { return _device; }
    public Intent sendIntent()
    {
        /*
        ContentValues rowData = new ContentValues();
        rowData.put(Locations_Data, _id);
        rowData.put(Light_Provider.Light_Data.TIMESTAMP, _timestamp);
        rowData.put(Light_Provider.Light_Data.LIGHT_LUX, double_light_lux);
        rowData.put(Light_Provider.Light_Data.ACCURACY, accuracy);
        rowData.put(Light_Provider.Light_Data.LABEL, label);
*/
        Intent accel_dev = new Intent("ACTION_AWARE_LOCATIONS");
        //accel_dev.putExtra("data", rowData);
        return accel_dev;
    }

    private final int _id;
    private final long _timestamp;
    private final UUID _device;

    public final double doubleLatitude;
    public final double doubleLongitude;
    public final double doubleBearing;
    public final double doubleSpeed;
    public final double doubleAltitude;
    public final double accuracy;
    public final String provider;
    public final String label;

    public Locations(int id, long timestamp, UUID device, double doubleLatitude, double doubleLongitude,
                     double doubleBearing, double doubleSpeed, double doubleAltitude, String provider, double accuracy, String label) {

        this._id = id;
        this._timestamp = timestamp;
        this._device = device;
        this.doubleLatitude = doubleLatitude;
        this.doubleLongitude = doubleLongitude;
        this.doubleBearing = doubleBearing;
        this.doubleSpeed = doubleSpeed;
        this.doubleAltitude = doubleAltitude;
        this.provider = provider;
        this.accuracy = accuracy;
        this.label = label;
    }

    public String toString() {
        return "["+id()+"] - ["+timestamp()+"] - ["+device()+"] - ["+ doubleLatitude +"] - ["+ doubleLongitude +"] - ["+ doubleBearing +"]" +
                " - ["+ doubleSpeed +"] - ["+ doubleAltitude +"] - "+ provider +" - ["+ accuracy +"] - "+ label+"";
    }
}