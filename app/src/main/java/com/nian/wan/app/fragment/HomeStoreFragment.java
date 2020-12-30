package com.nian.wan.app.fragment;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.activity.ExchangePTBActivity;
import com.nian.wan.app.activity.HomeStoreGoodsDetailActivity;
import com.nian.wan.app.activity.HomeStoreMissionActivity;
import com.nian.wan.app.activity.HomeStoreRegularActivity;
import com.nian.wan.app.activity.HomeStoreScoreRecordActivity;
import com.nian.wan.app.activity.SignWebActivity;
import com.nian.wan.app.adapter.HomeStoreGridViewAdapter;
import com.nian.wan.app.bean.HomeStoreGoodsBean;
import com.nian.wan.app.bean.UserIsBindPhoneBean;
import com.nian.wan.app.bean.UserSignBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.sunfusheng.marqueeview.MarqueeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 首页商城
 */
public class HomeStoreFragment extends Fragment {

    @BindView(R.id.img_store_score_record)
    ImageView imgStoreScoreRecord;

    @BindView(R.id.img_store_score_regular)
    ImageView imgStoreScoreRegular;

    @BindView(R.id.tv_home_store_valid_score)
    TextView tvHomeStoreValidScore;

    @BindView(R.id.tv_home_store_sign)
    TextView tvHomeStoreSign;

    @BindView(R.id.tv_home_store_score_mission)
    TextView tvHomeStoreScoreMission;

    @BindView(R.id.tv_home_store_Letter_all)
    TextView tvHomeStoreLetterAll;

    @BindView(R.id.ll_home_store_letter_layout)
    LinearLayout llHomeStoreLetterLayout;

    @BindView(R.id.tv_home_store_Letter_abcd)
    TextView tvHomeStoreLetterAbcd;

    @BindView(R.id.tv_home_store_Letter_efgh)
    TextView tvHomeStoreLetterEfgh;

    @BindView(R.id.tv_home_store_Letter_ijkl)
    TextView tvHomeStoreLetterIjkl;

    @BindView(R.id.tv_home_store_Letter_mnop)
    TextView tvHomeStoreLetterMnop;

    @BindView(R.id.tv_home_store_Letter_qrst)
    TextView tvHomeStoreLetterQrst;

    @BindView(R.id.tv_home_store_Letter_uvwx)
    TextView tvHomeStoreLetterUvwx;

    @BindView(R.id.tv_home_store_Letter_yz)
    TextView tvHomeStoreLetterYz;

    @BindView(R.id.tv_home_store_type_all)
    TextView tvHomeStoreTypeAll;

    @BindView(R.id.tv_home_store_type_virtual)
    TextView tvHomeStoreTypeVirtual;

    @BindView(R.id.tv_home_store_type_true)
    TextView tvHomeStoreTypeTrue;

