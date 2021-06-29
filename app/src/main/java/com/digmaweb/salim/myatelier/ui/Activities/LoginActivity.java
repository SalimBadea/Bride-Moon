package com.digmaweb.salim.myatelier.ui.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    private Button login, btnclos;
    private TextView register, text;
    AlertDialog.Builder builder;
    ConstraintLayout log;
    private ProgressDialog progressDialog;
    public static SharedPreferences prefs;
    public static final String PREFERENCE_KEY = "myPrefs";
    public SharedPreferences.Editor editor;
    Animation fromsmall, fromnothing, forloci, togo;
    private LinearLayout overbox1, mykoten;
    private ImageView locion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Assaf Font.ttf" , true);

        locion = findViewById(R.id.locion);
        btnclos = findViewById(R.id.btnclos);
        overbox1 = (LinearLayout) findViewById(R.id.overbox1);
        mykoten = (LinearLayout) findViewById(R.id.mykoten);

        log = (ConstraintLayout) findViewById(R.id.log);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        text = mykoten.findViewById(R.id.textve2);

        login = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        register = findViewById(R.id.register);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        btnclos.setOnClickListener(this);

        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this, R.anim.fromnothing);
        forloci = AnimationUtils.loadAnimation(this, R.anim.forloci);
        togo = AnimationUtils.loadAnimation(this, R.anim.togo);

        log.setAlpha(1);
        mykoten.setAlpha(0);
        overbox1.setAlpha(0);

        locion.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        if (v == login) {
            userLogin();

        } else if (v == register) {
            Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(i);
        } else if (v == btnclos) {
            overbox1.startAnimation(togo);
            mykoten.startAnimation(togo);
            locion.startAnimation(togo);
            locion.setVisibility(View.GONE);
            log.startAnimation(togo);

            ViewCompat.animate(overbox1).setStartDelay(1000).alpha(0).start();
            ViewCompat.animate(mykoten).setStartDelay(1000).alpha(0).start();
            ViewCompat.animate(log).setStartDelay(1000).alpha(0).start();

            Intent i = new Intent(this , MainActivity.class);
            prefs = this.getSharedPreferences(SignUpActivity.PREFS_NAME, Context.MODE_PRIVATE);
            editor = prefs.edit();
            String email = prefs.getString("email", "");
            String username = prefs.getString("username", "");
            editor.apply();
            startActivity(i);
        }

    }


    private void userLogin() {
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        if (Email.equals("") || Password.equals("")) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("حدث خطأ ما ...!");
            builder.setMessage("برجاء ادخال بريدك الالكتروني وكلمة المرور.");
            builder.setPositiveButton("حاول مرة اخري", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    email.setText("");
                    password.setText("");
                }
            });
            builder.create();
            builder.show();
        } else {

            text.setAlpha(1);
            text.startAnimation(fromsmall);
            locion.startAnimation(fromsmall);
            overbox1.setAlpha(1);
            overbox1.startAnimation(fromnothing);
            mykoten.setAlpha(1);
            mykoten.startAnimation(fromsmall);
            locion.setVisibility(View.VISIBLE);

            new loginUser(Email, Password).execute(Constants.URL_Login);


        }
    }

    public class loginUser extends AsyncTask<String, Void, String> {

        private String email;
        private String password;

        public loginUser(String email, String password) {
            this.email = email;
            this.password = password;
        }

        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection con = null;
        BufferedReader reader = null;

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("email", email);
                builder.appendQueryParameter("password", password);

                String urlQuery = builder.build().getEncodedQuery();
                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(urlQuery);
                writer.flush();
                writer.close();
                os.close();
                con.connect();

                InputStream is = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                String token = object.getString("token");

                prefs = getSharedPreferences(PREFERENCE_KEY , MODE_PRIVATE);
                editor = prefs.edit();
                editor.putString("token", token);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putBoolean("islogged", true);
                editor.apply();

                if (token.equals(prefs.getString("token", ""))) {

                    String txt = "تم تسجيل الدخول بنجاح !" ;
                    Toast ts = Toast.makeText(LoginActivity.this,txt , Toast.LENGTH_SHORT);
                    View bg = ts.getView();
                    bg.setBackgroundColor(Color.parseColor("#493fb0"));
                    ts.setGravity(Gravity.BOTTOM , 20 , 20);
                    ts.show();


                } else {
                    Toast.makeText(LoginActivity.this,"حدث خطا ما !! .. برجاء ادخل البريد الالكتروني وكلمة المرور بشكل صحيح !!", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}




