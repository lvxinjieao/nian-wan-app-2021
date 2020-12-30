package com.nian.wan.app.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nian.wan.app.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ScreenActivity extends Activity {

    private ViewPager viewPager;
    private ArrayList<String> imageUrls = null;

    private ImageScaleAdapter imageScaleAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finish();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageUrls = this.getIntent().getExtras().getStringArrayList("imgs_urls");

        if (imageUrls != null && imageUrls.size() >= 0) {
            imageScaleAdapter = new ImageScaleAdapter();
            viewPager.setAdapter(imageScaleAdapter);
        }
    }

    public int x1 = 0;
    public int x2 = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = (int) event.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2 = (int) event.getX();

                if (x1 == x2)
                    handler.sendEmptyMessage(0);

                break;
        }

        return super.dispatchTouchEvent(event);
    }

    class ImageScaleAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imageView = new ImageView(ScreenActivity.this);
            //使用glide加载图片
            Glide.with(ScreenActivity.this).load(imageUrls.get(position)).centerCrop().into(imageView);
            //添加到容器container
            container.addView(imageView);
            //返回这个view
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //从容器中移除这个view
            container.removeView((View) object);
        }
    }


}


