package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.RecommendPersonalDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.ImageTextIntroduceEntity;
import com.km.rmbank.mvp.model.RecommendPersonalModel;
import com.km.rmbank.mvp.presenter.RecommendPersonalPresenter;
import com.km.rmbank.mvp.view.IRecommendPersonalView;
import com.km.rmbank.utils.UmengShareUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.HeaderAndFooterWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ruffian.library.RTextView;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeRecommendFragment extends BaseFragment<IRecommendPersonalView,RecommendPersonalPresenter> implements IRecommendPersonalView {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private List<RecommendPersonalDto> recommendPersonalDtoList;
    private ViewPagerAdapter mAdapter;

    private int curPager = 1;
    private int curRecommend = -1;

    public static HomeRecommendFragment newInstance(Bundle bundle) {

        HomeRecommendFragment fragment = new HomeRecommendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected RecommendPersonalPresenter createPresenter() {
        return new RecommendPersonalPresenter(new RecommendPersonalModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        recommendPersonalDtoList = new ArrayList<>();
        initViewPager();
        getPresenter().getRecommendPersons(curPager);
    }



    private void initViewPager(){
        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int prePosition = 0;
            private int scrollOrien = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curRecommend = position;
                if (prePosition < 0){
                    scrollOrien = 0;
                } else if (position > prePosition){
                    scrollOrien = -1;
                } else {
                    scrollOrien = 1;
                }
                showCurRecommendFragment(scrollOrien);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden){
//            getPresenter().getRecommendPersons(1);
//        }
//    }

    @Override
    public void showRecommendPersons(List<RecommendPersonalDto> recommendPersonalDtos,int pageNo) {
        if (recommendPersonalDtos != null && recommendPersonalDtos.size() > 0){

            if (pageNo == 1){
                recommendPersonalDtoList.clear();
            }
            recommendPersonalDtoList.addAll(recommendPersonalDtos);
            mAdapter.notifyDataSetChanged();
            if (curRecommend < 0){
                curRecommend = 0;
            }
            mViewPager.setCurrentItem(curRecommend);
            showCurRecommendFragment(0);

//            notifyData(recommendPersonalDto);
        }

    }

    /**
     * 显示当前 推荐人的信息
     */
    private void showCurRecommendFragment(int scrollOrien){
        LogUtils.d("滑动方向 === " + scrollOrien);
        RecommendPersonalDto personalDto = recommendPersonalDtoList.get(curRecommend);

        RecommendPersonalDto prePerson = null;// = recommendPersonalDtoList.get(curRecommend + scrollOrien);
        if (curRecommend + scrollOrien >= 0 && curRecommend + scrollOrien < recommendPersonalDtoList.size()){
            prePerson = recommendPersonalDtoList.get(curRecommend + scrollOrien);
        }
        final TextView day = mViewManager.findView(R.id.tv_day);
        day.setText(prePerson == null ? "" : prePerson.getDay());
        day.setVisibility(View.INVISIBLE);

        final TextView nextDay = mViewManager.findView(R.id.next_day);
        nextDay.setText(personalDto.getDay());
        nextDay.setVisibility(View.GONE);
        mViewManager.setText(R.id.tv_month_year,personalDto.getYearMonth());
        mViewManager.setText(R.id.introduce,personalDto.getTitle());

        if (scrollOrien < 0){//向左滑
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(day,"translationY",0,-130);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(nextDay,"translationY",90,0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator1,animator2);
            animatorSet.setDuration(300);
            animatorSet.setStartDelay(200);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    nextDay.setTranslationY(0);
                    nextDay.setVisibility(View.GONE);
                    day.setTranslationY(0);
                    day.setText(nextDay.getText());
                }

                @Override
                public void onAnimationStart(Animator animation) {
                   day.setVisibility(View.VISIBLE);
                   nextDay.setTranslationY(90);
                   nextDay.setVisibility(View.VISIBLE);
                }
            });
            animatorSet.start();
        } else if (scrollOrien > 0){//向右滑
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(day,"translationY",0,90);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(nextDay,"translationY",-130,0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator1,animator2);
            animatorSet.setDuration(300);
            animatorSet.setStartDelay(200);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    nextDay.setTranslationY(0);
                    nextDay.setVisibility(View.GONE);
                    day.setTranslationY(0);
                    day.setText(nextDay.getText());
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    day.setVisibility(View.VISIBLE);
                    nextDay.setTranslationY(-130);
                    nextDay.setVisibility(View.VISIBLE);
                }
            });
            animatorSet.start();
        } else {//没有滑动
            day.setVisibility(View.VISIBLE);
        }

