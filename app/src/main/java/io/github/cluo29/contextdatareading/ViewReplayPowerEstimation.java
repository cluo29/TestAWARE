package io.github.cluo29.contextdatareading;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.cluo29.contextdatareading.providers.PowerResult_Provider.Power_Result;

import io.github.cluo29.contextdatareading.providers.PowerModel_Provider.PowerModel_Result;

/**
 * Created by Comet on 02/10/16.
 */

public class ViewReplayPowerEstimation extends AppCompatActivity {

    SimpleCursorAdapter dataAdapterReplay;

    TextView timerTextView;
    Spinner spinnerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powerestimation);

        //addPowerModelData();

        //addTestData();

        displayListViewReplay();

        //textPE2
        timerTextView= (TextView) findViewById(R.id.textPE2);
        spinnerSensor = (Spinner) findViewById(R.id.spinnerPE);
        loadSpinnerDataSensor();
        //todo display the  power model
        //check the current one
        //then display the list
        //then sum up
        //timerTextView.setText("mAh per hour");

    }

    public void addPowerModelData(){



        ContentValues data0 = new ContentValues();
        data0.put(PowerModel_Result.TIMESTAMP, 1L);
        data0.put(PowerModel_Result.DEVICE_NAME, "Nexus 5");
        data0.put(PowerModel_Result.SENSOR, "Accelerometer");
        data0.put(PowerModel_Result.SPEED, "Normal");
        data0.put(PowerModel_Result.VALUE, "0.4mAh per hour");
        getContentResolver().insert(PowerModel_Result.CONTENT_URI, data0);


        ContentValues data01 = new ContentValues();
        data01.put(PowerModel_Result.TIMESTAMP, 2L);
        data01.put(PowerModel_Result.DEVICE_NAME, "Nexus 5");
        data01.put(PowerModel_Result.SENSOR, "Light");
        data01.put(PowerModel_Result.SPEED, "Normal");
        data01.put(PowerModel_Result.VALUE, "0.175mAh per hour");
        getContentResolver().insert(PowerModel_Result.CONTENT_URI, data01);


        Log.d("Plot"," 73");
        /*

        PowerModel_Result._ID + " integer primary key autoincrement,"
                    + PowerModel_Result.TIMESTAMP + " real default 0,"
                    + PowerModel_Result.DEVICE_NAME + " text default '',"
                    + PowerModel_Result.SENSOR + " text default '',"
                    + PowerModel_Result.SPEED + " text default '',"
                    + PowerModel_Result.VALUE + " text default ''"
         */


        /*
        ContentValues data = new ContentValues();
        data.put(Power_Result.TIMESTAMP, 1L);
        data.put(Power_Result.DEVICE_ID, "Nexus 5");
        data.put(Power_Result.SENSOR, "Accelerometer");
        data.put(Power_Result.DELAY, "Normal");
        data.put(Power_Result.POWER, "0.4mAh per hour");
        getContentResolver().insert(Power_Result.CONTENT_URI, data);

        ContentValues data2 = new ContentValues();
        data2.put(Power_Result.TIMESTAMP, 2L);
        data2.put(Power_Result.DEVICE_ID, "Nexus 5");
        data2.put(Power_Result.SENSOR, "Light");
        data2.put(Power_Result.DELAY, "Normal");
        data2.put(Power_Result.POWER, "0.175mAh per hour");
        getContentResolver().insert(Power_Result.CONTENT_URI, data2);
*/

    }

    public void addTestData(){
        ContentValues data = new ContentValues();
        data.put(Power_Result.TIMESTAMP, 1L);
        data.put(Power_Result.DEVICE_ID, "Nexus 5");
        data.put(Power_Result.SENSOR, "Accelerometer");
        data.put(Power_Result.DELAY, "Normal");
        data.put(Power_Result.POWER, "0.4mAh per hour");
        getContentResolver().insert(Power_Result.CONTENT_URI, data);

        ContentValues data2 = new ContentValues();
        data2.put(Power_Result.TIMESTAMP, 2L);
        data2.put(Power_Result.DEVICE_ID, "Nexus 5");
        data2.put(Power_Result.SENSOR, "Light");
        data2.put(Power_Result.DELAY, "Normal");
        data2.put(Power_Result.POWER, "0.175mAh per hour");
        getContentResolver().insert(Power_Result.CONTENT_URI, data2);

        Log.d("Plot"," Created");
    }

    public void displayListViewReplay(){
        //dataAdapterReplay
        Cursor cursor = getContentResolver().query(Power_Result.CONTENT_URI, null,
                null, null, Power_Result._ID + " ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String[] columns = new String[] {
                Power_Result.SENSOR,
                Power_Result.DELAY,
                Power_Result.POWER
        };

        //UI binding

        int[] to = new int[] {
                R.id.textView_pe4,
                R.id.textView_pe5,
                R.id.textView_pe6
        };

        dataAdapterReplay = new SimpleCursorAdapter(this, R.layout.powerestimation_item, cursor, columns, to, 0);

        ListView listView = (ListView) findViewById(R.id.listViewPE);
        listView.setAdapter(dataAdapterReplay);
    }


    private void loadSpinnerDataSensor()
    {
        List<String> labels = new ArrayList<String>();
        Cursor cursor = getContentResolver().query(PowerModel_Result.CONTENT_URI, null,
                null, null, PowerModel_Result._ID + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String sensorRow = cursor.getString(cursor.getColumnIndex(PowerModel_Result.DEVICE_NAME));
                if(!labels.contains(sensorRow))
                    labels.add(sensorRow);
            }
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSensor.setAdapter(dataAdapter);
    }
}