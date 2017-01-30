package ru.drsk.progserega.stationapp;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.util.Log;

public class SelectStationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init_db();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);
    }

    @Override
    protected void onStart() {
        // предварительная инициализация приложения


        super.onStart();

    }
    protected void init_db()
    {
        // TODO if empty db:
        if (true)
        {
            if (!test_fill_db())
            {
                Log.e("init_db()", "error test_fill_db()");
            }
        }
    }
    protected boolean test_fill_db()
    {
        StationDbHelper dbHelper = new StationDbHelper(getApplicationContext());

        SQLiteDatabase db;
        // Gets the data repository in write mode
        try
        {
            db = dbHelper.getWritableDatabase();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        // Заполняем СП:
        ContentValues values = new ContentValues();
        try
        {
            String[] sp= new String[]{"ЮЭС", "ЦЭС","СЭС","ЗЭС"};
            for(int index = 1; index< sp.length; index++)
            {
                values.put("id", index);
                values.put("name", sp[index]);

                long newRowId = db.insert("sp_tbl", null, values);
                if (newRowId==-1)
                {
                    Log.e("test_fill_db()", "error insert row: " + values.toString());
                    return false;
                }
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}

