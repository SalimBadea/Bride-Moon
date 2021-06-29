package com.digmaweb.salim.myatelier.ui.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.digmaweb.salim.myatelier.ui.Activities.AteliersActivity;
import com.digmaweb.salim.myatelier.ui.Model.Ateliers;
import com.digmaweb.salim.myatelier.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<Ateliers> list ;
    Context context ;
    LayoutInflater inflater ;

    public MyAdapter(List<Ateliers> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View v = inflater.inflate(R.layout.layout_item , container , false);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
       TextView details = (TextView)v.findViewById(R.id.text);
       TextView name = (TextView)v.findViewById(R.id.name);
       name.setText(list.get(position).getName());
       Picasso.with(context).load(list.get(position).getPhoto_url()).into(imageView);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , AteliersActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("title" , list.get(position).getAddress());
                i.putExtra("name" , list.get(position).getName());
                i.putExtra("phone" , list.get(position).getPhone());
                i.putExtra("photo" , list.get(position).getPhoto_url());
                i.putExtra("rate" , list.get(position).getRate());
                i.putExtra("lat" , list.get(position).getLat());
                i.putExtra("att" , list.get(position).getAtt());
                i.putExtra("id" , list.get(position).getId());

                context.startActivity(i);
            }
        });
        container.addView(v);
        return v ;
    }

    public void setFilter(List<Ateliers> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }
}