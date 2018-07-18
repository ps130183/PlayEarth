package com.km.rmbank.dto;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/6/21.
 */

public class AutobiographyDto implements ItemDelegate {

    private int contentType;

    private String content;
    private String imagePath;

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int getItemViewRes() {
        int itemViewRes = 0;
        switch (contentType){
            case 0:
                itemViewRes = R.layout.item_autobiography_image;
                break;
            case 1:
                itemViewRes = R.layout.item_autobiography_text;
                break;
        }
        return itemViewRes;
    }
}
