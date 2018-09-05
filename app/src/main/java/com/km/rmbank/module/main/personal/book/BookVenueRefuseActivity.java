package com.km.rmbank.module.main.personal.book;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.main.book.BookVenueTypeActivity;
import com.km.rmbank.titleBar.SimpleTitleBar;

public class BookVenueRefuseActivity extends BaseActivity {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_book_venue_refuse;
    }

    @Override
    public String getTitleContent() {
        return "拒绝原因";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("重新申请");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BookVenueTypeActivity.class);
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }
}
