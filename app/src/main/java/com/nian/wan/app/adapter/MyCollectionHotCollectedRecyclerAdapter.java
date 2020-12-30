package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.MyCollectionHotGameBean;
import com.nian.wan.app.bean.ShouCangBean;
import com.nian.wan.app.fragment.MyCollectionFragment;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mc.developmentkit.utils.ToastUtil;
import android.widget.RelativeLayout;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @Description: 个人中心-我的收藏-热门游戏RecyclerView适配器
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/7 9:32
 * @Modified By:
 * @Modified Date:
 */
public class MyCollectionHotCollectedRecyclerAdapter extends
        RecyclerView.Adapter<MyCollectionHotCollectedRecyclerAdapter.ViewHolder> {

    TextView textView2;
    private List<MyCollectionHotGameBean> mHotGameBeanList;
    private WeakReference<Context> mWeakReference;
    private ShouCangBean shouCangBean;
    private ImageView imgGamePic;
    private TextView tvGameName;
    private TextView tvGameType;
    private TextView tvGameCollecedNumber;
    private TextView tvGameDescription;
    private TextView tvGameCollected;
    private String gameUrl;
    private TextView tvLiBao;
    private TextView tvFanLi;
    private ImageView imgTag;
    private RelativeLayout rlItemLayout;
    private Handler handler;


    public MyCollectionHotCollectedRecyclerAdapter(List<MyCollectionHotGameBean> hotGameBeanList, Context context, Handler handler) {
        this.mHotGameBeanList = hotGameBeanList;
        this.mWeakReference = new WeakReference(context);
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_mygame_hotgame_item, parent, false));
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        tvLiBao.setVisibility(View.GONE);
        tvFanLi.setVisibility(View.GONE);
        tvGameCollected.setText("收藏");
        //热门游戏图片
        Glide.with(x.app()).load( mHotGameBeanList.get(position).getGamePic()).error(R.drawable.default_picture).into(imgGamePic);
        rlItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHotGameBeanList.get(position).getSdk_version().equals("3")){
                    Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", Integer.parseInt(mHotGameBeanList.get(position).getGameId()));
                    mWeakReference.get().startActivity(intent);
                }else {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.parseInt(mHotGameBeanList.get(position).getGameId()));
                    mWeakReference.get().startActivity(intent);
                }
            }
        });
        //热门游戏名称
        tvGameName.setText(mHotGameBeanList.get(position).getGameName());
        //热门游戏类型
        tvGameType.setText(mHotGameBeanList.get(position).getGameType());
        //热门游戏收藏人数
        tvGameCollecedNumber.setText(mHotGameBeanList.get(position).getGameCollectedNumber());
        textView2.setText("人收藏");
        //热门游戏描述
        tvGameDescription.setText(mHotGameBeanList.get(position).getGameDescription());
        if ("1".equals(mHotGameBeanList.get(position).getIsCollected())) {
            tvGameCollected.setEnabled(false);
            tvGameCollected.setTextColor(mWeakReference.get().getResources().getColor(R.color.font_gray));
            tvGameCollected.setText("已收藏");
            tvGameCollected.setBackground(ContextCompat.getDrawable(mWeakReference.get(), R.drawable.zhi_jiao_gray));
        }

        if (mHotGameBeanList.get(position).getSdk_version().equals("3")){
            imgTag.setBackgroundResource(R.mipmap.tag_h5);
        }else {
            imgTag.setBackgroundResource(R.mipmap.tag_shouyou);
        }
        //收藏游戏
        tvGameCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameUrl = mHotGameBeanList.get(position).getGamePlayUrl();
                doCollected(mHotGameBeanList.get(position).getGameId());
                Message message = new Message();
                message.what = MyCollectionFragment.NEED_REFRESH;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHotGameBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.img_icon);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_game_name);
            tvGameType = (TextView) itemView.findViewById(R.id.tv_type);
            tvGameCollecedNumber = (TextView) itemView.findViewById(R.id.tv_num);
            tvGameDescription = (TextView) itemView.findViewById(R.id.tv_msg);
            tvGameCollected = (TextView) itemView.findViewById(R.id.btn_start);
            textView2 = (TextView) itemView.findViewById(R.id.tv_hint);
            tvLiBao = (TextView) itemView.findViewById(R.id.tv_package);
            tvFanLi = (TextView) itemView.findViewById(R.id.tv_fanli);
            rlItemLayout = (RelativeLayout) itemView.findViewById(R.id.btn_item_layout);
            imgTag = itemView.findViewById(R.id.img_tag);
        }
    }

    public void doCollected(String gameId) {
        Map<String, String> map = new HashMap<>();
        map.put("game_id", gameId);
        map.put("token", Utils.getPersistentUserInfo().token);
        map.put("status", 1 + "");
        HttpConstant.POST(scHander, HttpConstant.API_GAME_COLLECT, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler scHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        shouCangBean = new Gson().fromJson(msg.obj.toString(), ShouCangBean.class);
                        Log.e("收藏返回的数据", msg.obj.toString());
                        if (shouCangBean.getCode() == 200) {
//                            new DialogCollection(mWeakReference.get(),
//                                    R.style.MyDialogStyle, gameUrl).show();
                            EvenBean evenBean = new EvenBean();
                            evenBean.b = 5;
                            EventBus.getDefault().post(evenBean);
                        } else {
                            ToastUtil.showToast(shouCangBean.getMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    ToastUtil.showToast("请求失败");
                    break;
            }
        }
    };

}
