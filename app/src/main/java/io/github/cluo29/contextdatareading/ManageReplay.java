package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 29/09/16.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ManageReplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managereplay);


        Button goCreate = (Button) findViewById(R.id.buttonManageReplay1);
        Button goView = (Button) findViewById(R.id.buttonManageReplay2);

        goCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateReplay.class);
                startActivity(i);
                finish();

            }
        });

        goView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewReplay.class);
                startActivity(i);
                finish();

            }
        });

    }



}
