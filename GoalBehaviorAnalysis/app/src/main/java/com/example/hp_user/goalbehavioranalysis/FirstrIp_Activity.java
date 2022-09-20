package com.example.hp_user.goalbehavioranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstrIp_Activity extends AppCompatActivity {

    public EditText textip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstr_ip_layout);
        SysApplication.getInstance().addActivity(this);

        textip=(EditText)findViewById(R.id.edit_ip);

        Button button=(Button)findViewById(R.id.ok_ip);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyApplication myApp = (MyApplication) getApplication();
                myApp.ip=textip.getText().toString();
                Intent i = new Intent(FirstrIp_Activity.this,Login_Activity.class);
                startActivity(i);
            }
        });

    }
}
