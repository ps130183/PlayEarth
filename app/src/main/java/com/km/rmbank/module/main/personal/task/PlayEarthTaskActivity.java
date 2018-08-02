package com.km.rmbank.module.main.personal.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.EarthTaskDetailsDto;
import com.km.rmbank.dto.EarthTaskDto;
import com.km.rmbank.dto.TaskSignInDto;
import com.km.rmbank.entity.EarthTaskEntity;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.module.main.card.UserCardActivity;
import com.km.rmbank.module.main.card.UserCardModifyActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.profession.ProfessionIntroduceActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.mvp.model.EarthTaskModel;
import com.km.rmbank.mvp.presenter.EarthTaskPresenter;
import com.km.rmbank.mvp.view.EarthTaskView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.dialog.WindowCenterDialog;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

public class PlayEarthTaskActivity extends BaseActivity<EarthTaskView,EarthTaskPresenter> implements EarthTaskView {

    private List<EarthTaskDto> earthTaskDtosList;

    private MRecyclerView<EarthTaskEntity> edRecycler;
    private MRecyclerView<EarthTaskEntity> oRecycler;
    private List<EarthTaskEntity> edTaskEntities;
    private List<EarthTaskEntity> oTaskEntities;
    private String[] edTaskMainContents = {"每日签到","分享活动/资讯","名片分享","参加活动","邀请好友加入"};
    private String[] edTaskSubContents = {"签到","分享活动或资讯给好友","分享名片给好友发朋友圈","报名参加俱乐部、平台活动等",
            "成功邀请新的好友加入玩转地球"};
    private int[] edCurTimes = {0,0,0,0,0};
    private int[] edMaxTimes = {1,10,10,1,1};
    private int[] edPerAddNumber = {2,2,2,10,20};
    private String[] edButtonNames = {"签到","去分享","去分享","去报名","去邀请"};


    private String[] oTaskMainContents = {"完善资料","实名认证","职业认证","成为玩家"};
    private String[] oTaskSubContents = {"完善个人信息","完成实名认证","完成公司职位认证","成为玩转地球玩家合伙人"};
    private int[] oCurTimes = {0,0,0,0};
    private int[] oMaxTimes = {1,1,1,1};
    private int[] oPerAddNumber = {10,10,10,20};
    private String[] oButtonNames = {"去完善","去认证","去认证","去完成"};

