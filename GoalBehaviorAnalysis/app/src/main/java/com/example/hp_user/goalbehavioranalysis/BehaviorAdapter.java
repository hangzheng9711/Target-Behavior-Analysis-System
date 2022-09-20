package com.example.hp_user.goalbehavioranalysis;

/**
 * Created by hp-user on 2017/12/13.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BehaviorAdapter extends ArrayAdapter<Behavior>{
private int resourceId;
public BehaviorAdapter(Context context, int textViewResourceId, List<Behavior> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        }
@Override
public View getView(int position, View convertView, ViewGroup parent)
        {
        Behavior behavior=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView gongweihao=(TextView)view.findViewById(R.id.begongweihao);
        TextView start_time=(TextView)view.findViewById(R.id.start_time);
        TextView stop_time=(TextView)view.findViewById(R.id.end_time);
        TextView feature=(TextView)view.findViewById(R.id.kindofbehavior);

        gongweihao.setText(behavior.getGongweihao());
        start_time.setText(behavior.getStart_time());
        stop_time.setText(behavior.getStop_time());
        feature.setText(behavior.getFeature());
        return view;
        }
}
