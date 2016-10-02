package io.github.cluo29.contextdatareading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Comet on 01/10/16.
 */
public class ViewReplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreplay);


        //power
        Button download = (Button) findViewById(R.id.buttonVRR2);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewReplayPowerEstimation.class);
                startActivity(i);
            }
        });

    }

}

//ml result
//power
//processing time