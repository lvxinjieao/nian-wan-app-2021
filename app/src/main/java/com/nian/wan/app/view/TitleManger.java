package com.nian.wan.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 齐天大圣 on 2016/10/10.
 */

public class TitleManger {

    private static TitleManger titleBarManger=null;
    public Activity con;
    private RelativeLayout back;
    private TextView title;
    private View view;

    /**
     * 获取一个实例
     * @return
     */
    public static TitleManger getInsetance() {
        if (titleBarManger==null){
            titleBarManger= new TitleManger();
        }
        return  titleBarManger;
    }

    /**
     * set一个上下文
     *
     * @param con
     */
    public void setContext(final Activity con) {
        this.con = con;
        back = (RelativeLayout)con.findViewById(findId(con, "back"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con.finish();
            }
        });
    }

    public void setName(String name){
        title = (TextView)con.findViewById(findId(con, "name"));
        title.setText(name);
    }


    /**
     * 根据上下文获取资源ID
     *
     * @param context
     * @param id
     * @return
     */
    public int findId(Context context, String id) {
        if (context != null) {
            Resources resource = context.getResources();
            return resource.getIdentifier(id, "id", context.getPackageName());
        }
        return 0;
    }
}
