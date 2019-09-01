package com.res.easyorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {

    EditText con, add;
    Button b;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> quantity = new ArrayList<>();
    ArrayList<Integer> price = new ArrayList<>();
    ArrayList<String> finallist = new ArrayList<>();

    public String total;
    public int length, i, order_flag = 0;
    public String contact, address;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        con = (EditText) findViewById(R.id.contact);
        add = (EditText) findViewById(R.id.address);
        b = (Button) findViewById(R.id.confirm);

        name = getIntent().getExtras().getStringArrayList("name");
        quantity = getIntent().getExtras().getIntegerArrayList("quantity");
        price = getIntent().getExtras().getIntegerArrayList("price");
        total = String.valueOf(getIntent().getExtras().getInt("total"));

        //preventing key ENTER from making a newline
        add.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    //do nothing
                    return true;
                }
                return false;
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 new AlertDialog.Builder(OrderActivity.this)
                         .setMessage("Are you sure?")
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                                 progressDialog.setMessage("Placing order..");
                                 progressDialog.show();

                                 contact = con.getText().toString();
                                 address = add.getText().toString();
                                 length = name.size();

                                 for (i=0; i<length; i++)
                                 {
                                     finallist.add(name.get(i)+ " - " +quantity.get(i)+ " - " +price.get(i)+ "tk");
                                 }

                                 finallist.add("Total Ammount: " +total+ "tk");
                                 Calendar c = Calendar.getInstance();
                                 int hour = c.get(Calendar.HOUR_OF_DAY) + 1;
                                 int min = c.get(Calendar.MINUTE);
                                 int ampm = c.get(Calendar.AM_PM);
                                 String s;

                                 if (ampm == 0)
                                 {
                                     s = "am";
                                 }
                                 else
                                 {
                                     s = "pm";
                                 }

                                 final String time = "" +Integer.toString(hour)+ ":" +Integer.toString(min)+ "" +s;

                                 databaseReference.child("Contact: " +contact).child("Address: " +address).setValue(finallist);

                                 new CountDownTimer(10000, 1000) {
                                     public void onFinish() {

                                         progressDialog.dismiss();
                                         Toast.makeText(getApplicationContext(),"Order is placed! Your order will be delivered around at " +time+ ".",Toast.LENGTH_LONG).show();
                                         order_flag = 1;

                                         foodActivity f1 = new foodActivity();
                                         f1.fname.clear();
                                         f1.fqnty.clear();
                                         f1.fprice.clear();
                                         f1.total_ammount = 0;

                                         //auto sign out after placing order
                                         /*new CountDownTimer(5000, 1000) {
                                             public void onFinish() {

                                                 progressDialog.setMessage("Signing out..");
                                                 progressDialog.show();
                                                 foodActivity f1 = new foodActivity();
                                                 f1.fname.clear();
                                                 f1.fqnty.clear();
                                                 f1.fprice.clear();
                                                 f1.total_ammount = 0;

                                                 firebaseAuth.signOut();

                                                 new CountDownTimer(10000, 1000) {
                                                     public void onFinish() {

                                                         progressDialog.dismiss();
                                                         finish();
                                                         Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                                                         startActivity(intent);
                                                     }

                                                     public void onTick(long millisUntilFinished) {
                                                         // millisUntilFinished    The amount of time until finished.
                                                     }
                                                 }.start();
                                             }

                                             public void onTick(long millisUntilFinished) {
                                                 // millisUntilFinished    The amount of time until finished.
                                             }
                                         }.start();*/
                                     }

                                     public void onTick(long millisUntilFinished) {
                                         // millisUntilFinished    The amount of time until finished.
                                     }
                                 }.start();

                             }
                         })

                         .setNegativeButton("No", null)
                         .show();
             }
        });


    }

    public void onBackPressed(){

        if(order_flag == 1)
        {
            Intent i = new Intent(OrderActivity.this, CartActivity.class);
            Bundle b = new Bundle();

            name.clear();
            quantity.clear();
            price.clear();
            total = "0";

            //clearing cartlist of cartactivity
            b.putStringArrayList("name", name);
            b.putIntegerArrayList("qnty", quantity);
            b.putIntegerArrayList("price", price);
            b.putInt("total", Integer.valueOf(total));

            i.putExtras(b);
            startActivity(i);

            finish();
        }
        else
        {
            Intent i = new Intent(OrderActivity.this, CartActivity.class);
            Bundle b = new Bundle();

            //getting cartlist with original items
            b.putStringArrayList("name", name);
            b.putIntegerArrayList("qnty", quantity);
            b.putIntegerArrayList("price", price);
            b.putInt("total", Integer.valueOf(total));

            i.putExtras(b);
            startActivity(i);

            finish();
        }
    }

}
