package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionCheckBoxCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionCheckBoxUnCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionDelete;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionHideAllCheckBox;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionShowAllCheckBox;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxDelete;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxInteface;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxUnCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintHideAllCheckBox;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintShowAllCheckBox;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.fragment.MyCollectionFragment;
import com.nian.wan.app.fragment.MyDownloadFragment;
import com.nian.wan.app.fragment.MyFootPrintFragment;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.CollectionViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * @Description: 个人中心-我的游戏
 */
public class MyGameActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tb_my_collection)
    TabLayout mTbMyCollection;

    @BindView(R.id.vp_my_collection_content)
    CollectionViewPager mVpMyCollectionContent;

    //删除
    @BindView(R.id.img_my_collection_delete)
    RelativeLayout mImgMyCollectionDelete;

    //完成
    @BindView(R.id.tv_my_collection_complete)
    TextView mTvMyCollectionComplete;

    //底部删除布局
    @BindView(R.id.rl_my_collection_delete)
    RelativeLayout mRlMyCollectionDelete;

    //底部删除布局全选复选框
    @BindView(R.id.cb_my_collection_check_all)
    CheckBox mCbMyCollectionCheckAll;

    @BindView(R.id.btn_check_all)
    LinearLayout btnCheckAll;


    private boolean isCollectionHashData = true;
    private boolean isFootHashData = true;
    private int target;  //1 展示我的收藏  2我的足迹  3我的下载


    private static final int ACTION_MY_COLLECTION_DELETE = 0;
    private static final int ACTION_MY_COLLECTION_COMPLETE = 1;
    private static final int ACTION_MY_COLLECTION_DO_DELETE = 2;
    public static final int ACTION_FOOT_CHECKED_CHECKALL_CHECKBOX = 3;
    public static final int ACTION_FOOT_UNCHECK_CHECKALL_CHECKBOX = 4;
    public static final int ACTION_COLLECTION_CHECKED_CHECKALL_CHECKBOX = 5;
    public static final int ACTION_COLLECTION_UNCHECK_CHECKALL_CHECKBOX = 6;
    public static final int ACTION_COLLECTION_NO_DATA = 7;
    public static final int ACTION_FOOT_NO_DATA = 8;
    //确认删除
    public static final int ACTION_DELETE_CONFIRM = 9;

    private static IMyCollectionShowAllCheckBox mICollectionShowAllCheckBox;
    private static IMyCollectionHideAllCheckBox mICollectionHideAllCheckBox;
    private static IMyCollectionCheckBoxCheckAll mICollectionCheckBoxCheckAll;
    private static IMyCollectionCheckBoxUnCheckAll mICollectionCheckBoxUnCheckAll;
    private static IMyCollectionDelete mICollectionMyCollectionDelete;

    private static IMyFootprintShowAllCheckBox mIMyFootprintShowAllCheckBox;
    private static IMyFootprintHideAllCheckBox mIMyFootprintHideAllCheckBox;
    private static IMyFootprintCheckBoxCheckAll mIMyFootprintCheckBoxCheckAll;
    private static IMyFootprintCheckBoxUnCheckAll mIMyFootprintCheckBoxUnCheckAll;
    private static IMyFootprintCheckBoxInteface mIMyFootprintCheckBoxInteface;
    private static IMyFootprintCheckBoxDelete mIMyFootprintCheckBoxDelete;

    @SuppressLint("HandlerLeak")
    private Handler mMyCollectionActivityHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ACTION_FOOT_CHECKED_CHECKALL_CHECKBOX:
                    mCbMyCollectionCheckAll.setChecked(true);
                    break;
                case ACTION_FOOT_UNCHECK_CHECKALL_CHECKBOX:
                    mCbMyCollectionCheckAll.setChecked(false);
                    break;
                case ACTION_COLLECTION_CHECKED_CHECKALL_CHECKBOX:
                    mCbMyCollectionCheckAll.setChecked(true);
                    break;
                case ACTION_COLLECTION_UNCHECK_CHECKALL_CHECKBOX:
                    mCbMyCollectionCheckAll.setChecked(false);
                    break;
                case ACTION_COLLECTION_NO_DATA:
                    isCollectionHashData = false;
                    if (0 == mVpMyCollectionContent.getCurrentItem() && !isCollectionHashData) {
                        mImgMyCollectionDelete.setVisibility(View.INVISIBLE);
                    }
                    break;
                case ACTION_FOOT_NO_DATA:
                    isFootHashData = false;
                    break;
                //确认删除（我的足迹）
                case ACTION_DELETE_CONFIRM:
                    mRlMyCollectionDelete.setVisibility(View.GONE);
                    mCbMyCollectionCheckAll.setChecked(false);  //底部删除布局全选cb按钮
                    mImgMyCollectionDelete.setVisibility(View.VISIBLE);
                    mTvMyCollectionComplete.setVisibility(View.GONE);
                    mIMyFootprintHideAllCheckBox.hideAllCheckBox(); //隐藏我的足迹游戏条目cb按钮
                    isEnableViewPagerAndTabLayout(true); //可以滑动
                    break;

            }
        }
    };

    @Subscribe
    public void ShuaXin(EvenBean bean) {
        if (bean.b == 5) {
            isCollectionHashData = true;
            mRlMyCollectionDelete.setVisibility(View.GONE);
            mCbMyCollectionCheckAll.setChecked(false);
            mImgMyCollectionDelete.setVisibility(View.VISIBLE);
            mTvMyCollectionComplete.setVisibility(View.GONE);
            isEnableViewPagerAndTabLayout(true); //可以滑动
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public static void setmICollectionShowAllCheckBox(IMyCollectionShowAllCheckBox mICollectionShowAllCheckBox) {
        MyGameActivity.mICollectionShowAllCheckBox = mICollectionShowAllCheckBox;
    }

    public static void setmICollectionHideAllCheckBox(IMyCollectionHideAllCheckBox
                                                              mICollectionHideAllCheckBox) {
        MyGameActivity.mICollectionHideAllCheckBox = mICollectionHideAllCheckBox;
    }

    public static void setmICollectionCheckBoxCheckAll(IMyCollectionCheckBoxCheckAll
                                                               mICollectionCheckBoxCheckAll) {
        MyGameActivity.mICollectionCheckBoxCheckAll = mICollectionCheckBoxCheckAll;
    }

    public static void setmICollectionCheckBoxUnCheckAll(IMyCollectionCheckBoxUnCheckAll
                                                                 mICollectionCheckBoxUnCheckAll) {
        MyGameActivity.mICollectionCheckBoxUnCheckAll = mICollectionCheckBoxUnCheckAll;
    }

    public static void setmICollectionMyCollectionDelete(IMyCollectionDelete
                                                                 mICollectionMyCollectionDelete) {
        MyGameActivity.mICollectionMyCollectionDelete = mICollectionMyCollectionDelete;
    }

    public static void setmIMyFootprintShowAllCheckBox(IMyFootprintShowAllCheckBox
                                                               mIMyFootprintShowAllCheckBox) {
        MyGameActivity.mIMyFootprintShowAllCheckBox = mIMyFootprintShowAllCheckBox;
    }

    public static void setmIMyFootprintHideAllCheckBox(IMyFootprintHideAllCheckBox
                                                               mIMyFootprintHideAllCheckBox) {
        MyGameActivity.mIMyFootprintHideAllCheckBox = mIMyFootprintHideAllCheckBox;
    }

    public static void setmIMyFootprintCheckBoxCheckAll(IMyFootprintCheckBoxCheckAll
                                                                mIMyFootprintCheckBoxCheckAll) {
        MyGameActivity.mIMyFootprintCheckBoxCheckAll = mIMyFootprintCheckBoxCheckAll;
    }

    public static void setmIMyFootprintCheckBoxUnCheckAll(IMyFootprintCheckBoxUnCheckAll
                                                                  mIMyFootprintCheckBoxUnCheckAll) {
        MyGameActivity.mIMyFootprintCheckBoxUnCheckAll = mIMyFootprintCheckBoxUnCheckAll;
    }

    public static void setmIMyFootprintCheckBoxInteface(IMyFootprintCheckBoxInteface
                                                                mIMyFootprintCheckBoxInteface) {
        MyGameActivity.mIMyFootprintCheckBoxInteface = mIMyFootprintCheckBoxInteface;
    }

    public static void setmIMyFootprintCheckBoxDelete(IMyFootprintCheckBoxDelete
                                                              mIMyFootprintCheckBoxDelete) {
        MyGameActivity.mIMyFootprintCheckBoxDelete = mIMyFootprintCheckBoxDelete;
    }


    public static boolean myCollectionIsCheckAll;
    public static boolean myFootPrintIsCheckAll;
    private MyCollectionFragment myCollectionFragment;
    private MyFootPrintFragment myFootPrintFragment;
    private MyDownloadFragment myDownloadFragment;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initView() {
        setContentView(R.layout.viewpager_my_collection);

        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("我的游戏");

        target = getIntent().getIntExtra("target",0);
        isEnableViewPagerAndTabLayout(true);

        mTbMyCollection.addTab(mTbMyCollection.newTab().setText("我的收藏"));
        mTbMyCollection.addTab(mTbMyCollection.newTab().setText("我的足迹"));
        mTbMyCollection.addTab(mTbMyCollection.newTab().setText("我的下载"));

        mTbMyCollection.setTabMode(TabLayout.MODE_FIXED);
        mVpMyCollectionContent.setPagingEnabled(false);
        mVpMyCollectionContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if (myCollectionFragment == null){
                            myCollectionFragment = new MyCollectionFragment(mMyCollectionActivityHandler);
                        }
                        return myCollectionFragment;
                    case 1:
                        if (myFootPrintFragment == null){
                            myFootPrintFragment = new MyFootPrintFragment(mMyCollectionActivityHandler);
                        }
                        return myFootPrintFragment;
                    case 2:
                        if (myDownloadFragment == null){
                            myDownloadFragment = new MyDownloadFragment();
                        }
                        return myDownloadFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "我的收藏";
                    case 1:
                        return "我的足迹";
                    case 2:
                        return "我的下载";
                }
                return null;
            }
        });

        mTbMyCollection.setupWithViewPager(mVpMyCollectionContent);
        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCbMyCollectionCheckAll.isChecked()){
                    mCbMyCollectionCheckAll.setChecked(false);//我的收藏页面底部全选按钮选中
                }else {
                    mCbMyCollectionCheckAll.setChecked(true);//没选中
                }
            }
        });
        mCbMyCollectionCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChackAll) {
                if (0 == mVpMyCollectionContent.getCurrentItem()) {
                    if (isChackAll) {
                        mICollectionCheckBoxCheckAll.checkAll();  //我的收藏页面游戏条目全部选中
                    } else {
                        if (myCollectionIsCheckAll){
                            mICollectionCheckBoxUnCheckAll.unCheckAll(); //全部不选中
                        }
                    }
                } else {
                    if (isChackAll) {
                        mIMyFootprintCheckBoxCheckAll.checkAll();//我的足迹页面游戏条目全部选中
                    } else {
                        if (myFootPrintIsCheckAll){
                            mIMyFootprintCheckBoxUnCheckAll.unCheckAll();//没选中
                        }
                    }
                }
            }
        });
        mVpMyCollectionContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //如果当前下标是0,并且收藏有数据则显示删除按钮反之隐藏
                if (0 == position && isCollectionHashData) {
                    mImgMyCollectionDelete.setVisibility(View.VISIBLE);
                } else if (0 == position && !isCollectionHashData) {
                    mImgMyCollectionDelete.setVisibility(View.INVISIBLE);
                }
                if (1 == position && isFootHashData) {
                    mImgMyCollectionDelete.setVisibility(View.VISIBLE);
                } else if (1 == position && !isFootHashData) {
                    mImgMyCollectionDelete.setVisibility(View.INVISIBLE);
                }
                if (2 == position){
                    mImgMyCollectionDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        switch (target){
            case 1:
                mVpMyCollectionContent.setCurrentItem(0);
                break;
            case 2:
                mVpMyCollectionContent.setCurrentItem(1);
                break;
            case 3:
                mVpMyCollectionContent.setCurrentItem(2);
                break;
        }
    }


    @OnClick({R.id.back, R.id.img_my_collection_delete,
            R.id.tv_my_collection_complete, R.id.rl_my_collection_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            //删除
            case R.id.img_my_collection_delete:
                doClick(ACTION_MY_COLLECTION_DELETE);
                break;
            //删除布局
            case R.id.rl_my_collection_delete:
                doClick(ACTION_MY_COLLECTION_DO_DELETE);
                break;
            //完成
            case R.id.tv_my_collection_complete:
                doClick(ACTION_MY_COLLECTION_COMPLETE);
                break;
        }
    }


    /**
     * 响应点击事件
     *
     * @param action
     */
    public void doClick(int action) {
        switch (action) {
            //响应删除
            case ACTION_MY_COLLECTION_DELETE:
                //ViewPager的下标是0说明是我的收藏点击删除,1则是我的足迹
                if (0 == mVpMyCollectionContent.getCurrentItem()) {
                    //显示我的收藏CheckBox
                    mICollectionShowAllCheckBox.showAllCheckBox();
                } else {
                    //显示我的足迹CheckBox
                    mIMyFootprintShowAllCheckBox.showAllCheckBox();
                }
                //在编辑状态下禁用ViewPager的滑动事件
                isEnableViewPagerAndTabLayout(false);
                mTvMyCollectionComplete.setVisibility(View.VISIBLE);
                mImgMyCollectionDelete.setVisibility(View.GONE);
                mRlMyCollectionDelete.setVisibility(View.VISIBLE);
                break;
            //响应完成
            case ACTION_MY_COLLECTION_COMPLETE:
                if (0 == mVpMyCollectionContent.getCurrentItem()) {
                    //隐藏我的收藏CheckBox
                    mICollectionHideAllCheckBox.hideAllCheckBox();
                } else {
                    //隐藏我的足迹CheckBox
                    mIMyFootprintHideAllCheckBox.hideAllCheckBox();
                }
                //取消编辑状态启用ViewPager的滑动事件
                mCbMyCollectionCheckAll.setChecked(false);
                isEnableViewPagerAndTabLayout(true);
                mTvMyCollectionComplete.setVisibility(View.GONE);
                mImgMyCollectionDelete.setVisibility(View.VISIBLE);
                mRlMyCollectionDelete.setVisibility(View.GONE);
                break;
            //响应执行删除
            case ACTION_MY_COLLECTION_DO_DELETE:
                if (0 == mVpMyCollectionContent.getCurrentItem()) {
                    mICollectionMyCollectionDelete.doDelete();
                } else {
                    mIMyFootprintCheckBoxDelete.doDelete();
                }
//
//                if (0 == mVpMyCollectionContent.getCurrentItem()) {
//                    //隐藏我的收藏CheckBox
//                    mICollectionHideAllCheckBox.hideAllCheckBox();
//                } else {
//                    //隐藏我的足迹CheckBox
//                    mIMyFootprintHideAllCheckBox.hideAllCheckBox();
//                }
                //取消编辑状态启用ViewPager的滑动事件
//                mCbMyCollectionCheckAll.setChecked(false);
//                isEnableViewPagerAndTabLayout(true);
//                mTvMyCollectionComplete.setVisibility(View.GONE);
//                mImgMyCollectionDelete.setVisibility(View.VISIBLE);
//                mRlMyCollectionDelete.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 启用或禁用TabLayout、ViewPager
     *
     * @param isDisable
     */
    public void isEnableViewPagerAndTabLayout(boolean isDisable) {
        LinearLayout tabStrip = (LinearLayout) mTbMyCollection.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tabView = tabStrip.getChildAt(i);
            if (tabView != null) {
                tabView.setClickable(isDisable);
            }
        }
//        mVpMyCollectionContent.setPagingEnabled(isDisable);
    }


}
