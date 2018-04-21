package com.res.easyorder;

/**
 * Created by User on 02-Apr-18.
 */

public class item {

    String foodName, quantity;
    int foodImage, foodPrice;

    public item(String foodName, int foodImage, int foodPrice, String quantity)
    {
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodPrice = foodPrice;
        this.quantity = quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getFoodName()
    {
        return foodName;
    }

    public int getFoodImage()
    {
        return foodImage;
    }

    public int getFoodPrice()
    {
        return foodPrice;
    }

    public String getQuantity()
    {
        return quantity;
    }

}
