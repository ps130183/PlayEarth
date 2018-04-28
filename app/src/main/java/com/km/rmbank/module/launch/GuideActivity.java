package com.km.rmbank.module.launch;

import android.graphics.Bitmap;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;
import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SinglePage;
import com.km.rmbank.R;
import com.km.rmbank.utils.SystemBarHelper;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AbsGuideActivity {

    @Override
    public List<SinglePage> buildGuideContent() {
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

//        SinglePage page01 = new SinglePage();
//        page01.mBackground = getResources().getDrawable(R.mipmap.guide1);
//        guideContent.add(page01);
//
//        SinglePage page02 = new SinglePage();
//        page02.mBackground = getResources().getDrawable(R.mipmap.guide2);
//        guideContent.add(page02);
//
//        SinglePage page03 = new SinglePage();
//        page03.mBackground = getResources().getDrawable(R.mipmap.guide3);
//        guideContent.add(page03);
//
//        SinglePage page04 = new SinglePage();
//        page04.mBackground = getResources().getDrawable(R.mipmap.guide4);
//
//        guideContent.add(page04);
        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = new GuideFragment();
        guideContent.add(page05);

        return guideContent;
    }

    @Override
    public boolean drawDot() {
        return false;
    }

    @Override
    public Bitmap dotDefault() {
        return null;
    }

    @Override
    public Bitmap dotSelected() {
        return null;
    }

    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }
}
