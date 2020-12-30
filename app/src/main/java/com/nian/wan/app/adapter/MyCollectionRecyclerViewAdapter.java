package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.Type_Activity;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionIsShouldCheck;
import com.nian.wan.app.bean.CancelCollectionBean;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.MyCollectionBean;
import com.nian.wan.app.bean.SingleHotGameBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogMyCollectionConfirmDelete;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @Description: 我的收藏RecyclerView适配器
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/7 14:11
 * @Modified By:
 * @Modified Date:
 */
public class MyCollectionRecyclerViewAdapter extends RecyclerView.Adapter<MyCollectionRecyclerViewAdapter.ViewHolder> {

    private List<MyCollectionBean.CollectBean> collectBeanList;
    //是否显示所有CheckBox
    private boolean mIsShowAllCheckBox;
    //保存选中状态
    private Map<Integer, Boolean> mCheckedStatus = new HashMap<>();
    private WeakReference<Context> mWeakReference;

    private CancelCollectionBean cancelCollectionBean;
    private IMyCollectionIsShouldCheck iMyCollectionIsShouldCheck;


    public MyCollectionRecyclerViewAdapter(List<MyCollectionBean.CollectBean> collectBeanList, Context context) {
        this.collectBeanList = collectBeanList;
        mWeakReference = new WeakReference(context);
    }

