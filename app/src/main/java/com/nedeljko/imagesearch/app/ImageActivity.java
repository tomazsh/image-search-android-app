package com.nedeljko.imagesearch.app;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

public class ImageActivity extends Activity {
    private SmartImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Image image = (Image)getIntent().getExtras().getSerializable("image");
        mImageView = (SmartImageView)findViewById(R.id.imageView);
        mImageView.setImageUrl(image.getUrl());
    }
}
