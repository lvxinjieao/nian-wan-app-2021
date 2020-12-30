package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.AddressBean;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.HomeStoreGoodsBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogStorePublic;
import com.nian.wan.app.view.PopupWindow_address;
import com.nian.wan.app.view.PopupWindow_xuni;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;

import org.json.JSONObject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


/**
 * @Author: XiaYuShi
 * @Date: 2017/11/24
 * @Description: 首页商城商品详情
 * @Modify By:闫冰
 * @ModifyDate:
 */
public class HomeStoreGoodsDetailActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.ee)
    ImageView ee;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.tv_home_gift_detail_title)
    TextView tvHomeGiftDetailTitle;
    @BindView(R.id.img_home_gift_detail_game_pic)
    ImageView imgHomeGiftDetailGamePic;
    @BindView(R.id.tv_home_store_detail_game_name)
    TextView tvHomeStoreDetailGameName;
    @BindView(R.id.tv_home_store_detail_need_cost)
    TextView tvHomeStoreDetailNeedCost;
    @BindView(R.id.tv_home_store_detail_inventory)
    TextView tvHomeStoreDetailInventory;
    @BindView(R.id.tv_home_store_detail_valid_score)
    TextView tvHomeStoreDetailValidScore;
    @BindView(R.id.tv_home_store_detail_score_not_enough)
    TextView tvHomeStoreDetailScoreNotEnough;
    @BindView(R.id.rl_home_store_goods_decrease)
    RelativeLayout rlHomeStoreGoodsDecrease;
    @BindView(R.id.edt_home_store_get_goods_number)
    TextView edtHomeStoreGetGoodsNumber;
    @BindView(R.id.img_home_store_goods_increase)
    ImageView imgHomeStoreGoodsIncrease;
    @BindView(R.id.rl_home_store_goods_add)
    RelativeLayout rlHomeStoreGoodsAdd;
    @BindView(R.id.tv_home_gift_content)
    TextView tvHomeGiftContent;
    @BindView(R.id.tv_home_gift_detail_step1)
    TextView tvHomeGiftDetailStep1;
    @BindView(R.id.tv_home_gift_detail_step2)
    TextView tvHomeGiftDetailStep2;
    @BindView(R.id.tv_home_gift_detail_step3)
    TextView tvHomeGiftDetailStep3;
    @BindView(R.id.tv_home_gift_detail_step4)
    TextView tvHomeGiftDetailStep4;
    @BindView(R.id.tv_duihuan_title)
    TextView tvDuihuanTitle;
    @BindView(R.id.tv_shiyong)
    TextView tvShiyong;
    @BindView(R.id.tv_home_gift_detail_get_function)
    TextView tvHomeGiftDetailGetFunction;
    /*    @BindView(R.id.tv_home_gift_detail_tianxie)
        TextView tvHomeGiftDetailTianxie;*/
    @BindView(R.id.ll_duihuan_detail)
    RelativeLayout llDuihuanDetail;
    @BindView(R.id.tv_home_gift_detail_duty)
    TextView tvHomeGiftDetailDuty;
    @BindView(R.id.tv_xigu)
    TextView tvXigu;
    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    @BindView(R.id.tv_home_gift_detail_start_game)
    TextView tvHomeGiftDetailStartGame;
    @BindView(R.id.img_home_store_goods_decrease)
    ImageView getImgHomeStoreGoodsDecrease;
    @BindView(R.id.tv_home_gift_detail_get_function_click)
    TextView tvHomeGiftDetailGetFunctionClick;
    @BindView(R.id.ll_cost_step)
    LinearLayout llCostStep;

    private StoreBroadcast storeBroadcast;

    private final int TYPE_POINT_NOT_ENOUGH = 0;  //积分不足
    private final int TYPE_NOT_LOGIN = 1;   //没登录
    private final int TYPE_NO_ADDRESS = 2; //没有收获地址
    private final int TYPE_SUCCESS = 3; //兑换成功
    private final int TYPE_FAIL = 4;    //提交失败
    public static final int RESUME_COST = 5; //继续兑换


    private HomeStoreGoodsBean homeStoreGoodsBean;
    private DialogStorePublic dialogStorePublic;
    private PopupWindow_address popupWindow_address;
    private String goodsName;
    private String kefuQQ; //客服QQ
    private int goodType = 1;  //商品类型，1实物，2虚拟
    private String goodId; //商品id
    private boolean isLogin = false; //用户是否登录
    private int userPoint;  //用户可用点数
    private int price;  //价格
    private int needPoint; //所需点数
    private int kucun;  //库存
    private int num = 1; //兑换数量

    @Override
    public void initView() {
        setContentView(R.layout.activity_home_store_details);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        homeStoreGoodsBean = (HomeStoreGoodsBean) getIntent().getSerializableExtra("goods_detail");
        goodsName = homeStoreGoodsBean.getGood_name();
        tvHomeStoreDetailGameName.setText(goodsName);
    }


    /**
     * 请求商品详情
     */
    private void getDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("gift_id", homeStoreGoodsBean.getId());
        if (Utils.getPersistentUserInfo() != null) {
            isLogin = true;
            map.put("token", Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(handler, HttpConstant.API_SHOP_GIFT_DETAIL, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("商城商品详情返回数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            kucun = jsonData.getInt("number");
                            price = jsonData.getInt("price");
                            goodId = jsonData.getString("id");
                            kefuQQ = jsonData.getString("PC_SET_SERVER_QQ");
                            needPoint = price * num;
                            tvHomeStoreDetailInventory.setText(kucun + "");
                            tvHomeStoreDetailNeedCost.setText(price + "");
                            tvHomeGiftContent.setText(jsonData.getString("good_info"));
                            tvKefu.setText(kefuQQ);
                            Glide.with(x.app()).load(jsonData.getString("detail_cover")).error(R.drawable.default_picture).into(imgHomeGiftDetailGamePic);
                            if (jsonData.getInt("good_type") == 2) {  //虚拟物品
                                llCostStep.setVisibility(View.GONE);
                                goodType = 2;
                                tvDuihuanTitle.setText("使用说明");
                                tvShiyong.setText(jsonData.getString("good_usage"));
                                llDuihuanDetail.setVisibility(View.GONE);
                                tvShiyong.setVisibility(View.VISIBLE);
                            }

                            if (isLogin) {
                                userPoint = jsonData.getInt("shop_point");
                                tvHomeStoreDetailValidScore.setText(userPoint + "");
                                if (price > userPoint) {
                                    tvHomeStoreDetailScoreNotEnough.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("解析商品详情异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };


    /**
     * 按钮点击监听
     *
     * @param view
     */
    @OnClick({R.id.back, R.id.rl_home_store_goods_decrease, R.id.rl_home_store_goods_add,
            R.id.tv_kefu, R.id.tv_home_gift_detail_start_game,
            R.id.tv_home_gift_detail_get_function_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:  //返回
                finish();
                break;
            case R.id.rl_home_store_goods_decrease:  //兑换数量 减
                if (num > 1) {
                    num = num - 1;
                    edtHomeStoreGetGoodsNumber.setText(num + "");
                    needPoint = price * num;
                    if (needPoint <= userPoint) {
                        tvHomeStoreDetailScoreNotEnough.setVisibility(View.GONE);
                    }
                }
                Log.e("当前库存:" + kucun + "", "当前兑换:" + num + "");
                if (num == 1) {
                    getImgHomeStoreGoodsDecrease.setBackgroundResource(R.drawable.mall_commodity_reduce_not);
                }
                break;
            case R.id.rl_home_store_goods_add:  //兑换数量 加
                if (goodType == 2) {
                    ToastUtil.showToast("虚拟物品一次只能兑换一个~");
                    return;
                }
                if (num < kucun) {
                    getImgHomeStoreGoodsDecrease.setBackgroundResource(R.drawable.mall_commodity_reduce);
                    num = num + 1;
                    edtHomeStoreGetGoodsNumber.setText(num + "");
                    needPoint = price * num;
                    if (needPoint > userPoint) {
                        if (isLogin) {
                            tvHomeStoreDetailScoreNotEnough.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if (num == kucun) {
                    imgHomeStoreGoodsIncrease.setBackgroundResource(R.drawable.mall_commodity_add_not);
                }
                break;
            case R.id.tv_kefu:  //联系客服
//                PhoneUtils.dial(kefuQQ);
                Utils.talkWithQQCustom(this);
           /*     if (checkApkExist(this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="
                                    + kefuQQ + "&version=1")));
                } else {
                    ToastUtil.showToast("本机未安装QQ应用");
                }*/
                break;
            case R.id.tv_home_gift_detail_start_game:  //立即兑换
                Log.e("所需积分", needPoint + "," + "yonghu" + userPoint + "");
                if (Utils.getPersistentUserInfo() == null) {
                    dialogStorePublic = new DialogStorePublic(this, R.style.MyDialogStyle,
                            "暂未登录", "您还未登录帐号，暂时无法兑换商品",
                            TYPE_NOT_LOGIN);
                    dialogStorePublic.show();
                    return;
                }
                if (needPoint > userPoint) {
                    dialogStorePublic = new DialogStorePublic(this, R.style.MyDialogStyle,
                            "积分不足", "当前账户可用积分" + userPoint + "，暂时无法兑换"
                            , TYPE_POINT_NOT_ENOUGH);
                    dialogStorePublic.show();
                    return;
                }

                if (goodType == 1) {
                    getAddress(); //获取用户收获地址,准备吊起兑换窗口
                } else {
                    //兑换物品虚拟物品
                    exChange();
                }
                break;
            //点此填写或修改收货地址
            case R.id.tv_home_gift_detail_get_function_click:
                Intent intent = new Intent(this, AddressActivity.class);
                this.startActivity(intent);
                break;
        }
    }


    /**
     * 虚拟商品兑换
     */
    private void exChange() {
//        Map<String, String> map = new HashMap<>();
//        map.put("token", Utils.getPersistentUserInfo().token);
//        map.put("good_id", goodId);
//        map.put("num", num + "");
//        HttpCom.POST(exchangeHandler, HttpCom.API_SHOP_EXCHANGE, map, false);
        PopupWindow_xuni popupWindow_xuni = new PopupWindow_xuni(HomeStoreGoodsDetailActivity.this, goodId, goodsName, num + "", needPoint + "");
        popupWindow_xuni.showAtLocation(HomeStoreGoodsDetailActivity.this.findViewById(R.id.layout_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    @SuppressLint("HandlerLeak")
    Handler exchangeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("商品详情页面", "兑换商品返回数据:" + msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            String data = jsonObject.getString("data");
                            dialogStorePublic = new DialogStorePublic(HomeStoreGoodsDetailActivity.this, R.style.MyDialogStyle, "兑换成功", "激活码为：" + data, TYPE_SUCCESS);
                            dialogStorePublic.show();
                            getDetail();
                        }
                    } catch (Exception e) {
                        Log.e("商品详情页面", "兑换商品返回数据异常:" + e.toString());
                        dialogStorePublic = new DialogStorePublic(HomeStoreGoodsDetailActivity.this, R.style.MyDialogStyle, "提交失败", "可能是网络原因，请重新提交", TYPE_FAIL);
                        dialogStorePublic.show();
                    }
                    break;
                case 2:
                    dialogStorePublic = new DialogStorePublic(HomeStoreGoodsDetailActivity.this, R.style.MyDialogStyle, "提交失败", "可能是网络原因，请重新提交", TYPE_FAIL);
                    dialogStorePublic.show();
                    break;
            }
        }
    };


    /**
     * 获取用户收货地址
     */
    private void getAddress() {
        Type type = new TypeToken<HttpResult<AddressBean>>() {}.getType();
        new HttpUtils<AddressBean>(type, HttpConstant.API_PERSONAL_ADDRESS_USER, null, "获取用户收货地址", true) {
            @Override
            protected void _onSuccess(AddressBean s, String msg) {
                popupWindow_address = new PopupWindow_address(HomeStoreGoodsDetailActivity.this, goodId, goodsName, num + "",
                        needPoint + "",s);
                popupWindow_address.showAtLocation(HomeStoreGoodsDetailActivity.this.findViewById(R.id.layout_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                        0, 0); //设置layout在PopupWindow中显示的位置
            }

            @Override
            protected void _onError(String message, int code) {
                if(code==1111){
                    if (popupWindow_address != null && popupWindow_address.isShowing()) {
                        popupWindow_address.dismiss();
                    }
                    dialogStorePublic = new DialogStorePublic(HomeStoreGoodsDetailActivity.this, R.style.MyDialogStyle, "暂无收货地址", "缺少收货地址，兑换商品无法顺利送达哦", TYPE_NO_ADDRESS);
                    dialogStorePublic.show();
                }
            }

            @Override
            protected void _onError() {
            }
        };
    }


    /**
     * 一键唤起QQ聊天界面
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 登录监听
     */
    private void regsiterPersonalBroadcast() {
        storeBroadcast = new StoreBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(this).registerReceiver(storeBroadcast, intentFilter);
    }

    private class StoreBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    getDetail();
                    break;
                case DialogLoginActivity.EVENT_LOGIN_FILED:
//                    ToastUtil.showToast("登陆失败");
                    break;
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    break;
                //继续兑换
                case RESUME_COST:
                    HomeStoreGoodsDetailActivity.this.finish();
                    break;

            }
        }

    }

    @Subscribe     //此注解用于接收EventBus(兑换实物成功，刷新商品信息)
    public void getEventBus(EvenBean bean) {
        switch (bean.a) {
            case TYPE_SUCCESS:
                getDetail();
                break;
        }
        if (bean.b == 6) {
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        regsiterPersonalBroadcast();
        getDetail();
        if (popupWindow_address != null && popupWindow_address.isShowing()) {
            popupWindow_address.dismiss();
            getAddress(); //获取用户收获地址,准备吊起兑换窗口
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}


