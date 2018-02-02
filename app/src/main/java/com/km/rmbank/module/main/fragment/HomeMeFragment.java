package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.km.rmbank.module.main.personal.address.ReceiverAddressActivity;
import com.km.rmbank.module.main.personal.circlefriends.MyForumInfosActivity;
import com.km.rmbank.module.main.personal.integral.MyIntegralActivity;
import com.km.rmbank.module.main.personal.leader.MeetingListActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.member.MyTeamActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.module.main.personal.member.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.main.personal.order.MyOrderActivity;
import com.km.rmbank.module.main.personal.setting.SettingActivity;
import com.km.rmbank.module.main.shop.ShoppingCartActivity;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.presenter.UserPresenter;
import com.km.rmbank.mvp.view.IUserView;
import com.km.rmbank.utils.Constant;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMeFragment extends BaseFragment<IUserView,UserPresenter> implements IUserView {

    private int[] orderManagerRes = {R.mipmap.icon_order_wait_payment,R.mipmap.icon_order_wait_send_goods,
    R.mipmap.icon_order_wait_receive_goods,R.mipmap.icon_order_finished};
    private String[] orderManagerNames = {"待付款","待发货","待收货","已完成"};

    private int[] memberImageRes = {R.mipmap.icon_member_my_team,R.mipmap.icon_member_club,
            R.mipmap.icon_member_goods_manager,R.mipmap.icon_member_star};
    private String[] memberNames = {"我的团队","俱乐部","商品管理","我是大咖"};

    private int[] userModelImageRes = {R.mipmap.icon_user_admin,R.mipmap.icon_user_receiver_address,
            R.mipmap.icon_user_circle_friends,R.mipmap.icon_user_about_star,R.mipmap.icon_user_applied_action};
    private String[] userModelNames = {"助教管理","收货地址","捡漏专区","约咖管理","已报名活动"};

    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;

    @BindView(R.id.become_memeber)
    TextView becomeMember;

    @BindView(R.id.userMember)
    LinearLayout userMember;

    private List<ModelEntity> modelEntities;
    public static HomeMeFragment newInstance(Bundle bundle) {

        HomeMeFragment fragment = new HomeMeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_me;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(new UserModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {

        initOrderRecycler();

        initMemberRecycler();

        initUserModelRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPersonalInfo(RefreshPersonalInfoEvent event){
        getPresenter().getUserInfo();
    }

    /**
     * 订单管理
     */
    private void initOrderRecycler(){
        List<ModelEntity> modelEntities = new ArrayList<>();
        for(int i = 0; i < orderManagerNames.length; i++){
            modelEntities.add(new ModelEntity(orderManagerRes[i],orderManagerNames[i]));
        }
        RecyclerView orderRecycler = mViewManager.findView(R.id.orderRecycler);
        RecyclerAdapterHelper<ModelEntity> mHelper = new RecyclerAdapterHelper<>(orderRecycler);
        mHelper.addGrigLayoutMnager(4)
                .addCommonAdapter(R.layout.item_home_me_order_manager, modelEntities, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                holder.addRippleEffectOnClick();
                GlideUtils.loadImage(getContext(),mData.getModelRes(),holder.getImageView(R.id.modelImage));
                holder.setText(R.id.modelName,mData.getModelName());
            }
        }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ModelEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ModelEntity data, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderType",position + 1);
                startActivity(MyOrderActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ModelEntity data, int position) {
                return false;
            }

        });
    }

    /**
     * 合伙人管理
     */
    private void initMemberRecycler(){
        List<ModelEntity> modelEntities = new ArrayList<>();
        for(int i = 0; i < memberNames.length; i++){
            modelEntities.add(new ModelEntity(memberImageRes[i],memberNames[i]));
        }
        RecyclerView orderRecycler = mViewManager.findView(R.id.memberRecycler);
        RecyclerAdapterHelper<ModelEntity> mHelper = new RecyclerAdapterHelper<>(orderRecycler);
        mHelper.addGrigLayoutMnager(4)
                .addCommonAdapter(R.layout.item_home_me_order_manager, modelEntities, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
                    @Override
                    public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                        holder.addRippleEffectOnClick();
                        GlideUtils.loadImage(getContext(),mData.getModelRes(),holder.getImageView(R.id.modelImage));
                        holder.setText(R.id.modelName,mData.getModelName());
                    }
                }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ModelEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ModelEntity data, int position) {
                if (Constant.userInfo == null || "4".equals(Constant.userInfo.getRoleId())){
                    showToast("你暂无此权限");
                    return;
                }
                switch (data.getModelName()){
                    case "我的团队":
                        startActivity(MyTeamActivity.class);
                        break;
                    case "俱乐部":
                        if (true){
                            showToast(getResources().getString(R.string.notOpen));
                            return;
                        }
                        if (TextUtils.isEmpty(Constant.userInfo.getClubId())){
                            showToast("找不到你的俱乐部");
                            return;
                        }
                        getPresenter().getClubInfOfMe(Constant.userInfo.getClubId());
                        break;
                    case "商品管理":
                        startActivity(GoodsManagerActivity.class);
                        break;
                    case "我是大咖":
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
     * 普通用户的功能管理
     */
    private void initUserModelRecycler(){
        modelEntities = new ArrayList<>();
        for(int i = 0; i < userModelImageRes.length; i++){
            modelEntities.add(new ModelEntity(userModelImageRes[i],userModelNames[i]));
        }
        RecyclerView orderRecycler = mViewManager.findView(R.id.userModelRecycler);
        RecyclerAdapterHelper<ModelEntity> mHelper = new RecyclerAdapterHelper<>(orderRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_home_me_user_model, modelEntities, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
                    @Override
                    public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                        holder.addRippleEffectOnClick();
                        GlideUtils.loadImage(getContext(),mData.getModelRes(),holder.getImageView(R.id.modelImage));
                        holder.setText(R.id.modelName,mData.getModelName());
                    }
                }).create();
        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ModelEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ModelEntity data, int position) {
                switch (data.getModelName()){
                    case "助教管理":
                        startActivity(MeetingListActivity.class);
                        break;
                    case "收货地址":
                        startActivity(ReceiverAddressActivity.class);
                        break;
                    case "捡漏专区":
                        startActivity(MyForumInfosActivity.class);
                        break;
                    case "约咖管理":
                        showToast(getResources().getString(R.string.notOpen));
                        break;
                    case "已报名活动":
                        startActivity(AppliedActionActivity.class);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ModelEntity data, int position) {
                return false;
            }
        });
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        Constant.userInfo = userInfoDto;
        GlideUtils.loadProtrait(getContext(),userInfoDto.getPortraitUrl(),mViewManager.getImageView(R.id.userPortrait));
        mViewManager.setText(R.id.tv_user_nick_name,userInfoDto.getName());
        mViewManager.setText(R.id.tv_attention,userInfoDto.getKeepCount());
        mViewManager.setText(R.id.tv_integral,userInfoDto.getTotal());
        mViewManager.setText(R.id.tv_account,userInfoDto.getBalance() + "");

        if ("4".equals(userInfoDto.getRoleId())){
            flowLayout.setVisibility(View.GONE);
            becomeMember.setVisibility(View.VISIBLE);

        } else {
            flowLayout.setVisibility(View.VISIBLE);
            becomeMember.setVisibility(View.GONE);
            List<String> userMembers = new ArrayList<>();
            userMembers.add(userInfoDto.getRoleName());
            initFlowLayout(userMembers);
        }

        //隐藏合伙人
        if ("4".equals(userInfoDto.getRoleId())){
            userMember.setVisibility(View.GONE);
        } else {
            userMember.setVisibility(View.VISIBLE);
        }


