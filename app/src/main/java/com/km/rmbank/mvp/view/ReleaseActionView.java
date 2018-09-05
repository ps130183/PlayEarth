package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ReleaseActionDetailsDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/8/21.
 */

public interface ReleaseActionView extends MvpView {
    void releaseActionSuccess();
    void showReleaseActionDetails(ReleaseActionDetailsDto releaseActionDetailsDto);
}
