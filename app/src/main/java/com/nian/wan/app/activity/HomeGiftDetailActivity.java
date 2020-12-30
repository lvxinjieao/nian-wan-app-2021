package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.HomeItemGiftBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.NetworkUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.TimeUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGetGiftFailed;
import com.nian.wan.app.view.DialogGetGiftSuccess;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.DialogWeChatOfficialAccounts;
import com.nian.wan.app.view.DownLoadDialog;
import com.nian.wan.app.view.ShapeImageView;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Author: XiaYuShi
 * @Date: 2017/11/21
 * @Description: 首页礼包详情
 * @Modify By:
 * @ModifyDate:
 */
public class HomeGiftDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.gift_back)
    LinearLayout gift_back;

    @BindView(R.id.tv_home_gift_detail_title)
    TextView tvHomeGiftDetailTitle;
    @BindView(R.id.tv_home_gift_detail_get_gift)
    TextView tvHomeGiftDetailGetGift;
    @BindView(R.id.img_home_gift_detail_game_pic)
    ShapeImageView imgHomeGiftDetailGamePic;
    @BindView(R.id.tv_home_gift_detail_game_name)
    TextView tvHomeGiftDetailGameName;
    @BindView(R.id.tv_home_gift_detail_gift_number)
    TextView tvHomeGiftDetailGiftNumber;
    @BindView(R.id.tv_home_gift_detail_valid_date)
    TextView tvHomeGiftDetailValidDate;
    @BindView(R.id.tv_home_gift_content)
    TextView tvHomeGiftContent;
    @BindView(R.id.tv_home_gift_detail_get_gift_date)
    TextView tvHomeGiftDetailGetGiftDate;
    @BindView(R.id.tv_home_gift_detail_valid_server)
    TextView tvHomeGiftDetailValidServer;
    @BindView(R.id.tv_home_gift_detail_get_function)
    TextView tvHomeGiftDetailGetFunction;
    @BindView(R.id.tv_home_gift_detail_start_game)
    TextView tvHomeGiftDetailStartGame;
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.ee)
    ImageView ee;
    private HomeItemGiftBean homeGiftBean;
    private String giftId;

    @Override
    public void initView() {
        setContentView(R.layout.activity_gift_dettwe);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        giftId = getIntent().getStringExtra("gift_id");
        getGift(giftId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.gift_back, R.id.tv_home_gift_detail_start_game, R.id.tv_home_gift_detail_get_gift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gift_back:
                finish();
                break;
            case R.id.tv_home_gift_detail_start_game:
                if (null != Utils.getPersistentUserInfo()) {

                }
                break;
            case R.id.tv_home_gift_detail_get_gift:
                if (null != Utils.getPersistentUserInfo()) {
                    receiveGift(giftId);
                } else {
                    new DialogGoLogin(HomeGiftDetailActivity.this, R.style.MyDialogStyle, "暂时无法领取礼包哦~T_T~").show();
                }
                break;
        }
    }


    /**
     * 判断当前网络环境并显示下载Dialog
     * @param downURL
     */
    private void showDownLoadDialog(String downURL) {
        boolean isWIFI;
        if (NetworkUtils.getCurrentNetworkType().equals("Wi-Fi")) {
            isWIFI = true;
        }else {
            isWIFI = false;
        }
        DownLoadDialog dialog = new DownLoadDialog(HomeGiftDetailActivity.this, R.style.loading_dialog, downURL,isWIFI);
        dialog.setCancelable(false);
        dialog.show();
    }


    public void modifyView(HomeItemGiftBean bean) {
        homeGiftBean = bean;
        //名称
        tvHomeGiftDetailGameName.setText(homeGiftBean.getGiftbag_name());
        //图标
        if (!TextUtils.isEmpty(homeGiftBean.getIcon())) {
            Glide.with(x.app()).load(homeGiftBean.getIcon()).error(R.drawable.default_picture).into(imgHomeGiftDetailGamePic);
        }
        //剩余数量
        tvHomeGiftDetailGiftNumber.setText(homeGiftBean.getNovice_surplus() + "");
        //描述
        tvHomeGiftContent.setText(homeGiftBean.getDesribe());
        //顶部textView有效时间
        String validDate = "0".equals(homeGiftBean.getEnd_time()) ? "永久" : TimeUtils.timesThree(homeGiftBean.getEnd_time());
        //中部textView有效时间
        tvHomeGiftDetailValidDate.setText(TimeUtils.timesThree(homeGiftBean.getStart_time()) + "~" + validDate);
        tvHomeGiftDetailGetGiftDate.setText(TimeUtils.timesThree(homeGiftBean.getStart_time()) + "~" + validDate);
        //领取方法
        tvHomeGiftDetailGetFunction.setText(homeGiftBean.getDigest());
        //领取按钮
        if (1 == homeGiftBean.getReceived()) {
            tvHomeGiftDetailGetGift.setTextColor(this.getResources().getColor(R.color.font_gray));
            tvHomeGiftDetailGetGift.setText("已领取");
            tvHomeGiftDetailGetGift.setBackground(ContextCompat.getDrawable(this, R.drawable.zhi_jiao_gray));
        }


    }


    /**
     * 领取礼包
     */
    public void receiveGift(final String giftId) {
        Map<String, String> giftParams = new HashMap<>();
        giftParams.put("gift_id", giftId);
        giftParams.put("promote_id", new PromoteUtils().getPromoteId());
        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_GIFT_GET, giftParams, "领取礼包返回数据", true) {
            @Override
            protected void _onSuccess(String code, String msg) {
                tvHomeGiftDetailGetGift.setTextColor(getResources().getColor(R.color.font_gray));
                tvHomeGiftDetailGetGift.setText("已领取");
                tvHomeGiftDetailGetGift.setBackground(ContextCompat.getDrawable(HomeGiftDetailActivity.this, R.drawable.zhi_jiao_gray));
                new DialogGetGiftSuccess(HomeGiftDetailActivity.this, R.style.MyDialogStyle, code).show();
                getGift(giftId);
            }

            @Override
            protected void _onError(String message, int code) {
                if (code == 1117){
                    DialogWeChatOfficialAccounts dialog = new DialogWeChatOfficialAccounts(HomeGiftDetailActivity.this,R.style.MyDialogStyle,message);
                    dialog.show();
                }else {
                    new DialogGetGiftFailed(HomeGiftDetailActivity.this, R.style.MyDialogStyle, retryGetGiftHandler,message).show();
                }
            }

            @Override
            protected void _onError() {
                new DialogGetGiftFailed(HomeGiftDetailActivity.this, R.style.MyDialogStyle, retryGetGiftHandler,"网络缓慢").show();
            }
        };

    }




    /**
     * 获取礼包数据
     */
    private void getGift(String giftId) {
        Map<String, String> giftParams = new HashMap<>();
        giftParams.put("gift_id", giftId);
        Type type = new TypeToken<HttpResult<HomeItemGiftBean>>() {
        }.getType();
        new HttpUtils<HomeItemGiftBean>(type, HttpConstant.API_GIFT_DETAIL, giftParams, "获取礼包详情", true) {
            @Override
            protected void _onSuccess(HomeItemGiftBean bean, String msg) {
                if (bean.getSdk_version().equals("1")){
                    tvHomeGiftDetailStartGame.setText("下载游戏");
                }else {
                    tvHomeGiftDetailStartGame.setText("开始游戏");
                }
                modifyView(bean);
            }

            @Override
            protected void _onError(String message, int code) {
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };


    }


    @SuppressLint("HandlerLeak")
    Handler retryGetGiftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    receiveGift(giftId);
                    break;
            }
        }
    };

}
