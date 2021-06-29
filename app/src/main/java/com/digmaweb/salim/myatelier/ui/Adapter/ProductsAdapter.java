package com.digmaweb.salim.myatelier.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.digmaweb.salim.myatelier.ui.Model.AtelierResponse;
import com.digmaweb.salim.myatelier.ui.Activities.ModelsActivity;
import com.digmaweb.salim.myatelier.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductsAdapter extends BaseAdapter {

    Context ctx ;
    List<AtelierResponse> responseList ;

    public ProductsAdapter(Context ctx, List<AtelierResponse> responseList) {
        this.ctx = ctx;
        this.responseList = responseList;
    }

    @Override
    public int getCount() {
        if (responseList != null) {
            return responseList.size();
        }else {
            return 0 ;
        }
    }

    @Override
    public Object getItem(int position) {
        return responseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageView image ;
        LinearLayout view_dialog ;

        View view = convertView;
        if ( view == null) {

            view = LayoutInflater.from(ctx).inflate(R.layout.model , null);

            view_dialog = (LinearLayout) view.findViewById(R.id.view_dialog);
            image = (ImageView) view.findViewById(R.id.image);

            Picasso.with(ctx).load(responseList.get(position).getImage_Url()).into(image);
            view_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, ModelsActivity.class);
                    ctx.startActivity(i);
                }
            });
        }
        return view;
    }
    }


