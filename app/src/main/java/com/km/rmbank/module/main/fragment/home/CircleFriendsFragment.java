package com.km.rmbank.module.main.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.km.rmbank.R;
import com.km.rmbank.adapter.CircleFriendsAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.event.HomeTabLayoutEvent;
import com.km.rmbank.mvp.model.CircleFriendsModel;
import com.km.rmbank.mvp.presenter.CircleFriendsPresenter;
import com.km.rmbank.mvp.view.ICircleFriendsView;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.KeyboardWrapper;
import com.km.rmbank.utils.RecyclerUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.HeaderAndFooterWrapper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFriendsFragment extends BaseFragment<ICircleFriendsView,CircleFriendsPresenter> implements ICircleFriendsView {

    private RecyclerView circleFriendsRecycler;
    private List<CircleFriendsDto> circleFriendsDtos;
    private RecyclerAdapterHelper<CircleFriendsDto> mHelper;

    private EditText etComment;
    private int mConfirmCommentPosition = 0;
    private CustomPopWindow customPopWindow;

    @BindView(R.id.commentAnchor)
    View commentAnchor;

    public static CircleFriendsFragment newInstance(Bundle bundle) {

        CircleFriendsFragment fragment = new CircleFriendsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_circle_friends;
    }

    @Override
    protected CircleFriendsPresenter createPresenter() {
        return new CircleFriendsPresenter(new CircleFriendsModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        circleFriendsRecycler = mViewManager.getRecyclerView(R.id.circleFriendsRecycler);

        keyBorad((ViewGroup) mViewManager.findView(R.id.rl_root));
        initRecycler();

        initCommentWindow();

//        initCommentWindow();
    }

    /**
     * 初始化 人脉圈 控件
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
                .addHeaderAndFooterWrapper(new Integer[]{R.layout.header_rv_home3_forum}, null, new HeaderAndFooterWrapper.LoadHeaderAndFooterData() {
                    @Override
                    public void loadHeaderData(CommonViewHolder holder, int position) {

                    }

                    @Override
                    public void loadFooterData(CommonViewHolder holder, int position) {

                    }
                })
                .addEmptyWrapper(null)
                .addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        getPresenter().loadForumAllData(1,null,0);
                    }
                })
                .addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                        getPresenter().loadForumAllData(nextPage,wrapper,0);
                    }
                })
                .create();

        showLoading();

        //查看图片
        adapter.setOnClickCircleFriendsListener(new CircleFriendsAdapter.OnClickCircleFriendsListener() {
            @Override
            public void clickImage(ArrayList<String> imagePats, int curPosition) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imagePaths", imagePats);
                bundle.putInt("curPosition",curPosition - 1);
                startActivity(ShowBigImageActivity.class,bundle);
            }

            @Override
            public void clickPraise(CircleFriendsDto itemData,int position) {
                getPresenter().praiseForum(itemData.getId(),position - 1);
            }

            @Override
            public void submitNewComment(CircleFriendsDto itemData, int position) {
//                llComment.setVisibility(View.VISIBLE);
                etComment.requestFocus();
                KeyboardUtils.showSoftInput(commentAnchor);
                mConfirmCommentPosition = position;
                RecyclerUtils.moveToPosition(circleFriendsRecycler,position);
            }

            @Override
            public void getMoreComment(CircleFriendsDto itemData, int position) {
                getPresenter().loadMoreComment(itemData.getId(),position - 1);
            }
        });
    }


    @Override
    public void showForum(List<CircleFriendsDto> forumDtos,LoadMoreWrapper wrapper) {
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
        LogUtils.d("praise position = " + position);
        CircleFriendsAdapter adapter = (CircleFriendsAdapter) mHelper.getBasicAdapter();
        CircleFriendsDto friendsDto = adapter.getDatas().get(position);
        if ("0".equals(friendsDto.getIsNotPraise())){
            friendsDto.setIsNotPraise("1");
            friendsDto.setPraise(friendsDto.getPraise()+1);
        } else {
            friendsDto.setIsNotPraise("0");
            friendsDto.setPraise(friendsDto.getPraise()-1);
        }
        circleFriendsRecycler.getAdapter().notifyItemChanged(position + 1);
    }


    @Override
    public void addForumCommentSuccess(String commentContent, int position) {
//        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
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
        circleFriendsRecycler.getAdapter().notifyItemChanged(position + 1);

        etComment.setText("");
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

    /**
     * 提交评论
     * @param view
     */
    public void submitComment(View view){
        String comment = etComment.getText().toString();
        if (TextUtils.isEmpty(comment)){
            showToast("提交的评论不能为空");
            return;
        }
        KeyboardUtils.hideSoftInput(etComment);

//        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        CircleFriendsDto forumDto = circleFriendsDtos.get(mConfirmCommentPosition);
        getPresenter().addForumComment(forumDto.getId(),comment,mConfirmCommentPosition);
    }

    private void keyBorad(ViewGroup viewGroup){
        KeyboardWrapper mKeyboardWrapper = new KeyboardWrapper(viewGroup);
        mKeyboardWrapper.setKeyboardListener(new KeyboardWrapper.OnKeyboardChangeListener() {
            @Override
            public void showKeyBoard(int keyboardHeight) {
                showCommentWindow();
                EventBusUtils.post(new HomeTabLayoutEvent(true));
            }

            @Override
            public void hideKeyboard(int keyboardHeight) {
                hideCommentWindow();
                EventBusUtils.post(new HomeTabLayoutEvent(false));
            }

        });
    }


    /**
     * 初始化评论弹出框
     */
    private void initCommentWindow(){
        View commentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_comment_circle_friend,null,false);
        etComment = commentView.findViewById(R.id.et_comment);
        Button submit = commentView.findViewById(R.id.btn_confirm);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment(v);
            }
        });
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(commentView)
                .size(ScreenUtils.getScreenWidth(),ConvertUtils.dp2px(65))
                .setFocusable(true)
                .setOutsideTouchable(false)
                .enableOutsideTouchableDissmiss(false)
                .create();
    }

    /**
     * 显示评论 弹出框
     */
    private void showCommentWindow(){
      if (customPopWindow != null){
          customPopWindow.showAsDropDown(commentAnchor,0,ConvertUtils.dp2px(55));
      }
    }

    /**
     * 隐藏评论弹出框
     */
    private void hideCommentWindow(){
        if (customPopWindow != null){
            customPopWindow.dissmiss();
        }
    }
}
