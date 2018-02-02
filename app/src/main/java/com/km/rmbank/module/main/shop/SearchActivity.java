package com.km.rmbank.module.main.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.model.SearchModel;
import com.km.rmbank.mvp.presenter.SearchPresenter;
import com.km.rmbank.mvp.view.ISearchView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<ISearchView,SearchPresenter> implements ISearchView {

    @BindView(R.id.et_search)
    EditText etSearch;
    private String searchContent= "";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_search;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(new SearchModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecyclerview();
    }


    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        final GoodsListShoppingAdapter adapter = new GoodsListShoppingAdapter(this);
        mRecyclerview.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                if (adapter.getNextPage() > 1 && !TextUtils.isEmpty(searchContent)){
                    getPresenter().getSearchGoods(adapter.getNextPage(),searchContent);
                }
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsDto>() {
            @Override
            public void onItemClick(GoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo",itemData.getProductNo());
                startActivity(GoodsActivity.class,bundle);
            }
        });
    }

    @Override
    public void showSearchResult(List<GoodsDto> goodsDtos, int pageNo) {
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) mRecyclerview.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }


    @OnClick(R.id.btn_search)
    public void search(View view){
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) mRecyclerview.getAdapter();
        adapter.clearAllData();
        searchContent = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchContent)){
            showToast("请输入搜索内容");
            return;
        }
        getPresenter().getSearchGoods(adapter.getNextPage(),searchContent);
    }

    @OnClick(R.id.iv_back)
    public void back(View view){
        finish();
    }

}
