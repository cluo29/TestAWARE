package io.github.cluo29.contextdatareading;

import io.github.cluo29.contextdatareading.noisiness.*;

import io.github.cluo29.contextdatareading.semantization.parser.*;

//get an acceleration one
import io.github.cluo29.contextdatareading.table.Accelerometer;

//get an acceleration provider
import io.github.cluo29.contextdatareading.providers.Accelerometer_Provider.*;

import android.app.Service;

import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;


/**
 * Created by Comet on 08/08/16.
 */
public class AccelerationDataReading extends Service {
    @Override
    public void onCreate() {

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // read data file here
        try {

            final DataSource ds = new MysqlDataSource("awareframework.com", 3306, "Luo_834", "exnklPLF", "Luo_834");

            final DataNoiser dn = new SimpleDataNoiser();

            //214500 rows
            final AwareSimulator sim = new AwareSimulator(ds, dn, 1470404312164L, UUID.fromString("d9a6bd96-5c8c-40fe-8acf-39ba083be22e"));
            sim.setSpeed(1.0);

            sim.accelerometer.addListener(new AwareSimulator.Listener<Accelerometer>() {
                public void onEvent(Accelerometer event) {

                    /*
                    Intent battery_change_intent = new Intent("ACTION_AWARE_BATTERY_CHANGED");

                    battery_change_intent.putExtra("TIMESTAMP", event.timestamp());
                    battery_change_intent.putExtra("DEVICE_ID", Objects.toString(event.device()));
                    battery_change_intent.putExtra("STATUS", event.batteryStatus);
                    battery_change_intent.putExtra("LEVEL", event.batteryLevel);
                    battery_change_intent.putExtra("SCALE", event.batteryScale);
                    */

                    //put the simulated data into DB
                    Log.d("Acce","event.timestamp()= "+event.timestamp());
                    Log.d("Acce","event.doubleValues0()= "+event.doubleValues0);
                    Log.d("Acce","event.doubleValues1()= "+event.doubleValues1);
                    Log.d("Acce","event.doubleValues2()= "+event.doubleValues2);
                    ContentValues rowData = new ContentValues();
                    rowData.put(Accelerometer_Data.TIMESTAMP, event.timestamp());
                    rowData.put(Accelerometer_Data.DEVICE_ID, Objects.toString(event.device()));
                    rowData.put(Accelerometer_Data.VALUES_0, event.doubleValues0);
                    rowData.put(Accelerometer_Data.VALUES_1, event.doubleValues1);
                    rowData.put(Accelerometer_Data.VALUES_2, event.doubleValues2);
                    rowData.put(Accelerometer_Data.ACCURACY, event.accuracy);
                    rowData.put(Accelerometer_Data.LABEL, event.label);
                    getApplicationContext().getContentResolver().insert(Accelerometer_Data.CONTENT_URI, rowData);

                }
            });
            sim.start();

        } catch (SQLException|ClassNotFoundException e) {

            e.printStackTrace();

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
}