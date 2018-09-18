package com.km.rmbank.utils.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.km.rmbank.dto.JPushDto;
import com.km.rmbank.event.RefreshMessageEvent;
import com.km.rmbank.module.main.appoint.ActionOutdoorActivity;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.module.main.appoint.ActionRecentInfoActivity;
import com.km.rmbank.module.main.appoint.AppointAfternoonTeaActivity;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.module.main.personal.account.UserAccountDetailsActivity;
import com.km.rmbank.module.main.personal.book.BookVenueManageActivity;
import com.km.rmbank.utils.EventBusUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by kamangkeji on 17/6/26.
 */

public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        LogUtils.d("currentAction - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(action)){//JPush用户注册成功

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.d("用户点击打开了通知");

            openNotification(context,bundle);

        } else {
            LogUtils.d("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtils.d(" title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtils.d("message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.d("extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Gson gson = new Gson();
        JPushDto mJpushDto = gson.fromJson(extras,JPushDto.class);
        LogUtils.d(mJpushDto.toString());
        Intent intent;
        EventBusUtils.post(new RefreshMessageEvent());
        switch (mJpushDto.getType()){
            case 1://下午茶、晚宴
                intent = new Intent(context, AppointAfternoonTeaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("actionId",mJpushDto.getId());
                context.startActivity(intent);
                break;
            case 2://路演活动
                intent = new Intent(context, ActionRecentInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("actionId",mJpushDto.getId());
                context.startActivity(intent);
                break;
            case 3://账户明细
                intent = new Intent(context, UserAccountDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("actionPastId",mJpushDto.getId());
                context.startActivity(intent);
                break;

            case 4://户外基地
                intent = new Intent(context, ActionOutdoorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("activityId",mJpushDto.getId());
                intent.putExtra("scenicId",mJpushDto.getId());
                context.startActivity(intent);
                break;

            case 5://资讯
                intent = new Intent(context, ActionPastDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("actionPastId",mJpushDto.getId());
                context.startActivity(intent);
                break;
            case 6://场地审核
                intent = new Intent(context, BookVenueManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("actionPastId",mJpushDto.getId());
                context.startActivity(intent);
                break;
        }

    }
}
