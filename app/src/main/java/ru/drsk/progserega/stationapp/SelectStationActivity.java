package ru.drsk.progserega.stationapp;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectStationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        SqliteStorage sqliteStorage = new SqliteStorage(getApplicationContext());
        if (!sqliteStorage.init_db())
        {
            Log.e("init_db()", "error");
        }

        List<String> sp=sqliteStorage.getSp();

        Spinner s = (Spinner) findViewById(R.id.sp_selector);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
  //              android.R.layout.simple_spinner_item, sp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, sp);
        s.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        // предварительная инициализация приложения


        super.onStart();

    }

}

