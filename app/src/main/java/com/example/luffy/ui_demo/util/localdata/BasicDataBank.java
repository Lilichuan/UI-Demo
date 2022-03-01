package com.example.luffy.ui_demo.util.localdata;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.luffy.ui_demo.BuildConfig;

public class BasicDataBank {
    protected Context mContext;
    protected SQLiteDatabase db;
    protected SQLiteOpenHelper mHelper;

    public BasicDataBank(Context context, SQLiteOpenHelper dbHelper){
        mContext = context;
        mHelper = dbHelper;
    }

    protected void removeData(String tableName, String where, String[] whereArgs){
        getDb().delete(tableName, where, whereArgs);
    }

    /**
     * 放入一筆資料
     *
     * @param tableName
     * 希望放入的sql表格名稱
     *
     * @param values
     * 放入的資料
     *
     * @return
     * 是否執行成功
     */
    protected boolean putOneData(String tableName, ContentValues values){
        long c = getDb().insert(tableName, null, values);
        boolean result = c > 0;
        if(BuildConfig.DEBUG && !result){
            Log.e("BasicDataBank", "insert table「"+tableName +"」 failed.");
        }
        return result;
    }

    protected void updateData(String tableName, ContentValues values, String where){
        updateData(tableName, values ,where, null);
    }

    protected void updateData(String tableName, ContentValues values, String where, String[] whereArgs){
        int c = getDb().update(tableName, values ,where, whereArgs);
        boolean result = c > 0;
        if(BuildConfig.DEBUG && !result){
            Log.e("BasicDataBank", "update table「"+tableName +"」 failed.");
        }
    }

    /**
     * 檢查一張表裡面
     * 是否有指定條件的資料存在
     *
     * @param tableName
     * 表格名稱
     *
     * @param selection
     * 條件
     *
     * @return
     * 搜尋結果
     *
     */
    protected boolean haveData(String tableName, String selection, String[] whereArgs){
        Cursor cursor = query(tableName, null,selection, whereArgs);

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    /**
     * 初始化一個資料庫物件
     *
     * @return
     * 資料庫
     */
    protected SQLiteDatabase getDb(){
        if(db == null || !db.isOpen()){
            db = mHelper.getWritableDatabase();
        }
        return db;
    }

    protected Cursor query(String tableName, String[] columns, String where, String[] whereArg){
        return getDb().query(tableName, columns, where, whereArg,
                null , null, null, null);
    }

    public int getMaxId(String tableName, String idColumn){
        Cursor cursor = getDb().query(tableName, null, null, null,
                null , null, idColumn + DbNameSpace.ORDER_BY_DESC, null);

        if(cursor.getCount() < 1){
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();
        @SuppressLint("Range")
        int id = cursor.getInt(cursor.getColumnIndex(idColumn));
        cursor.close();
        return id;
    }

    @Override
    protected void finalize() throws Throwable {
        closeDB();
        super.finalize();
    }

    public void closeDB(){
        if(db != null && db.isOpen()){
            db.close();
            db = null;
        }
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 清空該表的所有資料
     *
     * @param table_name
     * 表格名稱
     */
    protected void deleteAll(String table_name){
        getDb().delete(table_name, null, null);
    }

    public String[] getWhereParam(String s){
        return new String[]{s};
    }

    /**
     * String[] param = getWhereParam(bean.getId() + "");
        updateData(TableName.EXTRA_MEAL_LIST, values, where, param);
     * @param s1
     * @param s2
     * @return
     */
    public String[] getWhereParam(String s1, String s2){
        return new String[]{s1, s2};
    }

}
