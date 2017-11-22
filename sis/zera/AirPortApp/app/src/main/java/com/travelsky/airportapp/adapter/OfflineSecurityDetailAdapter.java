package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.travelsky.airportapp.AlwaysMarqueeTextView;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.activity.ModifySecurityDetailsActivity;
import com.travelsky.airportapp.domain.AirInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by meisme on 2016/11/14.
 */
public class OfflineSecurityDetailAdapter extends BaseAdapter {
    private Context context;
    private List<FlightData.AccaServiceDateBean> serviceDateBySeq;
    private String create_time;
    private String update_time;
    private String signstate;
    public Map<Integer, AirInfo.DataBean.AccaServiceBean> map;
    private List<FlightData.AccaServiceDateBean> list2;
    private FlightData.AccaServiceDateBean accaServiceBean2;

    public OfflineSecurityDetailAdapter(Context context, List<FlightData.AccaServiceDateBean> serviceDateBySeq) {
        this.context = context;
        this.serviceDateBySeq = serviceDateBySeq;
        list2 = new ArrayList<>();
        map = new HashMap<>();
    }

    @Override
    public int getCount() {
        return serviceDateBySeq.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceDateBySeq.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.security_arrive_item, null);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.tv_message = (AlwaysMarqueeTextView) convertView.findViewById(R.id.tv_message);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //是否签名状态
        signstate = serviceDateBySeq.get(position).get_signstate();
        //已签名
        if ("N".equals(signstate)) {
            for (int i = 0; i < serviceDateBySeq.size(); i++) {
                accaServiceBean2 = serviceDateBySeq.get(i);
                String signstate2 = accaServiceBean2.get_signstate();
                if ("N".equals(signstate2)) {
                    if (!list2.contains(accaServiceBean2)) {
                        list2.add(accaServiceBean2);
                    }
                }
            }
        }
        if (list2 != null) {
            LogUtil.d("---offline","list不为空");
        }else {
            LogUtil.d("---offline","list空");
        }
        EventBus.getDefault().post(list2);

        holder.tv_amount.setText(serviceDateBySeq.get(position).get_item_value());
        holder.tv_message.setText(serviceDateBySeq.get(position).get_item_name());

        create_time = String.valueOf(serviceDateBySeq.get(position).get_create_time());
        update_time = serviceDateBySeq.get(position).get_update_time()+"";
        LogUtil.d("---create_time", create_time);
        LogUtil.d("---update_time", update_time);
        if (update_time != null) {
            String formatStr = "yyyyMMddHHmmss";
            String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu1 = DateToString.StringToDate(update_time, formatStr, dateFromatStr);
            LogUtil.d("---qianxu1", qianxu1);
            String substring1 = qianxu1.substring(5, qianxu1.length());
            LogUtil.d("---sbustring1", substring1);
            holder.tv_date.setText(substring1);
        } else {
            String formatStr = "yyyyMMddHHmmss";
            String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu1 = DateToString.StringToDate(create_time, formatStr, dateFromatStr);
            LogUtil.d("---qianxu11", qianxu1);
            String substring1 = qianxu1.substring(5, qianxu1.length());
            LogUtil.d("---sbustring11", substring1);
            holder.tv_date.setText(substring1);
        }

        //保障详情条目点击事件
        holder.tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("detailsadapter------", "点击了保障详情的listview");
                Intent intent = new Intent();
                intent.setClass(context, ModifySecurityDetailsActivity.class);
                intent.putExtra("name", serviceDateBySeq.get(position).get_item_name());
                intent.putExtra("num", serviceDateBySeq.get(position).get_item_value());
                intent.putExtra("Service_Seq", serviceDateBySeq.get(position).get_service_seq());
                intent.putExtra("service_type", serviceDateBySeq.get(position).get_service_type());
                intent.putExtra("create_time", serviceDateBySeq.get(position).get_create_time());
                intent.putExtra("update_time", serviceDateBySeq.get(position).get_update_time());
                intent.putExtra("start_time", serviceDateBySeq.get(position).get_start_time()+"");
                intent.putExtra("end_time", serviceDateBySeq.get(position).get_end_time()+"");
                intent.putExtra("Attach", serviceDateBySeq.get(position).get_attach());
                intent.putExtra("signstate", serviceDateBySeq.get(position).get_signstate());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_amount;
        AlwaysMarqueeTextView tv_message;
        TextView tv_date;
        TextView tv_time;
    }
}
