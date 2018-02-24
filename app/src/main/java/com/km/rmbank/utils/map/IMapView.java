package com.km.rmbank.utils.map;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by PengSong on 18/2/6.
 */

public interface IMapView {

    Context getContext();

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);


}
