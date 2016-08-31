package io.github.cluo29.contextdatareading.table;

import android.content.ContentValues;
import android.content.Intent;

import io.github.cluo29.contextdatareading.AbstractEvent;
import io.github.cluo29.contextdatareading.providers.Light_Provider.Light_Data;

import java.util.UUID;

public class Light implements AbstractEvent{

    public int id() { return _id; }
    public long timestamp() { return _timestamp; }
    public UUID device() { return _device; }

    public Intent sendIntent()
    {
        ContentValues rowData = new ContentValues();
        rowData.put(Light_Data.DEVICE_ID, _id);
        rowData.put(Light_Data.TIMESTAMP, _timestamp);
        rowData.put(Light_Data.LIGHT_LUX, double_light_lux);
        rowData.put(Light_Data.ACCURACY, accuracy);
        rowData.put(Light_Data.LABEL, label);

        Intent accel_dev = new Intent("ACTION_AWARE_LIGHT");
        accel_dev.putExtra("data", rowData);
        return accel_dev;
    }

    private final int _id;
    private final long _timestamp;
    private final UUID _device;

    public final double double_light_lux;
    public final int accuracy;
    public final String label;

    public Light(int id, long timestamp, UUID device, double double_light_lux, int accuracy, String label) {
        this._id = id;
        this._timestamp = timestamp;
        this._device = device;
        this.double_light_lux = double_light_lux;
        this.accuracy = accuracy;
        this.label = label;
    }

    public String toString() {
        return "["+id()+"] - ["+timestamp()+"] - ["+device()+"] - ["+ double_light_lux +"] - ["+ accuracy +"] - "+ label +"";
    }
}