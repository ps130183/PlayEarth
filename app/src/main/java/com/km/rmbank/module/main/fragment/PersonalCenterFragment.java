package com.km.rmbank.module.main.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Builder;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.HighlightOptions;
import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.main.card.UserCardActivity;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.module.main.personal.MyAttentionActivity;
import com.km.rmbank.module.main.personal.account.UserAccountActivity;
import com.km.rmbank.module.main.personal.action.AppliedActionActivity;
import com.km.rmbank.module.main.personal.book.BookVenueManageActivity;
import com.km.rmbank.module.main.personal.contacts.MyTeamActivity;
import com.km.rmbank.module.main.personal.crowd.CrowdFundingActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.setting.AboutMeActivity;
import com.km.rmbank.module.main.personal.setting.ServiceActivity;
import com.km.rmbank.module.main.personal.setting.SettingActivity;
import com.km.rmbank.module.main.personal.task.PlayEarthTaskActivity;
import com.km.rmbank.module.main.personal.ticket.TicketListActivity;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.presenter.UserPresenter;
import com.km.rmbank.mvp.view.IUserView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.ViewUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class PersonalCenterFragment extends BaseFragment<IUserView, UserPresenter> implements IUserView {

    private GlideImageView userPortrait;
    private TextView userName;
    private TextView realName;
//    private TextView positionReal;
    private ImageView realNameStatus;
    private TextView userPosition;
    private TextView userCompany;

    private LinearLayout llQiuPiao;

    private MRecyclerView<ModelEntity> personalCenter;
    private String[] modelNames = {"众筹", "人脉", "电子券", "活动","预订", "关注",  "客服", "关于", "设置"};
    private int[] modelImages = {R.mipmap.icon_personal_center_f9, R.mipmap.icon_personal_center_f1,
            R.mipmap.icon_personal_center_f2, R.mipmap.icon_personal_center_f3,
            R.mipmap.icon_personal_center_f10,
            R.mipmap.icon_personal_center_f4,
            R.mipmap.icon_personal_center_f6, R.mipmap.icon_personal_center_f7,
            R.mipmap.icon_personal_center_f8};

    public PersonalCenterFragment() {
        // Required empty public constructor
    }

    public static PersonalCenterFragment newInstance(Bundle bundle) {
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(new UserModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        personalCenter = mViewManager.findView(R.id.personalCenter);

        personalCenter.addHeaderLayout(R.layout.header_personal_center_user_info, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                userPortrait = holder.findView(R.id.userPortrait);
                userName = holder.findView(R.id.userName);
//                positionReal = holder.findView(R.id.realPosition);
                userPosition = holder.findView(R.id.userPosition);
                userCompany = holder.findView(R.id.userCompany);

                userPortrait.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(UserCardActivity.class);
                    }
                });
            }

        }).addHeaderLayout(R.layout.header_personal_center_user_rights, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                realName = holder.findView(R.id.realName);
                realNameStatus = holder.findView(R.id.realNameStatus);
                llQiuPiao = holder.findView(R.id.click1);
                LinearLayout click2 = holder.findView(R.id.click2);
                LinearLayout click3 = holder.findView(R.id.click3);
                LinearLayout click4 = holder.findView(R.id.click4);

//                holder.setText(R.id.userBalance,"账户余额：" + Constant.userInfo.getBalance());

                //赚球票
                llQiuPiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(PlayEarthTaskActivity.class);
                    }
                });

                //VIP会员
                click2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(BecomeMemberActivity.class);
                    }
                });

                //查看次数
                click3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("即将开放！");
                    }
                });

                //我的账户
                click4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(UserAccountActivity.class);
                    }
                });
            }
        }).addContentLayout(R.layout.item_personal_center_function, new ItemViewConvert<ModelEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, ModelEntity mData, int position, @NonNull List<Object> payloads) {
                GlideImageView functionImage = holder.findView(R.id.functionImage);
                GlideUtils.loadImageByRes(functionImage, mData.getModelRes());
                holder.setText(R.id.functionName, mData.getModelName());

                RelativeLayout rlContent = holder.findView(R.id.rlContent);
                if (personalCenter.getAllDatas().get(0).getModelName().equals("众筹")) {
                    if (position == 0 || position == 3 || position == 6 || position == 8) {
                        ViewUtils.setMargins(rlContent, 0, 0, 0, ConvertUtils.dp2px(10));
                    } else {
                        ViewUtils.setMargins(rlContent, 0, 0, 0, 0);
                    }
                } else {
                    if (position == 2 || position == 5 || position == 7) {
                        ViewUtils.setMargins(rlContent, 0, 0, 0, ConvertUtils.dp2px(10));
                    } else {
                        ViewUtils.setMargins(rlContent, 0, 0, 0, 0);
                    }
                }
            }

        }).create();

        personalCenter.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                ModelEntity entity = (ModelEntity) mData;
                //众筹

                switch (entity.getModelName()) {
                    case "众筹":
                        startActivity(CrowdFundingActivity.class);
                        break;
                    case "人脉"://人脉
                        startActivity(MyTeamActivity.class);
                        break;
                    case "电子券"://电子券
                        startActivity(TicketListActivity.class);
                        break;
                    case "活动"://活动
                        startActivity(AppliedActionActivity.class);
                        break;
                    case "关注"://关注
                        startActivity(MyAttentionActivity.class);
                        break;
                    case "消息"://消息
                        startActivity(MessageActivity.class);
                        break;
                    case "预订"://预订
                        startActivity(BookVenueManageActivity.class);
                        break;
                    case "客服"://客服电话
                        startActivity(ServiceActivity.class);
                        break;
                    case "关于"://关于
                        startActivity(AboutMeActivity.class);
                        break;
                    case "设置"://设置
                        startActivity(SettingActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * 初始化 引导
     */
    private void showGuide(){

        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        int targetPosition = 0;
        if (personalCenter.getAllDatas().get(0).getModelName().equals("众筹")){
            targetPosition = 6;
        } else {
            targetPosition = 5;
        }

        final View itemView = personalCenter.getItemView(targetPosition);
        Builder guide = NewbieGuide.with(this)
                .setLabel("pc")
                .setShowCounts(1)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLightWithOptions(userPortrait, HighLight.Shape.CIRCLE,new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(UserCardActivity.class);
                            }
                        }).build())
                        .setLayoutRes(R.layout.guide_pc_1)
                        .setBackgroundColor(0xcc000000)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLightWithOptions(llQiuPiao,new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(PlayEarthTaskActivity.class);
                            }
                        }).build())
                        .setLayoutRes(R.layout.guide_pc_2)
                        .setBackgroundColor(0xcc000000)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation));
        if (itemView != null){
            guide.addGuidePage(GuidePage.newInstance()
                    .addHighLightWithOptions(itemView,new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(BookVenueManageActivity.class);
                        }
                    }).build())
                    .setLayoutRes(R.layout.guide_pc_3)
                    .setBackgroundColor(0xcc000000)
                    .setEnterAnimation(enterAnimation)//进入动画
                    .setExitAnimation(exitAnimation)
            .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                @Override
                public void onLayoutInflated(View view, Controller controller) {
                    ImageView guideImAGE = view.findViewById(R.id.guideImage);
                    int top = itemView.getTop();
                    ViewUtils.setMargins(guideImAGE,100,top - ConvertUtils.dp2px(160),100,0);
                }
            }));
        }
        guide.show();
    }

    /**
     * 刷新用户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUserInfo(RefreshPersonalInfoEvent event) {
        getPresenter().getUserInfo();
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        Constant.userInfo = userInfoDto;
        GlideUtils.loadImageOnPregress(userPortrait, userInfoDto.getPortraitUrl(), null);
        userName.setText(userInfoDto.getName());
        userPosition.setText(userInfoDto.getPosition());
        userCompany.setText(userInfoDto.getCompany());
        if (userInfoDto.getStatus() == 2) {
            realName.setText("已实名");
            realName.setVisibility(View.VISIBLE);
            realNameStatus.setVisibility(View.GONE);
        } else {
            realName.setText("未实名");
            realName.setVisibility(View.GONE);
            realNameStatus.setVisibility(View.VISIBLE);
        }

//        if (userInfoDto.getPositionStatus() == 2){
//            positionReal.setText("已认证");
//        } else {
//            positionReal.setText("未认证");
//        }

        personalCenter.clear();
        List<ModelEntity> modelEntities = new ArrayList<>();
        for (int i = 0; i < modelImages.length; i++) {
            if (i == 0 && !Constant.userInfo.getRoleId().equals("5")) {
                continue;
            }
            ModelEntity entity = new ModelEntity(modelImages[i], modelNames[i]);
            entity.setItemLayoutRes(R.layout.item_personal_center_function);
            modelEntities.add(entity);
        }
        personalCenter.loadDataOfNextPage(modelEntities);
        personalCenter.post(new Runnable() {
            @Override
            public void run() {
                showGuide();
            }
        });

    }

    @Override
    public void showClubInf(ClubDto clubDto) {

    }
}
