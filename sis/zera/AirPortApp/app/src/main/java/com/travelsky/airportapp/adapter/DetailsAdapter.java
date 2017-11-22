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
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @author wty
 *         添加保障详情的数据
 */
public class DetailsAdapter extends BaseAdapter {

    private Context context;
    private List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen;
    private String create_time;
    private String update_time;
    private String signstate;
    public Map<Integer, AirInfo.DataBean.AccaServiceBean> map;
    private  List<AirInfo.DataBean.AccaServiceBean> list1;
    private  AirInfo.DataBean.AccaServiceBean accaServiceBean1;
    private AirInfo.DataBean.AccaServiceBean accaServiceBean2;
    private DataBaseDao dao;

    public DetailsAdapter(Context context, List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen) {
        this.context = context;
        this.accaServiceBeen = accaServiceBeen;
        list1 = new ArrayList<>();
        map = new HashMap<>();
        accaServiceBean1 = new AirInfo.DataBean.AccaServiceBean();
        dao = new DataBaseDao(context);
    }

    @Override
    public int getCount() {
        return accaServiceBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return accaServiceBeen.get(position);
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
        signstate = accaServiceBeen.get(position).getSignstate();
        //已签名
        if ("N".equals(signstate)) {
            for (int i = 0; i<accaServiceBeen.size();i++) {
                accaServiceBean2 = accaServiceBeen.get(i);
                String signstate2 = accaServiceBean2.getSignstate();
                if ("N".equals(signstate2)) {
                    if (!list1.contains(accaServiceBean2)){
                        list1.add(accaServiceBean2);
                    }
                }
            }
        }
        EventBus.getDefault().post(list1);
        holder.tv_amount.setText(accaServiceBeen.get(position).getItem_value());
        holder.tv_message.setText(accaServiceBeen.get(position).getItem_name());

        create_time = accaServiceBeen.get(position).getCreate_time();
        update_time = accaServiceBeen.get(position).getUpdate_time();
        LogUtil.d("---create_time",create_time);
        LogUtil.d("---update_time",update_time);
        if (update_time!=null) {
            String formatStr = "yyyyMMddHHmmss";
            String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu1 = DateToString.StringToDate(update_time, formatStr, dateFromatStr);
            LogUtil.d("---qianxu1",qianxu1);
            String substring1 = qianxu1.substring(5, qianxu1.length());
            LogUtil.d("---sbustring1",substring1);
            holder.tv_date.setText(substring1);
        } else {
            String formatStr = "yyyyMMddHHmmss";
            String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu1 = DateToString.StringToDate(create_time, formatStr, dateFromatStr);
            LogUtil.d("---qianxu11",qianxu1);
            String substring1 = qianxu1.substring(5, qianxu1.length());
            LogUtil.d("---sbustring11",substring1);
            holder.tv_date.setText(substring1);
        }

        //保障详情条目点击事件
        holder.tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.d("detailsadapter------", "点击了保障详情的listview");
                Intent intent = new Intent();
                intent.setClass(context, ModifySecurityDetailsActivity.class);
                intent.putExtra("name", accaServiceBeen.get(position).getItem_name());
                intent.putExtra("num", accaServiceBeen.get(position).getItem_value());
                intent.putExtra("Service_Seq", accaServiceBeen.get(position).getService_seq());
                intent.putExtra("service_type", "");
                intent.putExtra("create_time", accaServiceBeen.get(position).getCreate_time());
                intent.putExtra("update_time", accaServiceBeen.get(position).getUpdate_time());
                intent.putExtra("start_time", accaServiceBeen.get(position).getStart_time());
                intent.putExtra("end_time", accaServiceBeen.get(position).getEnd_time());
                intent.putExtra("Attach", accaServiceBeen.get(position).getAttach());
                intent.putExtra("signstate", accaServiceBeen.get(position).getSignstate());
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
