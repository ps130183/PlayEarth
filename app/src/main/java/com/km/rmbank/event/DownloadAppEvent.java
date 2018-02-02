package com.km.rmbank.event;

import android.app.Activity;

/**
 * Created by kamangkeji on 17/4/17.
 */

public class DownloadAppEvent {
    private Activity activity;

    public DownloadAppEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
