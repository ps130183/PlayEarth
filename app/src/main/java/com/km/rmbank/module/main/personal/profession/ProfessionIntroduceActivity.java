package com.km.rmbank.module.main.personal.profession;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.utils.Constant;
import com.yancy.gallerypick.utils.ScreenUtils;

public class ProfessionIntroduceActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_profession_introduce;
    }


    @Override
    public String getTitleContent() {
        return "职业认证";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        ImageView imageView = mViewManager.findView(R.id.imageView);
        imageView.getLayoutParams().height = ScreenUtils.getScreenWidth(mInstance) * 300 / 750 ;

        Button identification = mViewManager.findView(R.id.identification);
//        if (Constant.userInfo.getPositionStatus() == 2){
//            identification.setText("已认证");
//            identification.setEnabled(false);
//        }
    }

    /**
     * 立即认证
     * @param view
     */
    public void toIdentification(View view) {
        startActivity(ProfessionIdentificationActivity.class);
    }
}
