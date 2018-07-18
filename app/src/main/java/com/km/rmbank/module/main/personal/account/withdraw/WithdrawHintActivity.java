package com.km.rmbank.module.main.personal.account.withdraw;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.personal.account.UserAccountActivity;

public class WithdrawHintActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_withdraw_hint;
    }

    @Override
    public String getTitleContent() {
        return "提现";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        String money = getIntent().getStringExtra("withdrawMoney");
        String hint = "提现<font color='#3285ff'>" + money + "</font>元审核中，请耐心等待";
        TextView withdrawHint = mViewManager.findView(R.id.hint);
        withdrawHint.setText(Html.fromHtml(hint));
    }

    /**
     * 查询余额
     * @param view
     */
    public void queryBalance(View view) {
        startActivity(UserAccountActivity.class);
    }
}
