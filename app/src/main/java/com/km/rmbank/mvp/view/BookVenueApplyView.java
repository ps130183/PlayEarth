package com.km.rmbank.mvp.view;

import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/8/20.
 */

public interface BookVenueApplyView extends MvpView {
    void showBookVenueApplyList(List<BookVenueApplyDto> bookVenueApplyDtos);
    void cancelSuccess(BookVenueApplyDto bookVenueApplyDto);
}
