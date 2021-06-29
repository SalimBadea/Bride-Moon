package com.digmaweb.salim.myatelier.ui.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.digmaweb.salim.myatelier.R;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, email, password, address, phone;
    private Button login, btnclose;
    private ImageView locicon, profile_ph;
    private TextView msg ;
    private Button prof;
    private ProgressDialog progressDialog;
    private LinearLayout overbox, mykonten;
    AlertDialog.Builder builder;
    Bitmap bitmap;
    String photo;
    byte[] paramtersbyt;
    String data;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Animation fromsmall, fromnothing, forloci, togo;
    public static SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "myPrefs";
    private static final String URL_REGISTER = "http://digmaweb.com/android/v2/api/user/register";
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Assaf Font.ttf" , true);

        locicon = findViewById(R.id.locicon);
        btnclose = findViewById(R.id.btnclose);
        overbox = (LinearLayout) findViewById(R.id.overbox);
        mykonten = (LinearLayout) findViewById(R.id.mykonten);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        profile_ph = findViewById(R.id.profile_ph);
        prof = findViewById(R.id.prof);
        login = findViewById(R.id.login);
        msg = findViewById(R.id.textve2);

        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this, R.anim.fromnothing);
        forloci = AnimationUtils.loadAnimation(this, R.anim.forloci);
        togo = AnimationUtils.loadAnimation(this, R.anim.togo);

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView studio , close , choose ;
                final Dialog dialog = new Dialog(SignUpActivity.this);
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
        mykonten.setAlpha(0);
        overbox.setAlpha(0);
        locicon.setVisibility(View.GONE);


        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(this);
        btnclose.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
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
                profile_ph.setImageURI(path);
        }
    }

    private void registerUser() {

        final String Username = username.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        final String Address = address.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();

        bitmap = ((BitmapDrawable) profile_ph.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        photo = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        if (Email.isEmpty() || Password.isEmpty() || Username.isEmpty() || Phone.isEmpty() ||Address.isEmpty()) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("حدث خطأ ما ...!");
            builder.setMessage("برجاء ادخال البيانات بشكل صحيح .");
            builder.setPositiveButton("حاول مرة اخري", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    email.setText("");
                    password.setText("");
                    username.setText("");
                    phone.setText("");
                }
            });
            builder.create();
            builder.show();
        } else {
            locicon.startAnimation(fromsmall);
            overbox.setAlpha(1);
            overbox.startAnimation(fromnothing);
            mykonten.setAlpha(1);
            mykonten.startAnimation(fromsmall);
            locicon.setVisibility(View.VISIBLE);
            try {
                data = URLEncoder.encode("username" , "UTF-8") +"="+ URLEncoder.encode(Username , "UTF-8")+"&"+
                     URLEncoder.encode("email" , "UTF-8") +"="+URLEncoder.encode(Email , "UTF-8")+"&"+
                     URLEncoder.encode("password" , "UTF-8") +"="+URLEncoder.encode(Password , "UTF-8")+"&"+
                     URLEncoder.encode("address" , "UTF-8") +"="+URLEncoder.encode(Address , "UTF-8")+"&"+
                     URLEncoder.encode("phone" , "UTF-8") +"="+URLEncoder.encode(Phone , "UTF-8")+"&"+
                     URLEncoder.encode("photo" , "UTF-8") +"="+URLEncoder.encode(photo , "UTF-8");
                paramtersbyt = data.getBytes("UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Runnable runnable = new Runnable() {
                public void run() {
                    try {

                        URL insertUserUrl = new URL(URL_REGISTER);
                        HttpURLConnection insertConnection = (HttpURLConnection) insertUserUrl.openConnection();
                        insertConnection.setRequestMethod("POST");
                        insertConnection.getOutputStream().write(paramtersbyt);
                        InputStreamReader resultStreamReader = new InputStreamReader(insertConnection.getInputStream());
                        BufferedReader resultReader = new BufferedReader(resultStreamReader);
                        final String result = resultReader.readLine();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("username", Username);
        editor.putString("email", Email);
        editor.putString("password", Password);
        editor.putString("photo" , photo);
        editor.apply();
    }

    @Override
public void onClick(View v) {

    if (v == login){
        registerUser();

    }else if (v == btnclose) {
        overbox.startAnimation(togo);
        mykonten.startAnimation(togo);
        locicon.startAnimation(togo);
        locicon.setVisibility(View.GONE);

        ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
        ViewCompat.animate(mykonten).setStartDelay(1000).alpha(0).start();

        Intent i = new Intent(this , MainActivity.class);
        startActivity(i);

    }
}

}
