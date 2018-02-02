package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowBigImageActivity extends BaseActivity {

    private ViewPager mViewPager;
    private List<String> pictureUrls;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_show_big_image;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        pictureUrls = getIntent().getStringArrayListExtra("imagePaths");
        final int curPosition = getIntent().getIntExtra("curPosition",0);
        mViewPager = mViewManager.findView(R.id.viewpager);
        final TextView imageNumber = mViewManager.findView(R.id.imageNumber);

        imageNumber.setText((curPosition + 1) + " / " + pictureUrls.size());

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < pictureUrls.size(); i++){
            Bundle bundle = new Bundle();
            bundle.putString("imagePath",pictureUrls.get(i));
            fragmentList.add(ShowBigImageFragment.newInstance(bundle));
        }
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getSupportFragmentManager(),fragmentList,null);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(curPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageNumber.setText((position + 1) + " / " + pictureUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
