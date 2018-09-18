package com.km.rmbank.module.main.book;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.mvp.model.SelectVenueSitModel;
import com.km.rmbank.mvp.presenter.SelectVenueSitPresenter;
import com.km.rmbank.mvp.view.SelectVenueSitView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.List;

/**
 * 选择预定的地点
 */
public class SelectVenueSitActivity extends BaseActivity<SelectVenueSitView,SelectVenueSitPresenter> implements SelectVenueSitView {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_select_venue_sit;
    }

    @Override
    public String getTitleContent() {
        return "选择地点";
    }

    @Override
    protected SelectVenueSitPresenter createPresenter() {
        return new SelectVenueSitPresenter(new SelectVenueSitModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("下一步");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookVenueSitEntity sitEntity = getCheckSitId();
                if (sitEntity == null){
                    showToast("请选择一个场地");
                    return;
                }
                Bundle bundle = getIntent().getExtras();
                bundle.putInt("maxDays",sitEntity.getMaxDays());
                bundle.putString("placeId",sitEntity.getId());
                bundle.putString("price",sitEntity.getPrice());
                startActivity(SelectVenueTimeActivity.class,bundle);
            }
        });
    }


    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        final MRecyclerView<BookVenueSitEntity> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_select_venue_sit, new ItemViewConvert<BookVenueSitEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final BookVenueSitEntity mData, int position, @NonNull List<Object> payloads) {

                if (payloads.size() > 0){
                    ImageView status = holder.findView(R.id.status);
                    if (payloads.size() > 0){
                        status.setVisibility(mData.isChecked() ? View.VISIBLE : View.GONE);
                    }
                    return;
                }

                TextView details = holder.findView(R.id.details);
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = getIntent().getExtras();
                        bundle.putString("placeId",mData.getId());
                        bundle.putString("price",mData.getPrice());
                        startActivity(BookVenueSitDetailActivity.class,bundle);
                    }
                });

                holder.setText(R.id.venue_name,mData.getTitle());
                holder.setText(R.id.venue_intro,mData.getContent());

                GlideImageView venueLogo = holder.findView(R.id.venue_logo);
                GlideUtils.loadImageOnPregress(venueLogo,mData.getImageUrl(),null);

            }

        }).create();

        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                BookVenueSitEntity entity = (BookVenueSitEntity) mData;
                int prePosition = 0;
                for (int i = 0; i < mRecyclerView.getAllDatas().size(); i++){
                    BookVenueSitEntity bookVenueEntity = mRecyclerView.getAllDatas().get(i);
                    if (bookVenueEntity.isChecked()){
                        prePosition = i;
                        bookVenueEntity.setChecked(false);
                        break;
                    }

                }

                entity.setChecked(true);

                if (prePosition != position){
                    mRecyclerView.update(mRecyclerView.getAllDatas().get(prePosition),prePosition,"1");
                }

                mRecyclerView.update(entity,position,"1");
            }
        });


        int type = getIntent().getIntExtra("type",-1);
        if (type > 0){
            getPresenter().getBookVenueSitList(type);
        } else {
            showToast("没有获取到活动类型，请返回上一页重新选择！");
        }
    }

    @Override
    public void showBookVenueSitList(List<BookVenueSitEntity> bookVenueSitEntities) {
        MRecyclerView<BookVenueSitEntity> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.loadDataOfNextPage(bookVenueSitEntities);
    }

    /**
     * 获取选中的场地ID
     * @return
     */
    public BookVenueSitEntity getCheckSitId(){
        BookVenueSitEntity sitEntity = null;
        MRecyclerView<BookVenueSitEntity> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        for (BookVenueSitEntity entity : mRecyclerView.getAllDatas()){
            if (entity.isChecked()){
                sitEntity = entity;
                break;
            }
        }
        return sitEntity;
    }
}
