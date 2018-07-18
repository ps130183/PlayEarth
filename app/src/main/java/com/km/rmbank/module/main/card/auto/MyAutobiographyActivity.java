package com.km.rmbank.module.main.card.auto;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.main.card.auto.CreateMyAutobiographyActivity;
import com.km.rmbank.titleBar.SimpleTitleBar;

/**
 * 我的自传
 */
public class MyAutobiographyActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_autobiography;
    }

    @Override
    public String getTitleContent() {
        return "我的自传";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("编辑");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateMyAutobiographyActivity.class);
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initDialog();
    }

    private void initDialog(){
//        WindowCenterDialog hintDialog = new WindowCenterDialog(this, R.layout.dialog_autobiography_hint, new WindowCenterDialog.IViewConvert() {
//            @Override
//            public void convert(View view) {
//                int windowWidth = ScreenUtils.getScreenWidth(mInstance);
//                int windowHeight = ScreenUtils.getScreenHeight(mInstance);
//
////                FrameLayout content = view.findViewById(R.id.content);
////                content.getLayoutParams().height = windowHeight - ConvertUtils.dp2px(96);
////                ImageView imageView = view.findViewById(R.id.imageView);
////                imageView.getLayoutParams().height = windowHeight - ConvertUtils.dp2px(96);
////                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(windowWidth - ConvertUtils.dp2px(50),
////                        windowHeight - ConvertUtils.dp2px(96));
////                view.setLayoutParams(lp);
//            }
//        });
//        hintDialog.show();
    }
}
