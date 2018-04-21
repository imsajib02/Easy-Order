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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class foodActivity extends AppCompatActivity {

    private String type = null;
    public static int total_ammount = 0;

    public static ArrayList<String> fname = new ArrayList<>();
    public static ArrayList<Integer> fqnty = new ArrayList<>();
    public static ArrayList<Integer> fprice = new ArrayList<>();

    ListView simplelist;
    ArrayList<item> foodlist = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        simplelist = (ListView) findViewById(R.id.listview);

        type = getIntent().getExtras().getString("type");

        if(type.equals("breakfast"))
        {
            foodlist.add(new item("Rooti",R.drawable.ruti,5,"0"));
            foodlist.add(new item("Parata",R.drawable.porata,8,"0"));
            foodlist.add(new item("Tandoor",R.drawable.tandoor,15,"0"));
            foodlist.add(new item("Vegetable",R.drawable.sodji,10,"0"));
            foodlist.add(new item("Daal",R.drawable.dal,10,"0"));
            foodlist.add(new item("Omelet",R.drawable.dimvaji,15,"0"));
            foodlist.add(new item("Singara",R.drawable.singara,8,"0"));
            foodlist.add(new item("Samosa",R.drawable.samosa,10,"0"));
            foodlist.add(new item("Puri",R.drawable.puri,5,"0"));

        }
        else if(type.equals("lunch"))
        {
            foodlist.add(new item("Plain Rice",R.drawable.rice,15,"0"));
            foodlist.add(new item("Vegetable Rice",R.drawable.vegetable_rice,25,"0"));
            foodlist.add(new item("Polao",R.drawable.polao,70,"0"));
            foodlist.add(new item("Beef Tehari",R.drawable.beef_tehari,110,"0"));
            foodlist.add(new item("kacchi",R.drawable.kacchi,140,"0"));
            foodlist.add(new item("Vuna khichuri",R.drawable.vuna_khichuri,90,"0"));
            foodlist.add(new item("Vegetable khichuri",R.drawable.sobji_khichuri,75,"0"));
            foodlist.add(new item("Vuna Dim",R.drawable.dim_vuna,20,"0"));
            foodlist.add(new item("Daal",R.drawable.daal,15,"0"));
            foodlist.add(new item("Vegetable Salad",R.drawable.vegetable_salad,25,"0"));
            foodlist.add(new item("Bhorta",R.drawable.vorta,5,"0"));
            foodlist.add(new item("Shakvaji - 1",R.drawable.lalshak,10,"0"));
            foodlist.add(new item("Shakvaji - 2",R.drawable.shakvaji,10,"0"));
            foodlist.add(new item("Shorisha Elish",R.drawable.sorisa_elish,65,"0"));
            foodlist.add(new item("Fish Curry",R.drawable.fish_curry,60,"0"));
            foodlist.add(new item("Fish Fry",R.drawable.fish_fry,110,"0"));
            foodlist.add(new item("Chicken Roast",R.drawable.chicken_roast,115,"0"));
            foodlist.add(new item("Chicken Fry",R.drawable.chicken_fry,70,"0"));
            foodlist.add(new item("Beef",R.drawable.beef,80,"0"));
            foodlist.add(new item("Khashi",R.drawable.khashi,90,"0"));
        }
        else if(type.equals("dinner"))
        {
            foodlist.add(new item("Rooti",R.drawable.ruti,5,"0"));
            foodlist.add(new item("Tandoor",R.drawable.tandoor,15,"0"));
            foodlist.add(new item("Butter Naan",R.drawable.butter_naan,25,"0"));
            foodlist.add(new item("Singara",R.drawable.singara,8,"0"));
            foodlist.add(new item("Samosa",R.drawable.samosa,10,"0"));
            foodlist.add(new item("Puri",R.drawable.puri,5,"0"));
            foodlist.add(new item("Vegetable",R.drawable.sodji,10,"0"));
            foodlist.add(new item("Vuna Dim",R.drawable.dim_vuna,20,"0"));
            foodlist.add(new item("Plain Rice",R.drawable.rice,15,"0"));
            foodlist.add(new item("Beef Tehari",R.drawable.beef_tehari,110,"0"));
            foodlist.add(new item("kacchi",R.drawable.kacchi,140,"0"));
            foodlist.add(new item("Fish Curry",R.drawable.fish_curry,60,"0"));
            foodlist.add(new item("Fish Fry",R.drawable.fish_fry,110,"0"));
            foodlist.add(new item("Chicken Roast",R.drawable.chicken_roast,115,"0"));
            foodlist.add(new item("Chicken Fry",R.drawable.chicken_fry,70,"0"));
            foodlist.add(new item("Beef",R.drawable.beef,80,"0"));
            foodlist.add(new item("Khashi",R.drawable.khashi,90,"0"));
            foodlist.add(new item("Grilled Chicken",R.drawable.grilled_chicken,80,"0"));
            foodlist.add(new item("Beef Kabaab",R.drawable.kabab,80,"0"));
            foodlist.add(new item("French Fry",R.drawable.french_fry,40,"0"));
            foodlist.add(new item("Beef Burger",R.drawable.beef_burger,160,"0"));
            foodlist.add(new item("Chicken Burger",R.drawable.chicken_burger,120,"0"));
            foodlist.add(new item("Cheese Burger",R.drawable.cheese_burger,110,"0"));
            foodlist.add(new item("Halim",R.drawable.halim,50,"0"));
            foodlist.add(new item("Sandwich",R.drawable.sandwich,65,"0"));
            foodlist.add(new item("Shawarma",R.drawable.shawarma,80,"0"));
            foodlist.add(new item("Noodles",R.drawable.noodles,80,"0"));
            foodlist.add(new item("Vegetable Noodles",R.drawable.veg_noodles,95,"0"));
            foodlist.add(new item("Chicken Noodles",R.drawable.chicken_noodles,130,"0"));
        }
        else if(type.equals("setmenu"))
        {

        }
        else if(type.equals("drink"))
        {
            foodlist.add(new item("Tea",R.drawable.rong_cha,10,"0"));
            foodlist.add(new item("Milk Tea",R.drawable.dudh_cha,15,"0"));
            foodlist.add(new item("Green Tea",R.drawable.green_tea,15,"0"));
            foodlist.add(new item("Lemon Tea",R.drawable.lemon_tea,15,"0"));
            foodlist.add(new item("Tulsi Tea",R.drawable.tulsi_cha,15,"0"));
            foodlist.add(new item("Coffee",R.drawable.coffee,30,"0"));
            foodlist.add(new item("Latte",R.drawable.latte,65,"0"));
            foodlist.add(new item("Lacchi",R.drawable.lacchi,50,"0"));
            foodlist.add(new item("Borhani",R.drawable.borhani,40,"0"));
            foodlist.add(new item("Milkshake",R.drawable.milkshake,80,"0"));
            foodlist.add(new item("Mango Juice",R.drawable.mango_juice,70,"0"));
            foodlist.add(new item("Orange Juice",R.drawable.chicken_burger,70,"0"));
            foodlist.add(new item("Lemonade",R.drawable.lemonade,30,"0"));
            foodlist.add(new item("Coke",R.drawable.coke,30,"0"));
            foodlist.add(new item("Fanta",R.drawable.fanta,30,"0"));
            foodlist.add(new item("Sprite",R.drawable.sprite,30,"0"));
        }

        MyAdapter myAdapter = new MyAdapter(this,R.layout.list_view_layout,foodlist);
        simplelist.setAdapter(myAdapter);
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
                Intent i = new Intent(foodActivity.this,CartActivity.class);
                Bundle b = new Bundle();

                b.putStringArrayList("name", fname);
                b.putIntegerArrayList("qnty", fqnty);
                b.putIntegerArrayList("price", fprice);
                b.putInt("total", total_ammount);

                i.putExtras(b);
                startActivity(i);

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
                        Intent intent = new Intent(foodActivity.this,LoginActivity.class);
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
