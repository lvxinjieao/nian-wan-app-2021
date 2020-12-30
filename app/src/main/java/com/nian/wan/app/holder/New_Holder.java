package com.nian.wan.app.holder;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.activity.WebActivity;
import com.nian.wan.app.bean.HomeGameListBean;
import com.nian.wan.app.bean.StartGameBean;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mc.developmentkit.utils.ToastUtil;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 齐天大圣 on 2016/10/8.
 */

public class New_Holder extends BaseHolder<HomeGameListBean.MsgEntity> {

    private TextView play;
    private ImageView icon;
    private TextView name;
    private Activity activity;
    private TextView ren;
    private HomeGameListBean.MsgEntity msgEntity;
    private StartGameBean startGameBean;

    @Override
    protected void refreshView(HomeGameListBean.MsgEntity msgEntity, int position, Activity activity) {
        this.activity = activity;
        this.msgEntity = msgEntity;

        startGameBean = new StartGameBean();
        Glide.with(x.app()).load(msgEntity.getIcon()).error(R.drawable.default_picture).into(icon);
        name.setText(msgEntity.getGame_name());
        ren.setText(String.valueOf(msgEntity.getPlay_num()));
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartGame();
            }
        });
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.item_new, null);
        play = (TextView)inflate.findViewById(R.id.play);
        icon = (ImageView)inflate.findViewById(R.id.icon);
        name = (TextView)inflate.findViewById(R.id.name);
        ren = (TextView) inflate.findViewById(R.id.ren);
        inflate.setTag(this);
        return inflate;
    }

    //开始游戏网络请求
    private void StartGame(){
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo!=null){
            String token = userInfo.token;
            Map<String,String> map = new HashMap<>();
            map.put("game_id",msgEntity.getId());
            map.put("token",token);
            HttpConstant.POST(GameHandler, HttpConstant.StartGame,map,false);
        }else {
            new DialogGoLogin(activity,R.style.MyDialogStyle,"登录后可开始游戏~").show();
        }

    }


    /**
     * 开始游戏
     */
    Handler GameHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.e("开始游戏传来的数据",msg.obj.toString());
                    startGameBean = new Gson().fromJson(msg.obj.toString(),StartGameBean.class);
                    if (startGameBean.getStatus()==1){
                        if (startGameBean.getMsg().getUrl()!=null){
                            Intent intent = new Intent(activity, WebActivity.class);
                            intent.putExtra("url",startGameBean.getMsg().getUrl());
                            activity.startActivity(intent);
                        }
                    }else {
                        ToastUtil.showToast("游戏链接为空");
                    }
                    break;
                case 2:
                    ToastUtil.showToast("请求失败~");
                    break;
                default:
                    break;
            }
        }
    };

}
