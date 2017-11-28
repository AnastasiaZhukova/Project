package com.github.anastasiazhukova.project.demoapplication;

import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.ImageLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 21)
public class ImageLoaderTest {

    private ImageLoaderSampleActivity mActivity;

    private final String mUrl = "http://lorempixel.com/400/200/sports/1";
    private ImageView mView;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric
                .buildActivity(ImageLoaderSampleActivity.class)
                .create()
                .start()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(mActivity);
    }

    @Test
    public void load() throws Exception {
        mView = mActivity.findViewById(R.id.sample_image_view);
        ImageLoader.getInstance()
                .load(mUrl, new
                ImageLoader.IListener() {

                    @Override
                    public void onLoadStarted() {

                    }

                    @Override
                    public void onLoadFinished() {
                        assertEquals(mUrl, mView.getTag());
                    }
                })
                .into(mView)
                .start();
    }

}