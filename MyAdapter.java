package com.john.myapplication;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/12/21.
 */

public  class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
   private List<Map<String, Object>> data;
    //private Context context;
   // private List<Message> Datas;
    private Context mContext;
//    private LayoutInflater mInflater;
//    private Context context;
    public MyAdapter( Context mContext,List<Map<String, Object>> Datas) {
        this.data = Datas;
        this.inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    /**
     * 返回item的个数
     * @return
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 返回每一个item对象
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * 返回每一个item的id
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }




    /**
     * 暂时不做优化处理，后面会专门整理BaseAdapter的优化
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item,viewGroup,false);
            holder.imageView = (ImageView) view.findViewById(R.id.image1);
            holder.textView1 = (TextView) view.findViewById(R.id.text1);
            holder.textView2 = (TextView) view.findViewById(R.id.text2);
            //TextView textView2 = (TextView) view.findViewById(R.id.text2);
            view.setTag(holder);
        }else {
            holder=(ViewHolder)view.getTag();
        }
        holder.imageView.setImageResource((int)data.get(i).get("icon"));
        holder.textView1.setText(data.get(i).get("name").toString());
        holder.textView2.setText(data.get(i).get("data").toString());
//        holder.imageView.setImageResource(Datas.get(i).getImageId());
//        holder.textView1.setText(Datas.get(i).getTheme());
//        holder.textView2.setText(Datas.get(i).getContent());
//        此处需要返回view 不能是view中某一个
        return view;

    }
    class ViewHolder{

        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
    }
}