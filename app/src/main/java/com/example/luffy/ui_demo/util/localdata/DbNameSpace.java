package com.example.luffy.ui_demo.util.localdata;

import android.provider.BaseColumns;

/**
 * Created by tim
 */

public abstract class DbNameSpace implements BaseColumns {

    public static final String DB_NAME = "";
    public static final int DB_VERSION = 1;

    public static String SPLIT = ",";

    protected static final String TYPE_TEXT = " TEXT NOT NULL, ";
    protected static final String TYPE_TEXT_NULLABLE = " TEXT, ";
    protected static final String TYPE_INT = " INTEGER NOT NULL, ";
    protected static final String TYPE_INT_NULLABLE = " INTEGER, ";
    protected static final String TYPE_MONEY = " REAL NOT NULL, ";
    protected static final String TYPE_MONEY_NULLABLE = " REAL, ";

    public static final String ORDER_BY_ASC = " ASC";//由小到大
    public static final String ORDER_BY_DESC = " DESC";//由大到小
    public static final String LIKE_Q = " LIKE ?";
    public static final String IS_Q = "=?";

    protected String createString;
    protected String table_name;

    public void DbNameSpace(String tableName) {
        this.table_name = tableName;
    }

    public String getCreateTableString(){
        return createString;
    }

    public String getTableName() {
        return table_name;
    }

    public String removeTable(){
        return "DROP TABLE IF EXISTS "+ table_name;
    }

    protected String endText(String s){
        return s.replace(", "  ,  ")");
    }

    protected String startText(){
        return "CREATE TABLE " + table_name + " (" + _ID + " INTEGER PRIMARY KEY NOT NULL, ";
    }

    protected String getAddColumnStr(String columnName, String columnType){
        return "ALTER TABLE "+ table_name +" ADD COLUMN "+columnName + columnType;
    }

    abstract String getCreateStr();
}
