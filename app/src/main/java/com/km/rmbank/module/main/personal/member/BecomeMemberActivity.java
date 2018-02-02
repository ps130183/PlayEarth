package com.km.rmbank.module.main.personal.member;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.MemberModel;
import com.km.rmbank.mvp.presenter.MemberPresenter;
import com.km.rmbank.mvp.view.IMemberView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.animator.AnimatorViewWrapper;
import com.km.rmbank.utils.animator.ShowViewAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BecomeMemberActivity extends BaseActivity<IMemberView,MemberPresenter> implements IMemberView {

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.memberRecycler)
    RecyclerView memberRecycler;
    private List<MemberDto> memberList;

    @BindView(R.id.rlDialog)
    RelativeLayout rlDialog;

    @BindView(R.id.rlMember)
    RelativeLayout rlMember;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_become_member;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public boolean statusBarTextColorIsDark() {
        return false;
    }

    @Override
    protected MemberPresenter createPresenter() {
        return new MemberPresenter(new MemberModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        SystemBarHelper.immersiveStatusBar(this);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(mInstance);
        Toolbar toolbar = mViewManager.findView(R.id.toolBar);
        toolbar.setPadding(0, statusBarHeight, 0, 0);
        toolbar.getLayoutParams().height = ConvertUtils.dp2px(48) + statusBarHeight;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewManager.setText(R.id.userName, Constant.userInfo.getName());
        webView.loadUrl(ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/memberExample");
        initRecycler();

        initDialog();
    }

    private void initRecycler(){
        memberList = new ArrayList<>();

        RecyclerAdapterHelper<MemberDto> mHelper = new RecyclerAdapterHelper<>(memberRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_member, memberList, new RecyclerAdapterHelper.CommonConvert<MemberDto>() {
            @Override
            public void convert(CommonViewHolder holder, MemberDto mData, final int position) {
                TextView memberName = holder.getTextView(R.id.memberName);
                TextView introduce = holder.getTextView(R.id.introduce);
                RadioButton rbtnCheck = holder.findView(R.id.rbtnCheck);

                memberName.setText(mData.getMemberName());
                introduce.setText(mData.getMemberRecommend());

                rbtnCheck.setChecked(mData.isChecked());

                rbtnCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            checkMember(position);
                        }
                    }
                });

            }
        }).create();
        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MemberDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, MemberDto data, int position) {
                checkMember(position);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, MemberDto data, int position) {
                return false;
            }
        });
        getPresenter().getMemberList();
    }

    private void checkMember(int position){
        for (int i = 0; i < memberList.size(); i++){
            MemberDto memberDto = memberList.get(i);
            memberDto.setChecked(false);
            if (i == position){
                memberDto.setChecked(true);
            }
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                memberRecycler.getAdapter().notifyDataSetChanged();
            }
        });

    }

    @Override
    public void showMemberList(List<MemberDto> memberDtos) {
        memberList.addAll(memberDtos);
        memberRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showPayOrderResult(PayOrderDto payOrderDto) {
        if (payOrderDto == null){
            showToast("获取支付订单失败，请重试");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrderDto",payOrderDto);
        bundle.putInt("payType",1);
        startActivity(PaymentActivity.class,bundle);
    }

    private AnimatorViewWrapper animatorViewWrapper;
    private int height;
    private void initDialog(){
        animatorViewWrapper = new AnimatorViewWrapper(rlMember);
        height = animatorViewWrapper.getHeight();
        rlDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
    }


    @OnClick(R.id.btn_open)
    public void clickOpne(View view){
        openDialog();
    }

    @OnClick(R.id.close)
    public void clickClose(View view){
        closeDialog();
    }
    private void openDialog(){
        ObjectAnimator animator = ObjectAnimator.ofInt(animatorViewWrapper,"height",0,height);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                rlDialog.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    private void closeDialog(){
        ObjectAnimator animator = ObjectAnimator.ofInt(animatorViewWrapper,"height",height,0);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rlDialog.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    /**
     * 获取选中的会员
     * @return
     */
    private MemberDto getCurCheckedMember(){
        MemberDto memberDto = null;

        for (MemberDto memberDto1 : memberList){
            if (memberDto1.isChecked()){
                memberDto = memberDto1;
            }
        }

        return memberDto;
    }

    /**
     * 立即开通会员
     * @param view
     */
    @OnClick(R.id.btn_become)
    public void become(View view){
        MemberDto memberDto = getCurCheckedMember();
        if (memberDto == null){
            showToast("请选择会员类型");
            return;
        }
        getPresenter().createPayOrder(memberDto.getMemberMoney(),memberDto.getMemberId());
    }



}
