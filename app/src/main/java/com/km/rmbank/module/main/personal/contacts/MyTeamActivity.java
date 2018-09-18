package com.km.rmbank.module.main.personal.contacts;

import android.content.Context;
import android.os.Bundle;
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
import com.km.rmbank.adapter.TeamAdapter;
import com.km.rmbank.adapter.TeamSubItem;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.event.RefreshMyTeamDataEvent;
import com.km.rmbank.module.main.card.RecommendUserCardActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.mvp.model.MyTeamModel;
import com.km.rmbank.mvp.presenter.MyTeamPresenter;
import com.km.rmbank.mvp.view.IMyTeamView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTeamActivity extends BaseActivity<IMyTeamView,MyTeamPresenter> implements IMyTeamView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private TeamAdapter mTeamAdapter;
    private List<Object> teamDtoList;
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
        getPresenter().getMyTeamData();
    }


    public void initRecycler() {
        if (teamDtoList == null){
            teamDtoList = new ArrayList<>();
        }
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        mTeamAdapter = new TeamAdapter(teamDtoList);
        mRecyclerView.setAdapter(mTeamAdapter);
        mTeamAdapter.setOnClickSubListener(new TeamSubItem.OnClickSubListener() {
            @Override
            public void onClick(Object model) {
                MyTeamDto.MemberDtoListBean memberDtoListBean = (MyTeamDto.MemberDtoListBean) model;
                getPresenter().getUserCardById(memberDtoListBean.getId());
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshMyTeamDataEvent event){
        getPresenter().getMyTeamData();
    }

    @Override
    public void showMyTeam(final List<MyTeamDto> teamDtoList) {
        this.teamDtoList.addAll(teamDtoList);
        mTeamAdapter.updateData(this.teamDtoList);
    }

    @Override
    public void showUserCard(UserInfoDto cardDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCard",cardDto);
        startActivity(RecommendUserCardActivity.class,bundle);
    }

    @Override
    public void showContracts(List<ContractDto> contractDtos, List<ContractDto> linkManDtos) {
        String numbers = linkManDtos.size() + "";

    }


    /**
     * 打开联系人通讯录列表
     * @param view
     */
    @OnClick(R.id.myContact)
    public void openContactList(View view){
        startActivity(ContactsActivity.class);
    }

    /**
     * 打开 联系人列表
     * @param view
     */
    public void openContractList(View view) {
        if (Constant.mUnBindingContractList == null){
            showToast("正在获取通讯录信息，请稍后...");
            return;
        }

        final String contentValue1 = "1.  我们将导入您的手机通讯录，您可以邀请通讯录的好友成为您的人脉！(短信0.1元/条)";
        final String contentValue2 = "2.  终身绑定需要您<font color='#3285ff'>实名认证</font>并<font color='#3285ff'>成为玩家合伙人</font>，否则您名下的用户随时可能会被其他玩家合伙人抢走哦！";
        if (Constant.userInfo.getStatus() == 0 || Constant.userInfo.getStatus() == 3) {
            StyledDialog.buildIosAlert("欢迎使用通讯录","", new MyDialogListener() {
                @Override
                public void onFirst() {
                    startActivity(ContactsActivity.class);
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
            startActivity(ContactsActivity.class);
        }
    }

}