    public void setiMyCollectionIsShouldCheck(IMyCollectionIsShouldCheck iMyCollectionIsShouldCheck) {
        this.iMyCollectionIsShouldCheck = iMyCollectionIsShouldCheck;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_collection_swipe, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (holder.cbIsCheck.getVisibility() == View.GONE){
            holder.cbIsCheck.setChecked(false);
        }
        Glide.with(x.app()).load( collectBeanList.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgGamePic);
        holder.tvGameName.setText(collectBeanList.get(position).getGame_name());
        String s = collectBeanList.get(position).getGame_type_name();
        if (s.length() > 5) {
            holder.tvGameType.setText(s.substring(0, 5) + "...");
        } else {
            holder.tvGameType.setText(collectBeanList.get(position).getGame_type_name());
        }
        holder.tvGamePlayerNum.setText(String.valueOf(collectBeanList.get(position).getPlay_num()));
        holder.cbIsCheck.setVisibility(mIsShowAllCheckBox == true ? View.VISIBLE : View.GONE);
        holder.cbIsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mCheckedStatus.put(position, isChecked);
                } else {
                    mCheckedStatus.remove(position);
                }
                if (mCheckedStatus.size() == collectBeanList.size()) {
                    MyGameActivity.myCollectionIsCheckAll = true;
                    iMyCollectionIsShouldCheck.shouldCheck();   //全选
                }
                if (mCheckedStatus.size() < collectBeanList.size()) {
                    MyGameActivity.myCollectionIsCheckAll = false;
                    iMyCollectionIsShouldCheck.unShouldCheck();  //不全选
                }
            }

        });

        if (mCheckedStatus.containsKey(position) && mCheckedStatus.get(position) == true) {
            holder.cbIsCheck.setChecked(true);
        } else {
            holder.cbIsCheck.setChecked(false);
        }

        //删除我的收藏
        holder.btnDeleteCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除单个我的收藏
                cancelCollection(new String[]{collectBeanList.get(position).getId()});
                collectBeanList.remove(position);
                //MyCollectionRecyclerViewAdapter.this.notifyDataSetChanged();
                EvenBean bean = new EvenBean();
                bean.b = 5;
                EventBus.getDefault().post(bean);
            }
        });
        //当进入批量编辑模式隐藏找相似
        holder.tvGoLookLike.setVisibility(mIsShowAllCheckBox == true ? View.GONE : View.VISIBLE);
        //找相似
        holder.tvGoLookLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", collectBeanList.get(position).getGame_type_name());
                intent.putExtra("type_id", collectBeanList.get(position).getGame_type_id());
                if (collectBeanList.get(position).getSdk_Version() == 3){
                    intent.putExtra("isH5Game", true);
                }else {
                    intent.putExtra("isH5Game", false);
                }
                intent.setClass(mWeakReference.get(), Type_Activity.class);
                mWeakReference.get().startActivity(intent);
            }
        });

        if (collectBeanList.get(position).getSdk_Version() == 3){
            holder.tvGoPlay.setText("去玩");
        }else {
            holder.tvGoPlay.setText("下载");
            if (collectBeanList.get(position).getXia_status() == 0){
                holder.tvGoPlay.setEnabled(false);
                holder.tvGoPlay.setBackgroundResource(R.drawable.bian_kuang_green);
            }else {
                holder.tvGoPlay.setBackgroundResource(R.drawable.yuan_jiao_green_bg);
            }
        }
        //去玩
        holder.tvGoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (collectBeanList.get(position).getSdk_Version() == 3){
                    Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", Integer.valueOf(collectBeanList.get(position).getId()));
                    mWeakReference.get().startActivity(intent);
                }else {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.valueOf(collectBeanList.get(position).getId()));
                    mWeakReference.get().startActivity(intent);
                }
            }
        });

        //游戏详情
        holder.rlMyCollectionDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (collectBeanList.get(position).getSdk_Version() == 3){
                    Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", Integer.valueOf(collectBeanList.get(position).getId()));
                    mWeakReference.get().startActivity(intent);
                }else {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.valueOf(collectBeanList.get(position).getId()));
                    mWeakReference.get().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return collectBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGamePic;
        TextView tvGameName;
        TextView tvGameType;
        TextView tvGamePlayerNum;
        TextView tvGoLookLike;
        TextView tvGoPlay;
        CheckBox cbIsCheck;
        Button btnDeleteCollected;
        RelativeLayout rlMyCollectionDescription;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.img_my_collection_pic);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_my_collection_game_name);
            tvGameType = (TextView) itemView.findViewById(R.id.tv_my_collection_game_type);
            tvGamePlayerNum = (TextView) itemView.findViewById(R.id.tv_my_collection_game_player);
            tvGoLookLike = (TextView) itemView.findViewById(R.id.tv_go_look_like);
            tvGoPlay = (TextView) itemView.findViewById(R.id.tv_go_play);
            cbIsCheck = (CheckBox) itemView.findViewById(R.id.cb_my_collection_is_check);
            btnDeleteCollected = (Button) itemView.findViewById(R.id.btnDelete);
            rlMyCollectionDescription = (RelativeLayout) itemView.findViewById(R.id.rl_my_collection_description);
        }
    }


    /**
     * 要取消收藏的游戏id集合
     *
     * @param gameId
     */
    public void cancelCollection(String[] gameId) {
        if (0 == gameId.length) {
            return;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < gameId.length; i++) {
                stringBuffer.append(gameId[i] + ",");
            }
            Type type = new TypeToken<HttpResult<CancelCollectionBean>>() {
            }.getType();
            Map<String, String> cancelCollectionParams = new HashMap<>();
            if (null != Utils.getPersistentUserInfo()) {
                cancelCollectionParams.put("token", Utils.getPersistentUserInfo().token);
                cancelCollectionParams.put("game_id", stringBuffer.toString());
            }
            new HttpUtils<ArrayList<SingleHotGameBean>>(type, HttpConstant.API_PERSONAL_CANCEL_COLLECTION, cancelCollectionParams, "删除收藏", true) {

                @Override
                protected void _onSuccess(ArrayList<SingleHotGameBean> bean, String msg) {
                    EvenBean evenBean = new EvenBean();
                    evenBean.b = 5;
                    EventBus.getDefault().post(evenBean);

                }

                @Override
                protected void _onError(String message, int code) {
                    Utils.TS(message);
                    EvenBean evenBean = new EvenBean();
                    evenBean.b = 5;
                    EventBus.getDefault().post(evenBean);
                }

                @Override
                protected void _onError() {
                    EvenBean evenBean = new EvenBean();
                    evenBean.b = 5;
                    EventBus.getDefault().post(evenBean);
                }
            };
        }
    }


    /**
     * 是否显示全部CheckBox
     *
     * @param isShowAllCheckBox
     */
    public void isShowAllcheckBox(boolean isShowAllCheckBox) {
        this.mIsShowAllCheckBox = isShowAllCheckBox;
        this.notifyDataSetChanged();
    }

    /**
     * 是否全选CheckBox
     *
     * @param isCheckAllCheckBox
     */
    public void isCheckAllCheckBox(boolean isCheckAllCheckBox) {
        for (int i = 0; i < collectBeanList.size(); i++) {
            mCheckedStatus.put(i, isCheckAllCheckBox);
        }
        this.notifyDataSetChanged();
    }

    /**
     * 删除选中的CheckBox;必须要逐步操作否则会有以下问题
     * 1、选中条数无法全部删除
     * 2、选中条数无法全部删除且删除的条目CheckBox状态会因为RecyclerView复用Item的原因
     * 把状态付给删除条目下标之后条目
     */
    public void deleteChecked() {
        try {
            //1、把要删除的条目放在一个集合中并更新CheckBox状态集合
            final List<MyCollectionBean.CollectBean> myCollectionBeen = new ArrayList<>();
            for (int i = 0; i < collectBeanList.size(); i++) {
                if (null != mCheckedStatus.get(i) && mCheckedStatus.get(i) == true) {
                    //删除已经保存的CheckBox状态
                    //mCheckedStatus.remove(i);

                    //加入到要删除的集合中
                    myCollectionBeen.add(collectBeanList.get(i));
                }
            }
            @SuppressLint("HandlerLeak")
            Handler deleteHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case MyGameActivity.ACTION_DELETE_CONFIRM:
                            StringBuffer deleteId = new StringBuffer();
                            for (int k = 0; k < myCollectionBeen.size(); k++) {
                                deleteId.append(myCollectionBeen.get(k).getId() + ",");
                            }
                            //批量删除网络请求
                            cancelCollection(new String[]{deleteId.toString()});
                            //2、删除集合中的条目
                            for (int j = 0; j < myCollectionBeen.size(); j++) {
                                collectBeanList.remove(myCollectionBeen.get(j));
                            }
                            isShowAllcheckBox(false);
                            //3、刷新
                            MyCollectionRecyclerViewAdapter.this.notifyDataSetChanged();
                            break;
                    }
                }
            };
            if (myCollectionBeen.size() > 0) {
                new DialogMyCollectionConfirmDelete(mWeakReference.get(), R.style.MyDialogStyle, deleteHandler).show();
            } else {
                ToastUtil.showToast("请选择需要操作的数据");
            }
        }catch (Exception e){
            Log.e("删除选中的CheckBox异常",e.toString());
        }
    }

}
