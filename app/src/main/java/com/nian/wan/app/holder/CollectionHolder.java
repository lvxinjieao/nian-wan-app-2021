package com.nian.wan.app.holder;

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
import com.nian.wan.app.bean.ShouCang;
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
 * 收藏holder
 * Created by Administrator on 2017/4/27.
 */

public class CollectionHolder extends BaseHolder<ShouCang.MsgBean.OneMonthBean> {

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
    @BindView(R.id.type)
    TextView type;

    private StartGameBean startGameBean = new StartGameBean();
    private ShouCang.MsgBean.OneMonthBean listShouCang;
    private Activity mContext;


    @Override
    protected void refreshView(final ShouCang.MsgBean.OneMonthBean oneMonthBean, int position, Activity activity) {
        this.mContext = activity;
        this.listShouCang = oneMonthBean;
        Glide.with(x.app()).load(oneMonthBean.getIcon()).error(R.drawable.default_picture).transform(new RoundedCorners(6)).into(icon);
        name.setText(oneMonthBean.getGame_name());
        ren.setText(oneMonthBean.getPlay_num());
        type.setText(oneMonthBean.getGame_type_name());
        xuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    IsCheckBean isCheckBean = new IsCheckBean();
                    isCheckBean.id = oneMonthBean.getGame_id();
                    isCheckBean.ischeck = true;
                    isCheckBean.i = 1;
                    EventBus.getDefault().post(isCheckBean);
                }else{
                    IsCheckBean isCheckBean = new IsCheckBean();
                    isCheckBean.id = oneMonthBean.getGame_id();
                    isCheckBean.ischeck = false;
                    isCheckBean.i = 1;
                    EventBus.getDefault().post(isCheckBean);
                }
            }
        });
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.holder_collection, null);
        ButterKnife.bind(this, inflate);
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

    @OnClick({R.id.xiangsi, R.id.paly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiangsi:
                Intent intent = new Intent(mContext, Type_Activity.class);
                intent.putExtra("name","更多相似游戏");
                intent.putExtra("type_id",Integer.valueOf(listShouCang.getGame_type_id()));
                intent.putExtra("num","");
                mContext.startActivity(intent);
                break;
            case R.id.paly:
                StartGame();
                break;
        }
    }

    public void setBu(boolean a) {
        xuan.setChecked(a);
    }

    public String getId() {
        return listShouCang.getId() + "";
    }



    //开始游戏网络请求
    private void StartGame(){
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo!=null){
            String token = userInfo.token;
            Map<String,String> map = new HashMap<>();
            map.put("game_id",listShouCang.getGame_id());
            map.put("token",token);
            HttpConstant.POST(GameHandler, HttpConstant.StartGame,map,false);
        }else {
            new DialogGoLogin(mContext,R.style.MyDialogStyle,"登录后可开始游戏~").show();
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
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.putExtra("url",startGameBean.getMsg().getUrl());
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
}
