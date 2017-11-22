package com.travelsky.airportapp.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.util.List;

/**
 * @author wty
 *         填充航班管控页面ExpandableListView数据
 */
public class FlightListAdapter extends BaseExpandableListAdapter {

    private List<FlightData.AccaFlightDateBean> ylist;
    private List<FlightData.AccaFlightDateBean> nlist;
    private String acheme_dep_time;
    private DataBaseDao dao;

    Context context;
    String[] group = new String[]{"当前航班", "历史航班"};

    public FlightListAdapter(Context context, List<FlightData.AccaFlightDateBean> ylist, List<FlightData.AccaFlightDateBean> nlist) {
        this.ylist = ylist;
        this.nlist = nlist;
        this.context = context;
        dao = new DataBaseDao(context);
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.activity_main_item, null);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_id.setText(group[groupPosition]);
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        if (group[groupPosition] == "当前航班") {
            if (ylist == null) {
                return 0;
            }
            return ylist.size();
        } else {
            if (nlist == null) {
                return 0;
            }
            return nlist.size();
        }
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (group[groupPosition] == "当前航班") {
            return ylist.get(groupPosition);
        } else {
            return nlist.get(groupPosition);
        }


    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

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


        if (group[groupPosition] == "当前航班") {
            holder.icon.setImageResource(getDrawResourceID(ylist.get(childPosition).get_customer_code()));
            holder.aircorp.setText(dao.codeToName(ylist.get(childPosition).get_customer_code()).get(0));
            String dep_arr_flag = ylist.get(childPosition).get_dep_arr_flag();

            String in_out_port = "";
            if ("A".equals(dep_arr_flag)) {
                holder.tv_in_out_port.setText("进港");
                if (ylist.get(childPosition).get_real_arr_time()!=0) {
                    acheme_dep_time = ylist.get(childPosition).get_real_arr_time()+"";
                    in_out_port = "实际降落";
                } else if (ylist.get(childPosition).get_alter_arr_time()!=0) {
                    acheme_dep_time = ylist.get(childPosition).get_alter_arr_time()+"";
                    in_out_port = "预计降落";
                } else {
                    acheme_dep_time = ylist.get(childPosition).get_scheme_arr_time() + "";
                    in_out_port = "计划降落";

                }
                //根据三字码查找航班名称
                List<String> list = dao.threeCodeToName(ylist.get(childPosition).get_arr_airport_three());
                holder.comefrom.setText("来自    " + list.get(0));
            }

            if ("D".equals(dep_arr_flag)) {
                holder.tv_in_out_port.setText("出港");
                if (ylist.get(childPosition).get_real_dep_time() != 0) {
                    acheme_dep_time = ylist.get(childPosition).get_real_dep_time() + "";
                    in_out_port = "实际起飞";
                } else if (ylist.get(childPosition).get_alter_dep_time() != 0) {
                    acheme_dep_time = ylist.get(childPosition).get_alter_dep_time() + "";
                    in_out_port = "预计起飞";
                } else {
                    acheme_dep_time = ylist.get(childPosition).get_scheme_dep_time() + "";
                    in_out_port = "计划起飞";

                }

                List<String> list = dao.threeCodeToName(ylist.get(childPosition).get_dep_airport_three());
                holder.comefrom.setText("去往    " + list.get(0));
            }

            String formatStr = "yyyyMMddHHmmss";
            String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu = DateToString.StringToDate(acheme_dep_time, formatStr, dateFromatStr);
            holder.qianxu.setText("  " + in_out_port + "  " + qianxu);

            String flight_state = ylist.get(childPosition).get_flight_state();

            if ("延误".equals(flight_state)) {

                holder.incident.setText(flight_state);
                holder.incident.setTextColor(Color.RED);
            } else {
                holder.incident.setText("正常");
                holder.incident.setTextColor(Color.parseColor("#0a57b0"));
            }

            holder.FLT_No.setText(ylist.get(childPosition).get_flight_num_two());
            holder.airwei.setText(ylist.get(childPosition).get_slot());
            holder.plan.setText(ylist.get(childPosition).get_register_num());
        } else {
            holder.icon.setImageResource(getDrawResourceID(nlist.get(childPosition).get_customer_code()));

            holder.tv_in_out_port.setText("历史航班");
            holder.aircorp.setText(dao.codeToName(nlist.get(childPosition).get_customer_code()).get(0));
            String dep_arr_flag = nlist.get(childPosition).get_dep_arr_flag();

            String in_out_port = "";
            if ("A".equals(dep_arr_flag)) {
                holder.tv_in_out_port.setText("进港");
                if (nlist.get(childPosition).get_real_arr_time()!=0) {
                    acheme_dep_time = nlist.get(childPosition).get_real_arr_time()+"";
                    in_out_port = "实际降落";
                } else if (ylist.get(childPosition).get_alter_arr_time()!=0) {
                    acheme_dep_time = nlist.get(childPosition).get_alter_arr_time()+"";
                    in_out_port = "预计降落";
                } else {
                    acheme_dep_time = nlist.get(childPosition).get_scheme_arr_time()+"";
                    in_out_port = "计划降落";

                }

                List<String> list = dao.threeCodeToName(nlist.get(childPosition).get_arr_airport_three());
                holder.comefrom.setText("来自   " + list.get(0));
            }

            if ("D".equals(dep_arr_flag)) {
                holder.tv_in_out_port.setText("出港");
                if (nlist.get(childPosition).get_real_dep_time()!=0) {
                    acheme_dep_time = nlist.get(childPosition).get_real_dep_time()+"";
                    in_out_port = "实际起飞";
                } else if (nlist.get(childPosition).get_alter_dep_time()!=0) {
                    acheme_dep_time = nlist.get(childPosition).get_alter_dep_time()+"";
                    in_out_port = "预计起飞";
                } else {
                    acheme_dep_time = nlist.get(childPosition).get_scheme_dep_time()+"";
                    in_out_port = "计划起飞";

                }
                List<String> list = dao.threeCodeToName(nlist.get(childPosition).get_dep_airport_three());
                holder.comefrom.setText("去往   " + list.get(0));
            }

            String formatStr = "yyyyMMddHHmmss";
            String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu = DateToString.StringToDate(acheme_dep_time, formatStr, dateFromatStr);
            holder.qianxu.setText("  " + in_out_port + "  " + qianxu);

            String flight_state = nlist.get(childPosition).get_flight_state();

            if ("延误".equals(flight_state)) {

                holder.incident.setText(flight_state);
                holder.incident.setTextColor(Color.RED);
            } else {
                holder.incident.setText("正常");
                holder.incident.setTextColor(Color.parseColor("#0a57b0"));
            }

            holder.FLT_No.setText(nlist.get(childPosition).get_flight_num_two());
            holder.airwei.setText(nlist.get(childPosition).get_slot());
            holder.plan.setText(nlist.get(childPosition).get_register_num());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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