//        ObjectAnimator animator = ObjectAnimator.ofFloat(day,"translationY",50,0);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                day.setVisibility(View.VISIBLE);
//            }
//        });
//        animator.setDuration(300);
//        animator.setStartDelay(200);
//        animator.start();
    }

    @Override
    public void pariseResult(int curPager) {
        mAdapter.updateParise(curPager);
    }


    private class ViewPagerAdapter extends PagerAdapter{

        private SparseArray<RecommendPersonalDto> mPersons;
        private SparseArray<View> mViews;
        private SparseArray<RecyclerView> mRecyclers;

        public ViewPagerAdapter() {
            mPersons = new SparseArray<>();
            mViews = new SparseArray<>();
            mRecyclers = new SparseArray<>();
        }

        @Override
        public int getCount() {
            return recommendPersonalDtoList == null ? 0 : recommendPersonalDtoList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mViews.get(position);
            if (view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recommend_person,container,false);
                mViews.append(position,view);
                container.addView(view);
            }
            RecyclerView recommendRecycler = mRecyclers.get(position);
            if (recommendRecycler == null){
                recommendRecycler = view.findViewById(R.id.recommendRecycler);
                initRecycler(recommendRecycler,recommendPersonalDtoList.get(position),position);
                mRecyclers.append(position,recommendRecycler);
            }

            return view;
        }

        public void updateParise(int position){
            RecommendPersonalDto recommendPersonalDto = mPersons.get(position);
            String likeStatus = recommendPersonalDto.getLikeStatus();
            int likeCount = Integer.parseInt(recommendPersonalDto.getLikeCount());
            if (likeStatus.equals("0")) {//未点赞
                recommendPersonalDto.setLikeStatus("1");
                recommendPersonalDto.setLikeCount((likeCount + 1) + "");
            } else {
                recommendPersonalDto.setLikeStatus("0");
                recommendPersonalDto.setLikeCount((likeCount - 1) + "");
            }
            mRecyclers.get(position).getAdapter().notifyDataSetChanged();
            mViews.get(position).setTag(true);
//            notifyDataSetChanged();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
                mViews.delete(position);
                mRecyclers.delete(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            View view = (View) object;
            boolean isRefresh = view.getTag() == null ? false : (boolean) view.getTag();
            if (isRefresh == true){
                return PagerAdapter.POSITION_NONE;
            }

            if (recommendPersonalDtoList != null && recommendPersonalDtoList.size() == 0){
                return PagerAdapter.POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        private void initRecycler(RecyclerView recommendRecycler, final RecommendPersonalDto personalDto, final int curPager){

            mPersons.append(curPager,personalDto);
            List<RecommendPersonalDto.AtlasListBean> atlasListBeans = personalDto.getAtlasList();
            List<ImageTextIntroduceEntity> introduceEntities = new ArrayList<>();
            for (RecommendPersonalDto.AtlasListBean atlasListBean : atlasListBeans){
                introduceEntities.add(new ImageTextIntroduceEntity(atlasListBean.getContent(),atlasListBean.getImage()));
            }

            RecyclerAdapterHelper<ImageTextIntroduceEntity> mHelper = new RecyclerAdapterHelper<>(recommendRecycler);
            mHelper.addLinearLayoutManager().addCommonAdapter(R.layout.item_image_text_introduce, introduceEntities, new RecyclerAdapterHelper.CommonConvert<ImageTextIntroduceEntity>() {
                @Override
                public void convert(CommonViewHolder holder, ImageTextIntroduceEntity mData, int position) {
                    GlideImageView imageView =  holder.findView(R.id.image);
                    CircleProgressView progressView = holder.findView(R.id.progressView);
                    GlideUtils.loadImageFitHeight(imageView,mData.getImageUrl(),progressView);

                    holder.setText(R.id.content,mData.getContent());
                }
            }).addHeaderAndFooterWrapper(new Integer[]{R.layout.header_friends_recommend}, null, new HeaderAndFooterWrapper.LoadHeaderAndFooterData() {
                @Override
                public void loadHeaderData(CommonViewHolder holder, final int position) {
                    GlideUtils.loadImage(getContext(),personalDto.getBackImage(),holder.getImageView(R.id.backgroundImage));
                    RTextView tvPraise = holder.findView(R.id.tvPraise);
                    tvPraise.setText(personalDto.getLikeCount());
                    if (personalDto.getLikeStatus().equals("0")){
                        tvPraise.setIconNormal(getResources().getDrawable(R.mipmap.icon_home_friends_zan1));
                    } else {
                        tvPraise.setIconNormal(getResources().getDrawable(R.mipmap.icon_home_friends_zan2));
                    }
                    tvPraise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPresenter().pariseRecommendPerson(personalDto.getId(),curPager);
                        }
                    });

                    holder.setText(R.id.userName,personalDto.getPersonName() + " 丨 " + personalDto.getPersonIntroduce());

                    holder.findView(R.id.ivShare).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShareDto shareDto = new ShareDto();
                            shareDto.setTitle(personalDto.getPersonName());
                            shareDto.setContent(personalDto.getPersonIntroduce() + "邀请您点赞！！！");
                            shareDto.setSharePicUrl(personalDto.getAtlasList().get(0).getImage());
                            shareDto.setPageUrl(personalDto.getShareUrl());
                            share(shareDto);
                        }
                    });
                }

                @Override
                public void loadFooterData(CommonViewHolder holder, int position) {

                }
            }).create();

        }

        private void share(ShareDto shareDto){
            UmengShareUtils.openShare(getActivity(), shareDto, new UMShareListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onResult(SHARE_MEDIA share_media) {

                }

                @Override
                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                    LogUtils.d(throwable.getMessage());
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media) {

                }
            });
        }
    }
}
