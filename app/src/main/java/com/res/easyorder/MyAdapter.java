package com.res.easyorder;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyAdapter extends ArrayAdapter<item> {

    String name;
    int food_quan, price;
    int q_ty = 0, index;

    ArrayList<item> foodlist = new ArrayList<>();

    public MyAdapter(Context context, int textViewResourceId, ArrayList<item> objects){

        super(context, textViewResourceId, objects);
        foodlist = objects;
    }

    @Override
    public int getCount(){

        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_layout, null);

        ImageView foodpic = (ImageView) v.findViewById(R.id.foodpic);
        TextView foodname = (TextView) v.findViewById(R.id.foodname);
        final TextView foodprice = (TextView) v.findViewById(R.id.foodprice);
        Button plus = (Button) v.findViewById(R.id.plus);
        final EditText quantity = (EditText) v.findViewById(R.id.quantity);
        Button minus = (Button) v.findViewById(R.id.minus);
        final Button addtocart = (Button) v.findViewById(R.id.addtocart);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item item = getItem(position);
                q_ty = Integer.parseInt(item.getQuantity());
                q_ty = q_ty + 1;
                item.setQuantity(String.valueOf(q_ty));
                quantity.setText(item.getQuantity());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item item = getItem(position);
                q_ty = Integer.parseInt(item.getQuantity());

                if (q_ty > 0) {
                    q_ty = q_ty - 1;
                    item.setQuantity(String.valueOf(q_ty));
                    quantity.setText(item.getQuantity());
                }
            }
        });

        /*quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                {
                    item item = getItem(position);
                    q_ty = Integer.parseInt(quantity.getText().toString());
                    item.setQuantity(String.valueOf(q_ty));
                    quantity.setText("" +q_ty);
                }
            }
        });*/

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item item = getItem(position);
                foodActivity f = new foodActivity(); //declaring object of foodActivity class

                food_quan = Integer.parseInt(item.getQuantity());

                if (food_quan > 0){

                    name = item.getFoodName().toString();
                    price = item.getFoodPrice();
                    price = food_quan * price;

                    //accessing arraylist from foodActivity class
                    if (f.fname.contains(name))
                    {
                        index = f.fname.indexOf(name);

                        f.total_ammount = f.total_ammount - f.fprice.get(index) + price;

                        f.fname.remove(index);
                        f.fqnty.remove(index);
                        f.fprice.remove(index);

                        f.fname.add(name);
                        f.fqnty.add(food_quan);
                        f.fprice.add(price);

                        Toast.makeText(getContext(),name+ " updated in cart!",Toast.LENGTH_SHORT).show();
                    }

                    else if (!f.fname.contains(name))
                    {
                        f.fname.add(name);
                        f.fqnty.add(food_quan);
                        f.fprice.add(price);
                        f.total_ammount = f.total_ammount + price;
                        Toast.makeText(getContext(),name+ " added to cart!",Toast.LENGTH_SHORT).show();
                    }

                }

                else if (food_quan == 0){

                    name = item.getFoodName().toString();

                    if (f.fname.contains(name))
                    {
                        index = f.fname.indexOf(name);

                        f.total_ammount = f.total_ammount - f.fprice.get(index);

                        f.fname.remove(index);
                        f.fqnty.remove(index);
                        f.fprice.remove(index);

                        Toast.makeText(getContext(),name+ " removed from cart!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        foodname.setText(foodlist.get(position).getFoodName());
        foodpic.setImageResource(foodlist.get(position).getFoodImage());
        foodprice.setText("BDT: " +foodlist.get(position).getFoodPrice());
        quantity.setText(foodlist.get(position).getQuantity());

        return v;
    }
}
