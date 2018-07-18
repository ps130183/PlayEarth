package com.km.rmbank.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * Created by PengSong on 18/4/28.
 */

public class StringUtils {

    /**
     * 单独设置内部字体颜色
     * @param text
     * @param keyworld
     * @return
     */
    public static SpannableStringBuilder getSpannableTextColor(String text, String keyworld,int color){
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(text);
        if(text.contains(keyworld)){
            int spanStartIndex=text.indexOf(keyworld);
            int spacEndIndex=spanStartIndex+keyworld.length();
            spannableStringBuilder.setSpan(new ForegroundColorSpan(color),spanStartIndex,spacEndIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableStringBuilder;
    }

    /**
     * 隐藏手机号的中间四位
     * @param phone
     * @return
     */
    public static String hidePhone(String phone){
        String result = "";
        if (phone.length() == 11){
            result = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        }
        return result;
    }

}
