#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <string>
#include <ctime>
#include "mysql.h"

using namespace cv;
using namespace std;

//标准时间准换成时间戳
int standard_to_stamp(char *str_time)
{
    struct tm stm;
    int iY, iM, iD, iH, iMin, iS;
    
    memset(&stm,0,sizeof(stm));
    iY = atoi(str_time);
    iM = atoi(str_time+5);
    iD = atoi(str_time+8);
    iH = atoi(str_time+11);
    iMin = atoi(str_time+14);
    iS = atoi(str_time+17);
    
    stm.tm_year=iY-1900;
    stm.tm_mon=iM-1;
    stm.tm_mday=iD;
    stm.tm_hour=iH;
    stm.tm_min=iMin;
    stm.tm_sec=iS;
    
    //标准时间格式例如：2016-08-02 12:12:30
    return (int)mktime(&stm);
}

//时间戳转换成标准时间
char* stamp_to_standard(int stampTime)
{
    time_t tick = (time_t)stampTime;
    struct tm tm;
    char s[100];
    
    //tick = time(NULL);
    tm = *localtime(&tick);
    strftime(s, sizeof(s), "%Y-%m-%d %H:%M:%S", &tm);
    //printf("%d: %s\n", (int)tick, s);
    return s;
}

int isOverlap(const Rect &rc1, const Rect &rc2)//判断两个矩形是否相交，0表示不相交，1表示相交且rc1大，2表示相交且rc2大
{
    if (rc1.x + rc1.width  > rc2.x &&
        rc2.x + rc2.width  > rc1.x &&
        rc1.y + rc1.height > rc2.y &&
        rc2.y + rc2.height > rc1.y &&
        rc1.width * rc1.height >= rc2.width * rc2.height
        )
        return 1;
    else if
        (rc1.x + rc1.width  > rc2.x &&
         rc2.x + rc2.width  > rc1.x &&
         rc1.y + rc1.height > rc2.y &&
         rc2.y + rc2.height > rc1.y &&
         rc1.width * rc1.height < rc2.width * rc2.height
         )
        return 2;
    else return 0;
}

