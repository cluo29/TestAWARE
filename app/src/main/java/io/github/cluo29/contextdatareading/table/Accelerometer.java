package io.github.cluo29.contextdatareading.table;

import android.content.ContentValues;
import android.content.Intent;

import io.github.cluo29.contextdatareading.AbstractEvent;
import io.github.cluo29.contextdatareading.providers.Accelerometer_Provider;

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

public class Accelerometer implements AbstractEvent{

    public int id() { return _id; }
    public long timestamp() { return _timestamp; }
    public UUID device() { return _device; }
    public Intent sendIntent()
    {
        ContentValues rowData = new ContentValues();
        rowData.put(Accelerometer_Provider.Accelerometer_Data._ID, _id);
        rowData.put(Accelerometer_Provider.Accelerometer_Data.TIMESTAMP, _timestamp);
        rowData.put(Accelerometer_Provider.Accelerometer_Data.VALUES_0, doubleValues0);
        rowData.put(Accelerometer_Provider.Accelerometer_Data.VALUES_1, doubleValues1);
        rowData.put(Accelerometer_Provider.Accelerometer_Data.VALUES_2, doubleValues2);
        rowData.put(Accelerometer_Provider.Accelerometer_Data.ACCURACY, accuracy);
        rowData.put(Accelerometer_Provider.Accelerometer_Data.LABEL, label);

        Intent accel_dev = new Intent("ACTION_AWARE_ACCELEROMETER");
        accel_dev.putExtra("data", rowData);
        return accel_dev;
    }

    private final int _id;
    private final long _timestamp;
    private final UUID _device;

    public final double doubleValues0;
    public final double doubleValues1;
    public final double doubleValues2;
    public final int accuracy;
    public final String label;

    public Accelerometer(int id, long timestamp, UUID device, double doubleValues0, double doubleValues1, double doubleValues2, int accuracy, String label) {
        this._id = id;
        this._timestamp = timestamp;
        this._device = device;
        this.doubleValues0 = doubleValues0;
        this.doubleValues1 = doubleValues1;
        this.doubleValues2 = doubleValues2;
        this.accuracy = accuracy;
        this.label = label;
    }

    public String toString() {
        return "["+id()+"] - ["+timestamp()+"] - ["+device()+"] - ["+ doubleValues0 +"] - ["+ doubleValues1 +"] - ["+ doubleValues2 +"] - ["+ accuracy +"] - "+ label +"";
    }
}