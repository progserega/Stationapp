package ru.drsk.progserega.stationapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private SelectStationActivity StationActivity;
    private SqliteStorage sqliteStorage;

    public SpinnerActivity(SelectStationActivity selectStationActivity, SqliteStorage sqlStorage) {
        StationActivity = selectStationActivity;
        sqliteStorage = sqlStorage;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Log.d("onItemSelected()", "text: " + parent.getItemAtPosition(pos).toString());
        Spinner sp_spinner = (Spinner) StationActivity.findViewById(R.id.sp_selector);
        Spinner res_spinner = (Spinner) StationActivity.findViewById(R.id.res_selector);
        Spinner station_spinner = (Spinner) StationActivity.findViewById(R.id.station_selector);
        Log.d("onItemSelected()", "text: " + parent.getItemAtPosition(pos).toString());
        if (parent.equals(sp_spinner))
        {
            Log.i("onItemSelected()", "sp_selector: " + parent.getItemAtPosition(pos).toString());

            // заполнение списка РЭС:
            String current_sp_text = sp_spinner.getSelectedItem().toString();
            Log.d("onItemSelected()", "current selected SP:" + current_sp_text);

            List<String> res=sqliteStorage.getAllResBySpName(current_sp_text);
            if(res==null)
            {
                Log.e("onItemSelected()", "sqliteStorage.getAllResBySpName() error");
                res = new ArrayList<String>();
            }
            Log.i("onItemSelected()", "size of list res: " + res.size());

            ArrayAdapter<String> res_adapter = new ArrayAdapter<String>(StationActivity,
                    R.layout.one_row, R.id.text, res);
            res_spinner.setAdapter(res_adapter);
        }
        else if (parent.equals(res_spinner)) {
            Log.i("onItemSelected()", "res_selector: " + parent.getItemAtPosition(pos).toString());

            // заполнение списка подстанций:
            String current_sp_text = sp_spinner.getSelectedItem().toString();
            Log.d("OnItemSelected()", "current selected SP:" + current_sp_text);
            String current_res_text = res_spinner.getSelectedItem().toString();
            Log.d("OnItemSelected()", "current selected SP:" + current_res_text);

            List<String> stations = sqliteStorage.getAllStationByResName(current_sp_text, current_res_text);
            if (stations == null) {
                Log.e("onItemSelected()", "sqliteStorage.getAllStationByResName() error");
                stations = new ArrayList<String>();
            }
            Log.d("onItemSelected()", "size of list res: " + stations.size());

            ArrayAdapter<String> res_adapter = new ArrayAdapter<String>(StationActivity,
                    R.layout.one_row, R.id.text, stations);
            station_spinner.setAdapter(res_adapter);

        } else if (parent.equals(station_spinner)) {
            Log.i("onItemSelected()", "station_sp_spinner: " + parent.getItemAtPosition(pos).toString());
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Log.i("onNothingSelected()", "Nothing");
    }
}
