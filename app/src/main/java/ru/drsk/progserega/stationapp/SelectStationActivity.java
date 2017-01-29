package ru.drsk.progserega.stationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

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
        String db_name = "stations.sqlite";
        File db;


        try {
            db = new File(getFilesDir(), db_name);
  /*
  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
  outputStream.write(string.getBytes());
  outputStream.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class StationsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stations.db";


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String sql;
        // Таблица подстанций:
        sql = "CREATE TABLE station_tbl (" +
                "id integer PRIMARY KEY NOT NULL," +
                "name VARCHAR(255)," +
                "uniq_id integer," +
                "sp_id integer," +
                "res_id integer," +
                "np_id integer);";
        db.execSQL(sql);
        // Таблица СП:
        sql = "CREATE TABLE sp_tbl ("+
        "id integer PRIMARY KEY NOT NULL, "+
        "name VARCHAR(255) "+
        ");";
        db.execSQL(sql);
        // Таблица РЭС-ов:
        sql = "CREATE TABLE res_tbl ("+
        "id integer PRIMARY KEY NOT NULL, "+
        "name VARCHAR(255), "+
        "sp_id integer"+
        ");";
        db.execSQL(sql);
        // Населённые пункты
        sql = "CREATE TABLE np_tbl ("+
        "id integer PRIMARY KEY NOT NULL, "+
        "name VARCHAR(255) "+
        ");";
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        String sql;
        sql="drop table station_tbl;"
        db.execSQL(sql);
        sql="drop table sp_tbl;"
        db.execSQL(sql);
        sql="drop table res_tbl;"
        db.execSQL(sql);
        sql="drop table np_tbl;"
        db.execSQL(sql);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}