package com.km.rmbank.module.main.personal.member.goodsmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsTypeAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.event.GoodsTypeEvent;
import com.km.rmbank.module.main.shop.ShopActivity;
import com.km.rmbank.mvp.model.GoodsTypeModel;
import com.km.rmbank.mvp.presenter.GoodsTypePresenter;
import com.km.rmbank.mvp.view.IGoodsTypeView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.EventBusUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class GoodsTypeActivity extends BaseActivity<IGoodsTypeView, GoodsTypePresenter> implements IGoodsTypeView {

    private int[] backgroundRes = {R.drawable.shape_new_goods_select_goods_type_1,
            R.drawable.shape_new_goods_select_goods_type_2,
            R.drawable.shape_new_goods_select_goods_type_3,
            R.drawable.shape_new_goods_select_goods_type_4,
            R.drawable.shape_new_goods_select_goods_type_5,
            R.drawable.shape_new_goods_select_goods_type_6,
            R.drawable.shape_new_goods_select_goods_type_7};

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private HomeGoodsTypeDto goodsTypeDto;


    private boolean isTwoLevel = false;
    private boolean fromHome = false;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_goods_type;
    }

    @Override
    protected GoodsTypePresenter createPresenter() {
        return new GoodsTypePresenter(new GoodsTypeModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("商品类型");

        simpleTitleBar.setRightMenuContent("保存");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsTypeAdapter adapter = (GoodsTypeAdapter) mRecyclerView.getAdapter();
                HomeGoodsTypeDto goodsTypeDto = adapter.getCheckedGoodsType();
                List<HomeGoodsTypeDto> subGoodsTypeDtos = goodsTypeDto.getTypeList();
                if (goodsTypeDto.isEmpty()) {
                    showToast("请选择商品类型");
                    return;
                }

                EventBusUtils.post(new GoodsTypeEvent(GoodsTypeActivity.this.goodsTypeDto, goodsTypeDto));
                startActivity(CreateNewGoodsActivity.class);
            }
        });
    }



    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        goodsTypeDto = getIntent().getParcelableExtra("goodsTypeDto");
        initGoodsTypeView();

    }

    public void initGoodsTypeView() {
        RVUtils.setGridLayoutManage(mRecyclerView, 4);
//        RVUtils.addDivideItemForRv(mRecyclerView,0xffffffff);
        GoodsTypeAdapter adapter = new GoodsTypeAdapter(this);
        mRecyclerView.setAdapter(adapter);
        getPresenter().getGoodsType();
    }

    @Override
    public void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos) {
        GoodsTypeAdapter adapter = (GoodsTypeAdapter) mRecyclerView.getAdapter();
        adapter.addData(goodsTypeDtos);
        adapter.setDefaultChecked(goodsTypeDto);
    }
}
