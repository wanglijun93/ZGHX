package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.AccaServiceType;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.util.List;

/**
 * Created by iwanglijun on 2016/9/26.
 */
public class PreviewDocAdapter2 extends BaseAdapter {
    private Context context;
    private long start_time;
    private long end_time;
    private List<FlightData.AccaServiceDateBean> list1;
    private List<AccaServiceType> accaServiceType;
    private String item_name;
    private String flag;

    public PreviewDocAdapter2(Context context,  List<FlightData.AccaServiceDateBean> list1) {
        this.context = context;
        this.list1 = list1;
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return list1.get(position);
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
            convertView = View.inflate(context, R.layout.activity_preview_doc1_list, null);
            holder.tv_message = (TextView) convertView.findViewById(R.id.textview1);
            holder.create_time = (TextView) convertView.findViewById(R.id.textview2);
            holder.end_time = (TextView) convertView.findViewById(R.id.textview3);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.textview4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        item_name = list1.get(position).get_item_name();
        holder.tv_message.setText(list1.get(position).get_item_name());
        holder.tv_amount.setText(list1.get(position).get_item_value());

        start_time = list1.get(position).get_start_time();
        end_time = list1.get(position).get_end_time();

        DataBaseDao dao = new DataBaseDao(context);
        accaServiceType = dao.getAccaServiceType();

        for (AccaServiceType service : accaServiceType) {
            if (service.getSERVICE_NAME().equals(item_name)) {
                flag = service.getHOUR_FLAG();
                break;
            }
        }
        if ("Y".equals(flag)) {
            String formatStr = "yyyyMMddHHmm";
            String dateFromatStr = "yyyy-MM-dd HH:mm";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu1 = DateToString.StringToDate2(start_time, formatStr, dateFromatStr);
            LogUtil.d("---starttime",start_time + "");
            String substring1 = qianxu1.substring(5, qianxu1.length());
            holder.create_time.setText(substring1);

            //转换为2016-05-03 02:33:00 的格式
            String qianxu2 = DateToString.StringToDate2(end_time, formatStr, dateFromatStr);
            LogUtil.d("---starttime",end_time + "");
            String substring2 = qianxu2.substring(5, qianxu2.length());
            holder.end_time.setText(substring2);

            LogUtil.d("---time", start_time + "");
            LogUtil.d("---time1", end_time + "");
            LogUtil.d("---", "和map中的值一样，显示");
        } else {
            holder.create_time.setText("- - -   - - -");
            holder.end_time.setText("- - -   - - -");
        }


        return convertView;
    }

    class ViewHolder {
        TextView tv_amount;
        TextView tv_message;
        TextView create_time;
        TextView end_time;
    }
}
