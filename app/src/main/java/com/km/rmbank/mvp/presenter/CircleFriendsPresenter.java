package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.mvp.model.CircleFriendsModel;
import com.km.rmbank.mvp.view.ICircleFriendsView;
import com.km.rmbank.mvp.base.BasePresenter;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/17.
 */

public class CircleFriendsPresenter extends BasePresenter<ICircleFriendsView,CircleFriendsModel> {

    public CircleFriendsPresenter(CircleFriendsModel mModel) {
        super(mModel);
    }

    /**
     *
     * @param pageNo
     * @param wrapper
     * @param type 0：全部，1：我的，2：其他的
     */
    public void loadForumAllData(final int pageNo, final LoadMoreWrapper wrapper,int type){
        getMvpView().showLoading();

        if (type == 0){
            getMvpModel().loadForumAllData(pageNo)
                    .subscribe(newSubscriber(new Consumer<List<CircleFriendsDto>>() {
                        @Override
                        public void accept(List<CircleFriendsDto> circleFriendsDtos) throws Exception {
                            getMvpView().showForum(circleFriendsDtos,wrapper);
                        }
                    }));
        } else if (type == 1){
            getMvpModel().getMyForumList("0",pageNo)
                    .subscribe(newSubscriber(new Consumer<List<CircleFriendsDto>>() {
                        @Override
                        public void accept(@NonNull List<CircleFriendsDto> forumDtos) throws Exception {
                            getMvpView().showForum(forumDtos,wrapper);
                        }
                    }));
        } else {
            getMvpModel().getMyForumList("1",pageNo)
                    .subscribe(newSubscriber(new Consumer<List<CircleFriendsDto>>() {
                        @Override
                        public void accept(@NonNull List<CircleFriendsDto> forumDtos) throws Exception {
                            getMvpView().showForum(forumDtos,wrapper);
                        }
                    }));
        }

    }

    public void loadMoreComment(String forumId, final int position){
        getMvpModel().loadMoreComment(forumId)
                .subscribe(newSubscriber(new Consumer<List<CircleFriendsDto.CircleFriendsCommentDto>>() {
                    @Override
                    public void accept(List<CircleFriendsDto.CircleFriendsCommentDto> circleFriendsCommentDtos) throws Exception {
                        getMvpView().showMoreComment(circleFriendsCommentDtos,position);
                    }
                }));
    }

    public void praiseForum(String forumId, final int position){
        getMvpModel().praiseForum(forumId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().praiseForumSuccess(position);
                    }
                }));
    }
    public void addForumComment(String forumId, final String commentContent, final int position){
        getMvpModel().addForumComment(forumId,commentContent)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().addForumCommentSuccess(commentContent,position);
                    }
                }));
    }

    public void getMyForumInfos() {
        getMvpView().showLoading();
        getMvpModel().getMyForumInfos()
                .subscribe(newSubscriber(new Consumer<ForumInfoDto>() {
                    @Override
                    public void accept(@NonNull ForumInfoDto forumInfoDto) throws Exception {
                        getMvpView().showMyForumInfo(forumInfoDto);
                    }
                }));
    }

    public void uploadImage(String imagePath, final int position) {
        getMvpModel().imageUpload("6",imagePath)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().showImage(s,position);
                    }
                }));
    }

    public void releaseForum(ForumDto forumDto) {
        getMvpView().showLoading();
        getMvpModel().releaseForum(forumDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().releaseForumSuccess();
                    }
                }));
    }
}
