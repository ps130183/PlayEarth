package com.km.rmbank.event;

import com.km.rmbank.dto.WithDrawAccountDto;

/**
 * Created by PengSong on 18/7/2.
 */

public class StepEvent {
    private int step;
    private WithDrawAccountDto withDrawAccount;

    public StepEvent(int step) {
        this.step = step;
    }

    public StepEvent(int step, WithDrawAccountDto withDrawAccount) {
        this.step = step;
        this.withDrawAccount = withDrawAccount;
    }

    public int getStep() {
        return step;
    }

    public WithDrawAccountDto getWithDrawAccount() {
        return withDrawAccount;
    }
}
