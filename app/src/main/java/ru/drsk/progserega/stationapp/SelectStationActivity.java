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

        List<String> sp=sqliteStorage.getAllSp();
        if (sp==null)
        {
            Log.e("onCreate()", "error sqliteStorage.getAllSp()");
        }

        Spinner sp_spinner = (Spinner) findViewById(R.id.sp_selector);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
  //              android.R.layout.simple_spinner_item, sp);
        ArrayAdapter<String> sp_adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, sp);
        sp_spinner.setAdapter(sp_adapter);

        // Выбранная СП:
        String sp_name=sp_spinner.getSelectedView().toString();
        // Заполняем список РЭС-ов:
        List<String> res=sqliteStorage.getAllResOfSp(sp_name);
        if (sp==null)
        {
            Log.e("onCreate()", "error sqliteStorage.getAllResOfSp()");
        }
        Spinner res_spinner = (Spinner) findViewById(R.id.res_selector);
        ArrayAdapter<String> res_adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, res);
        res_spinner.setAdapter(res_adapter);

        // Выбранная РЭС:
        String res_name=res_spinner.getSelectedView().toString();
        // Заполняем список РЭС-ов:
        List<String> stations=sqliteStorage.getAllStationsOfRes(sp_name,res_name);
        if (stations==null)
        {
            Log.e("onCreate()", "error sqliteStorage.getAllStationsOfRes()");
        }
        Spinner stations_spinner = (Spinner) findViewById(R.id.station_selector);
        ArrayAdapter<String> stations_adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, stations);
        stations_spinner.setAdapter(stations_adapter);

    }

    @Override
    protected void onStart() {
        // предварительная инициализация приложения


        super.onStart();

    }

}

