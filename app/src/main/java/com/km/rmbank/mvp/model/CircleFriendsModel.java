package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.retrofit.image.ImageUpload;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/17.
 */

public class CircleFriendsModel extends BaseModel {

    public Observable<List<CircleFriendsDto>> loadForumAllData(int pageNo){
        return getService().getCircleFriendsList(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<CircleFriendsDto>>applySchedulers());
    }

    /**
     * 获取 我的捡漏专区  具体的  帖子列表
     * @param type
     * @param pageNo
     * @return
     */
    public Observable<List<CircleFriendsDto>> getMyForumList(String type, int pageNo){
        return getService().getMyForumList(Constant.userLoginInfo.getToken(),
                type,pageNo)
                .compose(this.<List<CircleFriendsDto>>applySchedulers());
    }

    public Observable<List<CircleFriendsDto.CircleFriendsCommentDto>> loadMoreComment(String forumId){
        return getService().getMoreCommentList(forumId)
                .compose(this.<List<CircleFriendsDto.CircleFriendsCommentDto>>applySchedulers());
    }

    public Observable<String> praiseForum(String forumId){
        return getService().praiseForum(Constant.userLoginInfo.getToken(),forumId)
                .compose(this.<String>applySchedulers());
    }
    public Observable<String> addForumComment(String forumId,String commentContent){
        return getService().addForumComment(Constant.userLoginInfo.getToken(),forumId,commentContent)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取 我的捡漏 信息  评论数  获赞数  发帖数
     * @return
     */
    public Observable<ForumInfoDto> getMyForumInfos(){
        return getService().getMyForumInfos(Constant.userLoginInfo.getToken())
                .compose(this.<ForumInfoDto>applySchedulers());
    }

    /**
     * 上传图片
     * @param optionType
     * @param imagePath
     * @return
     */
    public Observable<String> imageUpload(String optionType, String imagePath){
        return ImageUpload.imageUpload(getService(),optionType,imagePath).compose(this.<String>applySchedulers());
    }

    /**
     * 发布捡漏
     * @param forumDto
     * @return
     */
    public Observable<String> releaseForum(ForumDto forumDto){
        return getService().releaseForum(Constant.userLoginInfo.getToken(),
                forumDto.getRuleTitle(),
                forumDto.getRuleContent(),
                forumDto.getRulePictureUrl())
                .compose(this.<String>applySchedulers());
    }
}
