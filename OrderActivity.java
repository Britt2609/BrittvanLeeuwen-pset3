package com.example.britt.brittvleeuwen_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderActivity extends AppCompatActivity {

    HashMap<String, Integer> DishesMap = new HashMap<String, Integer>();

    Integer dish1 = 0;
    Integer dish2 = 0;
    Integer dish3 = 0;
    Integer dish4 = 0;
    Integer dish5 = 0;
    Integer dish6 = 0;
    String dishname1;
    String dishname2;
    String dishname3;
    String dishname4;
    String dishname5;
    String dishname6;

    ListView listview;
    TextView textview;
    List<String> dishesArray;

    ArrayAdapter<String> MyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        listview = findViewById(R.id.yourOrder);
        textview = findViewById(R.id.textView8);

        dishesArray = new ArrayList<String>();
        Intent intent = getIntent();
        String MyDish = intent.getStringExtra("dishname");


        dishname1 = "Spaghetti and Meatballs";
        dishname2 = "Margherita Pizza";
        dishname3 = "Grilled Steelhead Trout Sandwich";
        dishname4 = "Pesto Linguini";
        dishname5 = "Chicken Noodle Soup";
        dishname6 = "Italian Salad";

        LoadFromSharedPrefs();

        if (MyDish.equals(dishname1)) {
            dish1 += 1;
        }
        if (MyDish.equals(dishname2)) {
            dish2 += 1;
        }
        if (MyDish.equals(dishname3)) {
            dish3 += 1;
        }
        if (MyDish.equals(dishname4)) {
            dish4 += 1;
        }
        if (MyDish.equals(dishname5)) {
            dish5 += 1;
        }
        if (MyDish.equals(dishname6)) {
            dish6 += 1;
        }


        if (dish1 != 0) {
            dishesArray.add(dish1 + " X" + dishname1);
        }
        if (dish2 != 0) {
            dishesArray.add(dish2 + " X" + dishname2);
        }
        if (dish3 != 0) {
            dishesArray.add(dish3 + " X" + dishname3);
        }
        if (dish4 != 0) {
            dishesArray.add(dish4 + " X" + dishname4);
        }
        if (dish5 != 0) {
            dishesArray.add(dish5 + " X" + dishname5);
        }
        if (dish6 != 0) {
            dishesArray.add(dish6 + " X" + dishname6);
        }


        MyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dishesArray);

        listview.setAdapter(MyAdapter);

        MyAdapter.notifyDataSetChanged();

        saveToSharedPrefs();
    }

    public void ShowMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ShowEnd(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("name1", dishname1);
        intent.putExtra("name2", dishname2);
        intent.putExtra("name3", dishname3);
        intent.putExtra("name4", dishname4);
        intent.putExtra("name5", dishname5);
        intent.putExtra("name6", dishname6);
        intent.putExtra("dish1", dish1);
        intent.putExtra("dish2", dish2);
        intent.putExtra("dish3", dish3);
        intent.putExtra("dish4", dish4);
        intent.putExtra("dish5", dish5);
        intent.putExtra("dish6", dish6);
        startActivity(intent);
    }

    public void saveToSharedPrefs() {

    SharedPreferences pref = this.getSharedPreferences("settings", this.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();

        editor.putInt("dish1_#", dish1);
        editor.putInt("dish2_#", dish2);
        editor.putInt("dish3_#", dish3);
        editor.putInt("dish4_#", dish4);
        editor.putInt("dish5_#", dish5);
        editor.putInt("dish6_#", dish6);
        editor.apply();
    }


    public void LoadFromSharedPrefs() {
        SharedPreferences pref = this.getSharedPreferences("settings", this.MODE_PRIVATE);

        dish1  = pref.getInt("dish1_#", 0);
        dish2  = pref.getInt("dish2_#", 0);
        dish3  = pref.getInt("dish3_#", 0);
        dish4  = pref.getInt("dish4_#", 0);
        dish5  = pref.getInt("dish5_#", 0);
        dish6  = pref.getInt("dish6_#", 0);


    }


    public void EditOrder(View view) {
        Button add = findViewById(R.id.button);
        Button delete = findViewById(R.id.button2);
        add.setText("add");
        delete.setText("delete");
    }
    public void DeleteOrder(View view) {
        TextView text = findViewById(R.id.textView3);
        text.setText("Choose the dish you want to delete");

        listview.setLongClickable(false);
        listview.setLongClickable(true);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int index, long arg3) {
                String item_clicked = listview.getItemAtPosition(index).toString();
                if (item_clicked.endsWith(dishname1)) {
                    dish1 -= 1;
                    Toast.makeText(OrderActivity.this, dishname1 + " Deleted from your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname2)) {
                    dish2 -= 1;
                    Toast.makeText(OrderActivity.this, dishname2 + " Deleted from your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname3)) {
                    dish3 -= 1;
                    Toast.makeText(OrderActivity.this, dishname3 + " Deleted from your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname4)) {
                    dish4 -= 1;
                    Toast.makeText(OrderActivity.this, dishname4 + " Deleted from your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname5)) {
                    dish5 -= 1;
                    Toast.makeText(OrderActivity.this, dishname5 + " Deleted from your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname6)) {
                    dish6 -= 1;
                    Toast.makeText(OrderActivity.this, dishname6 + " Deleted from your order",
                            Toast.LENGTH_SHORT).show();
                }
                dishesArray.clear();


                if (dish1 != 0) {
                    dishesArray.add(dish1 + " X" + dishname1);
                }
                if (dish2 != 0) {
                    dishesArray.add(dish2 + " X" + dishname2);
                }
                if (dish3 != 0) {
                    dishesArray.add(dish3 + " X" + dishname3);
                }
                if (dish4 != 0) {
                    dishesArray.add(dish4 + " X" + dishname4);
                }
                if (dish5 != 0) {
                    dishesArray.add(dish5 + " X" + dishname5);
                }
                if (dish6 != 0) {
                    dishesArray.add(dish6 + " X" + dishname6);
                }
                saveToSharedPrefs();
                MyAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    public void AddOrder(View view) {

        TextView text = findViewById(R.id.textView3);
        text.setText("Choose the dish you want to add");
        listview.setLongClickable(false);
        listview.setLongClickable(true);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int index, long arg3) {
                String item_clicked = listview.getItemAtPosition(index).toString();
                if (item_clicked.endsWith(dishname1)) {
                    dish1 += 1;
                    Toast.makeText(OrderActivity.this, dishname1 + " Added to your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname2)) {
                    dish2 += 1;
                    Toast.makeText(OrderActivity.this, dishname2 + " Added to your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname3)) {
                    dish3 += 1;
                    Toast.makeText(OrderActivity.this, dishname3 + " Added to your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname4)) {
                    dish4 += 1;
                    Toast.makeText(OrderActivity.this, dishname4 + " Added to your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname5)) {
                    dish5 += 1;
                    Toast.makeText(OrderActivity.this, dishname5 + " Added to your order",
                            Toast.LENGTH_SHORT).show();
                }
                if (item_clicked.endsWith(dishname6)) {
                    dish6 += 1;
                    Toast.makeText(OrderActivity.this, dishname6 + " Added to your order",
                            Toast.LENGTH_SHORT).show();
                }
                dishesArray.clear();


                if (dish1 != 0) {
                    dishesArray.add(dish1 + " X" + dishname1);
                }
                if (dish2 != 0) {
                    dishesArray.add(dish2 + " X" + dishname2);
                }
                if (dish3 != 0) {
                    dishesArray.add(dish3 + " X" + dishname3);
                }
                if (dish4 != 0) {
                    dishesArray.add(dish4 + " X" + dishname4);
                }
                if (dish5 != 0) {
                    dishesArray.add(dish5 + " X" + dishname5);
                }
                if (dish6 != 0) {
                    dishesArray.add(dish6 + " X" + dishname6);
                }

                MyAdapter.notifyDataSetChanged();
                saveToSharedPrefs();
                return true;
            }
        });
    }
}
