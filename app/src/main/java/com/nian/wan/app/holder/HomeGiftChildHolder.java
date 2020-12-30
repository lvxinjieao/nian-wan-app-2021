package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.HomeGiftBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGetGiftFailed;
import com.nian.wan.app.view.DialogGetGiftSuccess;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.DialogWeChatOfficialAccounts;
import com.google.gson.reflect.TypeToken;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeGiftChildHolder extends BaseHolder<HomeGiftBean.GblistBean> {

    @BindView(R.id.tv_home_gift_name)
    TextView giftName;

    @BindView(R.id.tv_home_gift_description)
    TextView giftDescription;

    @BindView(R.id.btn_home_gift_copy_gift_code)
    TextView btnGet;

    @BindView(R.id.vw_line_small)
    View lineSmall;

    private Activity activity;
    private HomeGiftBean.GblistBean bean;

    @Override
    protected void refreshView(final HomeGiftBean.GblistBean bean, int position, Activity activity) {
        this.activity = activity;
        this.bean = bean;
        giftName.setText(bean.getGiftbag_name());
        giftDescription.setText(bean.getDesribe());
        if (bean.getReceived() == 1){
            btnGet.setTextColor(activity.getResources().getColor(R.color.font_gray));
            btnGet.setText("已领取");
            btnGet.setBackgroundResource(R.drawable.zhi_jiao_gray);
        }else {
            btnGet.setTextColor(activity.getResources().getColor(R.color.zhuse_lan));
            btnGet.setText("领取");
            btnGet.setBackgroundResource(R.drawable.bian_kuang_green);
        }
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGiftRequest(bean);
            }
        });
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.home_gift_child_item, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }


    /**
     * 领取礼包
     */
    private void getGiftRequest(final HomeGiftBean.GblistBean bean) {
        if (null != Utils.getPersistentUserInfo()) {
            Map<String, String> giftParams = new HashMap<>();
            giftParams.put("gift_id", bean.getGift_id());
            giftParams.put("promote_id", new PromoteUtils().getPromoteId());
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_GIFT_GET, giftParams, "HomeGiftChildHolder领取礼包返回数据", true) {
                @Override
                protected void _onSuccess(String code, String msg) {
                    new DialogGetGiftSuccess(activity, R.style.MyDialogStyle, code).show();
                    btnGet.setTextColor(activity.getResources().getColor(R.color.font_gray));
                    btnGet.setText("已领取");
                    btnGet.setBackgroundResource(R.drawable.zhi_jiao_gray);
                    bean.setReceived(1);
                }

                @Override
                protected void _onError(String message, int code) {
                    if (code == 1117) {
                        DialogWeChatOfficialAccounts dialog = new DialogWeChatOfficialAccounts(activity, R.style.MyDialogStyle, message);
                        dialog.show();
                    } else {
                        new DialogGetGiftFailed(activity, R.style.MyDialogStyle, retryGetGiftHandler,message).show();
                    }
                }

                @Override
                protected void _onError() {
                    new DialogGetGiftFailed(activity, R.style.MyDialogStyle, retryGetGiftHandler,"网络异常").show();
                }
            };
        } else {
            new DialogGoLogin(activity, R.style.MyDialogStyle, "暂时无法领取礼包哦 ~T_T~").show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler retryGetGiftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    getGiftRequest(bean);
                    break;
            }
        }
    };
}
