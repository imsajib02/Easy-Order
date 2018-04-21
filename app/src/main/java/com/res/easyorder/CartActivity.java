package com.res.easyorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<Integer> qntylist = new ArrayList<>();
    ArrayList<Integer> pricelist = new ArrayList<>();

    public int total_ammount = 0;
    private int length, i;
    private String name;
    private int quantity, price;

    TextView total;
    ListView simplelist;
    Button order;

    ArrayList<cartitem> orderlist = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        total = (TextView) findViewById(R.id.total);
        order = (Button) findViewById(R.id.order);
        simplelist = (ListView) findViewById(R.id.cartlist);

        namelist = getIntent().getExtras().getStringArrayList("name");
        qntylist = getIntent().getExtras().getIntegerArrayList("qnty");
        pricelist = getIntent().getExtras().getIntegerArrayList("price");
        total_ammount = getIntent().getExtras().getInt("total");

        length = namelist.size();

        for (i=0; i<length; i++)
        {
            name = namelist.get(i);
            quantity = qntylist.get(i);
            price = pricelist.get(i);

            orderlist.add(new cartitem(name,quantity,price));
        }

        CartAdapter cartAdapter = new CartAdapter(this,R.layout.cart_view_layout,orderlist);
        simplelist.setAdapter(cartAdapter);

        total.setText("Total Ammount: " +total_ammount+ "tk   ");


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                Bundle b = new Bundle();

                b.putStringArrayList("name", namelist);
                b.putIntegerArrayList("quantity", qntylist);
                b.putIntegerArrayList("price", pricelist);
                b.putInt("total", total_ammount);

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    //creating the action bar with cart and logout option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //cart and logout click listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.cart:
                return true;

            case R.id.logout:

                progressDialog.setMessage("Signing out..");
                progressDialog.show();

                new CountDownTimer(5000, 1000) {
                    public void onFinish() {

                        progressDialog.dismiss();
                        foodActivity f1 = new foodActivity();
                        f1.fname.clear();
                        f1.fqnty.clear();
                        f1.fprice.clear();
                        f1.total_ammount = 0;

                        firebaseAuth.signOut();
                        finish();
                        Intent intent = new Intent(CartActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
