package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mc.developmentkit.bean.GetPaidInfo;
import com.mc.developmentkit.bean.LinkAgeInfo;
import com.mc.developmentkit.callback.AliPayListener;
import com.mc.developmentkit.callback.LinkAgeListener;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.LinkAgeWindow;
import com.nian.wan.app.R;
import com.nian.wan.app.fragment.main_my.ZFBmessage;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.utils.WFTmessage;

import org.json.JSONObject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

;

/**
 * 描述：折扣充值Fragment
 * 作者：闫冰
 * 时间: 2018-07-27 10:25
 */
public class PayZheKouPay extends Fragment implements AliPayListener {
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_game_name)
    TextView tvGameName;
    @BindView(R.id.rl_choose_game)
    RelativeLayout rlChooseGame;
    @BindView(R.id.ll_xuanze)
    LinearLayout llXuanze;
    @BindView(R.id.et_jine)
    EditText etJine;
    @BindView(R.id.tv_bili)
    TextView tvBili;
    @BindView(R.id.tv_shifujine)
    TextView tvShifujine;
    @BindView(R.id.tv_hudePTB)
    TextView tvHudePTB;
    @BindView(R.id.mm)
    ImageView mm;
    @BindView(R.id.cb_pay_wx)
    CheckBox cbPayWx;
    @BindView(R.id.llWX)
    RelativeLayout llWX;
    @BindView(R.id.zz)
    ImageView zz;
    @BindView(R.id.cb_pay_zfb)
    CheckBox cbPayZfb;
    @BindView(R.id.llZFB)
    RelativeLayout llZFB;
//    @BindView(R.id.img_personal_center_jinzhu_pay)
//    ImageView imgPersonalCenterJinzhuPay;
//    @BindView(R.id.cb_pay_jinzhu)
//    CheckBox cbPayJinzhu;
//    @BindView(R.id.llJZ)
//    RelativeLayout llJZ;
    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.tv_personal_center_pay_rmb)
    TextView tvPersonalCenterPayRmb;
    @BindView(R.id.clear)
    ImageView clear;

    private String TAG = "PayZheKouPay";
    private Double discount = 10.0;
    private List<LinkAgeInfo> linkAgeInfos;
    private LinkAgeWindow linkAgeWindow;
    private String game_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_zhekou, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (Utils.getPersistentUserInfo() != null) {
            tvAccount.setText(Utils.getPersistentUserInfo().account);
        }
        cbPayZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPayWx.setChecked(false);
//                    cbPayJinzhu.setChecked(false);
                }
            }
        });
        cbPayWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPayZfb.setChecked(false);
//                    cbPayJinzhu.setChecked(false);
                }
            }
        });
