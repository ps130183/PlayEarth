package com.km.rmbank.module.main.card;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.OtherIdentityEntity;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DialogUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserCardModifyActivity extends BaseActivity {

    private NestedScrollView mScrollView;
    private MRecyclerView<OtherIdentityEntity> otherIdentityRecycler;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_card_modify;
    }

    @Override
    public String getTitleContent() {
        return "名片修改";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar toolBar = (SimpleTitleBar) titleBar;
        toolBar.setRightMenuContent("保存");
        toolBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initOtherIdentity();
    }

    private void initOtherIdentity(){
        mScrollView = mViewManager.findView(R.id.scrollView);
        otherIdentityRecycler = mViewManager.findView(R.id.otherIdentityRecycler);

        otherIdentityRecycler.addContentLayout(R.layout.item_user_card_other_identity, new ItemViewConvert<OtherIdentityEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final OtherIdentityEntity mData, final int position) {
                EditText userCompany = holder.findView(R.id.userCompany);
                EditText userPosition = holder.findView(R.id.userPosition);
                ImageView close = holder.findView(R.id.close);
                userCompany.setText(mData.getCompany());
                userPosition.setText(mData.getPosition());


                userCompany.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mData.setCompany(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                userPosition.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mData.setPosition(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否要删除当前身份？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                otherIdentityRecycler.delete(mData);
                            }
                        });

                    }
                });
            }
        }).create();

        List<OtherIdentityEntity> otherIdentities = new ArrayList<>();
        otherIdentities.add(new OtherIdentityEntity());
        otherIdentityRecycler.update(otherIdentities);
    }

    /**
     * 增加一个其他身份
     * @param view
     */
    public void addOtherIdentity(View view) {
        otherIdentityRecycler.insert(new OtherIdentityEntity());
        mScrollView.smoothScrollTo(0,mScrollView.getLayoutParams().height);
    }

    public void save(){
        LogUtils.d(otherIdentityRecycler.getAllDatas().toString());
    }
}
