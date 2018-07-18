package com.km.rmbank.module.main.card.auto;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.event.UpdateEditContentEvent;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.EventBusUtils;

public class EditAutoTextActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_edit_auto_text;
    }

    @Override
    public String getTitleContent() {
        return "编辑文字";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("完成");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText content = mViewManager.findView(R.id.content);
                String mContent = content.getText().toString();
                if (TextUtils.isEmpty(mContent)){
                    showToast("编辑的内容不能是空的！");
                    return;
                }

                int editContentType = getIntent().getIntExtra("editContentType",-1);
                if (editContentType < 0){
                    return;
                }
                UpdateEditContentEvent event = new UpdateEditContentEvent(editContentType,mContent,getIntent().getIntExtra("position",-1));
                EventBusUtils.post(event);
                finish();
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        EditText content = mViewManager.findView(R.id.content);
        String mContent = getIntent().getStringExtra("content");
        content.setText(mContent);
    }
}
