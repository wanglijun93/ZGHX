package com.travelsky.airportapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.activity.ModifySecurityDetailsActivity;
import com.travelsky.airportapp.domain.AirInfo;

import java.util.List;

/**
 * @author wlj
 *         添加已完成事项的数据
 *         特车页面数据适配器
 */
public class SpecialCarAdapter extends BaseAdapter {

    private Context context;
    //判断是否被选中
    public static boolean isChecked = true;

    private List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen;
    public static String name = "";
    private List<String> mTitleList;
    private TabLayout mTabLayout;
    private static final String[] names = {"引导车", "牵引车", "旅客摆渡车", "机组摆渡车",
            "客梯车", "升降平台车", "除冰车", "气源车", "电源车", "污水车", "垃圾车", "除冰液"
            , "清水车", "皮带传送车", "扫雪车", "残疾人专用车", "加温车", "叉车", "空调车", "充氮气"
            , "VIP用车"
    };

    public SpecialCarAdapter(Context context,List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen,TabLayout mTabLayout,List<String> mTitleList) {
        this.context = context;
        this.accaServiceBeen = accaServiceBeen;
        this.mTabLayout = mTabLayout;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
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
            convertView = View.inflate(context, R.layout.matter_gridview_item, null);

            holder.btn_matter = (Button) convertView.findViewById(R.id.btn_matter);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (accaServiceBeen != null) {
            for (AirInfo.DataBean.AccaServiceBean bean : accaServiceBeen) {
                if (bean.getItem_name().equals(names[position])) {
                    holder.btn_matter.setBackgroundResource(R.drawable.button_shape);
                }
            }
        }


        holder.btn_matter.setText(names[position]);
        holder.btn_matter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accaServiceBeen != null) {

                    for (AirInfo.DataBean.AccaServiceBean bean : accaServiceBeen) {
                        if (bean.getItem_name().equals(names[position])) {
                            isChecked = false;
                            break;
                        } else {
                            isChecked = true;
                        }
                    }
                }

                if (isChecked) {

                    String service_type = "";
                    if (0 == mTabLayout.getSelectedTabPosition()) {
                        service_type = mTitleList.get(0);
                    } else if (1 == mTabLayout.getSelectedTabPosition()) {
                        service_type = mTitleList.get(1);
                    }else {
                        service_type = mTitleList.get(2);
                    }
                    name = names[position];
                    Intent intent = new Intent(context, ModifySecurityDetailsActivity.class);
                    intent.putExtra("name", names[position]);
                    intent.putExtra("Service_Seq", "");
                    intent.putExtra("start_time", "");
                    intent.putExtra("end_time", "");
                    intent.putExtra("service_type", service_type);
                    context.startActivity(intent);

                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        Button btn_matter;
    }
}
