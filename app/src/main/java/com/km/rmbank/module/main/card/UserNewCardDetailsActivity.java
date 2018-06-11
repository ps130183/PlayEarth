package com.km.rmbank.module.main.card;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.PartTimeJob;
import com.km.rmbank.utils.Constant;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.delegate.ItemDelegate;

import java.util.ArrayList;
import java.util.List;

public class UserNewCardDetailsActivity extends BaseActivity {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_new_card_details;
    }

    @Override
    public String getTitleContent() {
        return "名片";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        loadUserInfo();
    }

    private void loadUserInfo(){
        int windowWidth = ScreenUtils.getScreenWidth();

        UserInfoDto userInfoDto = getIntent().getParcelableExtra("userCard");
        if (userInfoDto == null && Constant.userInfo != null){
            userInfoDto = Constant.userInfo;
        }
        GlideImageView userPortrait = mViewManager.findView(R.id.userPortrait);
        GlideUtils.loadImageOnPregress(userPortrait,userInfoDto.getPortraitUrl(),null);
        userPortrait.getLayoutParams().height = windowWidth;

        mViewManager.setText(R.id.userName,userInfoDto.getName());
//        mViewManager.setText(R.id.userCompany,);
        MRecyclerView partTimeJob = mViewManager.findView(R.id.partTimeJob);
        partTimeJob.addContentLayout(R.layout.item_user_card_part_time_job, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder bViewHolder, Object o, int i) {

            }
        }).create();
        List<Object> partList = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            partList.add(new PartTimeJob());
        }
        partTimeJob.update(partList);
    }
}
