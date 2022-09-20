package com.example.hp_user.goalbehavioranalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp-user on 2017/11/29.
 */

public class WorkSpotAdapter extends ArrayAdapter<WorkSpot> {
    private int resourceId;
    public WorkSpotAdapter(Context context, int textViewResourceId, List<WorkSpot>objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        WorkSpot workSpot=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView gongweihao=(TextView)view.findViewById(R.id.gongweihao);
        TextView xzoubiao=(TextView)view.findViewById(R.id.xzuobiao);
        TextView yzuobiao=(TextView)view.findViewById(R.id.yzuobiao);
        TextView length=(TextView)view.findViewById(R.id.length);
        TextView width=(TextView)view.findViewById(R.id.width);
        TextView shexiang=(TextView)view.findViewById(R.id.shexiang);

        gongweihao.setText(workSpot.getGongweihao());
        xzoubiao.setText(workSpot.getXzuobiao());
        yzuobiao.setText(workSpot.getYzuobiao());
        length.setText(workSpot.getLenght());
        width.setText(workSpot.getWidth());
        shexiang.setText(workSpot.getShexiang());

        return view;
    }
}
