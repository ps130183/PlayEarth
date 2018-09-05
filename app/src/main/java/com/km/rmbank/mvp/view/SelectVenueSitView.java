package com.km.rmbank.mvp.view;

import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/8/20.
 */

public interface SelectVenueSitView extends MvpView {
    void showBookVenueSitList(List<BookVenueSitEntity> bookVenueSitEntities);
}
