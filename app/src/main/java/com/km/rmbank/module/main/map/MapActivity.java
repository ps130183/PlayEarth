package com.km.rmbank.module.main.map;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.zhouwei.library.CustomPopWindow;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.event.MapLocationEvent;
import com.km.rmbank.event.RoutePlanDrivingResultEvent;
import com.km.rmbank.module.main.scenic.ScenicActivity;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.map.BaiduMapView;
import com.km.rmbank.utils.map.MapFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity {

    @BindView(R.id.mapView)
    MapView mMapView;
    private BaiduMapView bdMapView;

    private MapMarkerDto mapMarkerDto;
    @BindView(R.id.openNavigation)
    RelativeLayout openNavigation;
    @BindView(R.id.distance)
    TextView distance;

    @BindView(R.id.simple_tb_title_name)
    TextView tbTitleName;

    private List<MapMarkerDto> mapMarkerDtos;

    //弹出框
    private CustomPopWindow customPopWindow;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_map;
    }

    @Override
    public String getTitleContent() {
        return "基地";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        bdMapView = MapFactory.createBaiduMap(mMapView);
        bdMapView.onCreate(savedInstanceState);
        bdMapView.startLocation();

        mapMarkerDtos = getIntent().getParcelableArrayListExtra("mapMarkers");
        mapMarkerDto = getIntent().getParcelableExtra("mapMarker");
        if (mapMarkerDto != null){//规划路线
            routePlanByDriving();
        } else {
            showAllScenic();
        }
    }

    @Override
    protected void onDestroy() {
        bdMapView.onDestroy();
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
       bdMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        bdMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bdMapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        bdMapView.onSaveInstanceState(outState);
    }

    @OnClick(R.id.tvOpenNavigation)
    public void openNavigation(View view){
        DialogUtils.showDefaultAlertDialog("将要开启百度地图导航，是否继续？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                bdMapView.openNavigation(mInstance);
            }
        });
    }

    /**
     * 显示所有基地 会所 信息
     */
    private void showAllScenic(){

        bdMapView.addMarkerForMap(mapMarkerDtos, new BaiduMapView.OnMarkerInfoClickListener() {
            @Override
            public void clickMarkerInfo(MapMarkerDto markerDto) {
                Bundle bundle = new Bundle();
                bundle.putString("scenicId",markerDto.getId());
                bundle.putParcelable("mapMarker",markerDto);
                startActivity(ScenicActivity.class,bundle);
            }
        });
    }

    /**
     * 规划绿线
     */
    private void routePlanByDriving(){
        tbTitleName.setText(mapMarkerDto.getClubName());
        List<MapMarkerDto> mapMarkerDtos = new ArrayList<>();
        mapMarkerDtos.add(mapMarkerDto);
        bdMapView.addMarkerForMap(mapMarkerDtos, null);
        bdMapView.setEndMarker(mapMarkerDto);
    }

    /**
     * 定位成功开启路线规划
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void locationSuccess(MapLocationEvent event){
        if (event.isShowRouteLine()){
            bdMapView.routePlanByDrive();
        } else {
            mapMarkerDto = event.getTargetMarker();
        }
        openNavigation.setVisibility(View.VISIBLE);
        mViewManager.setText(R.id.scenicName,mapMarkerDto.getClubName());
        mViewManager.setText(R.id.scenicAddress,mapMarkerDto.getAddress());
    }

    /**
     * 获取规划距离
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void routePlanResult(RoutePlanDrivingResultEvent event){
        if (event.getDistance() > 0){
            setDistance(event.getDistance());
        } else {
            int distance = (int) DistanceUtil. getDistance(event.getStLatlng(), event.getEnLatlng());
            setDistance(distance);
        }
    }

    /**
     * 设置距离
     * @param distance
     */
    private void setDistance(int distance){
        String nuit = "米";
        if (distance >= 0 && distance < 1000){
            nuit = "米";
        } else if (distance >= 1000){
            distance = distance / 1000;
            nuit = "公里";
        } else {
            distance = 0;
        }

        this.distance.setText("距离：" + distance + nuit);
    }

}
