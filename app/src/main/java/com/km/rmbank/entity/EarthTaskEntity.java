package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/12.
 */

public class EarthTaskEntity implements ItemDelegate {

    private String mainContent;
    private String subContent;
    private int perAddNumber;//每次增加球票数量
    private int curTimes;//
    private int maxTimes;
    private String button;

    public EarthTaskEntity(String mainContent, String subContent, int perAddNumber, int curTimes, int maxTimes, String button) {
        this.mainContent = mainContent;
        this.subContent = subContent;
        this.perAddNumber = perAddNumber;
        this.curTimes = curTimes;
        this.maxTimes = maxTimes;
        this.button = button;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public int getPerAddNumber() {
        return perAddNumber;
    }

    public void setPerAddNumber(int perAddNumber) {
        this.perAddNumber = perAddNumber;
    }

    public int getCurTimes() {
        return curTimes;
    }

    public void setCurTimes(int curTimes) {
        this.curTimes = curTimes;
    }

    public int getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }


    /**
     * 是否已完成
     * @return
     */
    public boolean isFinished(){
        return curTimes >= maxTimes;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_play_earth_task;
    }
}
