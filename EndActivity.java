package com.example.britt.brittvleeuwen_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class EndActivity extends AppCompatActivity {

    RequestQueue queue;
    List<String> dishesArray = new ArrayList<>();
    ArrayAdapter<String> MyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);


        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name1");
        String name2 = intent.getStringExtra("name2");
        String name3 = intent.getStringExtra("name3");
        String name4 = intent.getStringExtra("name4");
        String name5 = intent.getStringExtra("name5");
        String name6 = intent.getStringExtra("name6");
        Integer dishname1 = intent.getIntExtra("dish1", 0);
        Integer dishname2 = intent.getIntExtra("dish2", 0);
        Integer dishname3 = intent.getIntExtra("dish3", 0);
        Integer dishname4 = intent.getIntExtra("dish4", 0);
        Integer dishname5 = intent.getIntExtra("dish5", 0);
        Integer dishname6 = intent.getIntExtra("dish6", 0);


        if (dishname1 != 0) {
            dishesArray.add(dishname1 + " X" + name1);
        }
        if (dishname2 != 0) {
            dishesArray.add(dishname2 + " X" + name2);
        }
        if (dishname3 != 0) {
            dishesArray.add(dishname3 + " X" + name3);
        }
        if (dishname4 != 0) {
            dishesArray.add(dishname4 + " X" + name4);
        }
        if (dishname5 != 0) {
            dishesArray.add(dishname5 + " X" + name5);
        }
        if (dishname6 != 0) {
            dishesArray.add(dishname6 + " X" + name6);
        }

        final TextView minutes = findViewById(R.id.textView9);
        final ListView order = findViewById(R.id.listOrder);

        MyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dishesArray);

        order.setAdapter(MyAdapter);

        MyAdapter.notifyDataSetChanged();

        saveToSharedPrefs();


        queue =  Volley.newRequestQueue(this);

        JsonObjectRequest JsonRequestpost = new JsonObjectRequest(Request.Method.POST, "https://resto.mprog.nl/order", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            minutes.setText("Order time: "+ response.getString("preparation_time"));
                        } catch (JSONException e) {
                            minutes.setText("That didn't work!");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                minutes.setText("ERROR!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(JsonRequestpost);
    }

    public void saveToSharedPrefs() {

        SharedPreferences pref = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("dish1_#", 0);
        editor.putInt("dish2_#", 0);
        editor.putInt("dish3_#", 0);
        editor.putInt("dish4_#", 0);
        editor.putInt("dish5_#", 0);
        editor.putInt("dish6_#", 0);
        editor.apply();
    }
}
