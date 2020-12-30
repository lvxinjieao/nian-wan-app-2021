package com.nian.wan.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.utils.ToastUtil;
;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/12/14.
 */

public class PopupWindow_xuni extends PopupWindow {

    @BindView(R.id.btn_close)
    RelativeLayout btnClose;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.layout_address)
    LinearLayout layoutAddress;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_need_point)
    TextView tvNeedPoint;
    @BindView(R.id.btn_duihuan)
    TextView btnDuihuan;
    @BindView(R.id.layout_main)
    LinearLayout layoutMain;
    @BindView(R.id.tv_num)
    TextView tvNum;

    private Activity mActivity;
    private String GoodsId;
    private String GoodsName;
    private String GoodsNum;
    private String GoodsPoint;
    private String UserAddress;
    private View mMenuView;

    private final int TYPE_SUCCESS = 3; //兑换成功
    private final int TYPE_FAIL = 4;    //兑换失败
    private final int TYPE_NOT_ENOUGH = 5;//库存不足


    public PopupWindow_xuni(Activity activity, String goodsId, String goodsName, String goodsNum, String goodsPoint) {
        super(activity);
        this.mActivity = activity;
        this.GoodsId = goodsId;
        this.GoodsName = goodsName;
        this.GoodsNum = goodsNum;
        this.GoodsPoint = goodsPoint;

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.5f;
        activity.getWindow().setAttributes(params);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_xuni, null);
        ButterKnife.bind(this, mMenuView);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.layout_main).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        tvGoodsName.setText(goodsName);
        tvNum.setText("×" + goodsNum);
        tvNeedPoint.setText(goodsPoint);
        tvAddress.setText(Utils.getPersistentUserInfo().account);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = 1f;
        mActivity.getWindow().setAttributes(params);
    }

    @OnClick({R.id.btn_close, R.id.btn_duihuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.btn_duihuan:
                get();
                break;
        }
    }


    /**
     * 兑换
     */
    private void get() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Utils.getPersistentUserInfo().token);
        map.put("good_id", GoodsId);
        map.put("num", GoodsNum + "");
        HttpConstant.POST(exchangeHandler, HttpConstant.API_SHOP_EXCHANGE, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler exchangeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("PopupWindow_address", "兑换商品返回数据:" + msg.obj
                                .toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            EvenBean bean = new EvenBean();
                            bean.a = TYPE_SUCCESS;
                            EventBus.getDefault().post(bean);
                            dismiss();
                            String data = jsonObject.getString("data");
                            DialogStorePublic dialogStorePublic = new DialogStorePublic(mActivity, R.style.MyDialogStyle, "兑换成功", "激活码为：" + data, TYPE_SUCCESS);
                            dialogStorePublic.show();
                        } else {
                            dismiss();
                            if (1109 == jsonObject.getInt("code")) {
                                new DialogStorePublic(mActivity, R.style.MyDialogStyle,
                                        "库存不足", "可减少兑换数量或选择其他商品",
                                        TYPE_NOT_ENOUGH).show();
                            } else {
                                ToastUtil.showToast(jsonObject.getString("msg"));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("PopupWindow_address", "兑换商品返回数据异常:" + e.toString());
                        DialogStorePublic dialogStorePublic = new DialogStorePublic(mActivity,
                                R.style.MyDialogStyle, "提交失败", "可能是网络原因，" +
                                "请重新提交", TYPE_FAIL);
                        dialogStorePublic.show();
                    }
                    break;
                case 2:
                    DialogStorePublic dialogStorePublic = new DialogStorePublic(mActivity,
                            R.style.MyDialogStyle, "提交失败", "可能是网络原因，" +
                            "请重新提交", TYPE_FAIL);
                    dialogStorePublic.show();
                    break;
            }
        }
    };
}
