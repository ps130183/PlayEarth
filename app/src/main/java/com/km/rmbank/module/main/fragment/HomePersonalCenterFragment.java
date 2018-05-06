package com.km.rmbank.module.main.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.main.card.UserNewCardActivity;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.module.main.personal.AttentionGoodsActivity;
import com.km.rmbank.module.main.personal.account.UserAccountActivity;
import com.km.rmbank.module.main.personal.action.AppliedActionActivity;
import com.km.rmbank.module.main.personal.address.ReceiverAddressActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.member.MyTeamActivity;
import com.km.rmbank.module.main.personal.member.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.main.personal.order.MyOrderActivity;
import com.km.rmbank.module.main.personal.setting.AboutMeActivity;
import com.km.rmbank.module.main.personal.setting.HelpDocumentActivity;
import com.km.rmbank.module.main.personal.setting.SettingActivity;
import com.km.rmbank.module.main.personal.ticket.TicketListActivity;
import com.km.rmbank.module.main.shop.ShoppingCartActivity;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.presenter.UserPresenter;
import com.km.rmbank.mvp.view.IUserView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DialogUtils;
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
    private String[] commonModuleNames = {"我的订单","地址","电子券","账户","购物车","客服","成为会员"};
    private int[] commonModuleImgs = {R.mipmap.icon_pc_my_order,R.mipmap.icon_pc_address,R.mipmap.icon_pc_ticket,
            R.mipmap.icon_pc_my_account,R.mipmap.icon_pc_shop_car,R.mipmap.icon_pc_service,R.mipmap.icon_pc_become_member};

    @BindView(R.id.memberModuleRecycler)
    RecyclerView memberModuleRecycler;
    private String[] memberModuleNames = {"活动","人脉","周边服务","商品管理"};
    private int[] memberModuleImgs = {R.mipmap.icon_pc_activity,R.mipmap.icon_pc_contacts,R.mipmap.icon_pc_around_service,R.mipmap.icon_pc_goods_manager};

    private int messageNum= 0;
    private int attentionNum = 0;
    private int dynamicNum = 0;
    private int fansNum = 0;

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
                if (Constant.userInfo != null && mData.getModelName().equals("成为会员") && Constant.userInfo.getStatus() != 2){
                    holder.findView(R.id.ivRealNameStatus).setVisibility(View.VISIBLE);
                    holder.findView(R.id.tvRealNameStatus).setVisibility(View.GONE);
                } else if (Constant.userInfo != null && mData.getModelName().equals("成为会员") && Constant.userInfo.getStatus() == 2){
                    holder.findView(R.id.ivRealNameStatus).setVisibility(View.GONE);
                    holder.findView(R.id.tvRealNameStatus).setVisibility(View.VISIBLE);
                } else {
                    holder.findView(R.id.ivRealNameStatus).setVisibility(View.GONE);
                    holder.findView(R.id.tvRealNameStatus).setVisibility(View.GONE);
                }
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
                    case "电子券":
                        startActivity(TicketListActivity.class);
                        break;
                    case "账户":
                        startActivity(UserAccountActivity.class);
                        break;
                    case "购物车":
                        startActivity(ShoppingCartActivity.class);
                        break;
                    case "客服":
                        callServicePhone();
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
        if (Constant.userInfo.getType() != 2){
            memberModels.remove(3);
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
                    case "活动"://已报名活动
                        startActivity(AppliedActionActivity.class);
                        break;
                    case "人脉":
//                        startActivity(ContactsActivity.class);
                        startActivity(MyTeamActivity.class);
//                        showToast(getResources().getString(R.string.notOpen));
                        break;
                    case "周边服务":
                        showToast(getResources().getString(R.string.notOpen));
                        break;
                    case "商品管理":
                        startActivity(GoodsManagerActivity.class);
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
     * 拨打客服电话
     */
    private void callServicePhone(){
        DialogUtils.showDefaultAlertDialog("是否拨打客服电话：13699231246？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "13699231246");
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
    /**
     * 消息
     * @param view
     */
    @OnClick(R.id.message)
    public void onClickMessage(View view){
//        if (messageNum == 0){
//
//            return;
//        }
        startActivity(MessageActivity.class);
    }

    /**
     * 关注
     * @param view
     */
    @OnClick(R.id.attention)
    public void onClickAttention(View view){
        if (attentionNum == 0){
            showToast("没有关注信息！");
            return;
        }
        startActivity(AttentionGoodsActivity.class);
    }

    /**
     * 动态
     * @param view
     */
    @OnClick(R.id.dynamic)
    public void onClickDynamic(View view){
        if (dynamicNum == 0){
            showToast("没有新的动态！");
            return;
        }
    }

    /**
     * 粉丝
     * @param view
     */
    @OnClick(R.id.fans)
    public void onClickFans(View view){
        if (fansNum == 0){
            showToast("没有粉丝！");
            return;
        }
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
     * 打开个人主页
     * @param view
     */
    @OnClick(R.id.iv_protrait)
    public void openUserMainPage(View view){
//        startActivity(PersonalHomePageActivity.class);
    }

    /**
     * 关于我们
     * @param view
     */
    @OnClick(R.id.aboutMe)
    public void aboutMe(View view){
        startActivity(AboutMeActivity.class);
    }

    @OnClick(R.id.help)
    public void openHelp(View view){
        startActivity(HelpDocumentActivity.class);
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
        attentionNum = TextUtils.isEmpty(userInfoDto.getKeepCount()) ? 0 : Integer.parseInt(userInfoDto.getKeepCount());
        GlideImageView protraitImage = mViewManager.findView(R.id.iv_protrait);
        GlideUtils.loadImageOnPregress(protraitImage,userInfoDto.getPortraitUrl(),null);

        mViewManager.setText(R.id.userName,userInfoDto.getName());

        //个人签名
        mViewManager.setText(R.id.introduce, TextUtils.isEmpty(userInfoDto.getPersonalizedSignature()) ? "暂时没有设置签名" : userInfoDto.getPersonalizedSignature());
        mViewManager.setText(R.id.keepCount,attentionNum+"");

        initRecycler();
    }

    @Override
    public void showClubInf(ClubDto clubDto) {

    }
}
