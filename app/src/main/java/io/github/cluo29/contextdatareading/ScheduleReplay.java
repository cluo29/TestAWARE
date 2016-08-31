package io.github.cluo29.contextdatareading;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.cluo29.contextdatareading.providers.Replay_Provider.Replay_Info;

/**
 * Created by Comet on 14/08/16.
 */
public class ScheduleReplay extends AppCompatActivity {

    Button stopButton;
    Button startButton;
    EditText editTextSchedule;


    ArrayList<Replay> products = new ArrayList<Replay>();
    ListAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        fillData();
        boxAdapter = new ListAdapter(this, products);

        ListView lvMain = (ListView) findViewById(R.id.listViewSchedule);
        lvMain.setAdapter(boxAdapter);

        /*
        Cursor cursor = getContentResolver().query(Replay_Info.CONTENT_URI, null,
                null, null, Replay_Info._ID + " ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String[] columns = new String[] {
                Replay_Info.APPNAME,
                Replay_Info.SENSOR,
                Replay_Info.STATUS
        };

        //UI binding
        int[] to = new int[] {
                R.id.tvSI1,
                R.id.tvSI2,
                R.id.tvSI3,
        };

        dataAdapterData = new SimpleCursorAdapter(this, R.layout.schedule_item, cursor, columns, to, 0);

        ListView listView = (ListView) findViewById(R.id.listViewSchedule);
        listView.setAdapter(dataAdapterData);
        */

        editTextSchedule = (EditText) findViewById(R.id.editTextSchedule);

        startButton = (Button) findViewById(R.id.buttonSchedule1);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String result = "Starting :";
                ArrayList<String> sensorList = new ArrayList<String>();

                for (Replay p : boxAdapter.getBox()) {
                    if (p.box){
                        result += "\n" + p.appName + ", " + p.sensor;
                        sensorList.add(p.sensor);
                    }
                }
                Toast.makeText(getApplicationContext(), result+"\n", Toast.LENGTH_LONG).show();

                //replay data
                //find speed   editTextSchedule
                Double speed = Double.parseDouble(editTextSchedule.getText().toString());

                /*Intent regIntent = new Intent(ScheduleReplay.this, AllSensorDataSending.class);
                regIntent.putExtra("speed", speed);
                regIntent.putExtra("sensorList", sensorList);
                startService(regIntent);
                */
                //for testing!
                sensorList = new ArrayList<String>();
                sensorList.add("Accelerometer");
                sensorList.add("Light");
                Intent context_unlock = new Intent();
                context_unlock.setAction("ACTION_TESTAWARE_START");
                context_unlock.putExtra("speed", speed);
                context_unlock.putExtra("sensorList", sensorList);
                sendBroadcast(context_unlock);
            }
        });

        stopButton = (Button) findViewById(R.id.buttonSchedule2);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> sensorList = new ArrayList<String>();

                String result = "Stopping :";
                for (Replay p : boxAdapter.getBox()) {
                    if (p.box){
                        result += "\n" + p.appName + ", " + p.sensor;
                        sensorList.add(p.sensor);
                    }
                }
                Toast.makeText(getApplicationContext(), result+"\n", Toast.LENGTH_LONG).show();
                //send an Intent
                //to stop
                Intent context_unlock = new Intent();
                context_unlock.setAction("ACTION_TESTAWARE_STOP");
                context_unlock.putExtra("sensorList", sensorList);
                sendBroadcast(context_unlock);
            }
        });
    }

    void fillData() {
        Cursor cursor = getContentResolver().query(Replay_Info.CONTENT_URI, null,
                null, null, Replay_Info._ID + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Replay replay_item = new Replay();
                replay_item.setAppName(cursor.getString(cursor.getColumnIndex(Replay_Info.APPNAME)));
                replay_item.setSensor(cursor.getString(cursor.getColumnIndex(Replay_Info.SENSOR)));
                replay_item.setStatus(cursor.getString(cursor.getColumnIndex(Replay_Info.STATUS)));
                replay_item.setBox(false);
                products.add(replay_item);
            }
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
    }
    //go with this
}
