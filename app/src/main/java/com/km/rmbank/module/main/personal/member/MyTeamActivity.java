package com.km.rmbank.module.main.personal.member;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.adapter.SuperLvHolder;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.MyTeamParentAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.module.main.personal.contacts.ContactsActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.mvp.model.MyTeamModel;
import com.km.rmbank.mvp.presenter.MyTeamPresenter;
import com.km.rmbank.mvp.view.IMyTeamView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.ContractUtils;
import com.km.rmbank.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MyTeamActivity extends BaseActivity<IMyTeamView,MyTeamPresenter> implements IMyTeamView,MyTeamParentAdapter.onClickUserListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    //获取 当前手机通讯录中，系统已存在的用户列表 和  不存在的用户列表
    private List<ContractDto> mContractDtos,mLinkManDtos;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("我的人脉");
    }

    @Override
    protected MyTeamPresenter createPresenter() {
        return new MyTeamPresenter(new MyTeamModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContractDtos = null;
        mLinkManDtos = null;
        getPresenter().getMyTeamData();
        requestContractPremission();
    }

    public void initRecycler() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,5);
        MyTeamParentAdapter adapter = new MyTeamParentAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickUserListener(this);

    }


    /**
     * 请求使用权限
     */
    private void requestContractPremission() {
        String[] locationPermission = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS};
        PermissionGen.needPermission(this, 1, locationPermission);
    }

    @PermissionSuccess(requestCode = 1)
    public void getLocationPermissionSuccess() {
        getAllContracts();
    }

    /**
     * 获取所有的手机联系人信息
     */
    private void getAllContracts() {
        showLoading();
        Observable.just(1)
                .map(new Function<Integer, List<ContractDto>>() {
                    @Override
                    public List<ContractDto> apply(Integer integer) throws Exception {
                        return ContractUtils.getAllPhoneContracts(mInstance);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ContractDto>>() {
                    @Override
                    public void accept(List<ContractDto> personalDynamicDtos) throws Exception {
                        getPresenter().getContracts(personalDynamicDtos);
                    }
                });
    }

    @Override
    public void showMyTeam(List<MyTeamDto> teamEntities) {
        MyTeamParentAdapter adapter = (MyTeamParentAdapter) mRecyclerView.getAdapter();
        adapter.addData(teamEntities);
    }

    @Override
    public void showUserCard(UserInfoDto cardDto) {
        Bundle bundle = new Bundle();
//        bundle.putParcelable("memberDto",itemData);
        bundle.putParcelable("userCard",cardDto);
//        startActivity(UserCardActivity.class,bundle);
    }

    @Override
    public void hideLoading() {
        if (dialogLoading != null && mContractDtos != null){
            dialogLoading.hide();
        }
    }

    @Override
    public void showContracts(List<ContractDto> contractDtos, List<ContractDto> linkManDtos) {
        hideLoading();
        mContractDtos = contractDtos;
        mLinkManDtos = linkManDtos;
        String numbers = linkManDtos.size() + "";
        mViewManager.setText(R.id.linkContractNum,numbers);
    }


    @Override
    public void clickUser(MyTeamDto.MemberDtoListBean itemData, int position) {
        getPresenter().getUserCardById(itemData.getId());
//        showToast(itemData.toString());

    }


    /**
     * 打开 联系人列表
     * @param view
     */
    public void openContractList(View view) {
        if (mContractDtos == null || mLinkManDtos == null){
            showToast("正在获取通讯录信息，请稍后...");
            return;
        }
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("contractList", (ArrayList<? extends Parcelable>) mContractDtos);
        bundle.putParcelableArrayList("linkManList", (ArrayList<? extends Parcelable>) mLinkManDtos);

        final String contentValue1 = "1.  我们将导入您的手机通讯录，您可以邀请通讯录的好友成为您的人脉！(短信0.1元/条)";
        final String contentValue2 = "2.  终身绑定需要您<font color='#3285ff'>实名认证</font>并<font color='#3285ff'>成为玩家合伙人</font>，否则您名下的用户随时可能会被其他玩家合伙人抢走哦！";
        if (Constant.userInfo.getStatus() == 0) {
            StyledDialog.buildIosAlert("欢迎使用通讯录","", new MyDialogListener() {
                @Override
                public void onFirst() {
                    startActivity(ContactsActivity.class,bundle);
                }

                @Override
                public void onSecond() {
                    startActivity(CertifyRulesActivity.class);
                }
            }).setCustomContentHolder(new SuperLvHolder(mInstance) {
                @Override
                protected void findViews() {
                    TextView content = rootView.findViewById(R.id.content);
                    content.setText(Html.fromHtml(contentValue1));
                    TextView content2 = rootView.findViewById(R.id.content2);
                    content2.setText(Html.fromHtml(contentValue2));
                }

                @Override
                protected int setLayoutRes() {
                    return R.layout.dialog_certify_idcard_custom;
                }

                @Override
                public void assingDatasAndEvents(Context context, @Nullable Object o) {

                }
            })
                    .setBtnColor(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent)
                    .setBtnText("取消", "立即认证").show();

        } else {
            startActivity(ContactsActivity.class,bundle);
        }
    }


}
