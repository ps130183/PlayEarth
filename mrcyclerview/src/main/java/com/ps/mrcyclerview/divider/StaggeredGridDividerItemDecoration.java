package com.ps.mrcyclerview.divider;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.ps.mrcyclerview.IAdapter;
import com.ps.mrcyclerview.RecyclerAdapter;

/**
 * Created by PengSong on 18/6/9.
 */

public class StaggeredGridDividerItemDecoration extends Y_DividerItemDecoration {

    private int dividerWidth;
    private @ColorInt
    int dividerColor;
    private RecyclerView mRecyclerView;

    public StaggeredGridDividerItemDecoration(Context context) {
        super(context);
    }

    public StaggeredGridDividerItemDecoration(RecyclerView recyclerView, int dividerWidth, int dividerColor) {
        super(recyclerView.getContext());
        mRecyclerView = recyclerView;
        this.dividerWidth = dividerWidth;
        this.dividerColor = dividerColor;
    }

    @Nullable
    @Override
    public Y_Divider getDivider(int itemPosition) {
        IAdapter adapter = (IAdapter) mRecyclerView.getAdapter();
        if (adapter.isContentView(itemPosition)){
            return new Y_DividerBuilder()
                    .setLeftSideLine(true, dividerColor, dividerWidth, 0, 0)
                    .setTopSideLine(true, dividerColor, dividerWidth, 0, 0)
                    .setRightSideLine(true, dividerColor, dividerWidth, 0, 0)
                    .setBottomSideLine(true, dividerColor, dividerWidth, 0, 0)
                    .create();
        }

        return null;

    }
}
