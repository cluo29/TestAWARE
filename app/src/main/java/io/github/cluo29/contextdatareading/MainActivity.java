package io.github.cluo29.contextdatareading;

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

import io.github.cluo29.contextdatareading.providers.Dataset_Provider.Dataset_Info;
import io.github.cluo29.contextdatareading.providers.Replay_Provider.Replay_Info;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter dataAdapterData;
    SimpleCursorAdapter dataAdapterReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button download = (Button) findViewById(R.id.button);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DownloadData.class);
                startActivity(i);
            }
        });

        Button create = (Button) findViewById(R.id.button4);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateReplay.class);
                startActivity(i);
            }
        });

        Button schedule = (Button) findViewById(R.id.button2);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ScheduleReplay.class);
                startActivity(i);
            }
        });

        displayListViewDataset();
        displayListViewReplay();

        Log.d("UNLOCK", "GOING");

        //start data reading
        //startService(new Intent(MainActivity.this, BatteryDataReading.class));

        //read
        //startService(new Intent(MainActivity.this, AccelerationDataReading.class));

        //start the datasending service
        startService(new Intent(MainActivity.this, AllSensorDataSending.class));

        //use Dummy to create
        //startService(new Intent(MainActivity.this, LightDataReading.class));

        //startService(new Intent(MainActivity.this, AccelerationFakeDataReading.class));


        /*
        ContentValues rowData = new ContentValues();  //System.currentTimeMillis());  1470404312165L
        rowData.put(Dataset_Info.TIMESTAMP, System.currentTimeMillis());
        rowData.put(Dataset_Info.APPNAME, "Fall Detection"); //Accelerometer
        rowData.put(Dataset_Info.SENSOR, "Locations");
        rowData.put(Dataset_Info.HOST, "awareframework.com");
        rowData.put(Dataset_Info.PORT, 3306);
        rowData.put(Dataset_Info.USER, "Hater");
        rowData.put(Dataset_Info.PASSWORD, "Hater");
        rowData.put(Dataset_Info.DATABASE, "Hater");
        rowData.put(Dataset_Info.STARTTIMESTAMP, 1470404312165L);
        rowData.put(Dataset_Info.DEVICE, "d9a6bd96-5c8c-40fe-8acf-39ba083be22e");
        getApplicationContext().getContentResolver().insert(Dataset_Info.CONTENT_URI, rowData);
*/

        /*
        ContentValues rowData = new ContentValues();  //System.currentTimeMillis());  1470404312165L
        rowData.put(Dataset_Info.TIMESTAMP, System.currentTimeMillis());
        rowData.put(Dataset_Info.APPNAME, "App in Session");
        rowData.put(Dataset_Info.SENSOR, "Applications");
        rowData.put(Dataset_Info.HOST, "awareframework.com");
        rowData.put(Dataset_Info.PORT, 3306);
        rowData.put(Dataset_Info.USER, "Hater");
        rowData.put(Dataset_Info.PASSWORD, "Hater");
        rowData.put(Dataset_Info.DATABASE, "Hater");
        rowData.put(Dataset_Info.STARTTIMESTAMP, 1470404312165L);
        rowData.put(Dataset_Info.DEVICE, "d9a6bd96-5c8c-40fe-8acf-39ba083be22e");
        getApplicationContext().getContentResolver().insert(Dataset_Info.CONTENT_URI, rowData);
        */
        /*

                ContentValues rowData = new ContentValues();
                rowData.put(Replay_Info.TIMESTAMP, System.currentTimeMillis());
                rowData.put(Replay_Info.APPNAME, "Fall Detection");
                rowData.put(Replay_Info.SENSOR, "Locations");  //Locations  Accelerometer
                rowData.put(Replay_Info.DATASET, "");
                rowData.put(Replay_Info.STARTTIMESTAMP, 1470404312165L);
                rowData.put(Replay_Info.SCHEDULE, 0);
                rowData.put(Replay_Info.STATUS, "pending");
                getApplicationContext().getContentResolver().insert(Replay_Info.CONTENT_URI, rowData);
                */

    }

    public void displayListViewDataset() {
        Cursor cursor = getContentResolver().query(Dataset_Info.CONTENT_URI, null,
                null, null, Dataset_Info._ID + " ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String[] columns = new String[] {
                Dataset_Info.APPNAME,
                Dataset_Info.SENSOR
        };

        //UI binding
        int[] to = new int[] {
                R.id.textView_dataset3,
                R.id.textView_dataset4
        };

        dataAdapterData = new SimpleCursorAdapter(this, R.layout.dataset_item, cursor, columns, to, 0);

        ListView listView = (ListView) findViewById(R.id.family_container);
        listView.setAdapter(dataAdapterData);


        //family is dataset

        //event is replay
    }

    public void displayListViewReplay(){
        //dataAdapterReplay
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
                R.id.textView_replay4,
                R.id.textView_replay5,
                R.id.textView_replay6
        };

        dataAdapterReplay = new SimpleCursorAdapter(this, R.layout.replay_item, cursor, columns, to, 0);

        ListView listView = (ListView) findViewById(R.id.eventListView);
        listView.setAdapter(dataAdapterReplay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
