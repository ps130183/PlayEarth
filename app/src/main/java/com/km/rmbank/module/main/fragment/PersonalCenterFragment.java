package com.km.rmbank.module.main.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.main.card.UserCardActivity;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.module.main.personal.AttentionGoodsActivity;
import com.km.rmbank.module.main.personal.account.UserAccountActivity;
import com.km.rmbank.module.main.personal.action.AppliedActionActivity;
import com.km.rmbank.module.main.personal.contacts.MyTeamActivity;
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
import com.km.rmbank.utils.dialog.DialogUtils;
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

public class PersonalCenterFragment extends BaseFragment<IUserView,UserPresenter> implements IUserView {

    private GlideImageView userPortrait;
    private TextView userName;
    private TextView realName;
    private ImageView realNameStatus;
    private TextView userPosition;
    private TextView userCompany;

    private MRecyclerView<ModelEntity> personalCenter;
    private String[] modelNames = {"人脉", "电子券", "活动", "关注", "消息", "客服", "关于", "设置"};
    private int[] modelImages = {R.mipmap.icon_personal_center_f1,
            R.mipmap.icon_personal_center_f2, R.mipmap.icon_personal_center_f3,
            R.mipmap.icon_personal_center_f4, R.mipmap.icon_personal_center_f5,
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
                realName = holder.findView(R.id.realName);
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
                realNameStatus = holder.findView(R.id.realNameStatus);
                LinearLayout click1 = holder.findView(R.id.click1);
                LinearLayout click2 = holder.findView(R.id.click2);
                LinearLayout click3 = holder.findView(R.id.click3);
                LinearLayout click4 = holder.findView(R.id.click4);

//                holder.setText(R.id.userBalance,"账户余额：" + Constant.userInfo.getBalance());

                //赚球票
                click1.setOnClickListener(new View.OnClickListener() {
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
                GlideUtils.loadImageByRes(functionImage,mData.getModelRes());
                holder.setText(R.id.functionName,mData.getModelName());

                if (position % 3 == 2){
                    RelativeLayout rlContent = holder.findView(R.id.rlContent);
                    ViewUtils.setMargins(rlContent,0,0,0, ConvertUtils.dp2px(10));
                }
            }

        }).create();

        personalCenter.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                switch (position){
                    case 0://人脉
                        startActivity(MyTeamActivity.class);
                        break;
                    case 1://电子券
                        startActivity(TicketListActivity.class);
                        break;
                    case 2://活动
                        startActivity(AppliedActionActivity.class);
                        break;
                    case 3://关注
                        startActivity(AttentionGoodsActivity.class);
                        break;
                    case 4://消息
                        startActivity(MessageActivity.class);
                        break;
                    case 5://客服电话
                        startActivity(ServiceActivity.class);
                        break;
                    case 6://关于
                        startActivity(AboutMeActivity.class);
                        break;
                    case 7://设置
                        startActivity(SettingActivity.class);
                        break;
                }
            }
        });

        List<ModelEntity> modelEntities = new ArrayList<>();
        for (int i = 0; i < modelImages.length; i++){
            ModelEntity entity = new ModelEntity(modelImages[i],modelNames[i]);
            entity.setItemLayoutRes(R.layout.item_personal_center_function);
            modelEntities.add(entity);
        }
        personalCenter.loadDataOfNextPage(modelEntities);

    }

    /**
     * 刷新用户信息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUserInfo(RefreshPersonalInfoEvent event){
        getPresenter().getUserInfo();
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        Constant.userInfo = userInfoDto;
        GlideUtils.loadImageOnPregress(userPortrait,userInfoDto.getPortraitUrl(),null);
        userName.setText(userInfoDto.getName());
        userPosition.setText(userInfoDto.getPosition());
        userCompany.setText(userInfoDto.getCompany());
        if (userInfoDto.getStatus() == 2){
            realName.setText("已认证");
            realNameStatus.setVisibility(View.GONE);
        }  else {
            realName.setText("未认证");
            realNameStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showClubInf(ClubDto clubDto) {

    }
}
