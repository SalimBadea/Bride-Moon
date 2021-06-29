package com.digmaweb.salim.myatelier.ui.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.digmaweb.salim.myatelier.ui.Model.AtelierResponse;
import com.digmaweb.salim.myatelier.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {


    Context context;
    List<AtelierResponse> list ;

    public MyPagerAdapter(Context context , List<AtelierResponse> list) {
        this.list = list ;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.new_item1,container , false);

        KenBurnsView image1 = (KenBurnsView) v.findViewById(R.id.ph_view);

        Picasso.with(context).load(list.get(position).getImage_Url()).into(image1);

        container.addView(v);
        return v ;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, final int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }


}
