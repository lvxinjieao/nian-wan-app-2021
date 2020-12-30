package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.RealNameActivity;
import com.nian.wan.app.bean.UserIsRealNameBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 实名认证成功弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogRealNameSuccess extends Dialog {

    //关闭实名认证成功弹窗
    @BindView(R.id.tv_real_name_success_back)
    TextView mTvRealNameSuccessBack;
    //返回个人中心
    @BindView(R.id.img_real_name_success_close)
    ImageView mImgRealNameSuccessClose;
    private Handler mRealNameSuccessHandler;
    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();
    private boolean h5GameReload;
    private String reloadUrl;
    //用户是否实名认证
    private UserIsRealNameBean isRealNameBean;
    //姓名
    private String personName;
    //身份证
    private String idCard;

    public DialogRealNameSuccess(Context context, int themeResId, Handler handler,
                                 boolean h5GameReload, String reloadUrl) {
        super(context, themeResId);
        this.mContext = context;
        this.mRealNameSuccessHandler = handler;
        this.h5GameReload = h5GameReload;
        this.reloadUrl = reloadUrl;
        inflate = LinearLayout.inflate(context, R.layout.dialog_real_name_success, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        if (h5GameReload) {
            isUserRealName();
            mTvRealNameSuccessBack.setText("返回游戏");
        }
    }


    /**
     * 是否实名认证
     */
    private void isUserRealName() {
        if (null != Utils.getPersistentUserInfo()) {
            Type type = new TypeToken<HttpResult<UserIsRealNameBean>>() {
            }.getType();
            new HttpUtils<UserIsRealNameBean>(type, HttpConstant.API_PERSONAL_CENTER_USER_AUTH_DATA, null, "是否实名认证", true) {

                @Override
                protected void _onSuccess(UserIsRealNameBean bean, String msg) {
                    isRealNameBean = bean;
                    if ("2".equals(isRealNameBean.getAge_status()) || "3"
                            .equals(isRealNameBean.getAge_status())) {
                        personName = isRealNameBean.getReal_name();
                        idCard = isRealNameBean.getIdcard();
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
    }


    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.tv_real_name_success_back, R.id.img_real_name_success_close})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回个人中心
            case R.id.tv_real_name_success_back:
                Message message = Message.obtain();
                message.what = RealNameActivity.EVENT_REAL_NAME_SUCCESS;
                mRealNameSuccessHandler.sendMessage(message);
                dismissDialog();
                break;
            //关闭实名认证弹窗
            case R.id.img_real_name_success_close:
                Message closeMessage = Message.obtain();
                closeMessage.what = RealNameActivity.EVENT_REAL_NAME_DETAIL;
                Bundle bundle = new Bundle();
                bundle.putString("personName", personName);
                bundle.putString("idCard", idCard);
                bundle.putString("reloadUrl", reloadUrl);
                bundle.putBoolean("h5GameReload", h5GameReload);
                closeMessage.setData(bundle);
                mRealNameSuccessHandler.sendMessage(closeMessage);
                dismissDialog();
                break;
        }
    }
}
