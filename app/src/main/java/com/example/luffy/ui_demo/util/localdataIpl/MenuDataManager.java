package com.example.luffy.ui_demo.util.localdataIpl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;


import com.example.luffy.ui_demo.util.localdata.BasicDataBank;

import java.util.ArrayList;
import java.util.List;

public class MenuDataManager extends BasicDataBank<MealItem> {

    public MenuDataManager(Context context){
        super(new MySQLiteOpenHelper(context));
    }

    @SuppressLint("Range")
    @Override
    public MealItem toData(Cursor cursor) {
        MealItem mealItem = new MealItem();
        mealItem.setSqlId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        mealItem.setPrice(cursor.getString(cursor.getColumnIndex(MealItem.COLUMN_MEAL_PRICE)));
        mealItem.setName(cursor.getString(cursor.getColumnIndex(MealItem.COLUMN_MEAL_NAME)));
        return mealItem;
    }

    @Override
    public ContentValues toContentValues(MealItem data) {
        ContentValues cv = new ContentValues();
        cv.put(MealItem.COLUMN_MEAL_NAME, data.getName());
        cv.put(MealItem.COLUMN_MEAL_PRICE, data.getPrice().toString());
        return cv;
    }

    public void addMeal(MealItem item){
        if(!item.dataIsOk()){
            return;
        }
        ContentValues cv = toContentValues(item);
        putOneData(MenuNameSpace.TABLE_NAME_MENU, cv);
    }

    public List<MealItem> getAllMeal(){
        List<MealItem> list = new ArrayList<>();
        Cursor cursor = getAll(MenuNameSpace.TABLE_NAME_MENU);
        while (cursor.moveToNext()){
            list.add(toData(cursor));
        }
        return list;
    }
}
