package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.km.rmbank.module.main.personal.address.ReceiverAddressActivity;
import com.km.rmbank.module.main.personal.hpage.PersonalHomePageActivity;
import com.km.rmbank.module.main.personal.member.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.main.personal.setting.AboutMeActivity;
import com.km.rmbank.module.main.personal.setting.SettingActivity;
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

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.modelRecycler)
    RecyclerView modelRecycler;
    private String[] modelNames = {"俱乐部管理","商品管理","人脉圈","收货地址","客服咨询","关于我们","设置"};
    private int[] modelImgs = {R.mipmap.icon_pc_model1,R.mipmap.icon_pc_model1,R.mipmap.icon_pc_model1,
            R.mipmap.icon_pc_model1,R.mipmap.icon_pc_model1,R.mipmap.icon_pc_model1,
            R.mipmap.icon_pc_model1};
    private List<ModelEntity> modelEntityList;
    private int curUserRole = 0; //0：未登录 1：普通用户 2：玩家合伙人 3：俱乐部合伙人

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
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(getContext());
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
        lp.topMargin = statusBarHeight;
        mToolbar.setLayoutParams(lp);

        mToolbar.inflateMenu(R.menu.toolbar_home_personal_center);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.qrcode){
                    showToast("二维码");
                } else if (item.getItemId() == R.id.message){
                    showToast("消息");
                }
                return false;
            }
        });
    }

    /**
     * 初始化 用户功能列表
     */
    private void initRecycler(){
        modelEntityList = new ArrayList<>();
        getModelListDatas();
        RecyclerAdapterHelper<ModelEntity> mHelper = new RecyclerAdapterHelper<>(modelRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_personal_center_model, modelEntityList, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                View line1 = holder.findView(R.id.line1);
                View line2 = holder.findView(R.id.line2);
                GlideImageView modelImage = holder.findView(R.id.modelImage);
                TextView modelName = holder.findView(R.id.modelName);

                GlideUtils.loadImageByRes(modelImage,mData.getModelRes());
                modelName.setText(mData.getModelName());

                if (isShowLine1(position)){
                    line1.setVisibility(View.VISIBLE);
                    line2.setVisibility(View.GONE);
                } else {
                    line1.setVisibility(View.GONE);
                    line2.setVisibility(View.VISIBLE);
                }
            }
        }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ModelEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ModelEntity data, int position) {
                if (Constant.userLoginInfo.isEmpty()){
                    showToast("请先登录");
                    return;
                }
                switch (data.getModelName()){
                    case "设置":
                        startActivity(SettingActivity.class);
                        break;
                    case "关于我们":
                        startActivity(AboutMeActivity.class);
                        break;
                    case "收货地址":
                        startActivity(ReceiverAddressActivity.class);
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

    private boolean isShowLine1(int curPosition){
        if (curUserRole == 0 || curUserRole == 1){//未登录或普通用户
            return curPosition % 2 == 1;
        } else if (curUserRole == 2){//玩家合伙人
            return curPosition % 2 == 0;
        } else if (curUserRole == 3){//俱乐部合伙人
            if (curPosition <= 1){
                return curPosition % 2 == 0;
            } else {
                return curPosition % 2 == 1;
            }
        }
        return curPosition % 2 == 0;
    }

    /**
     * 刷新个人中心 数据
     * @param userInfoDto
     */
    private void notifyPersonCenter(UserInfoDto userInfoDto){
        NestedScrollView scrollView = mViewManager.findView(R.id.nestedScrollView);
        scrollView.fullScroll(NestedScrollView.FOCUS_UP);
        modelEntityList.clear();
        getModelListDatas();
        modelRecycler.getAdapter().notifyDataSetChanged();
        if (curUserRole == 0){
            mViewManager.findView(R.id.llUnlogin).setVisibility(View.VISIBLE);
            mViewManager.findView(R.id.login).setVisibility(View.GONE);
        } else {
            mViewManager.findView(R.id.llUnlogin).setVisibility(View.GONE);
            mViewManager.findView(R.id.login).setVisibility(View.VISIBLE);
        }

        GlideImageView userPortrait = mViewManager.findView(R.id.userPortrait);
        GlideUtils.loadImageOnPregress(userPortrait,userInfoDto.getPortraitUrl(),null);
        userPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserHomePage(v);
            }
        });

        mViewManager.setText(R.id.userName,userInfoDto.getName());
        TextView becomeMember = mViewManager.findView(R.id.become_memeber);
        becomeMember.setVisibility(View.VISIBLE);
        String roleName = "";
        if (curUserRole != 2 && curUserRole != 3){
            roleName = "普通用户";
            becomeMember.setText("成为玩家合伙人");
        } else if (curUserRole == 2){
            roleName = "玩家合伙人";
            becomeMember.setText("成为俱乐部合伙人");
        } else if (curUserRole == 3){
            roleName = "俱乐部合伙人";
            becomeMember.setVisibility(View.GONE);
        }
        mViewManager.setText(R.id.userRoleName,roleName);

        mViewManager.setText(R.id.ticketCount,userInfoDto.getTicketCount() == null ? "" : userInfoDto.getTicketCount());
        mViewManager.setText(R.id.tv_attention,userInfoDto.getKeepCount() == null ? "" : userInfoDto.getKeepCount());
        mViewManager.setText(R.id.tv_account,userInfoDto.getBalance()+"");
    }

    /**
     * 获取个人中心 用户功能列表的名称
     */
    private void getModelListDatas(){
        for (int i=0; i<modelNames.length; i++){
            //未登录 或 普通用户时  去掉 俱乐部管理 和 商品管理
            if ((curUserRole == 0 || curUserRole == 1) && (i == 0 || i == 1)){
                continue;
            }
            //玩家合伙人 去掉俱乐部管理
            if (curUserRole == 2 && i == 0){
                continue;
            }

            ModelEntity entity = new ModelEntity(modelImgs[i],modelNames[i],false);
            modelEntityList.add(entity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getUserInfo();
    }

    /**
     * 刷新个人信息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPersonalInfo(RefreshPersonalInfoEvent event){
        getPresenter().getUserInfo();
    }

    /**
     * 打开登录页面
     * @param view
     */
    @OnClick({R.id.ivUnLogin,R.id.unLogin,R.id.tvUnLogin})
    public void unLogin(View view){
        startActivity(LoginActivity.class);
    }

    /**
     * 打开个人主页
     * @param view
     */
    public void openUserHomePage(View view){
        startActivity(PersonalHomePageActivity.class);
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        String roleId = userInfoDto.getRoleId();
        if ("4".equals(roleId) || (!"2".equals(roleId) && !"1".equals(roleId))){
            curUserRole = 1;
        } else if ("2".equals(roleId)){
            curUserRole = 2;
        } else if ("1".equals(roleId)){
            curUserRole = 3;
        } else {
            curUserRole = 0;//未登录
        }

        notifyPersonCenter(userInfoDto);
    }

    @Override
    public void showClubInf(ClubDto clubDto) {

    }
}
