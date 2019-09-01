package com.res.easyorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.res.easyorder.foodActivity.fname;

/**
 * Created by User on 20-Apr-18.
 */

public class CartAdapter extends ArrayAdapter<cartitem> {

    ArrayList<cartitem> orderlist = new ArrayList<>();
    public String foodname;
    public int index;

    public CartAdapter(Context context, int textViewResourceId, ArrayList<cartitem> objects){

        super(context, textViewResourceId, objects);
        orderlist = objects;
    }

    @Override
    public int getCount(){

        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.cart_view_layout,null);

        final TextView name = (TextView) v.findViewById(R.id.nameoffood);
        TextView quantity = (TextView) v.findViewById(R.id.quantityoffood);
        TextView price = (TextView) v.findViewById(R.id.priceoffood);
        Button delete = (Button) v.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartitem cartitem = getItem(position);

                foodname = cartitem.getFoodname();

                Intent intent = new Intent(getContext(),CartActivity.class);
                Bundle b = new Bundle();
                foodActivity f = new foodActivity();

                index = f.fname.indexOf(foodname);
                f.total_ammount = f.total_ammount - f.fprice.get(index);
                f.fname.remove(index);
                f.fqnty.remove(index);
                f.fprice.remove(index);

                b.putStringArrayList("name", f.fname);
                b.putIntegerArrayList("qnty", f.fqnty);
                b.putIntegerArrayList("price", f.fprice);
                b.putInt("total", f.total_ammount);

                intent.putExtras(b);

                view.getContext().startActivity(intent);
                ((CartActivity)getContext()).finish();

            }
        });

        name.setText(orderlist.get(position).getFoodname());
        quantity.setText("Quantity: " +orderlist.get(position).getQuantity());
        price.setText("BDT: " +orderlist.get(position).getPrice());

        return v;
    }
}
