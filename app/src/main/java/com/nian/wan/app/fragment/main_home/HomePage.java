package com.nian.wan.app.fragment.main_home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.listener.AppBarStateChangeListener;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.SearchActivity;
import com.nian.wan.app.activity.XinWenDetActivity;
import com.nian.wan.app.adapter.GameMenuAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.GameMenuBean;
import com.nian.wan.app.bean.HomeBannerBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogQRCode;
import com.nian.wan.app.view.LoadDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 首页Fragment
 */
public class HomePage extends Fragment {

    @BindView(R.id.tv_home)
    TextView tv_home;

    @BindView(R.id.iv_home_search)
    ImageView iv_home_search;

    @BindView(R.id.iv_home_download)
    ImageView iv_home_download;

    //================================================================================================

    @BindView(R.id.banner)
    XBanner banner;

    @BindView(R.id.gridView_game_menu)
    GridView gridView_game_menu;

    GameMenuAdapter gameMenuAdapter;

    //================================================================================================

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.smartrefreshLayout)
    SmartRefreshLayout smartrefreshLayout;

    @BindView(R.id.home_viewpager)
    ViewPager homeViewPager;


    private List<String> imageUrls = new ArrayList<>();
    private List<String> Urls = new ArrayList<>();
    private List<String> bannerText = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<String> slider_type = new ArrayList<>();

    public LoadDialog loadDialog;

    public HomeGameFragment homeGameFragment;
    public HuoDongFragment homeHuoDongFragment;
    public HomeOpenServerFragment homeOpenServerFragment;
    public GongGaoFragment homeGongGaoFragment;
    public ZiXunFragment homeZiXunFragment;
    public GongLueFragment homeGongLueFragment;


    private ArrayList<GameMenuBean> menus;

    private BoutiqueGameFragment boutique;

    Handler UI = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
