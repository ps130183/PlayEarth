package com.ps.mrcyclerview.divider;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ps.mrcyclerview.IAdapter;
import com.ps.mrcyclerview.RecyclerAdapter;
import com.ps.mrcyclerview.SRecyclerAdapter;

/**
 * Created by PengSong on 18/6/9.
 */

public class LinearDividerItemDecoration extends Y_DividerItemDecoration {

    private int dividerWidth;
    private @ColorInt
    int dividerColor;
    private int orientation = LinearLayoutManager.VERTICAL;
    private RecyclerView mRecyclerView;

    public LinearDividerItemDecoration(Context context) {
        super(context);
    }

    public LinearDividerItemDecoration(RecyclerView recyclerView, int dividerWidth, int dividerColor) {
        super(recyclerView.getContext());
        mRecyclerView = recyclerView;
        this.dividerWidth = dividerWidth;
        this.dividerColor = dividerColor;
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.orientation = llm.getOrientation();
    }

    @Nullable
    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        IAdapter adapter = (IAdapter) mRecyclerView.getAdapter();
        if (adapter.isContentView(itemPosition)) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                divider = new Y_DividerBuilder()
                        .setBottomSideLine(dividerWidth != 0, dividerColor, dividerWidth, 0, 0)
                        .create();
            } else {
                divider = new Y_DividerBuilder()
                        .setRightSideLine(dividerWidth != 0, dividerColor, dividerWidth, 0, 0)
                        .create();
            }
        }

        return divider;
    }
}
