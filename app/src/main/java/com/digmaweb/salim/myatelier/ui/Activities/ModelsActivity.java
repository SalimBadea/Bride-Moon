package com.digmaweb.salim.myatelier.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digmaweb.salim.myatelier.ui.Adapter.MyPagerAdapter;
import com.digmaweb.salim.myatelier.ui.Model.AtelierResponse;
import com.digmaweb.salim.myatelier.R;
import com.digmaweb.salim.myatelier.utils.RequestHandler;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ModelsActivity extends AppCompatActivity {

    List<AtelierResponse> data ;
    HorizontalInfiniteCycleViewPager pager;
    MyPagerAdapter myadapter ;
    String url = "http://digmaweb.com/android/v2/api/atelier/";
    String murl = "?more=1&token=" ;
    String photo_url = "http://digmaweb.com/android/v2/photos/atelier/";

    public static String token = "token";
    public static String id = "id";
    public static SharedPreferences mprefs ;
    public SharedPreferences.Editor editor;
    public static final String PRE_KEY = "myprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Cairo-Bold.ttf" , true);


        mprefs = this.getSharedPreferences(LoginActivity.PREFERENCE_KEY , Context.MODE_PRIVATE);
        editor = mprefs.edit() ;
        token = mprefs.getString("token" , "");
        editor.apply();

        mprefs = this.getSharedPreferences(AteliersActivity.PREY , Context.MODE_PRIVATE);
        editor = mprefs.edit();
        id = mprefs.getString("id" , "");
        editor.apply();

        jsonrequest();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        data = new ArrayList<>();
        pager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.pager);
        myadapter = new MyPagerAdapter(this , data);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModelsActivity.this , ProductsActivity.class));
            }
        });

    }

    private void jsonrequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+id+murl+token , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject parent = new JSONObject(response);
                    JSONObject mobject = parent.getJSONObject("data");

                    for (int i=0;i<mobject.length();i++){
                        JSONArray info = mobject.getJSONArray("photos");
                        for (int j=0;j<info.length();j++) {
                            JSONObject finall = info.getJSONObject(j);

                            AtelierResponse atelierResponse = new AtelierResponse();

                            atelierResponse.setImage_Url(photo_url + finall.getString("photo"));

                            data.add(i, atelierResponse);
                            myadapter.notifyDataSetChanged();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setupRecycleview(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setupRecycleview(List<AtelierResponse> data) {
        myadapter = new MyPagerAdapter(this , data);
        pager.setAdapter(myadapter);
    }


}

