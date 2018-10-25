package com.km.rmbank.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.km.rmbank.R;
import com.km.rmbank.module.main.HomeActivity;

/**
 * Created by PengSong on 18/10/19.
 */

public class NotificationUtils {

    private String channel_id = "1";
    private String channel_name = "channel_name";
    private NotificationManager manager;

    private static NotificationUtils instance;


    public static NotificationUtils getInstance(){
        if (instance == null){
            instance = new NotificationUtils();
        }

        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setChannel(Context context){
        NotificationChannel channel = new NotificationChannel(channel_id,channel_name, NotificationManager.IMPORTANCE_DEFAULT);
        getManager(context).createNotificationChannel(channel);
    }

    public NotificationManager getManager(Context context){
        if (manager == null){
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    /**
     * 打开一个正在运行中的通知
     * @param context
     */
    public void openGoingNotification(Context context){

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent toHome = PendingIntent.getActivity(context,0,intent,0);
        Notification notification;
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setChannel(context);
            builder = new Notification.Builder(context,channel_id);
        } else {
            builder = new Notification.Builder(context);
        }

        builder.setContentTitle("玩转地球正在运行")
                .setContentText("点击查看精彩内容或关闭程序")
                .setSmallIcon(R.mipmap._destop_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap._destop_icon))
                .setContentIntent(toHome)
                .setOngoing(true);
        notification = builder.build();
        getManager(context).notify(1,notification);
    }
}
