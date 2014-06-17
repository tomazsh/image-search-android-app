package com.nedeljko.imagesearch.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class ImageArrayAdapter extends ArrayAdapter<Image> {

    public ImageArrayAdapter(Context context, ArrayList<Image> images) {
        super(context, R.layout.image_list_item, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmartImageView imageView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            imageView = (SmartImageView)inflater.inflate(R.layout.image_list_item, parent, false);
        } else {
            imageView = (SmartImageView)convertView;
            imageView.setImageResource(android.R.color.transparent);
        }
        imageView.setImageUrl(getItem(position).getTbUrl());
        return imageView;
    }
}
