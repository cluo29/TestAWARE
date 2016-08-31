package io.github.cluo29.contextdatareading;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import io.github.cluo29.contextdatareading.providers.Light_Provider.Light_Data;
import io.github.cluo29.contextdatareading.table.Light;

/**
 * Created by Comet on 09/08/16.
 */
public class LightDataReading extends Service {
    @Override
    public void onCreate() {
        for(int i=0; i<200000; i++)
        {
            long currentTime = 1470404312165L+i*1000;
            createLight(currentTime, "d9a6bd96-5c8c-40fe-8acf-39ba083be22e", 19.0d, 3, "");
            Log.d("UNLOCK","" + currentTime + " created!");
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
    }

    public void createLight(long timestamp, String device_id, double double_light_lux,
                            int accuracy, String label) {
        ContentValues data = new ContentValues();
        data.put(Light_Data.TIMESTAMP, timestamp);
        data.put(Light_Data.DEVICE_ID, device_id);
        data.put(Light_Data.LIGHT_LUX, double_light_lux);
        data.put(Light_Data.ACCURACY, accuracy);
        data.put(Light_Data.LABEL, label);
        getContentResolver().insert(Light_Data.CONTENT_URI, data);
    }
}