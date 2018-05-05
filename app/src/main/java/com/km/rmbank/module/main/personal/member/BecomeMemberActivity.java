package com.km.rmbank.module.main.personal.member;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.mvp.model.MemberModel;
import com.km.rmbank.mvp.presenter.MemberPresenter;
import com.km.rmbank.mvp.view.IMemberView;
import com.km.rmbank.utils.Constant;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BecomeMemberActivity extends BaseActivity<IMemberView,MemberPresenter> implements IMemberView {

    @BindView(R.id.memberIntroduce)
    WebView memberIntroduce;
    private List<MemberDto> mMemberDtos;
    private int curMemberPosition = 0;

    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_become_member;
    }

    @Override
    public String getTitleContent() {
        return Constant.userInfo.getName();
    }

    @Override
    protected MemberPresenter createPresenter() {
        return new MemberPresenter(new MemberModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        getPresenter().getMemberList();
    }


    private void initViewPager(final List<MemberDto> memberDtos){
        this.mMemberDtos = memberDtos;
        List<Fragment> fragmentList = new ArrayList<>();

        for (MemberDto memberDto : memberDtos){
            Bundle bundle = new Bundle();
            if ("2".equals(memberDto.getMemberId())){
                bundle.putInt("imageRes",R.drawable.member_type_2);
            } else {
                bundle.putInt("imageRes",R.drawable.member_type_1);
            }
            fragmentList.add(MemberTypeFragment.newInstance(bundle));
        }

        ViewPager viewPager = mViewManager.findView(R.id.viewPager);
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true,new ScaleInTransformer());
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getSupportFragmentManager(),fragmentList,null);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showMemberIntroduce(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        showMemberIntroduce(0);
    }

    private void showMemberIntroduce(int position){
        if (mMemberDtos == null){
            return;
        }
        curMemberPosition = position;
        MemberDto memberDto = mMemberDtos.get(position);
        memberIntroduce.loadData(memberDto.getSmemberRecommend(),"text/html; charset=UTF-8", null);

        if ("1".equals(Constant.userInfo.getRoleId())){//俱乐部合伙人
            btnConfirm.setText("您已开通俱乐部合伙人");
            btnConfirm.setEnabled(false);
        } else if ("2".equals(Constant.userInfo.getRoleId())){//玩家合伙人
            btnConfirm.setText("您已开通玩家合伙人");
            btnConfirm.setEnabled(false);
        } else if ("4".equals(Constant.userInfo.getRoleId())) {//普通用户
            btnConfirm.setText("立即开通");
            btnConfirm.setEnabled(true);
        }
    }

    @Override
    public void showMemberList(List<MemberDto> memberDtos) {
        initViewPager(memberDtos);
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

    /**
     * 实名认证
     * @param view
     */
    public void certifyIDCard(View view) {
        startActivity(CertifyRulesActivity.class);
    }

    /**
     * 立即开通
     * @param view
     */
    public void confirmToPay(View view) {
        MemberDto memberDto = mMemberDtos.get(curMemberPosition);
        getPresenter().createPayOrder(memberDto.getMemberMoney(),memberDto.getMemberId());
    }
}
