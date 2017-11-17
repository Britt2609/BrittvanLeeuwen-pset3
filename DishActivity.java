package com.example.britt.brittvleeuwen_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DishActivity extends AppCompatActivity {

    // Instantiate the RequestQueue.
    RequestQueue queue;
    ImageView imagedish;
    String IdDish;
    String dish;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        Intent intent = getIntent();
        dish = intent.getStringExtra("dish");


        final TextView dishName = findViewById(R.id.textView5);
        final TextView description = findViewById(R.id.textView7);
        final TextView price = findViewById(R.id.textView6);

        queue =  Volley.newRequestQueue(this);


        String url = "https://resto.mprog.nl/menu";

        // Request a string response from the provided URL.
        JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {

                                JSONObject item = items.getJSONObject(i);
                                String Name = item.getString("name");
                                if (Name.equals(dish)) {
                                    dishName.setText(dish);
                                    description.setText(item.getString("description"));
                                    price.setText("$ " + item.getString("price"));
                                    getImage(item.getString("image_url"));
                                    IdDish = item.getString("id");
                                }
                            }

                        } catch (JSONException e) {
                            description.setText("That didn't work!");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                description.setText("ERROR!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(JsonRequest);
    }


    public void getImage(String urlImage){
        ImageRequest imageRequest = new ImageRequest(urlImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imagedish = findViewById(R.id.imageDish);
                imagedish.setImageBitmap(response);
            }
        }, 0, 0, null, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String ImageNotFound = "Image not found!";
                Toast.makeText(DishActivity.this, ImageNotFound, Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(imageRequest);
    }

    public void ShowMenu(View view) {
        Intent ToMenu = new Intent(DishActivity.this, MainActivity.class);
        startActivity(ToMenu);
    }

    public void ShowOrder(View view) {
        Intent ToMenu = new Intent(DishActivity.this, OrderActivity.class);
        ToMenu.putExtra("dishname", "");
        startActivity(ToMenu);
    }

    public void AddDish(View view) {
        Intent ToOrder = new Intent(DishActivity.this, OrderActivity.class);
        ToOrder.putExtra("dishname", dish);
        startActivity(ToOrder);
    }
}
