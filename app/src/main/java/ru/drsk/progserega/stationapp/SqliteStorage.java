package ru.drsk.progserega.stationapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

/**
 * Created by serega on 31.01.17.
 */

public class SqliteStorage {

    private Context context = null;
    private StationDbHelper dbHelper = null;
    private SQLiteDatabase db;

    public SqliteStorage(Context applicationcontext) {
        context = applicationcontext;
        dbHelper = new StationDbHelper(context);
    }
    public List<String> getSp()
    {
        Cursor cur;
        List<Long> ids = new ArrayList<Long>();
        List<String> sp = new ArrayList<String>();

        try
        {
            cur = db.query("sp_tbl", new String[]{"id", "name"},null,null,null,null,null);
            while(cur.moveToNext()) {
                long id=cur.getLong(cur.getColumnIndexOrThrow("id"));
                String name=cur.getString(cur.getColumnIndexOrThrow("name"));
                Log.i("getSP()", "get SP from db: " + name);
                ids.add(id);
                sp.add(name);
            }
            cur.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return sp;
    }
    /*
    public List<String> getRes(Long sp_id)
    {
        Cursor cur;
        List<Long> ids = new ArrayList<Long>();
        List<String> res = new ArrayList<String>();

        try
        {
            cur = db.query("res_tbl", new String[]{"id", "name"},null,null,null,null,null);
            while(cur.moveToNext()) {
                long id=cur.getLong(cur.getColumnIndexOrThrow("id"));
                String name=cur.getString(cur.getColumnIndexOrThrow("name"));
                Log.i("getSP()", "get SP from db: " + name);
                ids.add(id);
                sp.add(name);
            }
            cur.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return sp;
    }
*/
    public boolean init_db()
    {
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
        // TODO if empty db:
        if (true)
        {
            if (!test_fill_db())
            {
                Log.e("init_db()", "error test_fill_db()");
            }
        }
        return true;
    }

    protected boolean test_fill_db()
    {
        // Заполняем СП:
        ContentValues values = new ContentValues();
        try
        {
            String[] sp= new String[]{"ЮЭС", "ЦЭС","СЭС","ЗЭС"};
            for(int index = 0; index< sp.length; index++)
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
            return false;
        }
        // Заполняем РЭС:
        values = new ContentValues();
        int res_index=0;
        try
        {
            String[] res= new String[]{"РЭС3", "РЭС5","РЭС6","РЭС7"};
            for(int index = 0; index< res.length; index++)
            {
                values.put("id", res_index);
                values.put("name", res[index]);
                values.put("sp_id", 1);


                long newRowId = db.insert("res_tbl", null, values);
                if (newRowId==-1)
                {
                    Log.e("test_fill_db()", "error insert row: " + values.toString());
                    return false;
                }
            }
            res = new String[]{"РЭС2", "РЭС1"};
            for(int index = 0; index< res.length; index++)
            {
                values.put("id", res_index);
                values.put("name", res[index]);
                values.put("sp_id", 2);


                long newRowId = db.insert("res_tbl", null, values);
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
            return false;
        }

        return true;
    }

}