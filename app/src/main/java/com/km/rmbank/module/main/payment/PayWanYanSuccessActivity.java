package com.km.rmbank.module.main.payment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.module.main.personal.book.ReleaseActionActivity;
import com.km.rmbank.titleBar.SimpleTitleBar;

public class PayWanYanSuccessActivity extends BaseActivity {

    private BookVenueApplyDto mBookVenueApply;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_wan_yan_success;
    }

    @Override
    public String getTitleContent() {
        mBookVenueApply = getIntent().getParcelableExtra("VenueApplyDto");
        return mBookVenueApply.getPlaceTitle();
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("去发布");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("placeReservationId",mBookVenueApply.getId());
                startActivity(ReleaseActionActivity.class, bundle);
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }
}
