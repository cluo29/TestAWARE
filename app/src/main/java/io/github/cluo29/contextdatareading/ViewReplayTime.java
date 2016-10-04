package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 03/10/16.
 */
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


import io.github.cluo29.contextdatareading.providers.TimeResult_Provider.Time_Result;

public class ViewReplayTime extends AppCompatActivity {


    //textTime4  avg
    TextView average;
    //textTime5  longest
    TextView longest;
    //ms per execution

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        average = (TextView) findViewById(R.id.textTime4);
        longest = (TextView) findViewById(R.id.textTime5);


        //test data from targeted app

        long max=0L;
        double sum=0d;
        long count=0;
        //compute
        Cursor cursor = getContentResolver().query(Time_Result.CONTENT_URI, null, null, null, null);


        if (cursor != null) {
            while (cursor.moveToNext()) {

                long timeRow = cursor.getLong(cursor.getColumnIndex(Time_Result.TIME));
                sum = sum + timeRow;
                count=count+1;
                if(timeRow>max)
                {
                    max = timeRow;
                }
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        double avg = sum/count;

        avg = avg/1000;//in us, microsecond

        long avgLong = Math.round(avg);

        sum = sum/1000;//in us, microsecond

        long sumLong = Math.round(sum);

        average.setText(avgLong+ "μs per execution");

        longest.setText(sumLong+ "μs per execution");

    }


}