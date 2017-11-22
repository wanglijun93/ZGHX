package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.util.List;

/**
 * Created by iwangtianyi on 2016/11/8.
 * 离线航班数据显示的适配器
 */
public class OfflineSearchAdapter extends BaseAdapter {

    private Context context;
    private List<FlightData.AccaFlightDateBean> offFlight;
    private DataBaseDao dao;

    public OfflineSearchAdapter(Context context, List<FlightData.AccaFlightDateBean> offFlight) {
        this.context = context;
        this.offFlight = offFlight;
        dao = new DataBaseDao(context);
    }

    @Override
    public int getCount() {
        return offFlight.size();
    }

    @Override
    public Object getItem(int position) {
        return offFlight.get(position);
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
            convertView = View.inflate(context, R.layout.security_details_item, null);
            holder.comefrom = (TextView) convertView.findViewById(R.id.comefrom);
            holder.aircorp = (TextView) convertView.findViewById(R.id.aircorp);
            holder.incident = (TextView) convertView.findViewById(R.id.incident);
            holder.qianxu = (TextView) convertView.findViewById(R.id.qianxu);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.tv_in_out_port = (TextView) convertView.findViewById(R.id.tv_in_out_port);
            holder.FLT_No = (TextView) convertView.findViewById(R.id.FLT_No);
            holder.airwei = (TextView) convertView.findViewById(R.id.airwei);
            holder.plan = (TextView) convertView.findViewById(R.id.plan);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(getDrawResourceID(offFlight.get(position).get_customer_code()));
        holder.aircorp.setText(dao.codeToName(offFlight.get(position).get_customer_code()).get(0));
        String dep_arr_flag = offFlight.get(position).get_dep_arr_flag();

        String acheme_dep_time = "";
        String in_out_port = "";
        if ("A".equals(dep_arr_flag)) {
            holder.tv_in_out_port.setText("进港");
            if (offFlight.get(position).get_real_arr_time()!=0) {
                acheme_dep_time = offFlight.get(position).get_real_arr_time()+"";
                in_out_port = "实际降落";
            } else if (offFlight.get(position).get_alter_arr_time()!=0) {
                acheme_dep_time = offFlight.get(position).get_alter_arr_time()+"";
                in_out_port = "预计降落";
            } else {
                acheme_dep_time = offFlight.get(position).get_scheme_arr_time() + "";
                in_out_port = "计划降落";

            }
            //根据三字码查找航班名称
            List<String> list = dao.threeCodeToName(offFlight.get(position).get_arr_airport_three());
            holder.comefrom.setText("来自    " + list.get(0));
        }

        if ("D".equals(dep_arr_flag)) {
            holder.tv_in_out_port.setText("出港");
            if (offFlight.get(position).get_real_dep_time() != 0) {
                acheme_dep_time = offFlight.get(position).get_real_dep_time() + "";
                in_out_port = "实际起飞";
            } else if (offFlight.get(position).get_alter_dep_time() != 0) {
                acheme_dep_time = offFlight.get(position).get_alter_dep_time() + "";
                in_out_port = "预计起飞";
            } else {
                acheme_dep_time = offFlight.get(position).get_alter_dep_time() + "";
                in_out_port = "计划起飞";

            }

            List<String> list = dao.threeCodeToName(offFlight.get(position).get_dep_airport_three());
            holder.comefrom.setText("去往    " + list.get(0));
        }

        String formatStr = "yyyyMMddHHmmss";
        String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
        //转换为2016-05-03 02:33:00 的格式
        String qianxu = DateToString.StringToDate(acheme_dep_time, formatStr, dateFromatStr);
        holder.qianxu.setText("  " + in_out_port + "  " + qianxu);

        String flight_state = offFlight.get(position).get_flight_state();

        if ("延误".equals(flight_state)) {

            holder.incident.setText(flight_state);
            holder.incident.setTextColor(Color.RED);
        } else {
            holder.incident.setText("正常");
            holder.incident.setTextColor(context.getResources().getColor(R.color.blue));
        }

        holder.FLT_No.setText(offFlight.get(position).get_flight_num_two());
        holder.airwei.setText(offFlight.get(position).get_slot());
        holder.plan.setText(offFlight.get(position).get_register_num());
        return convertView;
    }

    class ViewHolder {
        TextView tv_id;
        ImageView icon;
        TextView aircorp;
        TextView tv_in_out_port;
        TextView comefrom;
        TextView qianxu;
        TextView FLT_No;
        TextView airwei;
        TextView plan;
        TextView incident;
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
