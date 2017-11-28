package com.github.anastasiazhukova.project.demoapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.ImageLoader;

public class ImageLoaderSampleActivity extends AppCompatActivity {

    private String mUrl = "http://lorempixel.com/1800/1800/sports/1/";
    private ImageView mView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_sample);

        mView = findViewById(R.id.sample_image_view);
        ImageLoader.getInstance().load(mUrl).into(mView).start();
    }

}
