package com.km.rmbank.module.main.card;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.DemandEntity;
import com.km.rmbank.entity.OtherIdentityEntity;
import com.km.rmbank.entity.PartTimeJob;
import com.km.rmbank.entity.SupplyAndDemandEntity;
import com.km.rmbank.entity.SupplyEntity;
import com.km.rmbank.module.main.card.auto.MyAutobiographyActivity;
import com.km.rmbank.module.main.personal.profession.ProfessionFinishedActivity;
import com.km.rmbank.module.main.personal.profession.ProfessionIntroduceActivity;
import com.km.rmbank.module.realname.CertifyCheckActivity;
import com.km.rmbank.module.realname.CertifyIdCardSuccessActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.StringUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ruffian.library.RTextView;

import java.util.ArrayList;
import java.util.List;

public class UserNewCardDetailsActivity extends BaseActivity {

    private MRecyclerView<PartTimeJob> partTimeJob;
    private MRecyclerView<SupplyAndDemandEntity> supplyRecycler;
    private MRecyclerView<SupplyAndDemandEntity> demandRecycler;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_new_card_details;
    }

    @Override
    public String getTitleContent() {
        return "名片";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        UserInfoDto userInfoDto = getIntent().getParcelableExtra("userCard");
        if (userInfoDto == null){
            simpleTitleBar.setRightMenuContent("编辑");
            simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("fromPage",1);
                    startActivity(UserCardModifyActivity.class,bundle);
                }
            });
        }
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initView();
        initSupplyAndDemand();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void initView(){
        partTimeJob = mViewManager.findView(R.id.partTimeJob);
        partTimeJob.addContentLayout(R.layout.item_user_card_part_time_job, new ItemViewConvert<PartTimeJob>() {
            @Override
            public void convert(@NonNull BViewHolder holder, PartTimeJob mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.userCompany,mData.getCompany());
                holder.setText(R.id.userPosition,mData.getPosition());
            }
        }).create();
    }

    private void loadUserInfo(){
        int windowWidth = ScreenUtils.getScreenWidth();

        boolean isMe = false;
        UserInfoDto userInfoDto = getIntent().getParcelableExtra("userCard");
        if (userInfoDto == null && Constant.userInfo != null){
            userInfoDto = Constant.userInfo;
            isMe = true;
        }
        GlideImageView userPortrait = mViewManager.findView(R.id.userPortrait);
        GlideUtils.loadImageOnPregress(userPortrait,userInfoDto.getPortraitUrl(),null);
        userPortrait.getLayoutParams().height = windowWidth;

        //用户基本信息
        mViewManager.setText(R.id.userName,userInfoDto.getName());
        mViewManager.setText(R.id.userCompany,userInfoDto.getCompany());
        mViewManager.setText(R.id.userPosition,userInfoDto.getPosition());

        //是否实名
        final int realNameStatus = userInfoDto.getStatus();
        LinearLayout llRealName = mViewManager.findView(R.id.ll_real_name);
        ImageView ivRealName = mViewManager.findView(R.id.iv_real_name);
        TextView tvRealName = mViewManager.findView(R.id.tv_real_name);
        if (TextUtils.isEmpty(userInfoDto.getName())){
            llRealName.setVisibility(View.GONE);
        } else {
            llRealName.setVisibility(View.VISIBLE);
            if (realNameStatus == 2){//已实名
                llRealName.setBackgroundResource(R.drawable.shape_real_name_1);
                ivRealName.setImageResource(R.mipmap.icon_real_name_1);
                tvRealName.setText("已实名");
                tvRealName.setTextColor(getResources().getColor(R.color.text_color_blue2));
            } else {//未实名
                if (!isMe){
                    llRealName.setVisibility(View.GONE);
                } else {
                    llRealName.setBackgroundResource(R.drawable.shape_real_name_2);
                    ivRealName.setImageResource(R.mipmap.icon_real_name_2);
                    tvRealName.setText("未实名");
                    tvRealName.setTextColor(getResources().getColor(R.color.text_color_red));
                }
            }
        }


        //职位认证
        TextView userPosition = mViewManager.findView(R.id.userPosition);
        final int position = userInfoDto.getPositionStatus();
        int positionRes = 0;
        if (position == 0){
            positionRes = R.drawable.icon_position_2;
        } else if (position == 1){
            positionRes = R.drawable.icon_position_3;
        } else {
            positionRes = R.drawable.icon_position_1;
        }

        if (position != 2 && isMe){//未认证
            addImage(userPosition,userInfoDto.getPosition(),positionRes);
        } else if (position == 2){
            addImage(userPosition,userInfoDto.getPosition(),positionRes);
        }

//        if (isMe){
//           userPosition.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   if (position == 1){//认证中
//                       Bundle bundle = new Bundle();
//                       bundle.putBoolean("isUserInfo",true);
//                       startActivity(ProfessionFinishedActivity.class,bundle);
//                   } else {//未认证  或 已完成
//                       startActivity(ProfessionIntroduceActivity.class);
//                   }
//
//               }
//           });
//
//           llRealName.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   if (realNameStatus == 2){//已实名
//                    startActivity(CertifyIdCardSuccessActivity.class);
//                   } else if (realNameStatus == 1){//审核中
//                        startActivity(CertifyCheckActivity.class);
//                   } else {//未实名
//                       startActivity(CertifyRulesActivity.class);
//                   }
//               }
//           });
//        }


        //兼任
        RTextView rtPartTime = mViewManager.findView(R.id.rtPartTimeJob);
        View line = mViewManager.findView(R.id.linePartTimeJob);
        if (userInfoDto.getIdentityList().size() == 0){
            partTimeJob.setVisibility(View.GONE);
            rtPartTime.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            partTimeJob.setVisibility(View.VISIBLE);
            rtPartTime.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            List<PartTimeJob> partList = new ArrayList<>();
            for (int i = 0; i < userInfoDto.getIdentityList().size(); i++){
                partList.add(new PartTimeJob(userInfoDto.getIdentityList().get(i).getCompany(),
                        userInfoDto.getIdentityList().get(i).getPosition()));
            }
            partTimeJob.clear();
            partTimeJob.loadDataOfNextPage(partList);
        }


        //名片信息
        if (TextUtils.isEmpty(userInfoDto.getCardPhone())){
            mViewManager.setText(R.id.userPhone,"暂未编辑");
        } else {
            mViewManager.setText(R.id.userPhone,"0".equals(userInfoDto.getAllowStutas()) ? userInfoDto.getCardPhone() : StringUtils.hidePhone(userInfoDto.getCardPhone()));
        }

        mViewManager.setText(R.id.userEmail,TextUtils.isEmpty(userInfoDto.getEmailAddress()) ? "暂未编辑" : userInfoDto.getEmailAddress());
        mViewManager.setText(R.id.userAddress,TextUtils.isEmpty(userInfoDto.getDetailedAddress()) ? "暂未编辑" :userInfoDto.getDetailedAddress());
        mViewManager.setText(R.id.userIntroduce,TextUtils.isEmpty(userInfoDto.getPersonalizedSignature()) ? "暂未编辑" :userInfoDto.getPersonalizedSignature());
        mViewManager.setText(R.id.userIndustry,TextUtils.isEmpty(userInfoDto.getIndustryName()) ? "暂未编辑" :userInfoDto.getIndustryName());

        //供应和需求
        if (userInfoDto.getProvideList().isEmpty() && userInfoDto.getDemandList().isEmpty()){
            mViewManager.findView(R.id.supplyAndDemand).setVisibility(View.GONE);
        } else {
            mViewManager.findView(R.id.supplyAndDemand).setVisibility(View.VISIBLE);
            List<SupplyAndDemandEntity> supplyEntities = new ArrayList<>();
            for (int i = 0; i < userInfoDto.getProvideList().size(); i++){
                SupplyEntity entity = userInfoDto.getProvideList().get(i);
                supplyEntities.add(new SupplyAndDemandEntity((i + 1) + ". " + entity.getResources()));
            }

            List<SupplyAndDemandEntity> demandEntities = new ArrayList<>();
            for (int i = 0; i < userInfoDto.getDemandList().size(); i++){
                DemandEntity entity = userInfoDto.getDemandList().get(i);
                demandEntities.add(new SupplyAndDemandEntity((i + 1) + ". " + entity.getResources()));
            }

            supplyRecycler.clear();
            demandRecycler.clear();
            supplyRecycler.loadDataOfNextPage(supplyEntities);
            demandRecycler.loadDataOfNextPage(demandEntities);
        }



    }

    /**
     * 给textView 尾部添加图片
     * @param tvContent
     * @param content
     * @param imageRes
     */
    private void addImage(TextView tvContent, String content,int imageRes){
        //注意此处showText后+ " "主要是为了占位
        SpannableString ss = new SpannableString(content + " ");
        int len = ss.length();
        //图片
        Drawable d = ContextCompat.getDrawable(mInstance, (imageRes));
        d.setBounds(0, 0, ConvertUtils.dp2px(49), ConvertUtils.dp2px(14));
        //构建ImageSpan
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        ss.setSpan(span, len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvContent.setText(ss);
    }

    /**
     * 初始化供应 和 需求
     */
    private void initSupplyAndDemand(){
        supplyRecycler = mViewManager.findView(R.id.supplyRecycler);
        supplyRecycler.addContentLayout(R.layout.item_supply_demand, new ItemViewConvert<SupplyAndDemandEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, SupplyAndDemandEntity mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.content,mData.getContent());
            }
        }).create();

        demandRecycler = mViewManager.findView(R.id.demandRecycler);
        demandRecycler.addContentLayout(R.layout.item_supply_demand, new ItemViewConvert<SupplyAndDemandEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, SupplyAndDemandEntity mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.content,mData.getContent());
            }
        }).create();
    }

    /**
     * 打开我的自传
     * @param view
     */
    public void openMyAutobiography(View view) {
        startActivity(MyAutobiographyActivity.class);
    }
}
