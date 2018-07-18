package com.km.rmbank.customview.step;

/**
 * Created by PengSong on 18/7/2.
 */

public class StepContent {
    private String stepName;
    private String stepContent;

    public StepContent(String stepName, String stepContent) {
        this.stepName = stepName;
        this.stepContent = stepContent;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepContent() {
        return stepContent;
    }

    public void setStepContent(String stepContent) {
        this.stepContent = stepContent;
    }
}
