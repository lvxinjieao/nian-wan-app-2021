package com.nian.wan.app.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * 我的礼包Adapter
 * Created by Administrator on 2017/3/20.
 */

public class MyGiftListAdapter extends BaseAdapter {

    private Context context;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
//    public List<MyGiftBean.MsgEntity> list = new ArrayList<>();
//    private SwipeItemView mLastSlideViewWithStatusOn;
//    private int pos1;

   /* public MyGiftListAdapter(List<MyGiftBean.MsgEntity> listDate, Context context){
        this.context = context;
        this.list = listDate;
    }

    public void setList(List<MyGiftBean.MsgEntity> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyGiftCenter_Hoder holder =null;

        if (convertView == null){
            holder = new MyGiftCenter_Hoder();
        }else {
            holder = (MyGiftCenter_Hoder) convertView.getTag();
        }
        MyGiftBean.MsgEntity  listMyGift = list.get(position);
        holder.setData(listMyGift,position,null);
        return holder.getContentView();*/

//        ViewHolder viewHolder = null;
//        SwipeItemView slideView = (SwipeItemView) convertView;
//        if(convertView==null){
//            View itemView = LayoutInflater.from(x.app()).inflate(R.layout.holder_mygift, null);
//            slideView = new SwipeItemView(x.app());
//            slideView.setContentView(itemView);
//            viewHolder = new ViewHolder(slideView);
//            slideView.setOnSlideListener(new SwipeItemView.OnSlideListener() {
//
//                @Override
//                public void onSlide(View view, int status) {
//                    if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
//                        mLastSlideViewWithStatusOn.shrink();
//                    }
//                    if (status == SLIDE_STATUS_ON) {
//                        mLastSlideViewWithStatusOn = (SwipeItemView) view;
//                    }
//                }
//            });
//            slideView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) slideView.getTag();
//        }
//        final MyGiftBean.MsgEntity item = list.get(position);
//        if (CustomSwipeListView.mFocusedItemView != null) {
//            CustomSwipeListView.mFocusedItemView.shrink();
//        }
//
//        viewHolder.deleteHolder.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                pos1 = position;
//                removeCheckedItem(position, "", item);
//            }
//        });
    }



//    private void removeCheckedItem(int pos, String imei, MyGiftBean.MsgEntity item) {
//        Map<String, String> map = new HashMap<String, String>();
////        map.put("gift_id", item.gift_id + "");
////        map.put("token", Utils.getLoginUser().token);
////        Log.e("adapter的删除参数", item.gift_id + "" + "++token++" + imei);
//        HttpCom.POST(mhandler, HttpCom.DelGiftURL, map, false);
//    }
//
//
//    public void setList(List<MyGiftBean.MsgEntity> b){
//        this.list=b;
//        notifyDataSetChanged();
//    }
//
//    public List<MyGiftBean.MsgEntity> getList(){
//        return list;
//    }
//
//    class ViewHolder {
//
//        public ViewHolder(View convertView) {
//            home_game_icon = (ImageView) convertView.findViewById(R.id.icon);
//            gift_name = (TextView) convertView.findViewById(R.id.gift_name);
//            name = (TextView) convertView.findViewById(R.id.name);
//            gitf_time = (TextView) convertView.findViewById(R.id.youxiaoqi);
//            jihuoma = (TextView) convertView.findViewById(R.id.jihuoma);
//            fuzhi = (TextView) convertView.findViewById(R.id.fuzhi);
//            deleteHolder = (ViewGroup) convertView.findViewById(R.id.holder);
//        }
//        public ImageView home_game_icon;
//        public TextView gift_name;
//        public TextView gitf_time;
//        public TextView fuzhi;
//        public TextView name;
//        public TextView jihuoma;
//        public ViewGroup deleteHolder;
//    }
//
//
//    Handler mhandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                Log.e("删除已领取礼包结果", msg.obj.toString());
//                try {
//                    JSONObject jsonObject = new JSONObject(msg.obj.toString());
//                    int status = jsonObject.getInt("status");
//                    String msg1 = jsonObject.getString("return_code");
//                    if (status == 1) {
//                        list.remove(pos1);
//                        notifyDataSetChanged();
//                    }else{
//                        ToastUtil.showToast(msg1);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("解析删除礼包异常", e.toString());
//                }
//                break;
//                case 2:
//                    ToastUtil.showToast("网络异常");
//                    break;
//            }
//        }
//    };

