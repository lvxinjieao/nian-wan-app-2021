package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogAddressInformation;
import com.nian.wan.app.view.DialogAddressIsConfirmBack;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.gson.reflect.TypeToken;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.mc.developmentkit.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-添加新地址
 * @Author: 闫冰
 * @Date: Created in 2017/11/2 14:54
 * @Modified By:
 * @Modified Date:
 */
public class NewAddressActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    //收货人姓名
    @BindView(R.id.edt_new_address_username)
    EditText mEdtNewAddressUsername;
    //收货人手机
    @BindView(R.id.edt_new_address_user_mobile)
    TextView mEdtNewAddressUserMobile;
    //收货人省市区
    @BindView(R.id.edt_new_address_area)
    TextView mTvNewAddressUserAddress;
    //收货人详细地址
    @BindView(R.id.edt_new_address_description)
    EditText mEdtNewAddressDescription;
    //保存
    @BindView(R.id.tv_new_address_save)
    TextView mTvNewAddressSave;
    //省市区选择器
    private CityPickerView mCityPicker;
    private boolean canShow;

    private boolean isHaveAddress = false;  //当前用户是否已经有地址，用于判断保存时是添加地址还是编辑地址
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userDetailAddress;
    public static final int CONFIRM_EXCIT = 0;

    @SuppressLint("HandlerLeak")
    Handler transferActionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CONFIRM_EXCIT:
                    NewAddressActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        setContentView(R.layout.receiving_address_add);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        isHaveAddress = getIntent().getBooleanExtra("isHaveAddress", false);
        userName = getIntent().getStringExtra("userName");
        userPhone = getIntent().getStringExtra("userPhone");
        userAddress = getIntent().getStringExtra("userAddress");
        userDetailAddress = getIntent().getStringExtra("userDetailAddress");
        if (isHaveAddress) {
            title.setText("编辑地址");
            mEdtNewAddressUsername.setText(userName);
            mEdtNewAddressUserMobile.setText(userPhone);
            mTvNewAddressUserAddress.setText(userAddress.replace(userDetailAddress, ""));
            mEdtNewAddressDescription.setText(userDetailAddress);
        } else {
            title.setText("添加新地址");
        }

        mCityPicker = new CityPickerView.Builder(NewAddressActivity.this)
                .textSize(20)
                .title("")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#0b0b0b")
                .backgroundPop(0xa0000000)
                .confirTextColor("#99CC33")
                .cancelTextColor("#99CC33")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
    }

    @OnClick({R.id.back, R.id.edt_new_address_area, R.id.tv_new_address_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (!TextUtils.isEmpty(mEdtNewAddressUsername.getText().toString()) ||
                        !TextUtils.isEmpty(mEdtNewAddressDescription.getText().toString()) ||
                        !TextUtils.isEmpty(mEdtNewAddressUserMobile.getText().toString()) ||
                        !TextUtils.isEmpty(mTvNewAddressUserAddress.getText().toString())) {
                    new DialogAddressIsConfirmBack(this, R.style.MyDialogStyle, transferActionHandler).show();
                } else {
                    this.finish();
                }
                break;
            //收货人省市区
            case R.id.edt_new_address_area:
                KeyboardUtils.hideSoftInput(this);
                if (mCityPicker.isShow()) {
                    canShow = false;
                } else {
                    canShow = true;
                }

                if (canShow) {
                    cityPicker();
                }

                break;
            //保存收货地址
            case R.id.tv_new_address_save:
                if (checkAddressFormat()) {
                    if (Utils.getPersistentUserInfo() != null) {
                        if (isHaveAddress) { //当前用户已经有地址，保存操作为编辑地址
                            editAddress(Utils.getPersistentUserInfo().token, mEdtNewAddressUsername
                                    .getText().toString(), mEdtNewAddressUserMobile.getText()
                                    .toString(), mTvNewAddressUserAddress.getText()
                                    .toString(), mEdtNewAddressDescription
                                    .getText().toString());
                        } else {  //当前用户没有地址，保存操作为添加地址
                            addAddress(Utils.getPersistentUserInfo().token, mEdtNewAddressUsername
                                            .getText().toString(), mEdtNewAddressUserMobile
                                            .getText()
                                            .toString(), mTvNewAddressUserAddress.getText()
                                            .toString(),
                                    mEdtNewAddressDescription.getText().toString());
                            Log.e("地址详情", mEdtNewAddressDescription.getText().toString());
                        }
                    }
                }
                break;
        }
    }


    private void cityPicker() {
        //监听方法，获取选择结果
        mCityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                mTvNewAddressUserAddress.setText("");
                mTvNewAddressUserAddress.setText(province.getName() + city.getName() + "市" + district.getName());
            }

            @Override
            public void onCancel() {

            }
        });
        mCityPicker.show();
    }

    public boolean checkAddressFormat() {
        Pattern patternName = Pattern.compile("^([A-Za-z]|[\\u4E00-\\u9FA5])+$");
        Matcher matcherName = patternName.matcher(mEdtNewAddressUsername.getText().toString());
//        if (!matcherName.matches()) {
//            new DialogAddressInformation(this, R.style.MyDialogStyle,
//                    "请输入正确的收货人姓名").show();
//            return false;
//        }
        if (!Utils.isChinese(mEdtNewAddressUsername.getText().toString()) || mEdtNewAddressUsername.getText().toString().length() < 2) {
            new DialogAddressInformation(this, R.style.MyDialogStyle,
                    "请输入大于两个字符的中文姓名").show();
            return false;
        }

        if (!Utils.isMobileNO(mEdtNewAddressUserMobile.getText().toString())) {
            new DialogAddressInformation(this, R.style.MyDialogStyle,
                    "请输入正确的手机号").show();
            return false;
        }
        if (TextUtils.isEmpty(mTvNewAddressUserAddress.getText().toString())) {
            new DialogAddressInformation(this, R.style.MyDialogStyle,
                    "请选择所在地区").show();
            return false;
        }
        if ((mEdtNewAddressDescription.getText().toString().length() < 5)) {
            new DialogAddressInformation(this, R.style.MyDialogStyle,
                    "请输入正确详细地址").show();
            return false;
        }
        try {
            String s = mEdtNewAddressDescription.getText().toString();
            int i = Integer.parseInt(s);
            new DialogAddressInformation(this, R.style.MyDialogStyle,
                    "请输入正确详细地址").show();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 添加用户收货地址
     */
    private void addAddress(String token, String name, String phone, String address, String detail) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("phone", phone);
        map.put("address", address);
        map.put("detail", detail);
        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_PERSONAL_ADDRESS_ADD, map, "添加用户收货地址", true) {
            @Override
            protected void _onSuccess(String s, String msg) {
                finish();
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


    /**
     * 编辑用户收货地址
     */
    private void editAddress(String token, String name, String phone, String address, String detail) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("phone", phone);
        map.put("address", address);
        map.put("detail", detail);
        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_PERSONAL_ADDRESS_EDIT, map, "编辑用户收货地址", true) {
            @Override
            protected void _onSuccess(String s, String msg) {
                finish();
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
