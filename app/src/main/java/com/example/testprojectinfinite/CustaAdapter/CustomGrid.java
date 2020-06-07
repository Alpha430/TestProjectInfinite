package com.example.testprojectinfinite.CustaAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testprojectinfinite.R;

import java.util.ArrayList;
import java.util.List;

public class CustomGrid extends BaseAdapter {
    private Activity mContext;
    private int selectedPosition=-1;
    private int selectedColor = Color.parseColor("#00BCD4");



    public CustomGrid(Activity mContext, List<Integer> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }


    private class ViewHolder
    {
        TextView txtName;
        LinearLayout bg;


    }



    List<Integer> dataList;

    public void setSelectedPosition(int position)
    {
        selectedPosition=position;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        try {
            ViewHolder holder;
            LayoutInflater inflater = mContext.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_single, null);
                holder = new ViewHolder();

                holder.txtName = (TextView) convertView.findViewById(R.id.grid_text);



                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(selectedPosition!= -1 && position == selectedPosition)
            {
                holder.txtName .setBackgroundColor(selectedColor);
            }
            /*else
            {
                final int version = Build.VERSION.SDK_INT;
                if (version >= 21) {
                    holder.bg .setBackground(ContextCompat.getDrawable(mContext, R.drawable.bordertext));
                } else {
                    holder.bg .setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bordertext));
                }
                //convertView.setBackgroundColor(deafultcolor);
            }
*/





            holder.txtName.setText(dataList.get(position).toString());




        } catch (Exception e) {
            e.getMessage();
        }


        return convertView;
    }
}