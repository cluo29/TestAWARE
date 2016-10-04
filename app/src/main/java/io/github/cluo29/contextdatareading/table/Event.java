package io.github.cluo29.contextdatareading.table;

/**
 * Created by Comet on 04/10/16.
 */

import android.content.ContentValues;
import android.content.Intent;

import io.github.cluo29.contextdatareading.AbstractEvent;
import io.github.cluo29.contextdatareading.providers.Event_Provider.Event_Data;

import java.util.UUID;

public class Event implements AbstractEvent {

    public int id() { return _id; }
    public long timestamp() { return _timestamp; }
    public UUID device() { return _device; }

    public Intent sendIntent()
    {
        Intent accel_dev = new Intent(event);
        return accel_dev;
    }

    private final int _id;
    private final long _timestamp;
    private final UUID _device;
    public final String event;

    public Event(int id, long timestamp, UUID device, String event1)
    {
        this._id = id;
        this._timestamp = timestamp;
        this._device = device;
        this.event = event1;
    }

    public String toString(){
        return "["+id()+"] - ["+timestamp()+"] - ["+device()+"] - ["+ event +"]";
    }
}
