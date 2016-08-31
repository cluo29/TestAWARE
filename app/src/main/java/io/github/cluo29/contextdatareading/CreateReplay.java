package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 13/08/16.
 */
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.github.cluo29.contextdatareading.providers.Dataset_Provider.Dataset_Info;
import io.github.cluo29.contextdatareading.providers.Replay_Provider.Replay_Info;

public class CreateReplay extends AppCompatActivity{

    Spinner spinnerApp;
    Spinner spinnerSensor;
    Button saveButton;
    EditText startTimestamp; //Long.parseLong(startTimestamp.getText().toString()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //show app name
        spinnerApp = (Spinner) findViewById(R.id.spinner);
        spinnerSensor = (Spinner) findViewById(R.id.spinner2);
        saveButton = (Button) findViewById(R.id.buttonCreate1);
        startTimestamp = (EditText) findViewById(R.id.editTextCreate1);
        // Loading spinner data from database
        loadSpinnerDataApp();
        //show sensor type
        loadSpinnerDataSensor();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textSpinnerApp = spinnerApp.getSelectedItem().toString();
                String textSpinnerSensor = spinnerSensor.getSelectedItem().toString();
                Long startTime = Long.parseLong(startTimestamp.getText().toString());

                /*
                ContentValues rowData = new ContentValues();
                rowData.put(Replay_Info.TIMESTAMP, System.currentTimeMillis());
                rowData.put(Replay_Info.APPNAME, textSpinnerApp);
                rowData.put(Replay_Info.SENSOR, textSpinnerSensor);
                rowData.put(Replay_Info.DATASET, "");
                rowData.put(Replay_Info.STARTTIMESTAMP, startTime);
                rowData.put(Replay_Info.SCHEDULE, 0);
                rowData.put(Replay_Info.STATUS, "pending");
                getApplicationContext().getContentResolver().insert(Replay_Info.CONTENT_URI, rowData);
                */


                //back to main menu
                finish();
            }
        });


    }

    private void loadSpinnerDataApp()
    {
        List<String> labels = new ArrayList<String>();
        Cursor cursor = getContentResolver().query(Dataset_Info.CONTENT_URI, null,
                null, null, Dataset_Info._ID + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                labels.add(cursor.getString(cursor.getColumnIndex(Dataset_Info.APPNAME)));
            }
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApp.setAdapter(dataAdapter);
    }

    private void loadSpinnerDataSensor()
    {
        List<String> labels = new ArrayList<String>();
        Cursor cursor = getContentResolver().query(Dataset_Info.CONTENT_URI, null,
                null, null, Dataset_Info._ID + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                labels.add(cursor.getString(cursor.getColumnIndex(Dataset_Info.SENSOR)));
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
