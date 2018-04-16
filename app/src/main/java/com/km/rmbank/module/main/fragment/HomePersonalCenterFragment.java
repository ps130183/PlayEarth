package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.card.UserNewCardActivity;
import com.km.rmbank.module.main.personal.account.UserAccountActivity;
import com.km.rmbank.module.main.personal.address.ReceiverAddressActivity;
import com.km.rmbank.module.main.personal.contacts.ContactsActivity;
import com.km.rmbank.module.main.personal.hpage.PersonalHomePageActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.member.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.main.personal.order.MyOrderActivity;
import com.km.rmbank.module.main.personal.setting.AboutMeActivity;
import com.km.rmbank.module.main.personal.setting.SettingActivity;
import com.km.rmbank.module.main.personal.ticket.TicketListActivity;
import com.km.rmbank.module.main.shop.ShoppingCartActivity;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.presenter.UserPresenter;
import com.km.rmbank.mvp.view.IUserView;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.ViewUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePersonalCenterFragment extends BaseFragment<IUserView,UserPresenter> implements IUserView {

    @BindView(R.id.commonModuleRecycler)
    RecyclerView commonModuleRecycler;
    private String[] commonModuleNames = {"我的订单","地址","卡券","账户","购物车","客服","成为会员"};
    private int[] commonModuleImgs = {R.mipmap.icon_pc_my_order,R.mipmap.icon_pc_address,R.mipmap.icon_pc_ticket,
            R.mipmap.icon_pc_my_account,R.mipmap.icon_pc_shop_car,R.mipmap.icon_pc_service,R.mipmap.icon_pc_become_member};

    @BindView(R.id.memberModuleRecycler)
    RecyclerView memberModuleRecycler;
    private String[] memberModuleNames = {"活动","人脉","周边服务"};
    private int[] memberModuleImgs = {R.mipmap.icon_pc_activity,R.mipmap.icon_pc_contacts,R.mipmap.icon_pc_around_service};

    public HomePersonalCenterFragment() {
        // Required empty public constructor
    }

    public static HomePersonalCenterFragment newInstance(Bundle bundle) {
        HomePersonalCenterFragment fragment = new HomePersonalCenterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_personal_center;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(new UserModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initToolbar();
        initRecycler();
    }

    /**
     * 初始化 标题栏
     */
    private void initToolbar(){
        Toolbar mToolbar = mViewManager.findView(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.icon_pc_setting);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.class);
            }
        });
    }

    private void initRecycler(){
        List<ModelEntity> commonModels = new ArrayList<>();
        for (int i = 0; i < commonModuleNames.length; i++){
            commonModels.add(new ModelEntity(commonModuleImgs[i],commonModuleNames[i]));
        }
        RecyclerAdapterHelper<ModelEntity> commonHelper = new RecyclerAdapterHelper<>(commonModuleRecycler);
        commonHelper.addGrigLayoutMnager(4)
                .addDividerItemDecorationForGrid(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_personal_center_model, commonModels, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                holder.addRippleEffectOnClick();
                GlideImageView modelImage = holder.findView(R.id.modelImage);
                GlideUtils.loadImageByRes(modelImage,mData.getModelRes());
                holder.setText(R.id.modelName,mData.getModelName());
            }
        }).create();

        commonHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ModelEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ModelEntity data, int position) {
                switch (data.getModelName()){
                    case "我的订单":
                        startActivity(MyOrderActivity.class);
                        break;
                    case "地址":
                        startActivity(ReceiverAddressActivity.class);
                        break;
                    case "卡券":
                        startActivity(TicketListActivity.class);
                        break;
                    case "账户":
                        startActivity(UserAccountActivity.class);
                        break;
                    case "购物车":
                        startActivity(ShoppingCartActivity.class);
                        break;
                    case "客服":
                        break;
                    case "成为会员":
                        startActivity(BecomeMemberActivity.class);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ModelEntity data, int position) {
                return false;
            }

        });


        List<ModelEntity> memberModels = new ArrayList<>();
        for (int i = 0; i < memberModuleNames.length; i++){
            memberModels.add(new ModelEntity(memberModuleImgs[i],memberModuleNames[i]));
        }
        RecyclerAdapterHelper<ModelEntity> memberHelper = new RecyclerAdapterHelper<>(memberModuleRecycler);
        memberHelper.addGrigLayoutMnager(4)
                .addDividerItemDecorationForGrid(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_personal_center_model, memberModels, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
                    @Override
                    public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                        holder.addRippleEffectOnClick();
                        GlideImageView modelImage = holder.findView(R.id.modelImage);
                        GlideUtils.loadImageByRes(modelImage,mData.getModelRes());
                        holder.setText(R.id.modelName,mData.getModelName());
                    }
                }).create();
        memberHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ModelEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ModelEntity data, int position) {
                switch (data.getModelName()){
                    case "活动":
                        showToast(getResources().getString(R.string.notOpen));
                        break;
                    case "人脉":
                        startActivity(ContactsActivity.class);
                        break;
                    case "周边服务":
                        showToast(getResources().getString(R.string.notOpen));
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ModelEntity data, int position) {
                return false;
            }

        });
    }


    /**
     * 打开用户名片
     * @param view
     */
    @OnClick(R.id.userQRCode)
    public void clickUserQRCode(View view){
        startActivity(UserNewCardActivity.class);
    }

    /**
     * 关于我们
     * @param view
     */
    @OnClick(R.id.aboutMe)
    public void aboutMe(View view){
        startActivity(AboutMeActivity.class);
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
        GlideImageView protraitImage = mViewManager.findView(R.id.iv_protrait);
        GlideUtils.loadImageOnPregress(protraitImage,userInfoDto.getPortraitUrl(),null);

        //个人签名
        mViewManager.setText(R.id.introduce, TextUtils.isEmpty(userInfoDto.getPersonalizedSignature()) ? "暂时没有设置签名" : userInfoDto.getPersonalizedSignature());
        mViewManager.setText(R.id.keepCount,TextUtils.isEmpty(userInfoDto.getKeepCount()) ? "0" : userInfoDto.getKeepCount());
    }

    @Override
    public void showClubInf(ClubDto clubDto) {

    }
}
