package com.digmaweb.salim.myatelier.ui.Activities;

import android.content.Context;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digmaweb.salim.myatelier.ui.Adapter.MyAdapter;
import com.digmaweb.salim.myatelier.ui.Model.Ateliers;
import com.digmaweb.salim.myatelier.R;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class DataActivity extends AppCompatActivity {


    ActionBar actionBar;
    List<Ateliers> ateliersList ;
    HorizontalInfiniteCycleViewPager pager;
    Context context;
    private MyAdapter adapter;
    public static final String ph_url = "http://digmaweb.com/android/v2/photos/atelier/";
    public static final String mUrl = "http://digmaweb.com/android/v2/api/ateliers";
    private Double Latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Assaf Font.ttf" , true);

        jsonrequest();

        ateliersList = new ArrayList<>();
        pager = (HorizontalInfiniteCycleViewPager)findViewById(R.id.cycle_pager);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("الأتيليهات");
        }

    }

    private void jsonrequest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                 JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String Address = object.getString("title");
                        String Name = object.getString("name");
                        String Phone = object.getString("phone");
                        String Photo_url = ph_url + object.getString("photo");
                        String Rate = object.getString("rate");
                        String id = object.getString("id");
                        Latitude = Double.valueOf(object.getString("lat"));
                        longitude = Double.valueOf(object.getString("att"));

                        Ateliers ateliers = new Ateliers();
                        ateliers.setAddress(Address);
                        ateliers.setName(Name);
                        ateliers.setPhone(Phone);
                        ateliers.setPhoto_url(Photo_url);
                        ateliers.setRate(Rate);
                        ateliers.setLat(String.valueOf(Latitude));
                        ateliers.setAtt(String.valueOf(longitude));
                        ateliers.setId(id);

                        ateliersList.add(ateliers);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "error"+e.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("src" , e.toString());
                }

                adapter = new MyAdapter(ateliersList , getBaseContext());
                adapter.notifyDataSetChanged();
                pager.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DataActivity.this);
        requestQueue.add(stringRequest);
    }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newtext) {

                newtext = newtext.toLowerCase();
                List<Ateliers> newlist = new ArrayList<Ateliers>();
                for (Ateliers atelier : ateliersList) {
                    String name = atelier.getName().toLowerCase();
                    if (name.contains(newtext))
                        newlist.add(atelier);
                    adapter.notifyDataSetChanged();
                }

                adapter.setFilter(newlist);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
