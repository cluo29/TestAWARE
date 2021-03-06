package io.github.cluo29.contextdatareading;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import io.github.cluo29.contextdatareading.providers.PowerModel_Provider;

/**
 * Created by Comet on 01/10/16.
 */
public class ViewReplay extends AppCompatActivity {

    //spinnerVRR

    Spinner spinnerSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreplay);

        spinnerSensor = (Spinner) findViewById(R.id.spinnerVRR);
        loadSpinnerDataSensor();
        //power
        Button download = (Button) findViewById(R.id.buttonVRR2);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewReplayPowerEstimation.class);
                startActivity(i);
            }
        });


        Button download2 = (Button) findViewById(R.id.buttonVRR3);

        download2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewReplayTime.class);
                startActivity(i);
            }
        });

        Button download3 = (Button) findViewById(R.id.buttonVRR1);

        download3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewReplayMachineLearning.class);
                startActivity(i);
            }
        });

        //time



    }

    private void loadSpinnerDataSensor()
    {
        List<String> labels = new ArrayList<String>();

                    labels.add("Replay1");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSensor.setAdapter(dataAdapter);
    }
}

//ml result
//power
//processing time