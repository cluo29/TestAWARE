package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 04/10/16.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.github.cluo29.contextdatareading.providers.TestResult_Provider.Test_Result;

public class ViewReplayMachineLearning extends AppCompatActivity {

    LineChart ambient_chart;

    Spinner spinnerClasses;

    ArrayList<String> classes = new ArrayList<String> ();

    //int totalAccuracy;

    //for one class

    ArrayList<String> count = new ArrayList<String> ();
    ArrayList<Float> precision = new ArrayList<Float> ();
    ArrayList<Float> recall = new ArrayList<Float> ();
    ArrayList<Float> MSE = new ArrayList<Float> (); //if regression

    //count classes, accuracy, precision, recall, MSE, per class, and total
    String selectedClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmachinelearning);


        spinnerClasses = (Spinner) findViewById(R.id.spinnerML);
        loadSpinnerDataSensor();
        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here


                count = new ArrayList<String> ();
                precision = new ArrayList<Float> ();
                recall = new ArrayList<Float> ();
                MSE = new ArrayList<Float> (); //if regression


                selectedClass=spinnerClasses.getSelectedItem().toString();

                readDataBase(selectedClass);

                ambient_chart = (LineChart) findViewById(R.id.chart1);
                ViewGroup.LayoutParams params = ambient_chart.getLayoutParams();
                params.height = 200;
                ambient_chart.setLayoutParams(params);
                ambient_chart.setBackgroundColor(Color.WHITE);
                ambient_chart.setDrawGridBackground(true);
                ambient_chart.setDrawBorders(true);
                ambient_chart.getAxisRight().setEnabled(true);
                ambient_chart.getXAxis().setEnabled(true);

                ambient_chart.setDragEnabled(false);
                ambient_chart.setScaleEnabled(false);
                ambient_chart.setPinchZoom(false);
                ambient_chart.getLegend().setForm(Legend.LegendForm.LINE);
                ambient_chart.setDescription("");
                ambient_chart.setContentDescription("");

                YAxis left = ambient_chart.getAxisLeft();
                left.setDrawLabels(true);
                left.setDrawGridLines(true);
                left.setDrawAxisLine(true);

                left.setGranularity(0.1f);


                ArrayList<Entry> yValPrecision = new ArrayList<Entry>();

                for(int i=0; i < precision.size(); i++){
                    yValPrecision.add(new Entry(precision.get(i),i));
                    //Log.d("PLot",precision.get(i)+"");

                }

                ArrayList<Entry> yValRecall = new ArrayList<Entry>();

                for(int i=0; i < recall.size(); i++){
                    yValRecall.add(new Entry(recall.get(i),i));

                    //Log.d("PLot",recall.get(i)+"");

                }

                LineDataSet set, set2;

                set = new LineDataSet(yValPrecision, "Precision");

                set.setAxisDependency(YAxis.AxisDependency.LEFT);
                set.setColor(Color.GREEN);
                set.setHighLightColor(Color.GREEN);
                set.setDrawValues(false);
                //set.setValueTextSize(12f);
                set.setDrawCircles(false);
                set.setLabel("Precision");

                set2 = new LineDataSet(yValRecall, "Recall");

                set2.setAxisDependency(YAxis.AxisDependency.LEFT);
                set2.setColor(Color.BLUE);
                set2.setHighLightColor(Color.BLUE);
                set2.setDrawValues(false);
                //set2.setValueTextSize(12f);
                set2.setDrawCircles(false);
                set2.setLabel("Recall");

                ArrayList<ILineDataSet> lines = new ArrayList<ILineDataSet> ();
                lines.add(set); // add the datasets
                lines.add(set2);

                // create a data object with the datasets
                LineData data = new LineData(count,lines);




                ambient_chart.setData(data);
                ambient_chart.getData().notifyDataChanged();
                ambient_chart.notifyDataSetChanged();
                ambient_chart.setVisibleXRange(0f,0f+count.size());

                Legend l = ambient_chart.getLegend();

                // modify the legend ...
                l.setForm(Legend.LegendForm.LINE);
                l.setTextSize(15f);
                l.setTextColor(Color.BLACK);

                ambient_chart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public void readDataBase(String classQuery)
    {
        //classQuery indoor
        int tp=0;
        int fp=0;
        int fn=0;

        Cursor cursor = getContentResolver().query(Test_Result.CONTENT_URI, null, null, null, Test_Result.TIMESTAMP + " ASC");


        if (cursor != null) {
            while (cursor.moveToNext()) {

                String classLabel = cursor.getString(cursor.getColumnIndex(Test_Result.EXPECTATION)); //tp or fn, recall

                String classOutput = cursor.getString(cursor.getColumnIndex(Test_Result.OUTPUT)); //tp or fp, precision
                if (classQuery.equals(classLabel)) {
                    count.add("" + (count.size() + 1));
                    if (classLabel.equals(classOutput)) {
                        //indoor, indoor
                        tp = tp + 1;
                    } else {
                        //indoor, outdoor
                        fn = fn + 1;
                    }

                    if(tp+fp==0)
                    {
                        precision.add(0f);
                    }
                    else
                    {
                        precision.add(tp/(0f+tp+fp));
                    }
                    if(tp+fn==0)
                    {
                        recall.add(0f);
                    }
                    else
                    {
                        recall.add(tp/(0f+tp+fn));
                    }

                    continue;
                }
                if(classOutput.equals(classLabel))
                {
                    count.add("" + (count.size() + 1));
                    //xx, indoor
                    fp = fp+1;

                    if(tp+fp==0)
                    {
                        precision.add(0f);
                    }
                    else
                    {
                        precision.add(tp/(0f+tp+fp));
                    }
                    if(tp+fn==0)
                    {
                        recall.add(0f);
                    }
                    else
                    {
                        recall.add(tp/(0f+tp+fn));
                    }
                }
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        Log.d("Plot",count.size() +" in count");

        Log.d("Plot",precision.size() +" in precision");

        Log.d("Plot",recall.size() +" in recall");

    }



    private void loadSpinnerDataSensor()
    {
        List<String> labels = new ArrayList<String>();
        Cursor cursor = getContentResolver().query(Test_Result.CONTENT_URI, null,
                null, null, Test_Result._ID + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String sensorRow = cursor.getString(cursor.getColumnIndex(Test_Result.EXPECTATION));
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
        spinnerClasses.setAdapter(dataAdapter);
    }

}
