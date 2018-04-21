package com.res.easyorder;

/**
 * Created by User on 20-Apr-18.
 */

public class cartitem {

    String foodname;
    int quantity, price;

    public cartitem(String foodname, int quantity, int price)
    {
        this.foodname = foodname;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFoodname()
    {
        return foodname;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getPrice()
    {
        return price;
    }
}
