package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Lab4Adapter extends BaseAdapter {
    List<PhotoFAN> photoList;
    Context context;

    public Lab4Adapter(List<PhotoFAN> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lab4Holder lab4Holder= null;
        if (lab4Holder == null){
           convertView= LayoutInflater.from(context).inflate(R.layout.lv_img,parent, false);
            lab4Holder = new Lab4Holder();
            lab4Holder.img = convertView.findViewById(R.id.img);
             parent.setTag(lab4Holder);
        } else {
            convertView = (View) parent.getTag();
        }
        Glide.with(context).load(photoList.get(position).url_z).into(lab4Holder.img);
        return convertView;
    }

    public class Lab4Holder{
        ImageView img;
    }
}
