package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.activity.Type_Activity;
import com.nian.wan.app.activity.WebActivity;
import com.nian.wan.app.bean.IsCheckBean;
import com.nian.wan.app.bean.MyZuJiBean;
import com.nian.wan.app.bean.StartGameBean;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 足迹
 */
public class ZujiHolder extends BaseHolder<MyZuJiBean.MsgEntity> implements View.OnClickListener{
    @BindView(R.id.xuan)
    CheckBox xuan;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ren)
    TextView ren;
    @BindView(R.id.xiangsi)
    TextView xiangsi;
    @BindView(R.id.paly)
    TextView paly;
    private int anInt;
    private Activity mContext;
    private MyZuJiBean.MsgEntity listZuji;
    private StartGameBean startGameBean;
    private String gameURL;

    @Override
    protected void refreshView(final MyZuJiBean.MsgEntity msgEntity, int position, Activity activity) {
        this.listZuji =msgEntity;
        this.mContext =activity;
        anInt = position;

        Glide.with(x.app()).load(listZuji.getIcon()).error(R.drawable.default_picture).transform(new RoundedCorners(10)).into(icon);
        name.setText(listZuji.getGame_name());
        ren.setText(listZuji.getPlay_num());

        xuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    IsCheckBean isCheckBean = new IsCheckBean();
                    isCheckBean.id = msgEntity.getId();
                    isCheckBean.ischeck = true;
                    isCheckBean.i = 2;
                    EventBus.getDefault().post(isCheckBean);
                }else{
                    IsCheckBean isCheckBean = new IsCheckBean();
                    isCheckBean.id = msgEntity.getId();
                    isCheckBean.ischeck = false;
                    isCheckBean.i = 2;
                    EventBus.getDefault().post(isCheckBean);
                }
            }
        });
    }


    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.holder_collection, null);
        ButterKnife.bind(this, inflate);
        startGameBean = new StartGameBean();
        inflate.setTag(this);
        return inflate;
    }

    public void hide() {
        xuan.setVisibility(View.GONE);
        paly.setVisibility(View.VISIBLE);
    }

    public void display() {
        xuan.setVisibility(View.VISIBLE);
        paly.setVisibility(View.GONE);
    }


    public void setBu(boolean a) {
        xuan.setChecked(a);
    }

    public boolean getBu() {
        return xuan.isChecked();
    }

    public String getId() {
        return anInt +"";
    }

    @OnClick({R.id.xiangsi, R.id.paly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiangsi:
                Intent intent = new Intent(mContext, Type_Activity.class);
                intent.putExtra("name","更多相似游戏");
                intent.putExtra("type_id",Integer.valueOf(listZuji.getGame_type_id()));
                intent.putExtra("num","");
                mContext.startActivity(intent);
                break;
            case R.id.paly:
                StartGame();
                break;
        }
    }


    //开始游戏网络请求
    private void StartGame(){
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo!=null){
            Map<String,String> map = new HashMap<>();
            map.put("game_id",listZuji.getId());
            map.put("token",userInfo.token);
            HttpConstant.POST(GameHandler, HttpConstant.StartGame,map,false);
        }else {
            new DialogGoLogin(mContext,R.style.MyDialogStyle,"登录后可开始游戏~").show();
        }
    }


    /**
     * 开始游戏
     */
    @SuppressLint("HandlerLeak")
    Handler GameHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.e("开始游戏传来的数据",msg.obj.toString());
                    startGameBean = new Gson().fromJson(msg.obj.toString(),StartGameBean.class);
                    if (startGameBean.getStatus()==1){
                        if (startGameBean.getMsg().getUrl()!=null){
                            gameURL = startGameBean.getMsg().getUrl();
                            Intent intent =new Intent(getContentView().getContext(),WebActivity.class);
                            intent.putExtra("url",gameURL);
                            mContext.startActivity(intent);
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


    /**
     * 找相似
     */

}