//                case 0:
//                    boutique = new BoutiqueGameFragment();
//                    FragmentTransaction beginTransaction = manager.beginTransaction();
//                    beginTransaction.replace(R.id.boutique_game, boutique);
//                    beginTransaction.commit();
//                    break;

                case 1:
                    setBanner();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, null);
        ButterKnife.bind(this, view);

        smartrefreshLayout.setRefreshHeader(new ClassicsHeader(x.app()));

        smartrefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                UI.sendEmptyMessage(0);

                EvenBean evenBean = new EvenBean();
                evenBean.reFresh = 1;
                EventBus.getDefault().post(evenBean);
                refreshlayout.finishRefresh(500);//传入false表示刷新失败
            }
        });


        gameMenuAdapter = new GameMenuAdapter(getActivity());
        gridView_game_menu.setAdapter(gameMenuAdapter);

        menus = new ArrayList<GameMenuBean>();

        GameMenuBean menu1 = new GameMenuBean();
        menu1.setType_name("游戏");
        menu1.setType_image(R.drawable.icon_jing_xuan);

        GameMenuBean menu2 = new GameMenuBean();
        menu2.setType_name("活动");
        menu2.setType_image(R.drawable.icon_kapai);

        GameMenuBean menu3 = new GameMenuBean();
        menu3.setType_name("开服");
        menu3.setType_image(R.drawable.icon_jue_se);

        GameMenuBean menu4 = new GameMenuBean();
        menu4.setType_name("公告");
        menu4.setType_image(R.drawable.icon_jing_ji);

        GameMenuBean menu5 = new GameMenuBean();
        menu5.setType_name("资讯");
        menu5.setType_image(R.drawable.icon_ce_lue);

        GameMenuBean menu6 = new GameMenuBean();
        menu6.setType_name("攻略");
        menu6.setType_image(R.drawable.icon_h5);

        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);
        menus.add(menu4);
        menus.add(menu5);
        menus.add(menu6);
        gameMenuAdapter.setData(menus);

        loadBanner();

        initView();

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                EvenBean evenBean = new EvenBean();
                if (state == State.COLLAPSED) {         //收起
                    evenBean.isExpandable = 1;
                    EventBus.getDefault().post(evenBean);
                } else if (state == State.EXPANDED) {   //展开
                    evenBean.isExpandable = 2;
                    EventBus.getDefault().post(evenBean);
                }
            }
        });
        homeViewPager.setCurrentItem(0);
        return view;
    }


    private void initView() {

        boutique = new BoutiqueGameFragment();
        updateView(R.id.boutique_game, boutique, null);

        homeViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if (homeGameFragment == null) {
                            homeGameFragment = new HomeGameFragment();
                        }
                        return homeGameFragment;

                    case 1:
                        if (homeHuoDongFragment == null) {
                            homeHuoDongFragment = new HuoDongFragment();
                        }
                        return homeHuoDongFragment;

                    case 2:
                        if (homeOpenServerFragment == null) {
                            homeOpenServerFragment = new HomeOpenServerFragment();
                        }
                        return homeOpenServerFragment;

                    case 3:
                        if (homeGongGaoFragment == null) {
                            homeGongGaoFragment = new GongGaoFragment();
                        }
                        return homeGongGaoFragment;

                    case 4:
                        if (homeZiXunFragment == null) {
                            homeZiXunFragment = new ZiXunFragment();
                        }
                        return homeZiXunFragment;

                    case 5:
                        if (homeGongLueFragment == null) {
                            homeGongLueFragment = new GongLueFragment();
                        }
                        return homeGongLueFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return menus.size();
            }
        });

        gridView_game_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeViewPager.setCurrentItem(position);
            }
        });
    }


    /**
     * 首页轮播图
     */
    private void loadBanner() {

        if (imageUrls.size() <= 0) {

            Type type = new TypeToken<HttpResult<List<HomeBannerBean>>>() {
            }.getType();

            new HttpUtils<List<HomeBannerBean>>(type, HttpConstant.API_HOME_BANNER, null, "首页轮播图", false) {

                @Override
                protected void _onSuccess(List<HomeBannerBean> listBean, String msg) {
                    if (null != listBean) {
                        for (int i = 0; i < listBean.size(); i++) {
                            imageUrls.add(listBean.get(i).getData());
                            Urls.add(listBean.get(i).getUrl());
                            descriptions.add(listBean.get(i).getTitle());
                            bannerText.add(listBean.get(i).getTitle());
                            slider_type.add(listBean.get(i).getSlider_type());
                        }
                        UI.sendEmptyMessage(1);
                    }
                }

                @Override
                protected void _onError(String message, int code) {
                    banner.setData(imageUrls, null);
                    banner.setmAdapter(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            Glide.with(getActivity()).load(imageUrls.get(position)).error(R.drawable.deft_xigu).into((ImageView) view);
                        }
                    });
                }

                @Override
                protected void _onError() {
                    ToastUtil.showToast("网络缓慢");
                }
            };
        }
    }


    /**
     * Banner
     */
    private void setBanner() {
        //添加广告数据
        banner.setData(imageUrls, null);//第二个参数为提示文字资源集合

        banner.setmAdapter(new XBanner.XBannerAdapter() {

            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                Glide.with(getActivity())
                        .load(imageUrls.get(position))
                        .error(R.drawable.deft_xigu)
                        .transform(new RoundedCorners(10))
                        .into((ImageView) view);
            }
        });

        banner.setPageTransformer(Transformer.Default);

        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                String s = Urls.get(position);
                String t = bannerText.get(position);
                if (s != null && !s.equals("")) {
                    Intent intent = new Intent(getActivity(), XinWenDetActivity.class);
                    intent.putExtra("URL", s);
                    intent.putExtra("topTitle", t);
                    getActivity().startActivity(intent);
                } else {
                    ToastUtil.showToast("暂无链接地址");
                }
            }
        });
    }


    @OnClick({
            R.id.tv_home,
            R.id.iv_home_search,
            R.id.iv_home_download/**,
     R.id.btn_home_paihang,
     R.id.btn_home_yaoqing*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_home: //搜索
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;

            case R.id.iv_home_search: //搜索
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;

            case R.id.iv_home_download: //下载列表
                if (Utils.getLoginUser() != null) {
                    Intent intent = new Intent(getContext(), MyGameActivity.class);
                    intent.putExtra("target", 3);
                    startActivity(intent);
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
                    android.app.FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;


//            case R.id.btn_home_paihang:
//                getActivity().startActivity(new Intent(getActivity(), RackingActivity.class));
//                break;
//
//            case R.id.btn_home_yaoqing:
//                getActivity().startActivity(new Intent(getActivity(), InvitingFriendsActivity.class));
//                break;
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            dismissDialog();
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("公众号信息", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            String url = jsonData.getString("PC_SET_QRCODE");  //二维码图片url
                            String title = jsonData.getString("WXGZH_NAME");  //公众号名称
                            new DialogQRCode(getActivity(), R.style.MyDialogStyle, url, title).show();
                        } else {
                            ToastUtil.showToast(jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        Log.e("公众号信息异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };


    private void showDialog() {
        if (loadDialog == null) {
            loadDialog = new LoadDialog(getActivity(), R.style.MyDialogStyle);
        }
        loadDialog.setCancelable(false);
        loadDialog.show();
    }

    private void dismissDialog() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    FragmentManager manager;
    FragmentTransaction transaction;

    public void updateView(int id, Fragment fragment, Bundle bundle) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.add(id, fragment);
        transaction.commit();
    }
    /////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onResume() {
        super.onResume();
        if (imageUrls.size() > 0)
            UI.sendEmptyMessage(1);
    }
}
