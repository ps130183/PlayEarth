package com.km.rmbank.dto;

import java.util.List;

/**
 * Created by PengSong on 18/9/7.
 */

public class MessageAllDto {

    /**
     * sumCount : 9
     * noticeList : [{"contentType":"2","count":0,"header":"推送消息","noticeDto":{"content":"职业认证成功","contentType":2,"createDate":1536302733000,"formatCreateDate":"2018-09-07 14:45:33","id":"3456","status":0,"title":"职业认证","type":3,"userId":"19044"}},{"contentType":"3","count":0,"header":"系统消息","noticeDto":{"content":"重磅消息:福利来啦，清凉一夏一起去山上凉快8，告别城市的喧嚣回归大自然8.玩转地球玩家合伙人费用即日起优惠调整为1988.其它优惠政策不变哦","contentType":3,"createDate":1530154166000,"formatCreateDate":"2018-06-28 10:49:26","id":"2305","status":0,"type":3,"userId":"manager"}},{"contentType":"1","count":0,"header":"账户消息"},{"contentType":"4","count":0,"header":"活动消息","noticeDto":{"content":"您的活动预定成功，请到个人电子券查看~","contentType":4,"createDate":1536138048000,"formatCreateDate":"2018-09-05 17:00:48","id":"3403","status":0,"title":"活动报名成功","type":1,"userId":"19044"}}]
     */

    private int sumCount;
    private List<NoticeListDto> noticeList;

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public List<NoticeListDto> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeListDto> noticeList) {
        this.noticeList = noticeList;
    }

}
