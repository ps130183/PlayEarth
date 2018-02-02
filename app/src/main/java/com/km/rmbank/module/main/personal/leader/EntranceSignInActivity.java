package com.km.rmbank.module.main.personal.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.QRCodeUtils;

import butterknife.BindView;

public class EntranceSignInActivity extends BaseActivity {

    @BindView(R.id.iv_entrance_sign_in)
    ImageView ivEntranceSignIn;
    private ActionDto actionDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_entrance_sign_in;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        actionDto = getIntent().getParcelableExtra("actionDto");
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("入场签到");
        simpleTitleBar.setRightMenuContent("签到统计");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actionId = actionDto.getId();
                Bundle bundle = new Bundle();
                bundle.putString("actionId",actionId);
                startActivity(SignInListActivity.class,bundle);
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(actionDto.getCode())){
            ivEntranceSignIn.setImageBitmap(QRCodeUtils.createQRCode(this,actionDto.getCode()));
        }
    }
}