int main()
{
    //文件名
    string file_name="1.mp4";
    //路径名
    string path_name="/Users/zhenghang/Documents/课程/计算机课程/软件工程课程设计/MyOpenCV/MyOpenCV/";
    //连接文件名和路径名
    string pathAndFile_name=path_name+file_name;
    
    //第一个开始时间
    char timeStamp_start[100] = "2019-03-03 10:20:00";
    int t_start = standard_to_stamp(timeStamp_start);
    //第一个结束时间
    int t_stop=t_start+160;
    char timeStamp_stop[100];
    strcpy(timeStamp_stop,stamp_to_standard(t_stop));
    
    //工位号
    int station_number[8]={0};
    //工位
    vector<Rect> gongwei;
    
    //连接数据库添加工位横坐标、纵坐标、宽度、高度
    MYSQL *connection, mysql;
    mysql_init(&mysql);
    connection = mysql_real_connect(&mysql,"localhost","root","zh43907019","behavior_analysis",0,0,0);
    
    if (connection == NULL)
    {
        printf("连接失败:%s\n", mysql_error(&mysql));
    }
    else
    {
        //printf("连接成功, 服务器版本: %s, 客户端版本: %s.\n", MYSQL_SERVER_VERSION, mysql_get_client_info());
        
        MYSQL_RES *result;
        MYSQL_ROW sql_row;
        
        int res;
        
        mysql_query(&mysql, "SET NAMES GBK"); //设置编码格式,否则在cmd下无法显示中文
        
        char sql[200];
        sprintf(sql, "select * from station where video_number = (select video_number from video where file_name='%s')",file_name.c_str());
        
        res=mysql_query(&mysql, sql);
        if(!res)
        {
            result=mysql_store_result(&mysql);//保存查询到的数据到result
            if(result)
            {
                int i=0;
                
                while((sql_row=mysql_fetch_row(result)))//获取具体的数据
                {
                    gongwei.push_back(Rect(atoi(sql_row[3]), atoi(sql_row[4]), atoi(sql_row[5]), atoi(sql_row[6])));
                    station_number[i++]=atoi(sql_row[1]);
                }
            }
        }
        else
        {
            cout<<"query sql failed!"<<endl;
        }
    }
    
    //读取视频
    VideoCapture video(pathAndFile_name);
    
    //判断视频是否打开
    if (!video.isOpened())
        return 0;
    
    //视频帧
    Mat image;
    
    //帧计数
    int big_number=1;
    int number=0;
    //状态计数
    int status_bad_number[10]={0};
    int status_good_number[10]={0};
    
    cvNamedWindow("window", 1);
    
    //视频继续
    for (;;)
    {
        //读取视频
        video >> image;
        
        //判断是否有当前帧
        if (!image.data)
            break;
        if (waitKey(33) == 'q')
            break;
        
        number++;
        
        Mat hsvImg;
        Mat imgThresholded;
        Mat img_1;
        
        cvtColor(image, hsvImg, COLOR_BGR2HSV);
        
        //ps里的hsv值转化为opencv里的hsv值方式：(h/2，h*s%，h*v%)
        //ps里的hsv值：粉：h:270-310;s:9-20;v:50-90
        //ps里的hsv值：蓝：h:200-230;s:40-70;v:40-90
        
        const Scalar hsvRedLo( 135,24,135);
        const Scalar hsvRedHi(155,62,279);
        
        const Scalar hsvBlueLo( 100,80,80);
        const Scalar hsvBlueHi(115,161,207);
        
        vector<Scalar> hsvLo{hsvRedLo, hsvBlueLo};
        vector<Scalar> hsvHi{hsvRedHi, hsvBlueHi};
        
        vector<Rect> foundRectLast;
        
        for(int i=0;i<2;i++)
        {
            // 查找指定范围内的颜色
            inRange(hsvImg, hsvLo[i], hsvHi[i], imgThresholded);
            // 转换成二值图
            threshold(imgThresholded, imgThresholded, 1, 255, THRESH_BINARY);
            
            copyMakeBorder(imgThresholded, img_1, 1, 1, 1, 1, BORDER_CONSTANT, 0);
            
            vector<vector<Point> > contours0;
            vector<Vec4i> hierarchy;
            findContours(img_1, contours0, hierarchy, CV_RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
            
            vector<Rect> found, foundRect;
            
            for (int idx = 0; idx < contours0.size(); idx++ )
            {
                Rect bound = boundingRect(Mat(contours0[idx]));
                Point bc = Point(bound.x + bound.width / 2,
                                 bound.y + bound.height / 2);
                uchar x = imgThresholded.at<uchar>(bc);
                if (x > 0&&bound.width>30&&bound.height>30)
                {
                    Rect box2 = Rect(bc.x-bound.width / 2, bc.y-bound.height / 2,bound.width, bound.height);
                    found.push_back(box2);
                }
            }
            
            //遍历found寻找相交的矩形
            for(int i = 0; i < found.size(); i++){
                Rect r = found[i];
                
                int j = 0;
                for(; j < found.size(); j++){
                    if(i!=j && isOverlap(r, found[j])==2)
                        break;
                }
                if(j == found.size()){
                    foundRect.push_back(r);
                    foundRectLast.push_back(r);
                }
            }
            
            if(i==0)
            {
                for(int j = 0; j < foundRect.size(); j++){
                    Rect r = foundRect[j];
                    rectangle(image, r.tl(), r.br(), Scalar(0, 0, 255), 2);
                }
            }
            else
            {
                for(int j = 0; j < foundRect.size(); j++){
                    Rect r = foundRect[j];
                    rectangle(image, r.tl(), r.br(), Scalar(255, 0, 0), 2);
                }
            }
        }
        
        
        for(int j = 0; j < gongwei.size(); j++){
            Rect r = gongwei[j];
            rectangle(image, r.tl(), r.br(), Scalar(0, 255, 0), 2);
        }
        
        //遍历foundRectLast寻找工位矩形里是否有员工矩形
        for(int j = 0; j < gongwei.size(); j++){
            if(foundRectLast.size()==0)
                status_bad_number[j]++;
            for(int i = 0; i < foundRectLast.size(); i++){
                if(isOverlap(foundRectLast[i], gongwei[j])!=0)
                {status_good_number[j]++;break;}
                if(i==foundRectLast.size()-1)
                    status_bad_number[j]++;
            }
        }
        
        imshow("window", image);
    
        //cout<<number<<endl;
        
        if(number>=4000)
        {
            //cout<<"第"<<big_number<<"个4000帧："<<endl;
            //状态值
            char status[10];
            for(int j = 0; j < gongwei.size(); j++)
            {
                if((double)status_good_number[j]/4000.0>=0.7)
                     strcpy(status,"good");
                else
                    strcpy(status,"bad");
                
                //cout<<"工位"<<station_number[j]<<":"<<status<<endl;
                
                char sql[200];
                 sprintf(sql, "insert into behavior(start_time,stop_time,station_number,feature) values('%s','%s','%d','%s')", timeStamp_start, timeStamp_stop,station_number[j], status);
                 if (mysql_query(&mysql, sql))
                 {
                 cout << "query sql failed!" << endl;
                 }
            }
            //下一个开始时间
            t_start = t_start+160;
            strcpy(timeStamp_start,stamp_to_standard(t_start));
            //下一个结束时间
            t_stop = t_stop+160;
            strcpy(timeStamp_stop,stamp_to_standard(t_stop));
            big_number++;
            number=0;
            for(int j = 0; j < gongwei.size(); j++){
                status_bad_number[j]=0;
                status_good_number[j]=0;
            }
        }
    }
    mysql_close(&mysql);
}