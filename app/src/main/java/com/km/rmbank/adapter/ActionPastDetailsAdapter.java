package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/18.
 */

public class ActionPastDetailsAdapter extends BaseAdapter<ActionPastDto.DynamicBean> implements BaseAdapter.IAdapter<ActionPastDetailsAdapter.ViewHolder> {

    public ActionPastDetailsAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_action_past_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionPastDto.DynamicBean bean = getItemData(position);
        if (bean.getDynamicImageList() == null || bean.getDynamicImageList().size() == 0){
            holder.rvImage.setVisibility(View.GONE);
        } else {
            holder.rvImage.setVisibility(View.VISIBLE);
        }
        holder.adapter.addData(bean.getDynamicImageList());
        holder.tvContent.setText(bean.getDynamicImageContent());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.rv_image)
        RecyclerView rvImage;
        ImageTextAdapter adapter;

        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            initRv();
        }
        private void initRv(){
            RVUtils.setLinearLayoutManage(rvImage, LinearLayoutManager.VERTICAL);
            RVUtils.addDivideItemForRv(rvImage,RVUtils.DIVIDER_COLOR_WHITE);
            adapter = new ImageTextAdapter(mContext);
            rvImage.setAdapter(adapter);
        }
    }
}
