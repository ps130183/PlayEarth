package com.km.rmbank.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/14.
 */

public class ForumImageAdapter extends BaseAdapter<String> implements BaseAdapter.IAdapter<ForumImageAdapter.ViewHolder> {

    public ForumImageAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_release_forum_image);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        String imageUrl = getItemData(position);
        if (!TextUtils.isEmpty(imageUrl)){
            GlideUtils.loadImage(mContext,imageUrl,holder.ivForumImage);
        } else {
            GlideUtils.loadImage(mContext, R.mipmap.ic_open_camera,holder.ivForumImage);
        }
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_forum_image)
        ImageView ivForumImage;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setImageUrl(String imageUrl,int position){
        List<String> mDatas = getAllData();
//        mDatas.get(position) = imageUrl;
        notifyItemChanged(position);
    }
}
