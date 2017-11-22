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
 *
 *         清洁也没数据适配器
 */
public class CleanAdapter extends BaseAdapter {

    private Context context;
    private List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen;
    //判断选项是否被选中
    private boolean isChecked = true;
    private String name;
    private TabLayout mTabLayout;
    private List<String> mTitleList;
    private static final String[] names = {"航前清洁", "航后清洁", "快速清洁"};

    public CleanAdapter(Context context,List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen,TabLayout mTabLayout,List<String> mTitleList) {
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
        final ViewHolder finalViewHolder = holder;
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
                    intent.putExtra("name", name);
                    intent.putExtra("Service_Seq", "");
                    intent.putExtra("start_time","");
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
