package com.digmaweb.salim.myatelier.ui.Activities;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digmaweb.salim.myatelier.ui.Adapter.ProductsAdapter;
import com.digmaweb.salim.myatelier.ui.Model.AtelierResponse;
import com.digmaweb.salim.myatelier.R;
import com.digmaweb.salim.myatelier.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ProductsActivity extends AppCompatActivity {

    ActionBar actionBar;
    Context mcontext;
    List<AtelierResponse> data ;
    ProductsAdapter adapter ;
    GridView view ;
    public static String token ;
    public static String id ;
    String url = "http://digmaweb.com/android/v2/api/atelier/";
    String murl = "?more=1&token=" ;
    String rate_url = "/rate?token=" ;
    String photo_url = "http://digmaweb.com/android/v2/photos/atelier/";
    Button rate;
    String name ;

    Animation fromsmall , fromnothing , forloci , togo;
    private LinearLayout overbox2 ;
    public static SharedPreferences mprefs ;
    public SharedPreferences.Editor editor;
    public static final String PRE_KEY = "myprefs";
    RatingBar ratingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Cairo-Bold.ttf" , true);

        mprefs = this.getSharedPreferences(AteliersActivity.PREY , Context.MODE_PRIVATE);
        editor = mprefs.edit();
        name = mprefs.getString("name" , "");
        id = mprefs.getString("id" , "");
        editor.apply();

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(name);
        }

        mprefs = this.getSharedPreferences(LoginActivity.PREFERENCE_KEY , Context.MODE_PRIVATE);
        editor = mprefs.edit() ;
        token = mprefs.getString("token" , "");
        editor.apply();

        data = new ArrayList<>();
        view = (GridView) findViewById(R.id.model);
        adapter = new ProductsAdapter(this , data);

        jsonrequest();

        fromsmall = AnimationUtils.loadAnimation(this , R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this , R.anim.fromnothing);
        forloci = AnimationUtils.loadAnimation(this , R.anim.forloci);
        togo = AnimationUtils.loadAnimation(this , R.anim.togo);

        overbox2 = (LinearLayout)findViewById(R.id.overbox2);
        overbox2.setAlpha(0);

        rate = findViewById(R.id.rate_it);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button later , submit ;
                final Dialog dialog = new Dialog(ProductsActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.rate_box);

                overbox2.setAlpha(1);
                overbox2.startAnimation(fromnothing);
                dialog.show();

                ratingBar = dialog.findViewById(R.id.ratebar);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        update(rating);
                    }
                });
                submit = dialog.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        overbox2.setAlpha(0);
                        overbox2.startAnimation(togo);
                        dialog.dismiss();

                    }
                });

                later = dialog.findViewById(R.id.later);
                later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        overbox2.setAlpha(0);
                        overbox2.startAnimation(togo);
                        dialog.dismiss();
                    }
                });

            }
        });

        }

    private void update(final float rating) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+id+rate_url+token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("error");
                    Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String , String> params = new HashMap<>();
               params.put("rate" , String.valueOf(rating));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                            adapter.notifyDataSetChanged();
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

        adapter = new ProductsAdapter(this , data);
        view.setAdapter(adapter);

    }



}
