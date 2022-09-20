package com.example.hp_user.goalbehavioranalysis;

/**
 * Created by hp-user on 2017/11/29.
 */

public class Behavior {

    private String gongweihao;
    private String start_time;
    private String stop_time;
    private String feature;


    public Behavior(String gongweihao,String start_time, String stop_time,String feature)
    {
        this.gongweihao=gongweihao;
        this.start_time=start_time;
        this.stop_time=stop_time;
        this.feature=feature;

    }
    public String getGongweihao(){
        return gongweihao;
    }
    public String getStart_time(){
        return start_time;
    }
    public String getStop_time(){
        return stop_time;
    }
    public String getFeature() {
        return feature;
    }

}
