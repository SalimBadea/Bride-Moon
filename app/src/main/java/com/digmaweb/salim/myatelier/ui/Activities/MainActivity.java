package com.digmaweb.salim.myatelier.ui.Activities;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.view.Gravity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.digmaweb.salim.myatelier.utils.Constants;
import com.digmaweb.salim.myatelier.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Build;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    ActionBar actionBar;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    public static SharedPreferences mprefs;
    public SharedPreferences.Editor editor;
    public static final String Prefs = "PREFS";

    public static final String mUrl = "http://digmaweb.com/android/v2/api/ateliers";
    public static final String logout_url = "http://digmaweb.com/android/v2/api/user/logout?token=";
    public static final String ph_url = "http://digmaweb.com/android/v2/photos/atelier/";
    public static final String URL_REGISTER = "http://digmaweb.com/android/v2/api/user/register";


    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;
    private GoogleMap mgoogleMap;
    private GoogleApiClient mgoogleApiClient;
    private LatLng latLng;

    private String TAG = "MainActivity";
    private Bundle mBundle;
    int fetchType = Constants.USE_ADDRESS_LOCATION;
    Animation fromsmall , fromnothing , forloci , togo;

    private String photo;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap bitmap;
    ImageView image , logo;
    TextView userName, userEmail;
    private EditText edit;
    private Button go;

    LinearLayout layout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isGooglePlayServicesAvailable()) {
            String txt = "perfect !!";
            Toast ts = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
            View bg = ts.getView();
            bg.setBackgroundColor(Color.parseColor("#6dddf1"));
            ts.setGravity(Gravity.BOTTOM, 20, 20);
            ts.show();

            Calligrapher calligrapher = new Calligrapher(this);
            calligrapher.setFont(this , "Assaf Font.ttf" , true);
            initMap();
        } else {

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.logoicon);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.START
                | Gravity.CENTER_VERTICAL);
        layoutParams.leftMargin = 20;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        fromsmall = AnimationUtils.loadAnimation(this , R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this , R.anim.fromnothing);
        forloci = AnimationUtils.loadAnimation(this , R.anim.forloci);
        togo = AnimationUtils.loadAnimation(this , R.anim.togo);

        mBundle = savedInstanceState;

        edit = findViewById(R.id.place);
        go = findViewById(R.id.go);
        logo = findViewById(R.id.atelie_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , DataActivity.class));
            }
        });
        nav_view = (NavigationView) findViewById(R.id.navview);
        nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        View header = nav_view.getHeaderView(0);
        userName = header.findViewById(R.id.user_name);
        userEmail = header.findViewById(R.id.user_email);
        image = header.findViewById(R.id.profile_image);

        mprefs =this.getSharedPreferences(SignUpActivity.PREFS_NAME, Context.MODE_PRIVATE);
        editor = mprefs.edit();
        String nameView = mprefs.getString("username", "");
        String emailView = mprefs.getString("email","");
        editor.apply();

        userName.setText(nameView);
        userEmail.setText(emailView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TextView studio , close , choose ;
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog);
                dialog.show();

                choose = dialog.findViewById(R.id.choose);
                studio = dialog.findViewById(R.id.studio);
                studio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gallaryIntent();
                        dialog.dismiss();

                    }
                });

                close = dialog.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void gallaryIntent() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "اختر ملف"), SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int status = api.isGooglePlayServicesAvailable(this);
        if (status == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(status)) {
            Dialog dialog = api.getErrorDialog(this, status, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't Connect", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {

            case R.id.none:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;

            case R.id.normal:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;

            case R.id.terrain:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

            case R.id.satellite:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.hybrid:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.H) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.C) {
            Intent i = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(i);
        } else if (id == R.id.Exit) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            mprefs = this.getSharedPreferences(LoginActivity.PREFERENCE_KEY, Context.MODE_PRIVATE);
            editor = mprefs.edit();
            editor.clear();
            editor.remove(mprefs.getString("token" , ""));
            editor.apply();
            this.finish();
        }
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;
        gotoLocacionzoom(31.0458158 ,31.3862444 , 16);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }

        mgoogleMap.setMyLocationEnabled(true);

                mgoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();

        mgoogleApiClient.connect();

        if (mgoogleMap != null) {

            mgoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc = new Geocoder(MainActivity.this);
                    latLng = marker.getPosition();
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocation(latLng.latitude , latLng.longitude , 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                }
            });

        }
    }

    private void gotoLocacionzoom(double lat, double lng, float zoom) {
        latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mgoogleMap.moveCamera(update);
    }

    Marker marker ;
    public void geoLocate(View view) throws IOException {
        String location = edit.getText().toString();
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();
        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        gotoLocacionzoom(lat, lng, 17);

        setMarker(location, lat, lng);
    }

    private void setMarker(String location, double lat, double lng) {

        MarkerOptions options = new MarkerOptions()
                .title(location)
                .draggable(true)
                .snippet("I am here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .position(new LatLng(lat,lng));
        marker = mgoogleMap.addMarker(options);
    }

    LocationRequest mlocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = LocationRequest.create();
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiClient, mlocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null){
            Toast.makeText(this ,"لا يمكن العثور ع الموقع الحالي" ,Toast.LENGTH_LONG).show();
        }else {
            LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng , 16);
            mgoogleMap.animateCamera(update);
        }

    }
}
