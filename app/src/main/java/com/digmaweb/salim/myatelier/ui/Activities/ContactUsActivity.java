package com.digmaweb.salim.myatelier.ui.Activities;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.digmaweb.salim.myatelier.R;
import me.anwarshahriar.calligrapher.Calligrapher;

public class ContactUsActivity extends AppCompatActivity {

    private ImageView image ;
    private ImageButton mobile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this , "Assaf Font.ttf" , true);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mobile = findViewById(R.id.call);
        image = findViewById(R.id.imageview);
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:+201032324334"));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if ( id == android.R.id.home){

            this.finish();
        }

        return true ;
    }
}
