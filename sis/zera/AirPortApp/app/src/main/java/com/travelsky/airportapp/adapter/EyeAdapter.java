package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.AccaServiceType;
import com.travelsky.airportapp.domain.EyeInfo;
import com.travelsky.airportapp.utils.BitmapAndStringUtils;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.util.List;

/**
 * Created by iwanglijun on 2016/10/12.
 * 签字预览的数据适配器
 */
public class EyeAdapter extends BaseAdapter {
    private Context context;
    private List<EyeInfo.FlightDataBean.SignInfoBean> signInfo;
    private List<AccaServiceType> accaServiceType;
    private String flag;

    public EyeAdapter(Context context, List<EyeInfo.FlightDataBean.SignInfoBean> signInfo) {
        this.context = context;
        this.signInfo = signInfo;
    }

    @Override
    public int getCount() {
        return signInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return signInfo.get(position);
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
            convertView = View.inflate(context, R.layout.activity_eye_list, null);
            holder.fuwu = (TextView) convertView.findViewById(R.id.fuwu);
            holder.fuwutime = (TextView) convertView.findViewById(R.id.fuwutime);
            holder.fuwuendtime = (TextView) convertView.findViewById(R.id.fuwuendtiem);
            holder.fuwushuliang = (TextView) convertView.findViewById(R.id.fuwushuliang);
            holder.imageView_eye = (ImageView) convertView.findViewById(R.id.imageView_eye);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //2017/08/16 今天经理需求改动，if判断去掉
//        if (signInfo.get(position).getServer_Sign() != null) {
            LogUtil.d("---签名不为空","签名不为空");
            String item_name = signInfo.get(position).getItem_Name();
            holder.fuwu.setText(signInfo.get(position).getItem_Name());
            String start_time = signInfo.get(position).getStart_Time();
            String end_time = signInfo.get(position).getEnd_Time();
            String formatStr = "yyyyMMddHHmm";
            String dateFromatStr = "yyyy-MM-dd HH:mm";
            //转换为2016-05-03 02:33:00 的格式
            String fuwustarttime = DateToString.StringToDate(start_time, formatStr, dateFromatStr);
            String fuwuendtime = DateToString.StringToDate(end_time, formatStr, dateFromatStr);
            DataBaseDao dao = new DataBaseDao(context);
            accaServiceType = dao.getAccaServiceType();

            for (AccaServiceType service : accaServiceType) {
                if (service.getSERVICE_NAME().equals(item_name)) {
                    flag = service.getHOUR_FLAG();
                    break;
                }
            }
            if ("Y".equals(flag)) {
                holder.fuwutime.setText(fuwustarttime);
                holder.fuwuendtime.setText(fuwuendtime);
            } else {
                holder.fuwutime.setText("- - -   - - -");
                holder.fuwuendtime.setText("- - -   - - -");
            }

            holder.fuwushuliang.setText(signInfo.get(position).getItem_Value());
            String server_sign = signInfo.get(position).getServer_Sign();

            LogUtil.d("TAG", "bitmap111==-"+BitmapAndStringUtils.convertStringToIcon(server_sign));
            holder.imageView_eye.setImageBitmap(BitmapAndStringUtils.convertStringToIcon(server_sign));
//        }else {
            LogUtil.d("---签名为空","签名为空");
//        }
        return convertView;
    }

    class ViewHolder {
        TextView fuwu;
        TextView fuwutime;
        TextView fuwuendtime;
        TextView fuwushuliang;
        ImageView imageView_eye;
    }
}
