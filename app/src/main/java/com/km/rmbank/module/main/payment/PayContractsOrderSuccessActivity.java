package com.km.rmbank.module.main.payment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.personal.member.MyTeamActivity;

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

        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                startActivity(MyTeamActivity.class);
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
            }
        };
    }
}
