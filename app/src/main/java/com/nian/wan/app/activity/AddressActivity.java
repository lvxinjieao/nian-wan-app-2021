package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.AddressBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogAddressDeleteConfirm;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-地址
 */
public class AddressActivity extends FragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    //收货人姓名
    @BindView(R.id.tv_address_username)
    TextView mTvAddressUsername;
    //收货人手机
    @BindView(R.id.tv_address_user_mobile)
    TextView mTvAddressUserMobile;
    //收货人地址
    @BindView(R.id.tv_address_user_address)
    TextView mTvAddressUserAddress;
    //编辑
    @BindView(R.id.tv_address_edit)
    TextView mTvAddressEdit;
    //删除
    @BindView(R.id.tv_address_delete)
    TextView mTvAddressDelete;
    //添加收货地址
    @BindView(R.id.tv_address_add_new)
    TextView mTvAddressAddNew;
    //未添加收货地址
    @BindView(R.id.ll_address_none)
    LinearLayout mLlAddressNone;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.ll_address_main)
    LinearLayout llAddressMain;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userDetailAddress;
    public static final int CONFIRM_DELETE = 0;

    @SuppressLint("HandlerLeak")
    Handler transferActionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CONFIRM_DELETE:
                    delAddress();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiving_address);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("收货地址");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    @OnClick({R.id.back, R.id.tv_address_edit, R.id.tv_address_delete, R.id.tv_address_add_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            //编辑地址
            case R.id.tv_address_edit:
                Intent intentEdit = new Intent(AddressActivity.this,
                        NewAddressActivity.class);
                intentEdit.putExtra("isHaveAddress", true);
                intentEdit.putExtra("userName", userName);
                intentEdit.putExtra("userPhone", userPhone);
                intentEdit.putExtra("userAddress", userAddress);
                intentEdit.putExtra("userDetailAddress", userDetailAddress);
                startActivity(intentEdit);
                break;
            //删除地址
            case R.id.tv_address_delete:
                new DialogAddressDeleteConfirm(this, R.style.MyDialogStyle,
                        transferActionHandler).show();
                break;
            //添加地址
            case R.id.tv_address_add_new:
                Intent intentAdd = new Intent(AddressActivity.this,
                        NewAddressActivity.class);
                intentAdd.putExtra("isHaveAddress", false);
                startActivity(intentAdd);
                break;
        }
    }


    /**
     * 获取用户收货地址
     */
    private void getAddress() {

        Type type = new TypeToken<HttpResult<AddressBean>>() {}.getType();
        new HttpUtils<AddressBean>(type, HttpConstant.API_PERSONAL_ADDRESS_USER, null, "获取用户收货地址", true) {
            @Override
            protected void _onSuccess(AddressBean s, String msg) {
                userName = s.getConsignee();
                userPhone = s.getConsignee_phone();
                userAddress = s.getConsignee_address();
                userDetailAddress = s.getDetailed_address();

                mTvAddressUsername.setText(userName);
                mTvAddressUserMobile.setText(userPhone);
                mTvAddressUserAddress.setText(userAddress+userDetailAddress);
                llAddressMain.setVisibility(View.VISIBLE);
                mTvAddressAddNew.setVisibility(View.GONE);
                mLlAddressNone.setVisibility(View.GONE);
            }

            @Override
            protected void _onError(String message, int code) {
                if(code==1111){
                    llAddressMain.setVisibility(View.GONE);
                    mLlAddressNone.setVisibility(View.VISIBLE);
                    mTvAddressAddNew.setVisibility(View.VISIBLE);
                }else{
                    Utils.TS(message);
                }
            }

            @Override
            protected void _onError() {
            }
        };
    }


    /**
     * 删除用户收获地址
     */
    private void delAddress() {
        Type type = new TypeToken<HttpResult<String>>() {}.getType();
        new HttpUtils<String>(type, HttpConstant.API_PERSONAL_ADDRESS_DEL, null, "删除用户收获地址", true) {
            @Override
            protected void _onSuccess(String s, String msg) {
                getAddress();
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢，请稍后重试");
            }
        };
    }

}
