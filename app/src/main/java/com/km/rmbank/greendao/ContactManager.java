package com.km.rmbank.greendao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.km.rmbank.base.BaseApplication;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.greendao.gen.ContactDao;
import com.km.rmbank.service.ContractService;
import com.km.rmbank.utils.ContractUtils;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/5/22.
 */

public class ContactManager {


    public static final double PAGE_LIMIT = 100;
    private static volatile ContactManager mInstance;
    private Comparator<Contact> comparator;//字母大小比对

    private ContactDao mContactDao;

    private ContactManager() {
        mContactDao = GreenDaoManager.getInstance().getSession().getContactDao();
        comparator = new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                CharSequence p1 = o1.getContactNameSpell().subSequence(0, 1);
                CharSequence p2 = o2.getContactNameSpell().subSequence(0, 1);

                if (p1.charAt(0) > p2.charAt(0)) {
                    return 1;
                } else if (p1.charAt(0) == p2.charAt(0)) {
                    return 0;
                }
                return -1;
            }
        };
    }

    public static ContactManager getInstance() {
        if (mInstance == null) {
            synchronized (ContactManager.class) {
                if (mInstance == null) {
                    mInstance = new ContactManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取当前手机的联系人数据并加入到本地数据库
     */
    public void initLoadContacts() {
        Observable.just(1)
                .map(new Function<Integer, List<Contact>>() {
                    @Override
                    public List<Contact> apply(Integer integer) throws Exception {
                        return getContactsLocal();
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Contact>, Observable<Contact>>() {
                    @Override
                    public Observable<Contact> apply(List<Contact> contacts) throws Exception {
                        return Observable.fromIterable(contacts);
                    }
                })
                .doOnNext(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {
                        saveContacts(contact);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {

                    }
                });

    }


    /**
     * 保存联系人到本地  如果有联系人则更新数据
     * @param contact
     */
    public void saveContacts(Contact contact){
        Contact contact1 = getContactByPhone(contact.getPhone());
        if (contact1 != null) {//数据库存在
            contact.setId(contact1.getId());
            contact.setIsInvited(contact1.getIsInvited());
        }
        mContactDao.save(contact);
    }


    /**
     * 根据手机号查找联系人
     *
     * @param phone
     * @return
     */
    public Contact getContactByPhone(String phone) {
        Contact contact = mContactDao.queryBuilder().where(ContactDao.Properties.Phone.eq(phone)).unique();
        if (contact == null) {
            return null;
        }
        return contact;
    }

    /**
     * 根据id查找联系人
     *
     * @param ID
     * @return
     */
    public Contact getContactById(Long ID) {
        Contact contact = mContactDao.queryBuilder().where(ContactDao.Properties.Id.eq(ID)).unique();
        if (contact == null) {
            return null;
        }
        return contact;
    }


    /**
     * 分页查询
     * @param offset 当前页数
     * @return
     */
    public Observable<List<Contact>> getContactByOffset(final int offset) {
        return Observable.just(offset)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, List<Contact>>() {
                    @Override
                    public List<Contact> apply(Integer integer) throws Exception {
                        return mContactDao.queryBuilder()
                                .offset((int) (offset * PAGE_LIMIT))
                                .limit((int) PAGE_LIMIT)
                                .orderAsc(ContactDao.Properties.ContactNameFirstLetter)
                                .list();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取所有的数据
     * @return
     */
    public Observable<List<Contact>> getAllContact(){
        return Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, List<Contact>>() {
                    @Override
                    public List<Contact> apply(Integer integer) throws Exception {
                        return mContactDao.queryBuilder().list();
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询联系人  数量统计
     * @return
     */
    public Observable<Long> getContactsNum(){
        return Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Long>() {
                    @Override
                    public Long apply(Integer integer) throws Exception {
                        return mContactDao.queryBuilder().count();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }



    /**
     * 获取手机本地的联系人列表
     *
     * @return
     */
    public List<Contact> getContactsLocal() {
        List<Contact> contactList = getContactsLocal(BaseApplication.getInstance());
        contactList = orderByLetter(contactList);
        return contactList;
    }

    /**
     * 获取手机联系人
     *
     * @param context
     * @return
     */
    private List<Contact> getContactsLocal(Context context) {
        long startTime = System.currentTimeMillis();
        //联系人集合
        List<Contact> data = new ArrayList<>();
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
            while (contactsCursor.moveToNext()) {
                //获取联系人的ID
                long contactId = contactsCursor.getInt(0);
                //获取联系人的姓名
                String name = contactsCursor.getString(2);
                //获取联系人的号码
                String phoneNumber = contactsCursor.getString(1);
                //号码处理
                String replace = phoneNumber.replace(" ", "").replace("-", "").replace("+", "");
                //判断号码是否符合手机号
                if (RegexUtils.isMobileExact(replace)) {
                    //如果联系人Map不包含该contactId
                    String nameSpell = HanziToPinyin(name);
                    Contact contact = new Contact(null, contactId, name, nameSpell,nameSpell.subSequence(0, 1).toString(), replace, false);
                    data.add(contact);
                }
            }
            contactsCursor.close();
        }
        long endTime = System.currentTimeMillis();

        LogUtils.d("获取到  " + data.size() + "  条数据");
        LogUtils.d("getPhoneContacts ： " + ((endTime - startTime) / 1000f) + " s");
        return data;
    }

    /**
     * 根据汉字首字母排序
     *
     * @param contractDtoList
     * @return
     */
    private List<Contact> orderByLetter(List<Contact> contractDtoList) {
        Collections.sort(contractDtoList, comparator);
        return contractDtoList;
    }

    /**
     * 汉字转拼音
     *
     * @param hanZi
     * @return
     */
    public String HanziToPinyin(String hanZi) {
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
