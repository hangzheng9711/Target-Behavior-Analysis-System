package com.example.hp_user.goalbehavioranalysis;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckOutcome extends FragmentActivity implements OnClickListener {
    // 底部菜单4个Linearlayout
    private LinearLayout ll_allworkspotpie;
    private LinearLayout ll_gegongweizhu;
    private LinearLayout ll_monthzhexian;
    private LinearLayout ll_dayzhexian;


    // 底部菜单4个菜单标题
    private TextView tv_allworkspotpie;
    private TextView tv_gegongweizhu;
    private TextView tv_monthzhexian;
    private TextView tv_dayzhexian;



    // 3个Fragment
    private Fragment PieFragment;
    private Fragment ZhuFragment;
    private Fragment MonthFragment;
    private Fragment DayFragment;

    private Button button;
    private String data1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkanalysisoutcome_new_layout);
        SysApplication.getInstance().addActivity(this);

        //获取工位信息
        Intent intent =getIntent();
        String data=intent.getStringExtra("extra_data");
        data1=data;

        button=(Button)findViewById(R.id.top_return_home);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//查看工位信息按钮监听事件
                Intent i = new Intent(CheckOutcome.this, Home_Activity.class);
                startActivity(i);
            }
        });

        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();
        // 初始化并设置当前Fragment
        initFragment(0);

    }

    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (PieFragment == null) {
                    PieFragment = new PieFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("key",data1);
                    PieFragment.setArguments(bundle);
                    transaction.add(R.id.content, PieFragment);
                } else {

                    transaction.show(PieFragment);
                }
                break;
            case 1:
                if (ZhuFragment == null) {
                    ZhuFragment = new ZhuFragment();
                    transaction.add(R.id.content, ZhuFragment);
                } else {
                    transaction.show(ZhuFragment);
                }
                break;
            case 2:
                if (MonthFragment == null) {
                    MonthFragment = new MonthFragment();
                    transaction.add(R.id.content,MonthFragment);
                } else {
                    transaction.show(MonthFragment);
                }
                break;
            case 3:
                if (DayFragment == null) {
                    DayFragment = new DayFragment();
                    transaction.add(R.id.content,DayFragment);
                } else {
                    transaction.show(DayFragment);
                }

                break;
            default:
                break;
        }

        // 提交事务
        transaction.commit();

    }

    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (PieFragment != null) {
            transaction.hide(PieFragment);
        }
        if (ZhuFragment != null) {
            transaction.hide(ZhuFragment);
        }
        if (MonthFragment!= null) {
            transaction.hide(MonthFragment);
        }
        if (DayFragment!= null) {
            transaction.hide(DayFragment);
        }


    }

    private void initEvent() {
        // 设置按钮监听
        ll_allworkspotpie.setOnClickListener(this);
        ll_gegongweizhu.setOnClickListener(this);
        ll_monthzhexian.setOnClickListener(this);
        ll_dayzhexian.setOnClickListener(this);
    }

    private void initView() {

        // 底部菜单3个Linearlayout
        this.ll_allworkspotpie = (LinearLayout) findViewById(R.id.id_tab_suoyougongweixiaolv);
        this.ll_gegongweizhu = (LinearLayout) findViewById(R.id.id_tab_gegongweitongji);
        this.ll_monthzhexian = (LinearLayout) findViewById(R.id.id_tab_gegongweiniantongji);
        this.ll_dayzhexian = (LinearLayout) findViewById(R.id.id_tab_gegongweiyuetongji);


        // 底部菜单3个菜单标题
        this.tv_allworkspotpie = (TextView) findViewById(R.id.text_suoyougongwei);
        this.tv_gegongweizhu = (TextView) findViewById(R.id.text_gegongweitongji);
        this.tv_monthzhexian = (TextView) findViewById(R.id.text_gegongweiniantongji);
        this.tv_dayzhexian=(TextView) findViewById(R.id.text_gegongweiyuetongji);


    }


    @Override
    public void onClick(View v) {

        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.id_tab_suoyougongweixiaolv:
                tv_allworkspotpie.setTextColor(0xff1B940A);
                initFragment(0);
                break;
            case R.id.id_tab_gegongweitongji:
                tv_gegongweizhu.setTextColor(0xff1B940A);
                initFragment(1);
                break;
            case R.id.id_tab_gegongweiniantongji:
                tv_monthzhexian.setTextColor(0xff1B940A);
                initFragment(2);
                break;
            case R.id.id_tab_gegongweiyuetongji:
                tv_dayzhexian.setTextColor(0xff1B940A);
                initFragment(3);
                break;
           // case R.id.top_return_home:
             //   Intent i=new  Intent(CheckOutcome.this,Home_Activity.class);
             //   startActivity(i);
              //  break;
            default:
                break;
        }

    }

    private void restartBotton() {
        // TextView置为灰色
        tv_allworkspotpie.setTextColor(0x7b7b7b7b);
        tv_gegongweizhu.setTextColor(0x7b7b7b7b);
        tv_monthzhexian.setTextColor(0x7b7b7b7b);
        tv_dayzhexian.setTextColor(0x7b7b7b7b);

    }

}

