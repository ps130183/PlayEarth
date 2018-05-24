package com.km.rmbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.greendao.ContactManager;
import com.km.rmbank.mvp.model.MyTeamModel;
import com.km.rmbank.utils.Constant;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 获取联系人手机号，并通过后台分析是否可导入我的人脉中。
 * 分段遍历
 */
public class ContractService extends Service {

    private MyTeamModel myTeamModel;
    public ContractService() {
        myTeamModel = new MyTeamModel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogUtils.d("开始运行ContractService");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                List<ContractDto> contractDtos = ContractUtils.getAllPhoneContracts(ContractService.this);
//                List<ContractDto> contractDtos = ContractUtils.getPhoneContacts(ContractService.this);
//                LogUtils.d("联系人总数 ----- " + contractDtos.size());
//                List<ContractDto> contractDtosSub = new ArrayList<>();
//                for (int i = 0; i < contractDtos.size(); i++){
//                    contractDtosSub.add(contractDtos.get(i));
//                    if (i % 100 == 99){
//                        LogUtils.d("当前 i=== " + i);
//                        analyzeContractList(contractDtosSub);
//                        contractDtosSub = new ArrayList<>();
//                    }
//                }
//                analyzeContractList(contractDtosSub);

            }
        }).start();
        ContactManager.getInstance()
                .initLoadContacts();
    }

    @Override
    public void onDestroy() {
        LogUtils.d("销毁ContractService");
        super.onDestroy();
    }

    private void analyzeContractList(List<ContractDto> contractDtos){
        myTeamModel.getAllContracts(contractDtos)
                .doOnNext(new Consumer<List<ContractDto>>() {
                    @Override
                    public void accept(List<ContractDto> contractDtos) throws Exception {
                        LogUtils.d("当前请求的数据量 ---- 》" + contractDtos.size());
                    }
                })
                .subscribe(new Consumer<List<ContractDto>>() {
                    @Override
                    public void accept(List<ContractDto> contractDtos) throws Exception {
                        if (Constant.mBindingContractList == null) {
                            Constant.mBindingContractList = new ArrayList<>();
                        }
                        if (Constant.mUnBindingContractList == null) {
                            Constant.mUnBindingContractList = new ArrayList<>();
                        }
                        LogUtils.d("获取成功：--->" + contractDtos.size());
                        for (ContractDto contractDto : contractDtos){
                            if ("0".equals(contractDto.getStatus())){//没绑定
                                contractDto.setChecked(true);
                                Constant.mUnBindingContractList.add(contractDto);
                            } else {
                                Constant.mBindingContractList.add(contractDto);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        LogUtils.d("请求失败");
                        if (e instanceof SocketTimeoutException) {
                            LogUtils.d("请求超时，请稍后再试");
                        } else if (e instanceof ConnectException) {
                            LogUtils.d("连接服务器失败，请稍后再试");
                        }
                    }
                });
    }
}
