package com.res.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    Spinner sp;
    Button b;
    String[] table = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> quantity = new ArrayList<>();
    ArrayList<Integer> price = new ArrayList<>();
    ArrayList<String> finallist = new ArrayList<>();

    public String total;
    public int length, i;
    public String table_num;

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

        sp = (Spinner) findViewById(R.id.spinner);
        b = (Button) findViewById(R.id.confirm);

        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_item,table);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(a);

        name = getIntent().getExtras().getStringArrayList("name");
        quantity = getIntent().getExtras().getIntegerArrayList("quantity");
        price = getIntent().getExtras().getIntegerArrayList("price");
        total = String.valueOf(getIntent().getExtras().getInt("total"));

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

                                 table_num = sp.getSelectedItem().toString();
                                 length = name.size();

                                 for (i=0; i<length; i++)
                                 {
                                     finallist.add(name.get(i)+ " - " +quantity.get(i)+ " - " +price.get(i)+ "tk");
                                 }

                                 finallist.add("Total Ammount: " +total+ "tk");

                                 databaseReference.child("Table: " +table_num).setValue(finallist);

                                 new CountDownTimer(10000, 1000) {
                                     public void onFinish() {

                                         progressDialog.dismiss();
                                         Toast.makeText(getApplicationContext(),"Order is placed!",Toast.LENGTH_LONG).show();

                                         new CountDownTimer(5000, 1000) {
                                             public void onFinish() {

                                                 progressDialog.setMessage("Signing out..");
                                                 progressDialog.show();

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
                                         }.start();
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
}
