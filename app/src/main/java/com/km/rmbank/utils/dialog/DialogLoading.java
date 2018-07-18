package com.km.rmbank.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.km.rmbank.R;


public class DialogLoading extends Dialog {

    private TextView loadingLabel;
//    private LottieAnimationView lottieAnimationView;

    public DialogLoading(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_loading_layout);
        setCanceledOnTouchOutside(false);
        loadingLabel = (TextView) findViewById(R.id.loading_text);
//        lottieAnimationView
    }

    public void setDialogLabel(String label) {
        loadingLabel.setText(label);
    }

}