//        cbPayJinzhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    cbPayZfb.setChecked(false);
//                    cbPayWx.setChecked(false);
//                }
//            }
//        });

        etJine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!"".equals(s1)) {
                    Double integer = Double.valueOf(s1);
                    Double i = integer * (discount / 10);
                    DecimalFormat df = new DecimalFormat("######0.00");
                    String format = df.format(i);
                    tvShifujine.setText(format + "元");
                    tvHudePTB.setText(etJine.getText() + "个");
                    clear.setVisibility(View.VISIBLE);
                } else {
                    tvShifujine.setText("");
                    tvHudePTB.setText("");
                    clear.setVisibility(View.GONE);
                }

                if (s.length() <= 0) {
                    tvPersonalCenterPayRmb.setVisibility(View.GONE);
                } else {
                    tvPersonalCenterPayRmb.setVisibility(View.VISIBLE);
                }
            }
        });


        HashMap<String, String> map = new HashMap<>();
        map.put("sdk_version", "1");
        map.put("token", Utils.getPersistentUserInfo().token);
        HttpConstant.POST(handler, HttpConstant.DCGameURL, map, false);
    }


    private void XuanZe() {
        if (linkAgeInfos != null) {
            if (linkAgeWindow == null) {
                linkAgeWindow = new LinkAgeWindow(getActivity());
            }
            /**
             * 参数依次为：宽度，高度，显示位置，List（LinkAge），回调，是否显示联动
             */
            linkAgeWindow.showPop(llXuanze.getWidth()-50, 500, llXuanze, linkAgeInfos, linkAgeListener, false);
            linkAgeWindow.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        } else {
            ToastUtil.showToast("当前无代充游戏");
        }
    }

    /**
     * 获取可代充游戏列表
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    linkAgeInfos = HttpUtils.DNSGameCanPay(msg.obj.toString());
                    break;
                case 2:
                    Log.e("获取可代充游戏列表","错误");
                    break;
                default:
                    break;
            }
        }
    };


    LinkAgeListener linkAgeListener = new LinkAgeListener() {
        @Override
        public void checkedResult(String s, int i) {
            game_id = i + "";
            tvGameName.setText(s);       //选中的游戏设置在TextView上
            tvGameName.setTextColor(ContextCompat.getColor(x.app(), R.color.zi_hui));
            HashMap<String, String> map = new HashMap<>();
            map.put("game_id", i + "");
            HttpConstant.POST(zHandler, HttpConstant.DiscountURL, map, false);
        }
    };

    /**
     * 获取折扣
     */
    @SuppressLint("HandlerLeak")
    Handler zHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("获取折扣信息", msg.obj.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        discount = jsonObject.getDouble("data");
                        if (discount != 0) {
                            tvShifujine.setText("");
                            etJine.setText("");
                            tvHudePTB.setText("");
                            tvBili.setText(discount + "折");
                        } else {
                            tvBili.setText(10.00 + "折");
                            discount = 10.00;
                            etJine.setText("");
                            tvHudePTB.setText("");
                        }
                    } catch (Exception e) {
                        Log.e("获取折扣信息异常", e.toString());
                    }
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }
    };


    @OnClick({R.id.ll_xuanze, R.id.pay,R.id.llWX, R.id.llZFB,/** R.id.llJZ,*/R.id.clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_xuanze:
                XuanZe();
                break;
            case R.id.pay:
                Pay();
                break;
            case R.id.llWX:
                cbPayWx.setChecked(true);
                cbPayZfb.setChecked(false);
                //cbPayJinzhu.setChecked(false);
                break;
            case R.id.llZFB:
                cbPayWx.setChecked(false);
                cbPayZfb.setChecked(true);
                //cbPayJinzhu.setChecked(false);
                break;
//            case R.id.llJZ:
//                cbPayWx.setChecked(false);
//                cbPayZfb.setChecked(false);
//                cbPayJinzhu.setChecked(true);
//                break;
            case R.id.clear:
                etJine.setText("");
                tvShifujine.setText("");
                tvHudePTB.setText("");
                clear.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 点击确认按钮支付
     */
    private void Pay() {
        String jine1 = etJine.getText().toString();
        String s = tvGameName.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showToast("请选择游戏");
            return;
        }
        if (TextUtils.isEmpty(jine1)) {
            ToastUtil.showToast("请输入充值金额");
            return;
        }

        if (jine1.equals("0")) {
            ToastUtil.showToast("充值金额必须大于0");
            return;
        }

        if (!cbPayZfb.isChecked() && !cbPayWx.isChecked()) {
            ToastUtil.showToast("请选择一种支付方式");
            return;
        }

        GetPaidInfo getPaidInfo = new GetPaidInfo();
        getPaidInfo.good_name = "折扣游戏充值";
        getPaidInfo.code = "3";  //折扣充值
        getPaidInfo.pay_amount = etJine.getText().toString();  //金额
        getPaidInfo.body = "充值"+etJine.getText().toString()+"绑币";
        getPaidInfo.user_sign = Utils.getLoginUser().token;        //用户token
        getPaidInfo.game_id = game_id;                   //游戏id

        if (cbPayZfb.isChecked()) {
            ZFB(getPaidInfo);
        } else if (cbPayWx.isChecked()) {
            WX(getPaidInfo);
        }
    }


    /**
     * 微信支付
     */
    private void WX(GetPaidInfo info) {
        try {
            WFTmessage wftmessage = new WFTmessage(getActivity());
            wftmessage.weixinpay(info);
        } catch (Exception e) {
            ToastUtil.showToast("微信支付失败:支付参数异常");
        }

    }

    /**
     * 支付宝支付
     */
    private void ZFB(GetPaidInfo info) {
        try {
            ZFBmessage zfBmessage = new ZFBmessage(getActivity());
            zfBmessage.pay(info);
        } catch (Exception e) {
            ToastUtil.showToast("支付宝支付失败:支付参数异常");
        }

    }

    @Override
    public void zfbPayResult(String s) {
        Log.e("支付宝返回", s);
    }
}
