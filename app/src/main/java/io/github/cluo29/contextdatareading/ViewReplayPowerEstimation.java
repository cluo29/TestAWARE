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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import io.github.cluo29.contextdatareading.providers.PowerResult_Provider.Power_Result;

/**
 * Created by Comet on 02/10/16.
 */

public class ViewReplayPowerEstimation extends AppCompatActivity {

    SimpleCursorAdapter dataAdapterReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powerestimation);

        //addTestData();

        displayListViewReplay();

        //textPE2
    }

    public void addTestData(){
        ContentValues data = new ContentValues();
        data.put(Power_Result.TIMESTAMP, 1L);
        data.put(Power_Result.DEVICE_ID, "");
        data.put(Power_Result.SENSOR, "Accelerometer");
        data.put(Power_Result.DELAY, "Normal");
        data.put(Power_Result.POWER, "0.4mAh per hour");
        getContentResolver().insert(Power_Result.CONTENT_URI, data);

        ContentValues data2 = new ContentValues();
        data2.put(Power_Result.TIMESTAMP, 2L);
        data2.put(Power_Result.DEVICE_ID, "");
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


}