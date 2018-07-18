package com.km.rmbank.module.main.personal.member;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.module.realname.CertifyCheckActivity;
import com.km.rmbank.module.realname.CertifyIdCardSuccessActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.mvp.model.MemberModel;
import com.km.rmbank.mvp.presenter.MemberPresenter;
import com.km.rmbank.mvp.view.IMemberView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.dialog.DialogUtils;
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

    @BindView(R.id.certifyIDCard)
    TextView certifyIDCard;
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

        if (Constant.userInfo.getStatus() == 2){
            certifyIDCard.setText("您已实名认证，点击查看 >");
            certifyIDCard.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            certifyIDCard.setGravity(Gravity.CENTER);
        }
    }


    private void initViewPager(final List<MemberDto> memberDtos){
        this.mMemberDtos = memberDtos;
        //当前用户的会员级别
        int curUserRolePostion = 0;
        List<Fragment> fragmentList = new ArrayList<>();
        String curUserRoleId = Constant.userInfo.getRoleId();
        for (int i = 0; i < memberDtos.size(); i++){
            MemberDto memberDto = memberDtos.get(i);
            Bundle bundle = new Bundle();
            if (curUserRoleId.equals(memberDto.getMemberId())){
                curUserRolePostion = i;
                bundle.putString("openRoleId",memberDto.getMemberId());
            }
            bundle.putString("imageRes",memberDto.getMemberImage());
            fragmentList.add(MemberTypeFragment.newInstance(bundle));
        }

        ViewPager viewPager = mViewManager.findView(R.id.viewPager);
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true,new ScaleInTransformer());
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getSupportFragmentManager(),fragmentList,null);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(curUserRolePostion);

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
        showMemberIntroduce(curUserRolePostion);
    }

    private void showMemberIntroduce(int position){
        if (mMemberDtos == null){
            return;
        }
        curMemberPosition = position;
        MemberDto memberDto = mMemberDtos.get(position);
        memberIntroduce.loadData(memberDto.getSmemberRecommend(),"text/html; charset=UTF-8", null);

        switch (memberDto.getMemberId()){
            case "1"://俱乐部合伙人
                if ("1".equals(Constant.userInfo.getRoleId())){//俱乐部合伙人
                    btnConfirm.setText("您已是俱乐部合伙人");
                    btnConfirm.setEnabled(false);
                } else {
                    btnConfirm.setText("联系客服");
                    btnConfirm.setEnabled(true);
                }
                break;
            case "2"://玩家合伙人
                if ("1".equals(Constant.userInfo.getRoleId())){//俱乐部合伙人
                    btnConfirm.setText("您已是俱乐部合伙人");
                    btnConfirm.setEnabled(false);
                } else if ("2".equals(Constant.userInfo.getRoleId())){//玩家合伙人
                    btnConfirm.setText("您已是玩家合伙人");
                    btnConfirm.setEnabled(false);
                } else if ("4".equals(Constant.userInfo.getRoleId())) {//普通用户
                    btnConfirm.setText("立即开通");
                    btnConfirm.setEnabled(true);
                }
                break;
        }
//        if ("1".equals(Constant.userInfo.getRoleId())){//俱乐部合伙人
//            btnConfirm.setText("您已开通俱乐部合伙人");
//            btnConfirm.setEnabled(false);
//        } else if ("2".equals(Constant.userInfo.getRoleId())){//玩家合伙人
//            btnConfirm.setText("您已开通玩家合伙人");
//            btnConfirm.setEnabled(false);
//        } else if ("4".equals(Constant.userInfo.getRoleId())) {//普通用户
//            btnConfirm.setText("立即开通");
//            btnConfirm.setEnabled(true);
//        }
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
        if (Constant.userInfo.getStatus() == 0 || Constant.userInfo.getStatus() == 3){
            startActivity(CertifyRulesActivity.class);
        } else if (Constant.userInfo.getStatus() == 1){
            startActivity(CertifyCheckActivity.class);
        }else {
            startActivity(CertifyIdCardSuccessActivity.class);
        }

    }

    /**
     * 立即开通
     * @param view
     */
    public void confirmToPay(View view) {
        String content = btnConfirm.getText().toString();
        if ("联系客服".equals(content)){
            DialogUtils.showDefaultAlertDialog("是否拨打客服电话："+ Constant.SERVICE_PHONE +"?", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + Constant.SERVICE_PHONE);
                    intent.setData(data);
                    startActivity(intent);
                }
            });
        } else {
            MemberDto memberDto = mMemberDtos.get(curMemberPosition);
            getPresenter().createPayOrder(memberDto.getMemberMoney(),memberDto.getMemberId());
        }

    }
}
