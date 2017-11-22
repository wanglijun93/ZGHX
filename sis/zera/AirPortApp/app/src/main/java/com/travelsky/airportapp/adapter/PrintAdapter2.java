package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.FlightData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 离线模式下的 打印单据页面
 * @author wty
 *         添加打印单据页面数据
 */
public class PrintAdapter2 extends BaseAdapter {
    //判断选项是否被选中
    boolean btn_click = true;
    private Context context;
    private List<FlightData.AccaServiceDateBean> accaServiceBean;
    private String indexTableData[] = {"引导车", "牵引车", "旅客摆渡车", "机组摆渡车",
            "客梯车", "升降平台车", "除冰车", "气源车", "电源车", "污水车", "垃圾车", "除冰液"
            , "清水车", "皮带传送车", "扫雪车", "残疾人专用车", "加温车", "叉车", "空调车", "充氮气"
            , "VIP用车", "飞机勤务", "一般服务", "例行检查", "非例行检查", "飞机放行", "航前清洁", "航后清洁", "快速清洁"};
    private boolean isChecked;
    private String item_name;
    public Map<Integer, FlightData.AccaServiceDateBean> map;
    private String signstate;

    public PrintAdapter2(Context context, List<FlightData.AccaServiceDateBean> accaServiceBean) {
        this.context = context;
        this.accaServiceBean = accaServiceBean;

        map = new HashMap<>();
    }


    @Override
    public int getCount() {
        return accaServiceBean.size();
    }

    @Override
    public Object getItem(int position) {
        return accaServiceBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.print_gridview_item2, null);
            viewHolder.iv_print_icon = (ImageView) convertView.findViewById(R.id.iv_print_icon);
            viewHolder.btn_print_bill = (CheckBox) convertView.findViewById(R.id.btn_print_bill);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //是否签名
        signstate = accaServiceBean.get(position).get_signstate();
        if (accaServiceBean != null) {
            //已签名
            if ("Y".equals(signstate)) {
                //隐藏已签名的项目
                viewHolder.btn_print_bill.setVisibility(View.GONE);
            } else {
                viewHolder.btn_print_bill.setBackgroundResource(R.drawable.yellowbg);
                isChecked = true;
            }

        }
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.btn_print_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalViewHolder.btn_print_bill.isChecked()) {
                    finalViewHolder.iv_print_icon.setVisibility(View.VISIBLE);
                    map.put(position, accaServiceBean.get(position));
                } else {
                    finalViewHolder.iv_print_icon.setVisibility(View.GONE);
                    map.remove(position);
                }

                EventBus.getDefault().post(map);
            }
        });
        viewHolder.btn_print_bill.setText(accaServiceBean.get(position).get_item_name());

        return convertView;
    }


    class ViewHolder {
        ImageView iv_print_icon;
        CheckBox btn_print_bill;
    }
}
