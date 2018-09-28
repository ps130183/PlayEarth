package com.km.rmbank.module.main.card;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.DemandEntity;
import com.km.rmbank.entity.OtherIdentityEntity;
import com.km.rmbank.entity.SupplyEntity;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.event.SelectIndustryEvent;
import com.km.rmbank.event.UserInfoEvent;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.presenter.UserInfoPresenter;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.tencent.connect.UserInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class UserCardModifyActivity extends BaseActivity<IUserInfoView,UserInfoPresenter> implements IUserInfoView {

    private NestedScrollView mScrollView;
    private MRecyclerView<OtherIdentityEntity> otherIdentityRecycler;
    private MRecyclerView<SupplyEntity> supplyRecycler;
    private MRecyclerView<DemandEntity> demandRecycler;

    private String userPortraitPath;

    private GlideImageView userPortrait;
    private EditText etUserName;
    private EditText etUserCompany;
    private EditText etUserPosition;
    private EditText etUserPhone;
    private RadioGroup rgPhone;
    private RadioButton rbPhoneClose;
    private RadioButton rbPhoneOpen;

    private TextView etUserIndustry;
    private EditText etUserAddress;
    private EditText etEmailAddress;
    private EditText etUserIntroduce;

    private String allowStatus = "0";
    private IndustryDto industryDto;

    private int fromPage = -1;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_card_modify;
    }

    @Override
    public String getTitleContent() {
        return "名片修改";
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(new UserInfoModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar toolBar = (SimpleTitleBar) titleBar;
        toolBar.setRightMenuContent("保存");
        toolBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        fromPage = getIntent().getIntExtra("fromPage",0);
        initOtherIdentity();
        initSupplyRecycler();
        initDemandRecycler();
        initView();
    }

    private void initView(){
        userPortrait = mViewManager.findView(R.id.userPortrait);
        userPortraitPath = Constant.userInfo.getPortraitUrl();
        GlideUtils.loadImageOnPregress(userPortrait, userPortraitPath,null);

        etUserName = mViewManager.findView(R.id.etUserName);
        etUserCompany = mViewManager.findView(R.id.etUserCompay);
        etUserPosition = mViewManager.findView(R.id.etUserPosition);
        etUserPhone = mViewManager.findView(R.id.etUserPhone);
        etUserIndustry = mViewManager.findView(R.id.etUserIndustry);
        etUserAddress = mViewManager.findView(R.id.etUserAddress);
        etEmailAddress = mViewManager.findView(R.id.etUserEmail);
        etUserIntroduce = mViewManager.findView(R.id.etUserIntroduce);

        etUserName.setText(TextUtils.isEmpty(Constant.userInfo.getName()) ? "" : Constant.userInfo.getName());
        etUserCompany.setText(TextUtils.isEmpty(Constant.userInfo.getCompany()) ? "" : Constant.userInfo.getCompany());
        etUserPosition.setText(TextUtils.isEmpty(Constant.userInfo.getPosition()) ? "" : Constant.userInfo.getPosition());
        etUserPhone.setText(TextUtils.isEmpty(Constant.userInfo.getCardPhone()) ? "" : Constant.userInfo.getCardPhone());
        etUserAddress.setText(TextUtils.isEmpty(Constant.userInfo.getDetailedAddress()) ? "" : Constant.userInfo.getDetailedAddress());
        etEmailAddress.setText(TextUtils.isEmpty(Constant.userInfo.getEmailAddress()) ? "" : Constant.userInfo.getEmailAddress());
        etUserIntroduce.setText(TextUtils.isEmpty(Constant.userInfo.getPersonalizedSignature()) ? "" : Constant.userInfo.getPersonalizedSignature());
        etUserIndustry.setText(TextUtils.isEmpty(Constant.userInfo.getIndustryName()) ? "" : Constant.userInfo.getIndustryName());

        otherIdentityRecycler.loadDataOfNextPage(Constant.userInfo.getIdentityList());
        supplyRecycler.loadDataOfNextPage(Constant.userInfo.getProvideList());
        demandRecycler.loadDataOfNextPage(Constant.userInfo.getDemandList());


//        TextView tvUserName = mViewManager.findView(R.id.tvUserName);
//        TextView realName = mViewManager.findView(R.id.realName);
        if (Constant.userInfo.getStatus() == 2){//实名认证  名字不可修改
//            etUserName.setVisibility(View.GONE);
            etUserName.setEnabled(false);
//            tvUserName.setVisibility(View.VISIBLE);
//            realName.setVisibility(View.VISIBLE);
//            tvUserName.setText(Constant.userInfo.getName());
        } else {//未实名
//            etUserName.setVisibility(View.VISIBLE);
            etUserName.setEnabled(true);
//            tvUserName.setVisibility(View.GONE);
//            realName.setVisibility(View.GONE);
        }

        //电话的隐私 与 公开
        rgPhone = mViewManager.findView(R.id.rgPhone);
        rbPhoneClose = mViewManager.findView(R.id.rbPhoneClose);
        rbPhoneOpen = mViewManager.findView(R.id.rbPhoneOpen);
        allowStatus = Constant.userInfo.getAllowStutas();
        rgPhone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbPhoneClose){
                    allowStatus = "1";
                } else {
                    allowStatus = "0";
                }
            }
        });
        if (TextUtils.isEmpty(allowStatus) || "1".equals(allowStatus)){
            rbPhoneClose.setChecked(true);
        } else {
            rbPhoneOpen.setChecked(true);
        }

    }
    private void initOtherIdentity(){
        mScrollView = mViewManager.findView(R.id.scrollView);
        otherIdentityRecycler = mViewManager.findView(R.id.otherIdentityRecycler);

        otherIdentityRecycler.addContentLayout(R.layout.item_user_card_other_identity, new ItemViewConvert<OtherIdentityEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final OtherIdentityEntity mData, int position, @NonNull List<Object> payloads) {
                EditText userCompany = holder.findView(R.id.userCompany);
                EditText userPosition = holder.findView(R.id.userPosition);
                ImageView close = holder.findView(R.id.close);

                userCompany.setHint("请输入组织名称");
                userPosition.setHint("请输入职务");
                userCompany.setText(mData.getCompany());
                userPosition.setText(mData.getPosition());


                userCompany.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mData.setCompany(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                userPosition.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mData.setPosition(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否要删除当前身份？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                otherIdentityRecycler.delete(mData);
                            }
                        });

                    }
                });
            }

        }).create();
    }

    private void initSupplyRecycler(){
        supplyRecycler = mViewManager.findView(R.id.supplyRecycler);
        supplyRecycler.addContentLayout(R.layout.item_user_card_other_identity, new ItemViewConvert<SupplyEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final SupplyEntity mData, final int position, @NonNull List<Object> payloads) {
                EditText userCompany = holder.findView(R.id.userCompany);
                EditText userPosition = holder.findView(R.id.userPosition);
                ImageView close = holder.findView(R.id.close);

                userCompany.setFocusable(false);
                userCompany.setFocusableInTouchMode(false);
                userCompany.setHint("请选择供应对应的行业");
                userPosition.setHint("请输入供应内容");
                userCompany.setText(mData.getIndustryName());
                userPosition.setText(mData.getResources());

                userPosition.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mData.setResources(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                userCompany.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("industryType",1);
                        bundle.putInt("position",position);
                        startActivity(SelectIndustryActivity.class,bundle);
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否要删除当前供应？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                supplyRecycler.delete(mData);
                            }
                        });

                    }
                });
            }

        }).create();
    }
    private void initDemandRecycler(){
        demandRecycler = mViewManager.findView(R.id.demandRecycler);
        demandRecycler.addContentLayout(R.layout.item_user_card_other_identity, new ItemViewConvert<DemandEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final DemandEntity mData, final int position, @NonNull List<Object> payloads) {
                EditText userCompany = holder.findView(R.id.userCompany);
                EditText userPosition = holder.findView(R.id.userPosition);
                ImageView close = holder.findView(R.id.close);

                userCompany.setFocusable(false);
                userCompany.setFocusableInTouchMode(false);
                userCompany.setHint("请选择需求对应的行业");
                userPosition.setHint("请输入需求内容");
                userCompany.setText(mData.getIndustryName());
                userPosition.setText(mData.getResources());

                userPosition.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mData.setResources(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                userCompany.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("industryType",2);
                        bundle.putInt("position",position);
                        startActivity(SelectIndustryActivity.class,bundle);
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否要删除当前需求？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                demandRecycler.delete(mData);
                            }
                        });

                    }
                });
            }

        }).create();
    }
    /**
     * 增加一个其他身份
     * @param view
     */
    public void addOtherIdentity(View view) {
        otherIdentityRecycler.insert(new OtherIdentityEntity());
        mScrollView.smoothScrollTo(0,mScrollView.getLayoutParams().height);
    }

    /**
     * 添加需求
     * @param view
     */
    public void addSupply(View view) {
        supplyRecycler.insert(new SupplyEntity());
    }

    /**
     * 添加供应
     * @param view
     */
    public void addDemand(View view) {
        demandRecycler.insert(new DemandEntity());
    }

    public void save(){
//        if (industryDto == null && TextUtils.isEmpty(Constant.userInfo.getIndustryId())){
//            showToast("请选择所属行业");
//            return;
//        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setPortraitUrl(userPortraitPath);
        userInfoDto.setName(etUserName.getText().toString());
        userInfoDto.setCompany(etUserCompany.getText().toString());
        userInfoDto.setPosition(etUserPosition.getText().toString());
        userInfoDto.setCardPhone(etUserPhone.getText().toString());
        userInfoDto.setAllowStutas(TextUtils.isEmpty(allowStatus) ? "1" : allowStatus);
        userInfoDto.setIndustryId(industryDto == null ? Constant.userInfo.getIndustryId() : industryDto.getId());
        userInfoDto.setIndustryName(industryDto == null ? Constant.userInfo.getIndustryName() : industryDto.getName());
        userInfoDto.setDetailedAddress(etUserAddress.getText().toString());
        userInfoDto.setEmailAddress(etEmailAddress.getText().toString());
        userInfoDto.setPersonalizedSignature(etUserIntroduce.getText().toString());
        userInfoDto.setIdentityList(otherIdentityRecycler.getAllDatas());
        userInfoDto.setDemandList(demandRecycler.getAllDatas());
        userInfoDto.setProvideList(supplyRecycler.getAllDatas());


        if (userInfoDto.isEmpty()){
            if (TextUtils.isEmpty(userInfoDto.getName())){
                showToast("请编辑姓名");
            } else if (TextUtils.isEmpty(userInfoDto.getPosition())){
                showToast("请编辑职位");
            } else if (TextUtils.isEmpty(userInfoDto.getCompany())){
                showToast("请编辑公司");
            }
//            showToast("请补全用户信息");
        } else {
            getPresenter().saveUserInfo(userInfoDto);
        }
    }

    /**
     * 选择行业
     * @param view
     */
    public void selectIndustry(View view) {
        startActivity(SelectIndustryActivity.class);
    }

    /**
     * 选择行业结果
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectIndustryResult(SelectIndustryEvent event){
        switch (event.getIndustryType()){
            case 0://自己的行业
                industryDto = event.getIndustryDto();
                etUserIndustry.setText(industryDto.getName());
                break;
            case 1://供应的行业
                SupplyEntity entity = supplyRecycler.getAllDatas().get(event.getPosition());
                entity.setIndustryId(event.getIndustryDto().getId());
                entity.setIndustryName(event.getIndustryDto().getName());
                supplyRecycler.update(entity,event.getPosition(),null);
                break;
            case 2://需求的行业
                DemandEntity demandEntity = demandRecycler.getAllDatas().get(event.getPosition());
                demandEntity.setIndustryId(event.getIndustryDto().getId());
                demandEntity.setIndustryName(event.getIndustryDto().getName());
                demandRecycler.update(demandEntity,event.getPosition(),null);
                break;

        }

    }

    @Override
    public void saveUserInfoSuccess(UserInfoDto userInfoDto) {
        showToast("保存成功");
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCard",userInfoDto);
        bundle.putBoolean("isModify",true);
        if (fromPage == 0){
            startActivity(UserCardActivity.class,bundle);
        } else if (fromPage == 1){
            startActivity(UserNewCardDetailsActivity.class,bundle);
        }

        EventBusUtils.post(new RefreshPersonalInfoEvent());
    }

    @Override
    public void createUserCardSuccess(String token) {

    }

    /**
     * 选择用户头像
     * @param view
     */
    public void selectUserPortrait(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("editType",0);
        bundle.putString("portraitUrl",userPortraitPath);
        startActivity(EditUserInfoActivity.class,bundle);
    }

    /**
     * 接收设置的个人头像
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userPortraitSubscribe(UserInfoEvent event){
        int type = event.getInfoType();
        switch (type){
            case 0:
                userPortraitPath = event.getContent();
                GlideUtils.loadLocalImage(userPortrait,userPortraitPath);
                break;
        }

    }
}
