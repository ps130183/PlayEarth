package com.km.rmbank.module.main.personal.circlefriends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.customview.FirstRecyclerView;
import com.km.rmbank.delegate.ForumFooterDelegate;
import com.km.rmbank.delegate.ForumHeaderDelegate;
import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.event.MyForumCommentEvent;
import com.km.rmbank.mvp.model.CircleFriendsModel;
import com.km.rmbank.mvp.presenter.CircleFriendsPresenter;
import com.km.rmbank.mvp.view.ICircleFriendsView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.KeyboardWrapper;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.base.ItemViewDelegate;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyForumInfosActivity extends BaseActivity<ICircleFriendsView,CircleFriendsPresenter> implements ICircleFriendsView {

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @BindView(R.id.rv_forum)
    FirstRecyclerView rvForum;

    private KeyboardWrapper mKeyboardWrapper;

    private ForumFooterDelegate forumInfoBottomCell;
    private ForumHeaderDelegate forumInfoTopCell;


    @BindView(R.id.ll_comment)
    RelativeLayout llComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    private MyForumCommentEvent myForumLikeOrCommentEvent;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_forum_infos;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("捡漏专区");

        simpleTitleBar.setRightMenuRes(R.menu.toolbar_my_forum_infos);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.release){
                    startActivity(ReleaseForumActivity.class);
                }
                return false;
            }
        });
    }

    @Override
    protected CircleFriendsPresenter createPresenter() {
        return new CircleFriendsPresenter(new CircleFriendsModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        mKeyboardWrapper = new KeyboardWrapper(rlRoot);
        mKeyboardWrapper.setKeyboardListener(new KeyboardWrapper.OnKeyboardChangeListener() {
            @Override
            public void showKeyBoard(int keyboardHeight) {

            }

            @Override
            public void hideKeyboard(int keyboardHeight) {
                llComment.setVisibility(View.GONE);
            }
        });
        initViewPager();
    }


//        setRightIconClick(R.mipmap.ic_my_forum_info_release, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toNextActivity(ReleaseForumActivity.class);
//            }
//        });

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getMyForumInfos();
    }

    private void initViewPager() {

        List<String> items = new ArrayList<>();
        items.add("0");
        items.add("1");
        List<ItemViewDelegate<String>> itemViewDelegates = new ArrayList<>();
        forumInfoTopCell = new ForumHeaderDelegate();
        itemViewDelegates.add(forumInfoTopCell);
        forumInfoBottomCell = new ForumFooterDelegate(getSupportFragmentManager());
        itemViewDelegates.add(forumInfoBottomCell);

        RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(rvForum);
        mHelper.addLinearLayoutManager()
                .addMulitItemTypeAdapter(items,itemViewDelegates)
                .create();


        rvForum.setSubRvFirstVisibleListener(new FirstRecyclerView.SubRvFirstVisibleListener() {
            @Override
            public boolean getSubRvFirstVisible(MotionEvent event) {
                LinearLayoutManager llm = forumInfoBottomCell.getCurViewLlm();
                if (llm.findFirstVisibleItemPosition() == 0) {
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void showForum(List<CircleFriendsDto> forumDtos, LoadMoreWrapper wrapper) {

    }

    @Override
    public void showMoreComment(List<CircleFriendsDto.CircleFriendsCommentDto> forumCommentDtos, int position) {

    }

    @Override
    public void praiseForumSuccess(int position) {

    }

    @Override
    public void addForumCommentSuccess(String commentContent, int position) {

    }

    @Override
    public void showMyForumInfo(ForumInfoDto forumInfoDto) {
        forumInfoTopCell.setData(forumInfoDto);
    }

    @Override
    public void showImage(String imageUrl, int position) {

    }

    @Override
    public void releaseForumSuccess() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myForumLikeOrComment(MyForumCommentEvent event) {
        if (!event.isRequest()){
            return;
        }
        myForumLikeOrCommentEvent = event;
        llComment.setVisibility(View.VISIBLE);
        etComment.requestFocus();
        KeyboardUtils.showSoftInput(etComment);
    }

    @OnClick(R.id.btn_confirm)
    public void confirmComment(View view) {
        if (!myForumLikeOrCommentEvent.isRequest()) {
            return;
        }
        String comment = etComment.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            showToast("提交的评论不能为空");
            return;
        }

        KeyboardUtils.hideSoftInput(etComment);

        myForumLikeOrCommentEvent.setRequest(false);
        myForumLikeOrCommentEvent.setNewComment(comment);
        myForumLikeOrCommentEvent.setTo(myForumLikeOrCommentEvent.getFrom());
        EventBusUtils.post(myForumLikeOrCommentEvent);
        etComment.setText("");
//        ForumDto forumDto = myForumLikeOrCommentEvent.getForumDto();
//        mPresenter.addForumComment(forumDto.getId(), comment, myForumLikeOrCommentEvent.getPosition());
    }
}
