package com.example.stats;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CoronaAdapter mAdapter;
    List<Model> coronaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coronaList = new ArrayList<>();
        recyclerView.setAdapter(mAdapter);


        apiCall();
    }

    private void apiCall() {

        String URL = "https://corona.lmao.ninja/v2/countries";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONArray jsonObject = new JSONArray(response);

                    for (int i = 0; i< jsonObject.length(); i++) {
                        JSONObject getdata = jsonObject.getJSONObject(i);
                        JSONObject forflag = getdata.getJSONObject("countryInfo");

                        String todaycases = getdata.getString("todayCases");
                        String flag = forflag.getString("flag");
                        String recovered = getdata.getString("recovered");
                        String countryname = getdata.getString("country");
                        String deaths = getdata.getString("deaths");
                        String totalcases = getdata.getString("cases");


                        Model model = new Model(todaycases, totalcases, countryname, flag, recovered, deaths);
                        coronaList.add(model);

                    }
                        mAdapter  = new CoronaAdapter(getApplicationContext(), coronaList);
                        recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchitem = menu.findItem(R.id.search_bar);

        SearchView searchView = (SearchView) searchitem.getActionView();


        searchView.setQueryHint("Search by Country");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                mAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);




    }


}