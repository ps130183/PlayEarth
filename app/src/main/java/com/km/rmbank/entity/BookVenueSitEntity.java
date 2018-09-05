package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/8/11.
 */

public class BookVenueSitEntity implements ItemDelegate {



    private boolean checked;
    /**
     * address : 哈喽
     * content : 范德萨发生的富士达范德萨大富商大贾涮肉店花不够煽风点火个梵蒂冈是梵蒂冈
     * id : 1
     * imageUrl : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/08/34127d41252d47f58e6807b842af4962.png
     * price : 999
     * title : 唐人汇天天英雄会
     * type : 1
     */

    private String address;
    private String content;
    private String id;
    private String imageUrl;
    private String price;
    private String title;
    private String type;
    private int itemViewRes = R.layout.item_select_venue_sit;

    public void setItemViewRes(int itemViewRes) {
        this.itemViewRes = itemViewRes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int getItemViewRes() {
        return itemViewRes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
