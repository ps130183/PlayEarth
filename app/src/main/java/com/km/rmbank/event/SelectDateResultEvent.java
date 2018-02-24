package com.km.rmbank.event;

import android.util.SparseArray;

import com.km.rmbank.entity.CheckDateEntity;

import java.util.List;

/**
 * Created by PengSong on 18/2/8.
 */

public class SelectDateResultEvent {
    private List<CheckDateEntity> checkDateArray;

    public SelectDateResultEvent(List<CheckDateEntity> checkDateArray) {
        this.checkDateArray = checkDateArray;
    }

    public List<CheckDateEntity> getCheckDateArray() {
        return checkDateArray;
    }
}
