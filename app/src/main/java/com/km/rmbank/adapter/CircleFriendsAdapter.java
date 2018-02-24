package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.dto.CircleFriendsDto;
import com.ps.commonadapter.adapter.CommonAdapter;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.base.ItemViewDelegate;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/1/17.
 */

public class CircleFriendsAdapter extends MultiItemTypeAdapter<CircleFriendsDto> {

    private OnClickCircleFriendsListener onClickCircleFriendsListener;

    public CircleFriendsAdapter(Context context, List<CircleFriendsDto> datas) {
        super(context, datas);
        addItemViewDelegate(new CircleFriendsItemDelegate());
    }

    private class CircleFriendsItemDelegate implements ItemViewDelegate<CircleFriendsDto> {

        private List<String> imagePaths;
        private List<CircleFriendsDto.CircleFriendsCommentDto> circleFriendsCommentDtos;

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_circle_friends;
        }

        @Override
        public boolean isForViewType(CircleFriendsDto item, int position) {
            return item != null;
        }

        @Override
        public void convert(CommonViewHolder holder, CircleFriendsDto itemData, int position) {
            showImageRecycler(holder, itemData);

            showUserInfo(holder, itemData, position);


            showCommentInfo(holder, itemData, position);
        }

        /**
         * 显示发布的图片
         *
         * @param holder
         * @param itemData
         */
        private void showImageRecycler(CommonViewHolder holder, final CircleFriendsDto itemData) {
            RecyclerView imagesContent = holder.getRecyclerView(R.id.rv_images_content);
            imagePaths = new ArrayList<>();
            CommonAdapter<String> forumImgAdapter = new CommonAdapter<String>(mContext, imagePaths, R.layout.item_circle_friends_img_content) {
                @Override
                public void convert(CommonViewHolder holder, String mData, int position) {
                    ImageView imageView = holder.findView(R.id.iv_forum_img);
//                    CircleProgressView progressView = holder.findView(R.id.progressView);
                    //计算imageView的宽度
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    int windowWidth = ScreenUtils.getScreenWidth();
                    int imageWidth = (windowWidth - ConvertUtils.dp2px(32 + 24)) / 3;
                    layoutParams.width = imageWidth;
                    layoutParams.height = imageWidth;

//                    FrameLayout.LayoutParams progressParams = (FrameLayout.LayoutParams) progressView.getLayoutParams();
//                    progressParams.width = imageWidth;
//                    progressParams.height = imageWidth;

                    GlideUtils.loadImage(mContext,mData,imageView);
//                    GlideUtils.loadImageOnPregress(imageView, mData, null);
                }
            };
            RecyclerAdapterHelper<String> imagesHelper = new RecyclerAdapterHelper<>(imagesContent);
            imagesHelper.addLinearLayoutManager(LinearLayoutManager.HORIZONTAL)
                    .addAdapter(forumImgAdapter)
                    .create();

            if (itemData.getForumImgContents() != null) {
                imagesContent.setVisibility(View.VISIBLE);
                final List<String> forumImgContents = itemData.getForumImgContents();
                //设置图片，限制数量 最多为3张
                if (itemData.getForumImgContents().size() > 0 && forumImgContents.size() <= 3) {
                    imagePaths.addAll(forumImgContents);
                } else if (forumImgContents.size() > 3) {
                    imagePaths.addAll(forumImgContents.subList(0, 3));
                }
                forumImgAdapter.notifyDataSetChanged();
                //查看图片
                forumImgAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
                    @Override
                    public void onItemClick(CommonViewHolder holder, String data, int position) {
                        if (onClickCircleFriendsListener != null) {
                            onClickCircleFriendsListener.clickImage((ArrayList<String>) itemData.getForumImgContents(), position);
                        }
                    }

                    @Override
                    public boolean onItemLongClick(CommonViewHolder holder, String data, int position) {
                        return false;
                    }

                });
            } else {
                imagesContent.setVisibility(View.GONE);
            }
        }

        /**
         * 显示用户的信息
         *
         * @param holder
         * @param itemData
         */
        private void showUserInfo(CommonViewHolder holder, final CircleFriendsDto itemData, final int position) {
            //个人信息
            GlideImageView userProtrait = holder.findView(R.id.iv_user_portrait);
            TextView userNickName = holder.findView(R.id.tv_user_nick_name);
            TextView releaseTime = holder.findView(R.id.tv_release_time);
            TextView title = holder.findView(R.id.tv_forum_title);
            TextView content = holder.findView(R.id.tv_forum_content);

            userProtrait.loadCircleImage(itemData.getPortraitUrl(), R.mipmap.icon_default_protrait);
//            GlideUtils.loadProtrait(mContext,itemData.getPortraitUrl(),userProtrait);
            userNickName.setText(itemData.getNickName());
            releaseTime.setText(itemData.getCreateTime());
            title.setText(itemData.getRuleTitle());
            if (!TextUtils.isEmpty(itemData.getRuleContent())) {
                content.setVisibility(View.VISIBLE);
                content.setText(itemData.getRuleContent());
            } else {
                content.setVisibility(View.GONE);
            }


            //设置点赞 图标
            ImageView ivPraise = holder.findView(R.id.iv_praise);
            ImageView ivComment = holder.findView(R.id.iv_comment);
//            Drawable drawable = null;
            if ("0".equals(itemData.getIsNotPraise())) {
                GlideUtils.loadImage(mContext, R.mipmap.icon_cf_item_like_unpress, ivPraise);
//                    drawable= mContext.getResources().getDrawable(R.mipmap.icon_cf_item_like_unpress);
            } else {
                GlideUtils.loadImage(mContext, R.mipmap.icon_cf_item_like_pressed, ivPraise);
//                    drawable= mContext.getResources().getDrawable(R.mipmap.icon_cf_item_like_pressed);
            }
            /// 这一步必须要做,否则不会显示.
            TextView praise = holder.findView(R.id.tv_praise);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                like.setCompoundDrawables(drawable,null,null,null);
            praise.setText("(" + itemData.getPraise() + ")");


            TextView comment = holder.findView(R.id.tv_comment);
            comment.setText("(" + itemData.getCommentsNumber() + ")");

            ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCircleFriendsListener != null) {
                        onClickCircleFriendsListener.clickPraise(itemData, position);
                    }
                }
            });
            praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCircleFriendsListener != null) {
                        onClickCircleFriendsListener.clickPraise(itemData, position);
                    }
                }
            });

            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCircleFriendsListener != null) {
                        onClickCircleFriendsListener.submitNewComment(itemData, position);
                    }
                }
            });
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCircleFriendsListener != null) {
                        onClickCircleFriendsListener.submitNewComment(itemData, position);
                    }
                }
            });
        }

        /**
         * 显示评论的信息
         *
         * @param holder
         * @param itemData
         */
        private void showCommentInfo(CommonViewHolder holder, final CircleFriendsDto itemData, final int position) {
            RecyclerView commentRecycler = holder.getRecyclerView(R.id.rv_forum_comment);
            circleFriendsCommentDtos = new ArrayList<>();
            CommonAdapter<CircleFriendsDto.CircleFriendsCommentDto> commentAdapter = new CommonAdapter<CircleFriendsDto.CircleFriendsCommentDto>(mContext, circleFriendsCommentDtos, R.layout.item_circle_friends_comment) {
                @Override
                public void convert(CommonViewHolder holder, CircleFriendsDto.CircleFriendsCommentDto mData, int position) {
                    String comment = "<font color=\'#0099cf\'>" + mData.getNickName() + "</font>: " + mData.getRuleCommentContent();
                    TextView commentView = holder.findView(R.id.tv_forum_comment);
                    commentView.setText(Html.fromHtml(comment));
                }
            };
            RecyclerAdapterHelper<CircleFriendsDto.CircleFriendsCommentDto> commentHelper = new RecyclerAdapterHelper<>(commentRecycler);
            commentHelper.addLinearLayoutManager()
                    .addAdapter(commentAdapter)
                    .create();

            if (itemData.getRuleCommentsList().size() == 0) {
                commentRecycler.setVisibility(View.GONE);
            } else {
                commentRecycler.setVisibility(View.VISIBLE);
                circleFriendsCommentDtos.addAll(itemData.getRuleCommentsList());
                commentAdapter.notifyDataSetChanged();
//                    holder.commentAdapter.addData(itemData.getRuleCommentsList(),1);
            }

            View lineBottom = holder.findView(R.id.view_line_bottom);
            TextView moreComment = holder.findView(R.id.tv_more_comment);

            if (itemData.getMoreForumCommentDtos() != null && itemData.getMoreForumCommentDtos().size() >= 0) {
                lineBottom.setVisibility(View.VISIBLE);
                moreComment.setVisibility(View.GONE);
                circleFriendsCommentDtos.addAll(itemData.getMoreForumCommentDtos());
                commentAdapter.notifyDataSetChanged();
//                    holder.commentAdapter.addData(itemData.getMoreForumCommentDtos(),2);
            } else if (itemData.getRuleCommentsList().size() == 0) {
                moreComment.setVisibility(View.GONE);
                lineBottom.setVisibility(View.GONE);
            } else if (itemData.getRuleCommentsList().size() != 0 && itemData.getCommentNumberStatus() == 0) {
                moreComment.setVisibility(View.GONE);
                lineBottom.setVisibility(View.VISIBLE);
            } else {
                moreComment.setVisibility(View.VISIBLE);
                lineBottom.setVisibility(View.VISIBLE);
            }

            moreComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCircleFriendsListener != null) {
                        onClickCircleFriendsListener.getMoreComment(itemData, position);
                    }
                }
            });
        }

    }


    public interface OnClickCircleFriendsListener {
        void clickImage(ArrayList<String> imagePats, int curPosition);

        void clickPraise(CircleFriendsDto itemData, int position);

        void submitNewComment(CircleFriendsDto itemData, int position);

        void getMoreComment(CircleFriendsDto itemData, int position);
    }

    public void setOnClickCircleFriendsListener(OnClickCircleFriendsListener onClickCircleFriendsListener) {
        this.onClickCircleFriendsListener = onClickCircleFriendsListener;
    }

}
