package com.km.rmbank.delegate;

import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ForumInfoDto;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.base.ItemViewDelegate;

/**
 * Created by PengSong on 18/1/25.
 */

public class ForumHeaderDelegate implements ItemViewDelegate<String> {

    private TextView tvLikeNumber;
    private TextView tvCommentNumber;
    private TextView tvForumNumber;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.delegate_my_forum_info_top;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return "0".equals(item);
    }

    @Override
    public void convert(CommonViewHolder holder, String s, int position) {
        tvLikeNumber = holder.getTextView(R.id.tv_like_number);
        tvCommentNumber = holder.getTextView(R.id.tv_comment_number);
        tvForumNumber = holder.getTextView(R.id.tv_forum_number);
    }

    public void setData(ForumInfoDto forumInfoDto){
        tvLikeNumber.setText(forumInfoDto.getPraises() + "");
        tvCommentNumber.setText(forumInfoDto.getCommentsNumber()+"");
        tvForumNumber.setText(forumInfoDto.getPosts()+"");
    }
}
