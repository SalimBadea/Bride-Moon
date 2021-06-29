package com.digmaweb.salim.myatelier.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.digmaweb.salim.myatelier.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import me.anwarshahriar.calligrapher.Calligrapher;


public class AteliersActivity extends AppCompatActivity {

    TextView rate , title , phone , prod ;
    KenBurnsView image ;
    Context context ;
   androidx.appcompat.app.ActionBar actionBar ;
    ImageButton loc, call;
    public static SharedPreferences prefs ;
   public static SharedPreferences.Editor editor ;
   public static final String PREY = "myPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Assaf Font.ttf" , false);

        title = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        rate = findViewById(R.id.rate);
        prod = findViewById(R.id.prod);
        image = (KenBurnsView) findViewById(R.id.imageView);

        String nm = getIntent().getStringExtra("name");
        String ph = getIntent().getStringExtra("phone");
        final String tit = getIntent().getStringExtra("title");
        String rat = getIntent().getStringExtra("rate");
        String photo = getIntent().getStringExtra("photo");
        final String lat = getIntent().getStringExtra("lat");
        final String att = getIntent().getStringExtra("att");
        String id = getIntent().getStringExtra("id");


        prefs = getSharedPreferences(PREY, MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("name", nm);
        editor.putString("title", tit);
        editor.putString("phone", ph);
        editor.putString("rate", rat);
        editor.putString("photo", photo);
        editor.putString("lat" , lat);
        editor.putString("att" , att);
        editor.putString("id" , id);
        editor.apply();

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(nm);
        }
        title.setText(tit);
        phone.setText("+2"+" "+ph);
        rate.setText(rat);
        Picasso.with(context).load(prefs.getString("photo", photo)).into(image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            prod.setFontFeatureSettings("Cairo-Bold.ttf");
        }

        prod.setOnClickListener(this::onClick);
        loc = findViewById(R.id.location);
        loc.setOnClickListener(v -> {
            String uri = "geo:%f,%f?q= "+lat+","+att ;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri + tit));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });

        call = findViewById(R.id.call);
        call.setOnClickListener(v -> {
            PackageManager pm = getPackageManager();
            if (pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(call);
            }else {
                Toast.makeText(AteliersActivity.this,"No call facility available in device!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClick(View v) {
        Intent i = new Intent(AteliersActivity.this, ProductsActivity.class);
        startActivity(i);
    }
}






