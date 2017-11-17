package com.example.britt.brittvleeuwen_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> categoriesArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowMenu();
    }

    public void ShowOrder(View view) {
        Intent dishSelected = new Intent(MainActivity.this, OrderActivity.class);
        dishSelected.putExtra("dishname", "");
        startActivity(dishSelected);
    }

    public void ShowMenu() {

        final ListView listview = findViewById(R.id.cateco);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, categoriesArray);
        listview.setAdapter(adapter);

        categoriesArray.clear();

        final TextView textView = findViewById(R.id.textView5);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://resto.mprog.nl/categories";

        // Request a string response from the provided URL.
        JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray categories = response.getJSONArray("categories");
                            for (int i = 0; i < categories.length(); i++) {

                                categoriesArray.add(categories.getString(i));
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
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l){
                String cuisineCoursePicked = "You selected " + String.valueOf(adapter.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, cuisineCoursePicked, Toast.LENGTH_SHORT).show();


                Intent menuItemSelected = new Intent(MainActivity.this, MenuActivity.class);
                menuItemSelected.putExtra("category", String.valueOf(adapter.getItemAtPosition(position)));
                startActivity(menuItemSelected);
            }
        });
    }
}