package com.example.hp_user.goalbehavioranalysis;

/**
 * Created by hp-user on 2017/11/29.
 */

public class WorkSpot {
    private String gongweihao;
    private String xzuobiao;
    private String yzuobiao;
    private String lenght;
    private String width;
    private String shexiang;
    public WorkSpot(String gongweihao,String xzuobiao, String yzuobiao,String lenght,String width,String shexiang)
    {
        this.gongweihao=gongweihao;
        this.xzuobiao=xzuobiao;
        this.yzuobiao=yzuobiao;
        this.lenght=lenght;
        this.width=width;
        this.shexiang=shexiang;
    }
    public String getGongweihao(){
        return gongweihao;
    }
    public String getXzuobiao(){
        return xzuobiao;
    }
    public String getYzuobiao(){
        return yzuobiao;
    }
    public String getLenght() {
        return lenght;
    }
    public String getWidth(){
        return width;
    }
    public String getShexiang(){
        return shexiang;
    }
}
