package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class CheckBehavior_Activity extends AppCompatActivity  {

    public JSONObject object;
    private List<Behavior> behaviorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkbehavior_layout);
        SysApplication.getInstance().addActivity(this);

        EditText year1=(EditText)findViewById(R.id.year4);

        //获取工位信息
        Intent intent =getIntent();
        String data=intent.getStringExtra("extra_data");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        String year= sdf.format(new java.util.Date());

        year1.setText(year);

        jsonJX(data);
        BehaviorAdapter adapter =new BehaviorAdapter(CheckBehavior_Activity.this,R.layout.behavior_list_item,behaviorList);
        ListView listView=(ListView)findViewById(R.id.behavior_list);
        listView.setAdapter(adapter);
//为返回主界面按钮设置监听

        Button return_home_4_button=(Button)findViewById(R.id.return_home_2);
        return_home_4_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_4_intent = new Intent(CheckBehavior_Activity.this,Home_Activity.class);
                startActivity(return_home_4_intent);
            }
        });
    }

    private void jsonJX(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                object=jsonArray.getJSONObject(i);
                try {
                    String gongweihao = object.getString("station_number");
                    String start_time = object.getString("start_time");
                    String stop_time =object.getString("stop_time");
                    String feature= object.getString("feature");

                    // Toast.makeText(CheckWorkSpot_Activity.this,xzuobiao+"",Toast.LENGTH_SHORT).show();
                    Behavior behavior = new Behavior(gongweihao,start_time,stop_time,feature);
                    //ArrayList集合
                    behaviorList.add(behavior);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

