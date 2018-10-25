package com.km.rmbank.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;

/**
 * Created by PengSong on 18/10/22.
 */

public class ObtainCodeButton extends AppCompatTextView {

    private int timeSeconds = 60;//获取验证码的间隔时间  单位秒
    private int curTimes = timeSeconds;

    private boolean status = false;//是否获取验证码的状态

    private String sendCodeHint = "发送验证码";
    private String sendCodeAginHint = "重新发送";
    private int obtainNums = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int times = msg.what;
            curTimes = timeSeconds - times;
            status = curTimes > 0;
            if (curTimes == 0){
                status = false;
            }
            setEnabled(!status);
            updateText();
        }
    };

    public ObtainCodeButton(Context context) {
        this(context,null);
    }

    public ObtainCodeButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        sendCodeHint = (String) getText();
        initView();
    }

    private void initView(){
        setBackgroundResource(R.drawable.selector_obtain_phone_code);
        setEnabled(!status);
        setGravity(Gravity.CENTER);
        updateText();
    }

    private void updateText(){
        if (!status){
            setText(obtainNums == 0 ? sendCodeHint : sendCodeAginHint);
            setTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        } else {
            setText("" + curTimes);
            setTextColor(ContextCompat.getColor(getContext(),R.color.text_color_block8));
        }
        postInvalidate();
    }

    public void startObtainPhoneCode(){
        new Thread(){
            @Override
            public void run() {
                status = true;
                obtainNums++;
                for (int i = 1; i <= timeSeconds;i++){
                    try {
                        mHandler.sendEmptyMessage(i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

}
