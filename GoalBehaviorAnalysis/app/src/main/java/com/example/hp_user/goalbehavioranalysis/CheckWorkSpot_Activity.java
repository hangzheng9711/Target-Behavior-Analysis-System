package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckWorkSpot_Activity extends AppCompatActivity {
    public JSONObject object;
    private List<WorkSpot> workSpotList = new ArrayList<>();
    String[] shuzu = new String[100];
    int length_len=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkworkspot_layout);
        SysApplication.getInstance().addActivity(this);

        //获取工位信息
        final Intent intent =getIntent();
        String data=intent.getStringExtra("extra_data");
         //if(data!=null)
        // {
          //   Toast.makeText(CheckWorkSpot_Activity.this,1+"",Toast.LENGTH_SHORT).show();
         //}

        jsonJX(data);

      //  Toast.makeText(CheckWorkSpot_Activity.this,1+"",Toast.LENGTH_SHORT).show();

        WorkSpotAdapter adapter =new WorkSpotAdapter(CheckWorkSpot_Activity.this,R.layout.listview_item,workSpotList);
        ListView listView=(ListView)findViewById(R.id.list_workspot);
        listView.setAdapter(adapter);



        //为返回主界面按钮设置监听

        Button return_home_4_button=(Button)findViewById(R.id.return_home_4);
        return_home_4_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent return_home_4_intent = new Intent(CheckWorkSpot_Activity.this,Home_Activity.class);
                startActivity(return_home_4_intent);
            }
        });
        //为增加工位信息按钮设置监听
        Button add_button=(Button)findViewById(R.id.add);
        add_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent add_intent = new Intent(CheckWorkSpot_Activity.this,AddWorkSpot.class);
                add_intent.putExtra("extra_data", shuzu[length_len-1]);
                startActivity(add_intent);
            }
        });
    }

    private void jsonJX(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            length_len=jsonArray.length();
            for(int i=0;i<jsonArray.length();i++){
                object=jsonArray.getJSONObject(i);
                try {
                    String gongweihao = object.getString("station_number");
                    String xzuobiao = object.getString("abscissa");
                    String yzuobiao =object.getString("ordinate");
                    String length= object.getString("length");
                    String width= object.getString("width");
                    String shexiang=object.getString("video_number");
                   // Toast.makeText(CheckWorkSpot_Activity.this,xzuobiao+"",Toast.LENGTH_SHORT).show();
                    WorkSpot workSpot = new WorkSpot(gongweihao,xzuobiao,yzuobiao,length,width,shexiang);
                    //ArrayList集合
                    shuzu[i]=gongweihao;
                    workSpotList.add(workSpot);
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
