package com.km.rmbank.event;

/**
 * Created by PengSong on 18/4/19.
 */

public class TranslationScaleEvent {
    private float scale;

    public TranslationScaleEvent(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}
