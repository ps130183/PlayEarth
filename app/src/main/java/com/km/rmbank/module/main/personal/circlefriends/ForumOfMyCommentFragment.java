package com.km.rmbank.module.main.personal.circlefriends;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.adapter.CircleFriendsAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.event.MyForumCommentEvent;
import com.km.rmbank.module.main.fragment.home.ShowBigImageActivity;
import com.km.rmbank.mvp.model.CircleFriendsModel;
import com.km.rmbank.mvp.presenter.CircleFriendsPresenter;
import com.km.rmbank.mvp.view.ICircleFriendsView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.EventBusUtils;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumOfMyCommentFragment extends BaseFragment<ICircleFriendsView,CircleFriendsPresenter> implements ICircleFriendsView {
    @BindView(R.id.rv_forum)
    RecyclerView circleFriendsRecycler;

    private List<CircleFriendsDto> circleFriendsDtos;
    private RecyclerAdapterHelper<CircleFriendsDto> mHelper;

    private int mConfirmCommentPosition = 0;

    public static ForumOfMyCommentFragment newInstance(Bundle bundle) {
        ForumOfMyCommentFragment fragment = new ForumOfMyCommentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected CircleFriendsPresenter createPresenter() {
        return new CircleFriendsPresenter(new CircleFriendsModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my_forumsk;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    /**
     * 初始化列表
     */
    private void initRecycler(){
        circleFriendsDtos = new ArrayList<>();
        circleFriendsRecycler.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                if (getChildCount() > 0) {
                    View view = recycler.getViewForPosition(0);
                    if(view != null){
                        measureChild(view, widthSpec, heightSpec);
                        int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                        int measuredHeight = view.getMeasuredHeight();
                        setMeasuredDimension(measuredWidth, measuredHeight);
                    }
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
        CircleFriendsAdapter adapter = new CircleFriendsAdapter(getContext(),circleFriendsDtos);
        mHelper = new RecyclerAdapterHelper<>(circleFriendsRecycler);
        mHelper.addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addAdapter(adapter)
                .addEmptyWrapper(null)
//                .addRefreshView(mXRefreshView)
//                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
//                    @Override
//                    public void refresh() {
//                        getPresenter().loadForumAllData(1,null,1);
//                    }
//                })
                .addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                        getPresenter().loadForumAllData(nextPage,wrapper,2);
                    }
                })
                .create();

        getPresenter().loadForumAllData(1,null,2);

        //查看图片
        adapter.setOnClickCircleFriendsListener(new CircleFriendsAdapter.OnClickCircleFriendsListener() {
            @Override
            public void clickImage(ArrayList<String> imagePats, int curPosition) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imagePaths", imagePats);
                bundle.putInt("curPosition",curPosition);
                startActivity(ShowBigImageActivity.class,bundle);
            }

            @Override
            public void clickPraise(CircleFriendsDto itemData, int position) {
                getPresenter().praiseForum(itemData.getId(),position);
            }

            @Override
            public void submitNewComment(CircleFriendsDto itemData, int position) {
                MyForumCommentEvent event = new MyForumCommentEvent(true);
                event.setPosition(position);
                event.setFrom(MyForumCommentEvent.From.MYFORUM);
                EventBusUtils.post(event);
//                llComment.setVisibility(View.VISIBLE);
//                etComment.requestFocus();
//                KeyboardUtils.showSoftInput(etComment);
                mConfirmCommentPosition = position;
//                RecyclerUtils.moveToPosition(circleFriendsRecycler,position);
            }

            @Override
            public void getMoreComment(CircleFriendsDto itemData, int position) {
                getPresenter().loadMoreComment(itemData.getId(),position);
            }
        });
    }

//    @Override
//    public void showForum(List<ForumDto> forumDtos, int pageNo) {
//        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
//        adapter.addData(forumDtos,pageNo);
//    }
//
//    @Override
//    public void showMoreComment(List<ForumDto.ForumCommentDto> forumCommentDtos, int position) {
//        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
//        adapter.getItemData(position).setMoreForumCommentDtos(forumCommentDtos);
//        adapter.notifyItemChanged(position);
//    }
//
//    @Override
//    public void likeForumSuccess(int position) {
//        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
//        ForumDto forumDto = adapter.getItemData(position);
//        if ("0".equals(forumDto.getIsNotPraise())){
//            forumDto.setIsNotPraise("1");
//            forumDto.setPraise(forumDto.getPraise()+1);
//        } else {
//            forumDto.setIsNotPraise("0");
//            forumDto.setPraise(forumDto.getPraise()-1);
//        }
//        adapter.notifyItemChanged(position);
//    }

    @Override
    public void showForum(List<CircleFriendsDto> forumDtos, LoadMoreWrapper wrapper) {
        if (wrapper != null){
            wrapper.setLoadMoreFinish(forumDtos.size());
        }
        hideLoading();
        circleFriendsDtos.addAll(forumDtos);
        circleFriendsRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMoreComment(List<CircleFriendsDto.CircleFriendsCommentDto> forumCommentDtos, int position) {
        CircleFriendsDto circleFriendsDto = circleFriendsDtos.get(position);
        circleFriendsDto.setMoreForumCommentDtos(forumCommentDtos);
        circleFriendsRecycler.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void praiseForumSuccess(int position) {
        CircleFriendsDto friendsDto = circleFriendsDtos.get(position);
        if ("0".equals(friendsDto.getIsNotPraise())){
            friendsDto.setIsNotPraise("1");
            friendsDto.setPraise(friendsDto.getPraise()+1);
        } else {
            friendsDto.setIsNotPraise("0");
            friendsDto.setPraise(friendsDto.getPraise()-1);
        }
        circleFriendsRecycler.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void addForumCommentSuccess(String commentContent, int position) {
        CircleFriendsDto forumDto = circleFriendsDtos.get(position);
        CircleFriendsDto.CircleFriendsCommentDto forumCommentDto = new CircleFriendsDto.CircleFriendsCommentDto("松哥",commentContent);
        if (forumDto.getCommentNumberStatus() == 0){
            forumDto.getRuleCommentsList().add(forumCommentDto);
        } else if (forumDto.getCommentNumberStatus() == 1 && (forumDto.getMoreForumCommentDtos() != null && forumDto.getMoreForumCommentDtos().size() > 0)){
            forumDto.getMoreForumCommentDtos().add(forumCommentDto);
        } else if (forumDto.getCommentNumberStatus() == 1 && (forumDto.getMoreForumCommentDtos() == null || forumDto.getMoreForumCommentDtos().size() == 0)){
            getPresenter().loadMoreComment(forumDto.getId(),mConfirmCommentPosition);
        }

        forumDto.setCommentsNumber(forumDto.getCommentsNumber() + 1);
        circleFriendsRecycler.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void showMyForumInfo(ForumInfoDto forumInfoDto) {

    }

    @Override
    public void showImage(String imageUrl, int position) {

    }

    @Override
    public void releaseForumSuccess() {

    }

    public LinearLayoutManager getLayoutManager(){
        LinearLayoutManager llm = (LinearLayoutManager) circleFriendsRecycler.getLayoutManager();
        if (llm.findFirstVisibleItemPosition() == 0){
            circleFriendsRecycler.smoothScrollToPosition(0);
        }
        return llm;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAddComment(MyForumCommentEvent event){
        if (event.isRequest()){
            return;
        }
        if (event.getTo() == null || event.getTo() == MyForumCommentEvent.From.MYFORUM){
            return;
        }

        CircleFriendsDto circleFriendsDto = circleFriendsDtos.get(event.getPosition());
        getPresenter().addForumComment(circleFriendsDto.getId(),event.getNewComment(),event.getPosition());

    }
}
