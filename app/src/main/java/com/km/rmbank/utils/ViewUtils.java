package com.km.rmbank.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.oldrecycler.AppUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jarlen.photoedit.operate.OperateView;

/**
 * Created by PengSong on 18/1/31.
 */

public class ViewUtils {
    /**
     * 保存View为图片的方法
     */
    public static Bitmap saveBitmap(View v, String name) {


        String fileName = name + ".png";

        Bitmap bm;
        if (v instanceof OperateView){
            OperateView operateView = (OperateView) v;
            bm = Bitmap.createBitmap(operateView.getmWidth(),operateView.getmHeight(),Bitmap.Config.ARGB_8888);
        } else {
            bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                    Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "TIKTOK";
        LogUtils.e(TAG, "保存图片");
        File f = new File(AppUtils.getImagePath(fileName));
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            LogUtils.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bm;
    }

    /**
     * 为View设置 margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
