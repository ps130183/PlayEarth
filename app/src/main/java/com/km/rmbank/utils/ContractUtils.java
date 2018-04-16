package com.km.rmbank.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.km.rmbank.dto.ContractDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/3/30.
 */

public class ContractUtils {

    private static Uri PHONE_CONTRACT_NAME = ContactsContract.Contacts.CONTENT_URI;
    private static Uri PHONE_CONTRACT_PHONE = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public static List<ContractDto> getAllPhoneContracts(final Context context){
        List<ContractDto> contractDtoList = new ArrayList<>();
        //获取所有手机联系人的姓名  和  ID
        Cursor resultContractNames = context.getContentResolver().query(PHONE_CONTRACT_NAME,null,null,null,null);
        for (resultContractNames.moveToFirst(); !resultContractNames.isAfterLast(); resultContractNames.moveToNext()){
            String personId =  resultContractNames.getInt(resultContractNames.getColumnIndex(ContactsContract.Contacts._ID)) + "";
            String personName = resultContractNames.getString(resultContractNames.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            ContractDto contractDto = new ContractDto(personId,personName);


            //根据手机联系人的 ID 获取相应的所有手机号
            String phoneSelection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
            String[] phoneSelectionArgs = {personId};

            Cursor resultContractPhones = context.getContentResolver().query(PHONE_CONTRACT_PHONE,null,
                    phoneSelection,phoneSelectionArgs,null);

            List<String> personPhones = new ArrayList<>();
            StringBuffer phones = new StringBuffer();
            //遍历 某一联系人的 所有手机号码
            for (resultContractPhones.moveToFirst(); !resultContractPhones.isAfterLast(); resultContractPhones.moveToNext()){
                int columnIndex = resultContractNames.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String personPhone;
                if (columnIndex >= 0){
                    personPhone = resultContractPhones.getString(columnIndex);
                } else {
                    personPhone = resultContractPhones.getString(resultContractPhones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                if (!TextUtils.isEmpty(personPhone)){
                    personPhone = personPhone.replace(" ","");
                    if (personPhone.length() == 11){
                        personPhones.add(personPhone);
                        phones.append(personPhone).append("\n");
                    }
                }

            }
            resultContractPhones.close();

            //将联系人的手机号放到相应的联系人中
            if (personPhones.size() > 0){
                contractDto.setPhone(phones.length() > 0 ? phones.substring(0,phones.length()-1) : "");
                contractDto.setPhones(personPhones);
//                contractDto.setChecked(true);
                contractDtoList.add(contractDto);
            }

        }
        resultContractNames.close();

        return contractDtoList;
    }


    /**
     * 汉字转拼音
     * @param hanZi
     * @return
     */
    public static String HanziToPinyin(String hanZi){
        String result = "#";
        try {
            result = PinyinHelper.convertToPinyinString(hanZi, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
        } catch (PinyinException e) {
            e.printStackTrace();
            LogUtils.d("汉字---->" + hanZi + "转拼音失败");
            result = "#";
        }
        return result;
    }
}