    @BindView(R.id.gv_home_store_list)
    GridView gvHomeStoreList;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.ll_home_store_no_data)
    LinearLayout llHomeStoreNoData;

    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;

    @BindView(R.id.tv_jifen)
    TextView tvJifen;

    @BindView(R.id.ll_jifen)
    LinearLayout llJifen;

    @BindView(R.id.img_store_score_sign)
    ImageView imgStoreScoreSign;

    @BindView(R.id.img_store_score_task)
    ImageView imgStoreScoreTask;

    @BindView(R.id.rl_xianshi)
    RelativeLayout rlXianshi;

    @BindView(R.id.ll_home_store_marquee)
    LinearLayout llHomestoreMarquee;

    @BindView(R.id.img_red_hint)
    ImageView imgRedHint;

    @BindView(R.id.tv_hint_point)
    TextView tvHintPoint;

    @BindView(R.id.tv_home_store_duihuan)
    TextView tvHomeStoreDuihuan;


    private List<HomeStoreGoodsBean> storeGoodsBeans;
    private HomeStoreGridViewAdapter storeGridViewAdapter;
    private int Page = 1;
    private String goodType;
    private String Short;
    private List<CharSequence> MarqueeData;
    private StoreBroadcast storeBroadcast;
    private UserSignBean userSignBean;
    private String signPoint;
    private TranslateAnimation translateAnimation;
    private String TAG = "HomeStoreFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_store, null);
        ButterKnife.bind(this, view);
        storeGoodsBeans = new ArrayList<>();
        storeGridViewAdapter = new HomeStoreGridViewAdapter(getContext());
        gvHomeStoreList.setAdapter(storeGridViewAdapter);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new DefaultFooter(getActivity()));
        gvHomeStoreList.setFocusable(false);
        initView();
        return view;
    }

    private void initView() {
        if (null == Utils.getPersistentUserInfo()) {
            imgRedHint.setVisibility(View.VISIBLE);
        }
        gvHomeStoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), HomeStoreGoodsDetailActivity.class);
                intent.putExtra("goods_detail", storeGoodsBeans.get(i));
                startActivity(intent);
            }
        });

        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    rlXianshi.setVisibility(View.VISIBLE);
                    if (Utils.getPersistentUserInfo() == null) {
                        rlXianshi.setVisibility(View.GONE);
                    } else {
                        rlXianshi.setVisibility(View.VISIBLE);
                    }
                    springview.setEnable(true);
                } else {
                    if (storeGoodsBeans.size() <= 5) {
                        springview.setEnable(false);
                    } else {
                        springview.setEnable(true);
                    }
                    rlXianshi.setVisibility(View.GONE);
                }
            }
        });
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {

        @Override
        public void onRefresh() {

        }

        @Override
        public void onLoadmore() {
            Page = Page + 1;
            onLoadMord();
        }
    };

    /**
     * 加载更多
     */
    private void onLoadMord() {
        getShopList(goodType, Short, Page + "");
    }


    /**
     * 请求用户信息
     */
    private void getUserInfo() {
        if (Utils.getPersistentUserInfo() != null) {
            tvTitle.setVisibility(View.VISIBLE);

            Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {}.getType();

            new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, TAG + "用户综合信息", true) {

                @Override
                protected void _onSuccess(UserIsBindPhoneBean bean, String msg) {
                    tvHomeStoreValidScore.setText(bean.getShop_point());
                    tvJifen.setText(bean.getShop_point());
                    if (bean.getIssignin() == 0) {
                        imgRedHint.setVisibility(View.VISIBLE);
                        signPoint = String.valueOf(bean.getSign_point());
                        tvHomeStoreSign.setText("签到+" + signPoint);
                    } else {
                        modifyUiWithSign(false);
                    }
                }

                @Override
                protected void _onError(String message, int code) {
                }

                @Override
                protected void _onError() {
                }
            };
        } else {
            tvTitle.setVisibility(View.INVISIBLE);
            tvHomeStoreValidScore.setText("您还未登录");
            tvHomeStoreSign.setText("登录签到");
        }
    }

    /**
     * 请求用户信息
     */
    private void getUserInfo2() {
        if (Utils.getPersistentUserInfo() != null) {
            tvTitle.setVisibility(View.VISIBLE);
            Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {
            }.getType();

            new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, TAG + "用户综合信息2", true) {

                @Override
                protected void _onSuccess(UserIsBindPhoneBean bean, String msg) {
                    tvHomeStoreValidScore.setText(bean.getShop_point());
                    tvJifen.setText(bean.getShop_point());
                    if (bean.getIssignin() == 0) {
                        imgRedHint.setVisibility(View.VISIBLE);
                        tvHomeStoreSign.setText("签到+" + bean.getSign_point());
                    } else {
                        imgRedHint.setVisibility(View.INVISIBLE);
                        tvHomeStoreSign.setText("今日已签");
                        tvHomeStoreSign.setTextColor(getResources().getColor(R.color.ffffff));
                        tvHomeStoreSign.setAlpha(0.6f);
                    }
                }

                @Override
                protected void _onError(String message, int code) {
                }

                @Override
                protected void _onError() {
                }
            };
        } else {
            tvTitle.setVisibility(View.INVISIBLE);
            tvHomeStoreValidScore.setText("您还未登录");
            tvHomeStoreSign.setText("登录签到");
        }
    }

    /**
     * 直接进行签到
     */
    private void sign() {
        Map<String, String> signParams = new HashMap<>();
        if (null != Utils.getPersistentUserInfo()) {
            signParams.put("token", Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(singnHandler, HttpConstant.API_PERSONAL_USER_SIGN, signParams, true);
    }

    @SuppressLint("HandlerLeak")
    Handler singnHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    try {
                        Log.e("首页商城签到数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.optInt("code", -1);

                        if (200 == code) {
                            modifyUiWithSign(true);
                        } else {
                            ToastUtil.showToast("今日已签，无需重复签到");
                            getUserInfo();
                        }

                    } catch (Exception e) {
                        Log.e(TAG, "签到异常:" + e.toString());
                    }
                    break;
                case 2:
                    ToastUtil.showToast("签到网络缓慢");
                    break;
            }
        }
    };


    /**
     * 更新为已签到的UI
     */
    private void modifyUiWithSign(boolean needShowAnimation) {
        if (needShowAnimation) {
            tvHomeStoreSign.setVisibility(View.GONE);
            translateAnimation = (TranslateAnimation) android.view.animation.AnimationUtils.loadAnimation(getActivity(), R.anim.translate);
            if (signPoint != null) {
                tvHintPoint.setText("+" + signPoint);
            }
            tvHintPoint.startAnimation(translateAnimation);
            tvHintPoint.setTextColor(getResources().getColor(R.color.ffffff));
            tvHintPoint.setAlpha(0.6f);
            UIHander.sendEmptyMessageDelayed(1, 900);
        } else {
            tvHomeStoreSign.setText("今日已签");
            tvHomeStoreSign.setTextColor(getResources().getColor(R.color.ffffff));
            tvHomeStoreSign.setAlpha(0.6f);
        }
        imgRedHint.setVisibility(View.INVISIBLE);
        Intent intent = new Intent("com.yinu.login");
        intent.putExtra("login_status", DialogLoginActivity.ACTION_ALREADY_SIGN);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler UIHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvHintPoint.startAnimation(translateAnimation);
                    tvHomeStoreSign.setVisibility(View.VISIBLE);
                    tvHomeStoreSign.setText("已签到");
                    tvHomeStoreSign.setTextColor(getResources().getColor(R.color.ffffff));
                    tvHomeStoreSign.setAlpha(0.6f);
                    break;
            }
        }
    };


    /**
     * 文字跑马灯数据
     */
    private void getMarquee() {
        HttpConstant.POST(marqueeHandler, HttpConstant.API_SHOP_BINNER, null, false);
    }

    @SuppressLint("HandlerLeak")
    Handler marqueeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("文字跑马灯数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            if (jsonData.length() <= 0) {
                                llHomestoreMarquee.setVisibility(View.INVISIBLE);
                            } else {
                                llHomestoreMarquee.setVisibility(View.VISIBLE);
                            }
                            MarqueeData = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                JSONObject json = (JSONObject) jsonData.get(i);
                                String nickname = json.getString("account");
                                if (nickname.length() > 4) {
                                    nickname = nickname.substring(0, 4) + "**";
                                }
                                SpannableString sb = new SpannableString(nickname + "  兑换了" + "【" + json.getString("good_name") + "】");
                                sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.zhuse_lan)), 0, nickname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                MarqueeData.add(sb);
                            }
                            marqueeView.startWithList(MarqueeData);
                            marqueeView.startWithList(MarqueeData, R.anim.anim_bottom_in, R.anim.anim_top_out);
                        }
                    } catch (Exception e) {
                        Log.e("文字跑马灯数据异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };

    /**
     * 请求商品列表
     */
    private void getShopList(String type, String Short, String page) {
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }
        Map<String, String> map = new HashMap<>();
        if (type != null && !type.equals("")) {
            map.put("type", type);
        }
        if (Short != null && !Short.equals("")) {
            map.put("short", Short);
        }
        if (page != null && !page.equals("")) {
            map.put("p", page);
        }
        if (page.equals("1")) {
            storeGoodsBeans.clear();
        }

        Type type2 = new TypeToken<HttpResult<List<HomeStoreGoodsBean>>>() {
        }.getType();
        new HttpUtils<List<HomeStoreGoodsBean>>(type2, HttpConstant.API_SHOP_GOODS_LIST, map, "刚进来请求所有商品返回参数", false) {
            @Override
            protected void _onSuccess(List<HomeStoreGoodsBean> list, String msg) {
                if (list.size() > 0) {
                    storeGoodsBeans.addAll(list);
                    gvHomeStoreList.setVisibility(View.VISIBLE);
                    if (storeGoodsBeans.size() <= 5) {
                        springview.setEnable(false);
                    } else {
                        springview.setEnable(true);
                    }
                    storeGridViewAdapter.setData(storeGoodsBeans);
                    Utils.setListViewHeightBasedOnChildren(gvHomeStoreList, 2);
                } else {
                    if (storeGoodsBeans.size() == 0) {
                        springview.setEnable(false);
                        llHomeStoreNoData.setVisibility(View.VISIBLE);
                        gvHomeStoreList.setVisibility(View.GONE);
                    } else {
                        ToastUtil.showToast("已经到底了~");
                    }
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


    /**
     * 获取签到页面
     */
    @SuppressLint("HandlerLeak")
    Handler singinHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("获取签到页面返回数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            String signURL = jsonObject.getString("data") + "/token/" + Utils.getPersistentUserInfo().token;
                            Intent intent = new Intent(getActivity(), SignWebActivity.class);
                            intent.putExtra("name", "签到送积分");
                            intent.putExtra("url", signURL);
                            intent.putExtra("share", true);
                            getActivity().startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.e("获取签到页面数据异常", e.toString());
                        ToastUtil.showToast("获取签到页面数据异常，请稍候重试");
                    }
                    break;
                case 2:
                    ToastUtil.showToast("网络错误，请稍候重试");
                    break;
            }
        }
    };


    /**
     * 登录监听
     */
    private void regsiterPersonalBroadcast() {
        storeBroadcast = new StoreBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(storeBroadcast, intentFilter);
    }

    private class StoreBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    imgRedHint.setVisibility(View.INVISIBLE);
                    Page = 1;
                    getUserInfo();  //获取用户信息
                    break;
                case DialogLoginActivity.EVENT_LOGIN_FILED:
                     ToastUtil.showToast("登陆失败");
                    break;
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    tvHomeStoreSign.setAlpha(1f);
                    imgRedHint.setVisibility(View.VISIBLE);
                    getUserInfo();  //获取用户信息
                    break;
                //已签到
                case DialogLoginActivity.ACTION_ALREADY_SIGN:
                     getUserInfo2();
                    break;
            }
        }
    }


    /**
     * 获取签到页面
     */
    private void getSign() {
        if (Utils.getPersistentUserInfo() != null) {
            Map<String, String> map = new HashMap<>();
            map.put("token", Utils.getPersistentUserInfo().token);
            HttpConstant.POST(singinHandler, HttpConstant.API_SHOP_SIGN, map, false);
        } else {
            ToastUtil.showToast("请先登录~");
        }
    }

    @OnClick({R.id.img_store_score_record, R.id.img_store_score_regular, R.id.tv_home_store_sign,
            R.id.tv_home_store_score_mission, R.id.tv_home_store_Letter_all, R.id.tv_home_store_duihuan,
            R.id.ll_home_store_letter_layout, R.id.tv_home_store_Letter_abcd,
            R.id.tv_home_store_Letter_efgh, R.id.tv_home_store_Letter_ijkl,
            R.id.tv_home_store_Letter_mnop, R.id.tv_home_store_Letter_qrst,
            R.id.tv_home_store_Letter_uvwx, R.id.tv_home_store_Letter_yz,
            R.id.tv_home_store_type_all, R.id.tv_home_store_type_virtual,
            R.id.tv_home_store_type_true, R.id.img_store_score_sign, R.id.img_store_score_task})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_store_score_record:  //积分记录
                if (Utils.getPersistentUserInfo() != null) {
                    Intent storeScoreRecordIntent = new Intent(getContext(), HomeStoreScoreRecordActivity.class);
                    getContext().startActivity(storeScoreRecordIntent);
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.img_store_score_regular:  //积分规则
                Intent storeRegularIntent = new Intent(getContext(), HomeStoreRegularActivity.class);
                startActivity(storeRegularIntent);
                break;
            case R.id.tv_home_store_sign:  //签到
                if (Utils.getPersistentUserInfo() != null) {
                    if ("今日已签".equals(tvHomeStoreSign.getText())) {
//                        Map<String, String> map = new HashMap<>();
//                        map.put("token", Utils.getPersistentUserInfo().token);
//                        HttpCom.POST(singinHandler, HttpCom.API_SHOP_SIGN, map, false);
                    } else {
                        sign();
                    }
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            //积分任务
            case R.id.tv_home_store_score_mission:
                Intent homeStoreMissionIntent = new Intent(getContext(), HomeStoreMissionActivity.class);
                getContext().startActivity(homeStoreMissionIntent);
                break;
            //兑换平台币
            case R.id.tv_home_store_duihuan:
                if (Utils.getPersistentUserInfo() != null) {
                    getContext().startActivity(new Intent(getContext(), ExchangePTBActivity.class));
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tv_home_store_Letter_all:  //字母:全部
                tvHomeStoreLetterAll.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = null;
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.ll_home_store_letter_layout:
                break;
            case R.id.tv_home_store_Letter_abcd:
                tvHomeStoreLetterAbcd.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "ABCD";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_Letter_efgh:
                tvHomeStoreLetterEfgh.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "EFGH";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_Letter_ijkl:
                tvHomeStoreLetterIjkl.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "IJKL";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_Letter_mnop:
                tvHomeStoreLetterMnop.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "MNOP";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_Letter_qrst:
                tvHomeStoreLetterQrst.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "QRST";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_Letter_uvwx:
                tvHomeStoreLetterUvwx.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterYz.setBackgroundResource(R.color.white);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "UVWX";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_Letter_yz:
                tvHomeStoreLetterYz.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreLetterYz.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreLetterAll.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAll.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterEfgh.setBackgroundResource(R.color.white);
                tvHomeStoreLetterEfgh.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterIjkl.setBackgroundResource(R.color.white);
                tvHomeStoreLetterIjkl.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterMnop.setBackgroundResource(R.color.white);
                tvHomeStoreLetterMnop.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterQrst.setBackgroundResource(R.color.white);
                tvHomeStoreLetterQrst.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterUvwx.setBackgroundResource(R.color.white);
                tvHomeStoreLetterUvwx.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreLetterAbcd.setBackgroundResource(R.color.white);
                tvHomeStoreLetterAbcd.setTextColor(getResources().getColor(R.color.font_black_1));
                Short = "YZ";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_type_all:  //分类：所有
                tvHomeStoreTypeAll.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreTypeAll.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreTypeTrue.setBackgroundResource(R.color.white);
                tvHomeStoreTypeTrue.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreTypeVirtual.setBackgroundResource(R.color.white);
                tvHomeStoreTypeVirtual.setTextColor(getResources().getColor(R.color.font_black_1));
                goodType = "null";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_type_virtual:  //虚拟
                tvHomeStoreTypeVirtual.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreTypeVirtual.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreTypeTrue.setBackgroundResource(R.color.white);
                tvHomeStoreTypeTrue.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreTypeAll.setBackgroundResource(R.color.white);
                tvHomeStoreTypeAll.setTextColor(getResources().getColor(R.color.font_black_1));
                goodType = "2";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.tv_home_store_type_true:  //实物
                tvHomeStoreTypeTrue.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
                tvHomeStoreTypeTrue.setTextColor(getResources().getColor(R.color.font_white));
                tvHomeStoreTypeVirtual.setBackgroundResource(R.color.white);
                tvHomeStoreTypeVirtual.setTextColor(getResources().getColor(R.color.font_black_1));
                tvHomeStoreTypeAll.setBackgroundResource(R.color.white);
                tvHomeStoreTypeAll.setTextColor(getResources().getColor(R.color.font_black_1));
                goodType = "1";
                Page = 1;
                getShopList(goodType, Short, Page + "");
                break;
            case R.id.img_store_score_sign:  //签到
                if (Utils.getPersistentUserInfo() != null) {
//                    if ("今日已签".equals(tvHomeStoreSign.getText())) {
////                        Map<String, String> map = new HashMap<>();
////                        map.put("token", Utils.getPersistentUserInfo().token);
////                        HttpCom.POST(singinHandler, HttpCom.API_SHOP_SIGN, map, false);
//                        Utils.TS("您已经签到过了");
//                    } else {
//                        sign();
//                    }
                    getSign();
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.img_store_score_task:  //任务
                Intent homeStoreMissionIntent2 = new Intent(getContext(),
                        HomeStoreMissionActivity.class);
                getContext().startActivity(homeStoreMissionIntent2);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    @Override
    public void onResume() {
        super.onResume();
        regsiterPersonalBroadcast();
        //getUserInfo();  //获取用户信息
        getMarquee();
        getShopList(goodType, Short, 1 + "");
    }
}
