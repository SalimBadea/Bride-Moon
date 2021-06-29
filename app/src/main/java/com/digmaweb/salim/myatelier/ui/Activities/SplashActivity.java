package com.digmaweb.salim.myatelier.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.digmaweb.salim.myatelier.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView ;
    private TextView textView ;
    String email , token ;
    public static SharedPreferences prefs ;
    public static final String PREFER_KEY = "myPrefs";
    public SharedPreferences.Editor editor;
    private boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        imageView = findViewById( R.id.image );
        textView = findViewById( R.id.text );

        prefs = SplashActivity.this.getSharedPreferences(LoginActivity.PREFERENCE_KEY, Context.MODE_PRIVATE);
        editor = prefs.edit();
        token = prefs.getString("token" , "");
        editor.apply();

        Thread mythread = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(3000);
                    if (token.isEmpty()){
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(SplashActivity.this , MainActivity.class);
                        startActivity(i);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();

    }
    }

