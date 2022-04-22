package com.ccc.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends SimpleAdapter {
    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public void setViewImage(@NonNull ImageView v, String url) {

        Glide.with(v.getContext())
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(v);
    }
}
