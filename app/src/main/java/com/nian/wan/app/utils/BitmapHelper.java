package com.nian.wan.app.utils;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;

;

/**
 * Created by 齐天大圣 on 2016/10/9.
 */

public class BitmapHelper {

    public static ImageOptions getBitmapUtils(){
        ImageOptions options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
                // 图片缩放模式
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                 //得到ImageOptions对象
                .build();

        return options;
    }

}
