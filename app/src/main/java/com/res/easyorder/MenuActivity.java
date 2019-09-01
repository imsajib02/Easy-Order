package com.res.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ImageButton breakfast, lunch, dinner, setmenu, drink;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        breakfast = (ImageButton) findViewById(R.id.breakfast);
        lunch = (ImageButton) findViewById(R.id.lunch);
        dinner = (ImageButton) findViewById(R.id.dinner);
        setmenu = (ImageButton) findViewById(R.id.setmenu);
        drink = (ImageButton) findViewById(R.id.drink);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this,foodActivity.class);
                Bundle b = new Bundle();

                b.putString("type", "breakfast");
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this,foodActivity.class);
                Bundle b = new Bundle();

                b.putString("type", "lunch");
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this,foodActivity.class);
                Bundle b = new Bundle();

                b.putString("type", "dinner");
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        setmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this,foodActivity.class);
                Bundle b = new Bundle();

                b.putString("type", "setmenu");
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this,foodActivity.class);
                Bundle b = new Bundle();

                b.putString("type", "drink");
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
                Intent i = new Intent(MenuActivity.this,CartActivity.class);
                Bundle b = new Bundle();

                foodActivity f = new foodActivity();

                b.putStringArrayList("name", f.fname);
                b.putIntegerArrayList("qnty", f.fqnty);
                b.putIntegerArrayList("price", f.fprice);
                b.putInt("total", f.total_ammount);

                i.putExtras(b);
                startActivity(i);

                return true;

            case R.id.logout:

                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //alert user on pressing back
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setMessage("You are about to logout!")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("Cancel", null)
                .show();
    }
}
