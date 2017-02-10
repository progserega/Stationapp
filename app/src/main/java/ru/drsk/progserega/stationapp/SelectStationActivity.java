package ru.drsk.progserega.stationapp;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.app.Activity;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectStationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        SqliteStorage sqliteStorage = new SqliteStorage(getApplicationContext());
        if (!sqliteStorage.init_db())
        {
            Log.e("SelectStationActivity()", "init_db() error");
        }

        // заполнение списка СП:
        List<String> sp=sqliteStorage.getAllSp();
        if(sp==null)
        {
            Log.e("SelectStationActivity()", "sqliteStorage.getAllSp() error");
            return;
        }
        Spinner sp_spinner = (Spinner) findViewById(R.id.sp_selector);
        // выставляем оформление и содержимое:
        ArrayAdapter<String> sp_adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, sp);
        sp_spinner.setAdapter(sp_adapter);

        Spinner res_spinner = (Spinner) findViewById(R.id.res_selector);
        Spinner station_spinner = (Spinner) findViewById(R.id.station_selector);
        // прописываем обработчик:
        SpinnerActivity spinnerActivity = new SpinnerActivity(this, sqliteStorage);

        sp_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) spinnerActivity);
        res_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) spinnerActivity);
        station_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) spinnerActivity);

    }

    @Override
    protected void onStart() {
        // предварительная инициализация приложения


        super.onStart();

    }

}

