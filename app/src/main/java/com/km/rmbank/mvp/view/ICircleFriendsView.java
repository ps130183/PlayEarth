package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.mvp.base.MvpView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by PengSong on 18/1/17.
 */

public interface ICircleFriendsView extends MvpView {
    void showForum(List<CircleFriendsDto> forumDtos,LoadMoreWrapper wrapper);
    void showMoreComment(List<CircleFriendsDto.CircleFriendsCommentDto> forumCommentDtos, int position);
    void praiseForumSuccess(int position);
    void addForumCommentSuccess(String commentContent,int position);

    void showMyForumInfo(ForumInfoDto forumInfoDto);

    void showImage(String imageUrl,int position);
    void releaseForumSuccess();
}
