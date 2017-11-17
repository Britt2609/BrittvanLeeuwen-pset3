package com.example.britt.brittvleeuwen_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    List<String> dishesArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        final String category = intent.getStringExtra("category");

        final ListView listview = findViewById(R.id.dishes);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dishesArray);
        listview.setAdapter(adapter);

        dishesArray.clear();

        final TextView textView = findViewById(R.id.textView5);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

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
                                String dish = item.getString("category");

                                if (dish.equals(category)) {
                                    dishesArray.add(item.getString("name"));
                                }
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            textView.setText("That didn't work!");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                textView.setText("ERROR!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(JsonRequest);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                String ItemPicked = "You selected " + String.valueOf(adapter.getItemAtPosition(position));
                Toast.makeText(MenuActivity.this, ItemPicked, Toast.LENGTH_SHORT).show();


                Intent menuItemSelected = new Intent(MenuActivity.this, DishActivity.class);
                menuItemSelected.putExtra("dish", String.valueOf(adapter.getItemAtPosition(position)));
                startActivity(menuItemSelected);
            }
        });
    }

    //    public void ShowOrder(View view) {
//
//    }
//
    public void ShowMenu(View view) {
        Intent ToMenu = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(ToMenu);
    }

    public void ShowOrder(View view) {
        Intent dishSelected = new Intent(MenuActivity.this, OrderActivity.class);
        dishSelected.putExtra("dishname", "");
        startActivity(dishSelected);
    }
}
