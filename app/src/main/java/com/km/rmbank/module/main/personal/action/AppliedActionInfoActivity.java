package com.km.rmbank.module.main.personal.action;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.QRCodeUtils;

import java.util.Date;

import butterknife.BindView;

public class AppliedActionInfoActivity extends BaseActivity {

    private AppointDto appointDto;

    @BindView(R.id.rtcode)
    ImageView reCode;
    @BindView(R.id.signCode)
    TextView signCode;
    @BindView(R.id.actionName)
    TextView actionName;
    @BindView(R.id.actionTime)
    TextView actionTime;
    @BindView(R.id.actionAddress)
    TextView actionAddress;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_applied_action_info;
    }

    @Override
    public String getTitleContent() {
        appointDto = getIntent().getParcelableExtra("action");
        return "活动签到";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        reCode.setImageBitmap(QRCodeUtils.createQRCode(mInstance,"签到码：" + appointDto.getId()));
        signCode.setText("签到码：" + appointDto.getId());
        actionName.setText(appointDto.getTitle());
        actionTime.setText("开始时间：" + DateUtils.getInstance().dateToString(new Date(appointDto.getStartDate()), DateUtils.YMDHM));
        actionAddress.setText("举办地址：" + appointDto.getAddress());
    }
}
