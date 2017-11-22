package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.SearchBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iwanglijun on 2016/8/31.
 * 搜索列表适配器
 */
public class SearchResultAdapter extends BaseAdapter {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 用来控制checkbox的选中状态
     */
    public static Map<Integer, Boolean> isSelected;

    List<SearchBean> list;
    private String acheme_dep_time;
    private String dep_arr_flag;

    /**
     * 构造器
     *
     * @param context
     */
    public SearchResultAdapter(List<SearchBean> list, Context context) {
        this.context = context;
        this.list = list;
        isSelected = new HashMap<>();
        initflag();
    }

    /**
     * 设置checkbox是否选中
     */
    private void initflag() {
        for (int i = 0; i < list.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.airport_add_plane_list_item, null);
            holder.comefrom = (TextView) convertView.findViewById(R.id.comefrom);
            holder.aircorp = (TextView) convertView.findViewById(R.id.aircorp);
            holder.incident = (TextView) convertView.findViewById(R.id.incident);
            holder.qianxu = (TextView) convertView.findViewById(R.id.qianxu);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.tv_in_out_port = (TextView) convertView.findViewById(R.id.tv_in_out_port);
            holder.FLT_No = (TextView) convertView.findViewById(R.id.FLT_No);
            holder.airwei = (TextView) convertView.findViewById(R.id.airwei);
            holder.plan = (TextView) convertView.findViewById(R.id.plan);
            holder.cBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String customer_code = list.get(position).getCustomer_Code();
        holder.icon.setImageResource(getDrawResourceID(customer_code));
        holder.tv_in_out_port.setText("去往首都机场");
        holder.aircorp.setText(list.get(position).getCustomer_Name());
        String dep_arr_flag = list.get(position).getDep_arr_Flag();

        String in_out_port = "";
        if ("A".equals(dep_arr_flag)) {
            holder.tv_in_out_port.setText("进港");
            if (!list.get(position).getReal_Arr_Time().isEmpty()) {
                acheme_dep_time = list.get(position).getReal_Arr_Time();
                in_out_port = "实际降落";
            } else if (!list.get(position).getAlter_Arr_Time().isEmpty()) {
                acheme_dep_time = list.get(position).getAlter_Arr_Time();
                in_out_port = "预计降落";
            } else {
                acheme_dep_time = list.get(position).getScheme_Arr_Time();
                in_out_port = "计划降落";

            }
        }
        if ("D".equals(dep_arr_flag)) {
            holder.tv_in_out_port.setText("出港");
            if (!list.get(position).getReal_Dep_Time().isEmpty()) {
                acheme_dep_time = list.get(position).getReal_Dep_Time();
                in_out_port = "实际起飞";
            } else if (!list.get(position).getAlter_Dep_Time().isEmpty()) {
                acheme_dep_time = list.get(position).getAlter_Dep_Time();
                in_out_port = "预计起飞";
            } else {
                acheme_dep_time = list.get(position).getScheme_Dep_Time();
                in_out_port = "计划起飞";

            }
        }

        holder.qianxu.setText("  " + in_out_port + "  " + acheme_dep_time);

        String flight_state = list.get(position).getFlight_State();

        if ("延误".equals(flight_state)) {

            holder.incident.setText(flight_state);
            holder.incident.setTextColor(Color.RED);
        } else {
            holder.incident.setText("正常");
            holder.incident.setTextColor(Color.parseColor("#0a57b0"));
        }
        if ("".equals(list.get(position).getArr_Airport_Three())) {

            holder.comefrom.setText("去往    " + list.get(position).getDep_Airport_Three());
        } else {
            holder.comefrom.setText("来自    " + list.get(position).getArr_Airport_Three());
        }
        holder.FLT_No.setText(list.get(position).getFlight_Num_Two());
        holder.airwei.setText(list.get(position).getSlot());
        holder.plan.setText(list.get(position).getRegister_Num());
        holder.FLT_No.setText(list.get(position).getFlight_Num_Two());
        holder.airwei.setText(list.get(position).getSlot());
        holder.plan.setText(list.get(position).getRegister_Num());
        holder.cBox.setChecked(isSelected.get(position));

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    public class ViewHolder {
        public CheckBox cBox;
        public TextView tv_id;
        public ImageView icon;
        public TextView aircorp;
        public TextView tv_in_out_port;
        public TextView comefrom;
        public TextView qianxu;
        public TextView FLT_No;
        public TextView airwei;
        public TextView plan;
        public TextView incident;
    }
    /**
     * 根据图片的名称获取对应的资源id
     *
     * @param resourceName
     * @return
     */
    public int getDrawResourceID(String resourceName) {
        Resources res = context.getResources();
        int picid = res.getIdentifier("aircorp_" + resourceName.toLowerCase(), "drawable", context.getPackageName());
        return picid;
    }
}
