

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
import io.github.cluo29.contextdatareading.providers.Accelerometer_Provider;
import io.github.cluo29.contextdatareading.providers.Battery_Provider.Battery_Data;

import java.util.UUID;

public class Battery implements AbstractEvent {

    public int id() { return _id; }
    public long timestamp() { return _timestamp; }
    public UUID device() { return _device; }

    public Intent sendIntent()
    {
        ContentValues rowData = new ContentValues();
        rowData.put(Battery_Data._ID, _id);
        rowData.put(Battery_Data.TIMESTAMP, _timestamp);
        rowData.put(Battery_Data.STATUS, batteryStatus);
        rowData.put(Battery_Data.LEVEL, batteryLevel);
        rowData.put(Battery_Data.SCALE, batteryScale);
        rowData.put(Battery_Data.VOLTAGE, batteryVoltage);
        rowData.put(Battery_Data.TEMPERATURE, batteryTemperature);
        rowData.put(Battery_Data.PLUG_ADAPTOR, batteryAdaptor);
        rowData.put(Battery_Data.HEALTH, batteryHealth);
        rowData.put(Battery_Data.TECHNOLOGY, batteryTechnology);

        Intent accel_dev = new Intent("ACTION_AWARE_BATTERY_CHANGED");
        accel_dev.putExtra("data", rowData);
        return accel_dev;
    }

    private final int _id;
    private final long _timestamp;
    private final UUID _device;

    public final int batteryStatus;
    public final int batteryLevel;
    public final int batteryScale;
    public final int batteryVoltage;
    public final int batteryTemperature;
    public final int batteryAdaptor;
    public final int batteryHealth;
    public final String batteryTechnology;


    public Battery(int id, long timestamp, UUID device, int batteryStatus, int batteryLevel, int batteryScale,
                   int batteryVoltage, int batteryTemperature, int batteryAdaptor, int health, String technology) {

        this._id = id;
        this._timestamp = timestamp;
        this._device = device;
        this.batteryStatus = batteryStatus;
        this.batteryLevel = batteryLevel;
        this.batteryScale = batteryScale;
        this.batteryVoltage = batteryVoltage;
        this.batteryTemperature = batteryTemperature;
        this.batteryAdaptor = batteryAdaptor;
        this.batteryHealth = health;
        this.batteryTechnology = technology;
    }

    public String toString(){
        return "["+id()+"] - ["+timestamp()+"] - ["+device()+"] - ["+ batteryStatus +"] - ["+ batteryLevel +"] - ["+ batteryScale +"]" +
                " - ["+ batteryVoltage +"] - ["+ batteryTemperature + "] - ["+ batteryAdaptor +"] - ["+ batteryHealth +"] - "+ batteryTechnology +"";
    }

}
