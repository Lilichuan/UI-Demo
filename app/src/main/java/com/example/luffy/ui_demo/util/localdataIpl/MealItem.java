package com.example.luffy.ui_demo.util.localdataIpl;

import android.icu.math.BigDecimal;
import android.text.TextUtils;

public class MealItem {

    public static final String COLUMN_MEAL_NAME = "name";
    public static final String COLUMN_MEAL_PRICE = "price";

    public int sqlId;   public String name;
    public BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setSqlId(int sqlId) {
        this.sqlId = sqlId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPrice(String price_str) {
        this.price = new BigDecimal(price_str);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSqlId() {
        return sqlId;
    }

    public boolean dataIsOk(){
        return price != null && !TextUtils.isEmpty(name);
    }
}
