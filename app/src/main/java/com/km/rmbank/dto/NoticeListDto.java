package com.km.rmbank.dto;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/9/7.
 */
public class NoticeListDto implements ItemDelegate{
    /**
     * contentType : 2
     * count : 0
     * header : 推送消息
     * noticeDto : {"content":"职业认证成功","contentType":2,"createDate":1536302733000,"formatCreateDate":"2018-09-07 14:45:33","id":"3456","status":0,"title":"职业认证","type":3,"userId":"19044"}
     */

    private String contentType;
    private int count;
    private String header;
    private MessageDto noticeDto;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public MessageDto getNoticeDto() {
        return noticeDto;
    }

    public void setNoticeDto(MessageDto noticeDto) {
        this.noticeDto = noticeDto;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_message_type;
    }
}
