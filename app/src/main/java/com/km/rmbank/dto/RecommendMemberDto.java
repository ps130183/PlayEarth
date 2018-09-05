package com.km.rmbank.dto;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/8/17.
 */

public class RecommendMemberDto implements ItemDelegate {
    @Override
    public int getItemViewRes() {
        return R.layout.item_home_recommend_more;
    }
}
