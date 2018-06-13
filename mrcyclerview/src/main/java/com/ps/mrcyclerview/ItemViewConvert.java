package com.ps.mrcyclerview;

import android.support.annotation.NonNull;

/**
 * Created by PengSong on 18/6/4.
 */

public interface ItemViewConvert<D> {
    void convert(@NonNull BViewHolder holder,D mData, int position);
}