//        if (userInfoDto.getIsTeach() == 0){
//            ModelEntity entity = modelEntities.get(0);
//            if ("助教管理".equals(entity.getModelName())){
//                modelEntities.remove(entity);
//            }
//
//        }
    }

    private void initFlowLayout(List<String> mDatas){

        flowLayout.setAdapter(new TagAdapter<String>(mDatas) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) LayoutInflater.from(getContext())
                        .inflate(R.layout.flowlayout_item,flowLayout,false);
                textView.setText(s);
                return textView;
            }
        });

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                startActivity(BecomeMemberActivity.class);
                return true;
            }
        });
    }

    @Override
    public void showClubInf(ClubDto clubDto) {
        if (clubDto == null){
            showToast("你还没有自己的俱乐部");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }

    /**
     * 设置
     * @param view
     */
    @OnClick(R.id.setting)
    public void clickSetting(View view){
        startActivity(SettingActivity.class);
    }

    /**
     * 设置
     * @param view
     */
    @OnClick(R.id.message)
    public void clickMessage(View view){
        startActivity(MessageActivity.class);
    }

    /**
     * 设置
     * @param view
     */
    @OnClick(R.id.shopCard)
    public void clickShopCart(View view){
        startActivity(ShoppingCartActivity.class);
    }

    /**
     * 所有订单
     * @param view
     */
    @OnClick(R.id.allOrder)
    public void allOrder(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("orderType",0);
        startActivity(MyOrderActivity.class,bundle);
    }

    /**
     * 查看我的账户
     * @param view
     */
    @OnClick({R.id.ll_account,R.id.tv_account,R.id.account})
    public void openMyAccount(View view){
        startActivity(UserAccountActivity.class);
    }

    /**
     * 我的关注
     * @param view
     */
    @OnClick({R.id.ll_attention,R.id.tv_attention,R.id.attention})
    public void openAttention(View view){
        startActivity(AttentionGoodsActivity.class);
    }

    /**
     * 我的积分
     * @param view
     */
    @OnClick({R.id.ll_integral,R.id.tv_integral,R.id.integral})
    public void openIntegral(View view){
        startActivity(MyIntegralActivity.class);
    }

    /**
     * 我的名片
     * @param view
     */
    @OnClick({R.id.userPortrait,R.id.tv_user_nick_name,R.id.rl_qrcode,R.id.tv_qrcode,R.id.iv_qrcode})
    public void openUserCard(View view){
        startActivity(UserCardActivity.class);
    }

    @OnClick(R.id.become_memeber)
    public void becomeMember(View view){
        startActivity(BecomeMemberActivity.class);
    }
}
