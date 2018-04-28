package com.example.luffy.ui_demo.util.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tim
 */

public class DbHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase database;

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DbNameSpace.DB_NAME, factory, DbNameSpace.DB_VERSION);
    }

    public static SQLiteDatabase getDatabase(Context context){
        if (database == null || !database.isOpen()) {
            database = new DbHelper(context, null).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
