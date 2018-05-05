package com.km.rmbank.module.realname;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.utils.Constant;

public class CertifyCheckActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_certify_check;
    }

    @Override
    public String getTitleContent() {
        return "实名认证";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                startActivity(HomeActivity.class);
                return true;
            }
        });

        TextView becomeMember = mViewManager.findView(R.id.becomeMember);
        if ("4".equals(Constant.userInfo.getRoleId())){
            becomeMember.setVisibility(View.VISIBLE);
        } else {
            becomeMember.setVisibility(View.GONE);
        }
    }

    @Override
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HomeActivity.class);
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
