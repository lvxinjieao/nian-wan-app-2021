package com.nian.wan.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.bean.GetPaidInfo;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.DynamicShowPayWays;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.bean.UserIsBindPhoneBean;
import com.nian.wan.app.fragment.main_my.ZFBmessage;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.utils.WFTmessage;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值中心-平台币充值
 */
public class PayPTBFragment extends Fragment {
    @BindView(R.id.tv_personal_center_pay_account)
    TextView tvPersonalCenterPayAccount;

    @BindView(R.id.tv_account)
    TextView zhanghao;

    @BindView(R.id.tv_personal_center_pay_balance)
    TextView tvPersonalCenterPayBalance;

    @BindView(R.id.tv_yue)
    TextView yue;


    @BindView(R.id.tv_personal_center_pay_money)
    TextView tvPersonalCenterPayMoney;

    @BindView(R.id.tv_personal_center_pay_rmb)
    TextView tvPersonalCenterPayRmb;

    @BindView(R.id.jine)
    EditText jine;

    @BindView(R.id.clear)
    ImageView clear;

    @BindView(R.id.view)
    View view;

    @BindView(R.id.yiqian)
    RadioButton yiqian;
    @BindView(R.id.wubai)
    RadioButton wubai;
    @BindView(R.id.sanbai)
    RadioButton sanbai;
    @BindView(R.id.yibai)
    RadioButton yibai;

    @BindView(R.id.rg_group)
    RadioGroup rgGroup;

    @BindView(R.id.hb)
    TextView hb;

    @BindView(R.id.llWX)
    RelativeLayout llWX;
    @BindView(R.id.mm)
    ImageView mm;
    @BindView(R.id.cb_pay_wx)
    CheckBox weixin;

    @BindView(R.id.llZFB)
    RelativeLayout llZFB;
    @BindView(R.id.zz)
    ImageView zz;
    @BindView(R.id.zhifubao)
    CheckBox zhifubao;



    @BindView(R.id.pay)
    TextView pay;

    private String qian;
    private UserInfo loginUser;
    private DynamicShowPayWays dynamicShowPayWays;
    private String TAG = "PayPTBFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_ptb, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    public void initView() {
        dynamicShowPayWays();
        getUserInfo();
        weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    zhifubao.setChecked(false);
                }
            }
        });
        zhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin.setChecked(false);
                }
            }
        });

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvPersonalCenterPayRmb.setVisibility(View.VISIBLE);
                switch (checkedId) {
                    case R.id.yiqian:
                        jine.setText("1000");
                        hb.setText("1000");
                        break;
                    case R.id.wubai:
                        jine.setText("500");
                        hb.setText("500");
                        break;
                    case R.id.sanbai:
                        jine.setText("300");
                        hb.setText("300");
                        break;
                    case R.id.yibai:
                        jine.setText("100");
                        hb.setText("100");
                        break;
                }
            }
        });

        jine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    clear.setVisibility(View.VISIBLE);
                    hb.setText(s.toString());
                } else {
                    clear.setVisibility(View.GONE);
                    hb.setText("0");
                }
                if (s.length() <= 0) {
                    tvPersonalCenterPayRmb.setVisibility(View.GONE);
                } else {
                    tvPersonalCenterPayRmb.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @OnClick({R.id.pay, R.id.clear, R.id.llWX, R.id.llZFB})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.clear:
                yiqian.setChecked(false);
                wubai.setChecked(false);
                sanbai.setChecked(false);
                yibai.setChecked(false);
                jine.setText("");
                hb.setText("0.0");
                clear.setVisibility(View.GONE);
                break;

            case R.id.llWX: //微信支付RealtiveLayout
                weixin.setChecked(true);
                break;

            case R.id.llZFB://支付宝RealtiveLayout
                zhifubao.setChecked(true);
                break;

            case R.id.pay://支付
                Pay();
                break;
        }
    }

    /**
     * 根据后台设置显示支付方式
     */
    public void dynamicShowPayWays() {
        Type type = new TypeToken<HttpResult<DynamicShowPayWays>>() {
        }.getType();
        new HttpUtils<DynamicShowPayWays>(type, HttpConstant.API_PERSONAL_PAY_WAYS, null, "充值平台币根据后台设置显示支付方式", true) {

            @Override
            protected void _onSuccess(DynamicShowPayWays bean, String msg) {
                dynamicShowPayWays = bean;
                if (1 != dynamicShowPayWays.getWeixin()) {
                    llWX.setVisibility(View.GONE);
                }
                if (!"1".equals(dynamicShowPayWays.getAlipay())) {
                    llZFB.setVisibility(View.GONE);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);

            }

            @Override
            protected void _onError() {

            }
        };
    }


    private void getUserInfo() {
        if (Utils.getPersistentUserInfo() != null) {
            zhanghao.setText(Utils.getPersistentUserInfo().account);
            getUser();
        }
    }


    private void Pay() {
        qian = jine.getText().toString();
        if (TextUtils.isEmpty(qian)) {
            ToastUtil.showToast("请输入充值金额");
            return;
        }
        if (qian.equals("0")) {
            ToastUtil.showToast("充值金额必须大于0");
            return;
        }

        loginUser = Utils.getLoginUser(); //推广员id

        if (zhifubao.isChecked()) {
            ZFB();
        } else if (weixin.isChecked()) {
            WX();
        }
    }


    /**
     * 微信支付
     */
    private void WX() {
        GetPaidInfo getPaidInfo = new GetPaidInfo();
        getPaidInfo.pay_amount = qian;                                  //应付金额
        getPaidInfo.good_type = 1;                                      //1平台币   2绑币
        getPaidInfo.user_sign = Utils.getLoginUser().token;             //用户token
        getPaidInfo.promote_id = new PromoteUtils().getPromoteId();     //推广员id
        getPaidInfo.code = "2";                                         //折扣充值
        getPaidInfo.good_name = "平台币充值";
        getPaidInfo.body = "充值"+qian+"平台币";

        WFTmessage wftmessage = new WFTmessage(getActivity());
        wftmessage.weixinpay(getPaidInfo);
    }

    /**
     * 支付宝支付
     */
    private void ZFB() {
        GetPaidInfo getPaidInfo = new GetPaidInfo();
        getPaidInfo.good_name = "平台币充值";
        getPaidInfo.body = "充值"+qian+"平台币";
        getPaidInfo.pay_amount = qian;                              //应付金额
        getPaidInfo.user_sign = Utils.getLoginUser().token;         //用户token
        getPaidInfo.promote_id = new PromoteUtils().getPromoteId(); //推广员id
        getPaidInfo.code = "2";                                     //平台币充值

        ZFBmessage zfBmessage = new ZFBmessage(getActivity());
        zfBmessage.pay(getPaidInfo);
    }




    /**
     * 请求用户信息
     */
    private void getUser() {
        if (Utils.getPersistentUserInfo() != null) {
            Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {}.getType();

            new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, TAG + "充值平台币用户综合信息", true) {

                @Override
                protected void _onSuccess(UserIsBindPhoneBean bean, String msg) {
                    yue.setText(bean.getBalance() + " 平台币");
                }

                @Override
                protected void _onError(String message, int code) {
                }

                @Override
                protected void _onError() {
                }
            };
        }
    }

}
