package com.km.rmbank.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.greendao.bean.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PengSong on 18/3/30.
 */

public class ContractUtils {

    private static Uri PHONE_CONTRACT_NAME = ContactsContract.Contacts.CONTENT_URI;
    private static Uri PHONE_CONTRACT_PHONE = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public static List<ContractDto> getAllPhoneContracts(final Context context){
        long startTime = System.currentTimeMillis();
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
        long endTime =  System.currentTimeMillis();
        LogUtils.d("获取到  " + contractDtoList.size() + "  条数据");
        LogUtils.d("getAllPhoneContracts耗时 ： " + ((endTime - startTime)/1000f) + " s");

        return contractDtoList;
    }


    //获取系统联系人，获取1000个联系人0.2秒，最快速
    public static List<ContractDto> getPhoneContacts(Context context) {
        long startTime = System.currentTimeMillis();
        //联系人集合
        List<ContractDto> data = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        //搜索字段
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME};
        // 获取手机联系人
        Cursor contactsCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);
        if (contactsCursor != null) {
            //key: contactId,value: 该contactId在联系人集合data的index
//            Map<Integer, Integer> contactIdMap = new HashMap<>();
            while (contactsCursor.moveToNext()) {
                //获取联系人的ID
                int contactId = contactsCursor.getInt(0);
                //获取联系人的姓名
                String name = contactsCursor.getString(2);
                //获取联系人的号码
                String phoneNumber = contactsCursor.getString(1);
                //号码处理
                String replace = phoneNumber.replace(" ", "").replace("-", "").replace("+", "");
                //判断号码是否符合手机号
                if (RegexUtils.isMobileExact(replace)) {
                        //如果联系人Map不包含该contactId
                        ContractDto contacts = new ContractDto();
                        contacts.setId(contactId+"");
                        contacts.setNickName(name);
                        List<String> phones = new ArrayList<>();
                        phones.add(replace);
                        contacts.setPhones(phones);
                        data.add(contacts);
                }
            }
            contactsCursor.close();
        }
        long endTime =  System.currentTimeMillis();

        LogUtils.d("获取到  " + data.size() + "  条数据");
        LogUtils.d("getPhoneContacts ： " + ((endTime - startTime)/1000f) + " s");
        return data;
    }

}
