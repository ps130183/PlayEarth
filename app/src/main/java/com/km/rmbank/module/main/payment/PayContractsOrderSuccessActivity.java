package com.km.rmbank.module.main.payment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.event.RefreshMyTeamDataEvent;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.contacts.MyTeamActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.EventBusUtils;

/**
 * 通讯录 绑定 支付成功
 */
public class PayContractsOrderSuccessActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_contracts_order_success;
    }

    @Override
    public String getTitleContent() {
        return "支付成功";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        int number = getIntent().getIntExtra("number",0);
        mViewManager.setText(R.id.hintContent,"已将 " + number + " 名用户绑定到您的名下，可在我的团队里进行查看~");

        TextView becomeMember = mViewManager.findView(R.id.becomeMember);
        if ("4".equals(Constant.userInfo.getRoleId())){
            becomeMember.setVisibility(View.VISIBLE);
        } else {
            becomeMember.setVisibility(View.GONE);
        }

        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                startActivity(MyTeamActivity.class);
                EventBusUtils.post(new RefreshMyTeamDataEvent());
                return true;
            }
        });
    }

    @Override
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyTeamActivity.class);
                EventBusUtils.post(new RefreshMyTeamDataEvent());
            }
        };
    }

    /**
     * 成为玩家合伙人
     * @param view
     */
    public void becomeMember(View view) {
        startActivity(BecomeMemberActivity.class);
    }
}
