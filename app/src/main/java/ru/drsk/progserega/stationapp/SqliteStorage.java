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
    public List<String> getAllSp()
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
    public Long getSpIdbyName(String sp_name)
    {
        Cursor cur;
        Long sp_id = null;
        String selection = "name = ?";
        String[] selectionArgs = {sp_name};

        try
        {
            cur = db.query("sp_tbl", new String[]{"id"},selection,selectionArgs,null,null,null);
            while(cur.moveToNext()) {
                sp_id=cur.getLong(cur.getColumnIndexOrThrow("id"));
                Log.i("getSP()", "get SP id from db: " + sp_id);
            }
            cur.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return sp_id;
    }

    public List<String> getAllResBySpName(String sp_name)
    {
        Long sp_id=getSpIdbyName(sp_name);
        return getResBySpId(sp_id);
    }

    public List<String> getResBySpId(Long sp_id)
    {
        Cursor cur;
        List<Long> ids = new ArrayList<Long>();
        List<String> res = new ArrayList<String>();

        String selection = "sp_id = ?";
        String[] selectionArgs = {String.valueOf(sp_id)};

        try
        {
            cur = db.query("res_tbl", new String[]{"id", "name"},selection,selectionArgs,null,null,null);
            while(cur.moveToNext()) {
                long id=cur.getLong(cur.getColumnIndexOrThrow("id"));
                String name=cur.getString(cur.getColumnIndexOrThrow("name"));
                Log.i("getRes()", "get Res from db by SP id: " + name);
                ids.add(id);
                res.add(name);
            }
            cur.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return res;
    }

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
