package com.km.rmbank.module.main.book;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.BookVenueEntity;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

public class BookVenueTypeActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_book_venue_type;
    }

    @Override
    public String getTitleContent() {
        return "预定类型";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("下一步");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = getCheckVenue();
                if (type < 0){
                    showToast("请选择你需要的活动类型！");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("type",type);
                startActivity(SelectVenueSitActivity.class,bundle);
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        final MRecyclerView<BookVenueEntity> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_book_venue_type, new ItemViewConvert<BookVenueEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, BookVenueEntity mData, int position, @NonNull List<Object> payloads) {
                //刷新选中状态
                if (payloads.size() != 0){
                    CheckBox checkBox = holder.findView(R.id.checkbox);
                    if (payloads.size() > 0){
                        checkBox.setVisibility(mData.isChecked() ? View.VISIBLE : View.GONE);
                        checkBox.setChecked(mData.isChecked());
                    }
                    return;
                }

                GlideImageView ivVenue = holder.findView(R.id.iv_venue);
                GlideUtils.loadImageByRes(ivVenue,mData.getLogoRes());

                holder.setText(R.id.name,mData.getName());

            }
        }).create();

        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                BookVenueEntity entity = (BookVenueEntity) mData;
                int prePosition = 0;
                for (int i = 0; i < mRecyclerView.getAllDatas().size(); i++){
                    BookVenueEntity bookVenueEntity = mRecyclerView.getAllDatas().get(i);
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

        List<BookVenueEntity> bookVenueEntities = new ArrayList<>();
        bookVenueEntities.add(new BookVenueEntity(R.mipmap.icon_xiawucha,"下午茶"));
        bookVenueEntities.add(new BookVenueEntity(R.mipmap.icon_wanyan,"结缘晚宴"));
        bookVenueEntities.add(new BookVenueEntity(R.mipmap.icon_luyan,"路演大会"));
        mRecyclerView.loadDataOfNextPage(bookVenueEntities);
    }

    /**
     * 获取选中的活动类型
     * @return
     */
    private int getCheckVenue(){
        int type = -1;
        MRecyclerView<BookVenueEntity> mRecyclerView = mViewManager.findView(R.id.recyclerView);

        for (int i = 0; i < mRecyclerView.getAllDatas().size(); i++){
            BookVenueEntity entity = mRecyclerView.getAllDatas().get(i);
            if (entity.isChecked()){
                type = i + 1;
                break;
            }
        }
        return type;
    }
}