    //签到成功弹出框
    private WindowCenterDialog mSignInDialog;
    private TextView tvRunningDays;
    private TextView tvAddNumber;
    private ImageView ivClose;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_play_earth_task;
    }


    @Override
    public String getTitleContent() {
        return "赚球票";
    }

    @Override
    protected EarthTaskPresenter createPresenter() {
        return new EarthTaskPresenter(new EarthTaskModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initView();
        initSignInDialog();
        getPresenter().getEarthTaskList();
    }

    private void initView(){
        mViewManager.setText(R.id.totalNumber, Constant.userInfo.getBallWritTotal()+"");
        if (edTaskEntities == null){
            edTaskEntities = new ArrayList<>();
        }
        if (oTaskEntities == null){
            oTaskEntities = new ArrayList<>();
        }

        for (int i = 0; i < edTaskMainContents.length; i++){
            edTaskEntities.add(new EarthTaskEntity(edTaskMainContents[i],edTaskSubContents[i],edPerAddNumber[i],
                    edCurTimes[i],edMaxTimes[i],edButtonNames[i]));
        }

        for (int i = 0; i < oTaskMainContents.length; i++){
            oTaskEntities.add(new EarthTaskEntity(oTaskMainContents[i],oTaskSubContents[i],oPerAddNumber[i],
                    oCurTimes[i],oMaxTimes[i],oButtonNames[i]));
        }

        edRecycler = mViewManager.findView(R.id.everyDayTaskRecycler);
        edRecycler.addContentLayout(R.layout.item_play_earth_task, new ItemViewConvert<EarthTaskEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, EarthTaskEntity mData, int position, @NonNull List<Object> payloads) {
                showToWindow(holder, mData, position, payloads);
            }
        }).create();
        edRecycler.loadDataOfNextPage(edTaskEntities);


        edRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                EarthTaskEntity entity = (EarthTaskEntity) mData;
                Bundle bundle = new Bundle();
                switch (position){
                    case 0://签到
                        if (entity.getCurTimes() < entity.getMaxTimes()){
                            signIn(entity,position);
                        }
                        break;
                    case 1://分享活动
                        bundle.putInt("position",1);
                        startActivity(HomeActivity.class,bundle);
                        break;
                    case 2://分享名片
                        startActivity(UserCardActivity.class);
                        break;
                    case 3://参加活动
                        bundle.putInt("position",1);
                        startActivity(HomeActivity.class,bundle);
                        break;
                    case 4://邀请好友
                        startActivity(UserCardActivity.class);
                        break;
                }
            }
        });

        oRecycler = mViewManager.findView(R.id.oneTaskRecycler);
        oRecycler.addContentLayout(R.layout.item_play_earth_task, new ItemViewConvert<EarthTaskEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, EarthTaskEntity mData, int position, @NonNull List<Object> payloads) {
                showToWindow(holder, mData, position, payloads);
            }
        }).create();
        oRecycler.loadDataOfNextPage(oTaskEntities);

        oRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                switch (position){
                    case 0://完善资料
                        startActivity(UserCardModifyActivity.class);
                        break;
                    case 1://实名认证
                        startActivity(CertifyRulesActivity.class);
                        break;
                    case 2://职业认证
                        startActivity(ProfessionIntroduceActivity.class);
                        break;
                    case 3://成为玩家合伙人
                        startActivity(BecomeMemberActivity.class);
                        break;
                }
            }
        });

    }

    /**
     * 显示数据
     * @param holder
     * @param mData
     * @param position
     * @param payloads
     */
    private void showToWindow(@NonNull BViewHolder holder, EarthTaskEntity mData, int position, @NonNull List<Object> payloads){
        holder.setText(R.id.mainContent,mData.getMainContent());
        holder.setText(R.id.subContent,mData.getSubContent() + " " + mData.getCurTimes() + " / " + mData.getMaxTimes());
        holder.setText(R.id.perAddNumber,"+" + mData.getPerAddNumber() + "球票");
        holder.setText(R.id.button,mData.getButton());

        TextView button = holder.findView(R.id.button);
        if (mData.isFinished()){
            button.setText("已完成");
            button.setTextColor(0xffc4c7d5);
            button.setBackgroundColor(0x00000000);
        } else {
            button.setText(mData.getButton());
            button.setTextColor(0xffffffff);
            button.setBackgroundResource(R.drawable.shape_btn_shadow_blue_circular_bead);
        }
    }

    /**
     * 初始化签到弹出框
     */
    private void initSignInDialog(){
        mSignInDialog = new WindowCenterDialog(mInstance, R.layout.dialog_sign_in, new WindowCenterDialog.IViewConvert() {
            @Override
            public void convert(View view) {
                tvRunningDays = view.findViewById(R.id.runningDays);
                tvAddNumber = view.findViewById(R.id.addNumber);
                tvAddNumber.setText("+" + edTaskEntities.get(0).getPerAddNumber());
                ivClose = view.findViewById(R.id.close);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSignInDialog.dissmis();
                    }
                });
            }
        });
    }

    /**
     * 签到
     * @param entity
     */
    private void signIn(EarthTaskEntity entity, int position){
//        tvAddNumber.setText(entity.getPerAddNumber());
        getPresenter().signIn();
    }

    /**
     * 打开球票明细
     * @param view
     */
    public void openEarthTaskDetails(View view) {
        startActivity(EarthTaskDetailsActivity.class);
    }

    @Override
    public void showEarthTaskList(List<EarthTaskDto> earthTaskEntities) {
        if (earthTaskDtosList == null){
            earthTaskDtosList = new ArrayList<>();
        }
        earthTaskDtosList.clear();
        earthTaskDtosList.addAll(earthTaskEntities);

        for (EarthTaskDto earthTaskDto : earthTaskDtosList){
            EarthTaskEntity entity;
            switch (earthTaskDto.getId()){
                case "1":
                    refreshEdRecycler(0,earthTaskDto);
                    break;
                case "2":
                    refreshEdRecycler(1,earthTaskDto);
                    break;
                case "3":
                    refreshEdRecycler(2,earthTaskDto);
                    break;
                case "4":
                    refreshEdRecycler(3,earthTaskDto);
                    break;
                case "5":
                    refreshEdRecycler(4,earthTaskDto);
                    break;
                case "6":
                    refreshORecycler(0,earthTaskDto);
                    break;
                case "7":
                    refreshORecycler(1,earthTaskDto);
                    break;
                case "8":
                    refreshORecycler(2,earthTaskDto);
                    break;
                case "9":
                    refreshORecycler(3,earthTaskDto);
                    break;
            }
        }
    }

    @Override
    public void signInResult(TaskSignInDto signInDto) {
        tvRunningDays.setText(signInDto.getSignDays());
        tvAddNumber.setText("+" + signInDto.getValue());
        mSignInDialog.show();
        Constant.userInfo.setBallWritTotal(Constant.userInfo.getBallWritTotal() + Integer.parseInt(signInDto.getValue()));
        mViewManager.setText(R.id.totalNumber, Constant.userInfo.getBallWritTotal()+"");
        getPresenter().getEarthTaskList();
    }

    @Override
    public void showEarthTaskDetailList(List<EarthTaskDetailsDto> taskDetailsDtos) {

    }

    private void refreshEdRecycler(int position,EarthTaskDto earthTaskDto){
        EarthTaskEntity entity = edRecycler.getAllDatas().get(position);
        entity.setCurTimes(Integer.parseInt(earthTaskDto.getShareNumber()));
        entity.setMaxTimes(Integer.parseInt(earthTaskDto.getNumber()));
        entity.setPerAddNumber(Integer.parseInt(earthTaskDto.getValue()));
        entity.setMainContent(earthTaskDto.getTitle());
        entity.setSubContent(earthTaskDto.getDescribe());
        edRecycler.update(entity,position,null);
    }

    private void refreshORecycler(int position,EarthTaskDto earthTaskDto){
        EarthTaskEntity entity = oRecycler.getAllDatas().get(position);
        entity.setCurTimes(Integer.parseInt(earthTaskDto.getShareNumber()));
        entity.setMaxTimes(Integer.parseInt(earthTaskDto.getNumber()));
        entity.setPerAddNumber(Integer.parseInt(earthTaskDto.getValue()));
        entity.setMainContent(earthTaskDto.getTitle());
        entity.setSubContent(earthTaskDto.getDescribe());
        oRecycler.update(entity,position,null);
    }
}
