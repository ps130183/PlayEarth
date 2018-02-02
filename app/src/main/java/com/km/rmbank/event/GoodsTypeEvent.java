package com.km.rmbank.event;

import com.km.rmbank.dto.HomeGoodsTypeDto;

/**
 * Created by kamangkeji on 17/5/25.
 */

public class GoodsTypeEvent {
    private HomeGoodsTypeDto levelOneType;
    private HomeGoodsTypeDto levelTwoType;

    public GoodsTypeEvent(HomeGoodsTypeDto levelOneType, HomeGoodsTypeDto levelTwoType) {
        this.levelOneType = levelOneType;
        this.levelTwoType = levelTwoType;
    }

    public HomeGoodsTypeDto getLevelOneType() {
        return levelOneType;
    }

    public void setLevelOneType(HomeGoodsTypeDto levelOneType) {
        this.levelOneType = levelOneType;
    }

    public HomeGoodsTypeDto getLevelTwoType() {
        return levelTwoType;
    }

    public void setLevelTwoType(HomeGoodsTypeDto levelTwoType) {
        this.levelTwoType = levelTwoType;
    }
}
