package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.GameMenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏类别
 */
public class GameMenuAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<GameMenuBean> gameTypeBeans = new ArrayList<GameMenuBean>();


    public GameMenuAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    public void setData(List<GameMenuBean> gameTypeBeans) {
        this.gameTypeBeans = gameTypeBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gameTypeBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.game_menu_item, null);
            holder.image = convertView.findViewById(R.id.image);
            holder.text = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setBackgroundResource(gameTypeBeans.get(position).getType_image());
        holder.text.setText(gameTypeBeans.get(position).getType_name());
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView text;
    }


}
