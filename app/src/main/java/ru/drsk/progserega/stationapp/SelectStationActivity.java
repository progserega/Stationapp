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

        // заполнение списка СП:
        List<String> sp=sqliteStorage.getAllSp();
        Spinner sp_spinner = (Spinner) findViewById(R.id.sp_selector);
        ArrayAdapter<String> sp_adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, sp);
        sp_spinner.setAdapter(sp_adapter);

        // заполнение списка РЭС:
        String current_sp_text = sp_spinner.getSelectedItem().toString();
        Log.i("SelectStationActivity()", "current selected SP:" + current_sp_text);
        List<String> res=sqliteStorage.getAllResBySpName(current_sp_text);
        Log.i("SelectStationActivity()", "size of list res: " + res.size());

        Spinner res_spinner = (Spinner) findViewById(R.id.res_selector);
        ArrayAdapter<String> res_adapter = new ArrayAdapter<String>(this,
                R.layout.one_row, R.id.text, res);
        res_spinner.setAdapter(res_adapter);


    }

    @Override
    protected void onStart() {
        // предварительная инициализация приложения


        super.onStart();

    }

}

