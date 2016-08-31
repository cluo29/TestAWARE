package io.github.cluo29.contextdatareading;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import io.github.cluo29.contextdatareading.providers.AccelerometerFake_Provider.Accelerometer_Data;

/**
 * Created by Comet on 09/08/16.
 */
public class AccelerationFakeDataReading extends Service {
    @Override
    public void onCreate() {
        for(int i=0; i<200000; i++)
        {
            long currentTime = 1470404312165L+i*1000;
            createAcceleration(currentTime, "d9a6bd96-5c8c-40fe-8acf-39ba083be22e", 3.0d,
                    3.0d, 3.0d, 3, "");
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

    public void createAcceleration(long timestamp, String device_id, double doubleValues0,
                                   double doubleValues1,double doubleValues2,
                                   int accuracy, String label) {
        ContentValues data = new ContentValues();
        data.put(Accelerometer_Data.TIMESTAMP, timestamp);
        data.put(Accelerometer_Data.DEVICE_ID, device_id);
        data.put(Accelerometer_Data.VALUES_0, doubleValues0);
        data.put(Accelerometer_Data.VALUES_1, doubleValues1);
        data.put(Accelerometer_Data.VALUES_2, doubleValues2);
        data.put(Accelerometer_Data.ACCURACY, accuracy);
        data.put(Accelerometer_Data.LABEL, label);
        getContentResolver().insert(Accelerometer_Data.CONTENT_URI, data);
    }
}