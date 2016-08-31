package io.github.cluo29.contextdatareading;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import io.github.cluo29.contextdatareading.providers.Dataset_Provider.Dataset_Info;

/**
 * Created by Comet on 13/08/16.
 */
public class DownloadData extends AppCompatActivity{

    EditText AppName;
    EditText SourceName;
    EditText Host;
    EditText Port;
    EditText User;
    EditText Password;
    EditText DBName;
    EditText StartTimestamp;
    EditText DeviceID;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        AppName = (EditText) findViewById(R.id.editText);
        SourceName = (EditText) findViewById(R.id.editText2);
        Host = (EditText) findViewById(R.id.editText3);
        Port = (EditText) findViewById(R.id.editText4);
        User = (EditText) findViewById(R.id.editText5);
        Password = (EditText) findViewById(R.id.editText6);
        DBName = (EditText) findViewById(R.id.editText7);
        StartTimestamp = (EditText) findViewById(R.id.editText8);
        DeviceID = (EditText) findViewById(R.id.editText9);

        saveBtn = (Button) this.findViewById(R.id.button3);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                ContentValues rowData = new ContentValues();
                rowData.put(Dataset_Info.TIMESTAMP, System.currentTimeMillis());
                rowData.put(Dataset_Info.APPNAME, AppName.getText().toString());
                rowData.put(Dataset_Info.SENSOR, SourceName.getText().toString());
                rowData.put(Dataset_Info.HOST, Host.getText().toString());
                rowData.put(Dataset_Info.PORT, Integer.parseInt(Port.getText().toString()));
                rowData.put(Dataset_Info.USER, User.getText().toString());
                rowData.put(Dataset_Info.PASSWORD, Password.getText().toString());
                rowData.put(Dataset_Info.DATABASE, DBName.getText().toString());
                rowData.put(Dataset_Info.STARTTIMESTAMP, Long.parseLong(StartTimestamp.getText().toString()));
                rowData.put(Dataset_Info.DEVICE, DeviceID.getText().toString());
                getApplicationContext().getContentResolver().insert(Dataset_Info.CONTENT_URI, rowData);
*/

                //start service to download

                //back to main menu
                finish();
            }
        });
    }
}
