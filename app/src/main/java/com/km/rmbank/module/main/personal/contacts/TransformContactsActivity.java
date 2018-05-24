package com.km.rmbank.module.main.personal.contacts;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.ContactDto;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.greendao.ContactManager;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.greendao.gen.ContactDao;
import com.km.rmbank.mvp.model.ContactsModel;
import com.km.rmbank.mvp.presenter.ContractsPresenter;
import com.km.rmbank.mvp.view.ContractsView;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TransformContactsActivity extends BaseActivity<ContractsView, ContractsPresenter> implements ContractsView {

    private ContactManager mContactManager;
    private List<Contact> mContacts;
    private List<Contact> mCheckedContacts;

    private int pageNumber;//总页数

    private TextView curPageNumber;
    private CustomPopWindow mChoosePagePop;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_transform_contacts;
    }

    @Override
    public String getTitleContent() {
        return "通讯录";
    }

    @Override
    protected ContractsPresenter createPresenter() {
        return new ContractsPresenter(new ContactsModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        mContactManager = ContactManager.getInstance();
        curPageNumber = mViewManager.findView(R.id.curPageNumber);
        mCheckedContacts = new ArrayList<>();
        initRecycler();


        mContactManager.getContactsNum()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        pageNumber = (int) Math.ceil(aLong / ContactManager.PAGE_LIMIT);
                        initSelectPage();
                        getContactByPage(0);
                    }
                });

    }

    private void initRecycler() {
        mContacts = new ArrayList<>();
        final RecyclerView transformRecycler = mViewManager.findView(R.id.transformRecycler);
        RecyclerAdapterHelper<Contact> mHelper = new RecyclerAdapterHelper<>(transformRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_personal_contacts, mContacts, new RecyclerAdapterHelper.CommonConvert<Contact>() {
                    @Override
                    public void convert(CommonViewHolder holder, final Contact mData, final int position) {
                        holder.setText(R.id.nickName, mData.getContactName());
                        holder.setText(R.id.personPhone, mData.getPhone());

                        final CheckBox checkBox = holder.findView(R.id.checkbox);
                        if (mData.getIsInvited()){
                            checkBox.setEnabled(false);
                            checkBox.setChecked(false);
                        } else {
                            checkBox.setEnabled(true);
                            checkBox.setChecked(mData.isChecked());
                        }


                        TextView status = holder.findView(R.id.status);
                        checkBox.setVisibility(View.VISIBLE);

                        if (mData.getIsInvited()) {
                            status.setVisibility(View.VISIBLE);
                            status.setText("已邀请");
                        } else {
                            status.setVisibility(View.GONE);
                        }

                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.setChecked(!mData.isChecked());
                                transformRecycler.getAdapter().notifyItemChanged(position);

                                //检测是否全选
                                CheckBox checkBox = mViewManager.findView(R.id.checkbox);
                                checkBox.setChecked(isCheckedAll());
                            }
                        });
                    }
                }).create();
    }

    /**
     * 根据页码获取当前页的联系人数据
     *
     * @param curPage
     */
    private void getContactByPage(final int curPage) {
        mContactManager.getContactByOffset(curPage)
                .subscribe(new Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> contacts) throws Exception {
                        mContacts.clear();
                        mContacts.addAll(contacts);
                        mViewManager.setText(R.id.pageNumber, "本页" + mContacts.size() + "人，共" + pageNumber + "页");
                        mViewManager.setText(R.id.curPageNumber, "第" + (curPage + 1) + "页");
                        RecyclerView transformRecycler = mViewManager.findView(R.id.transformRecycler);
                        transformRecycler.getAdapter().notifyDataSetChanged();

                        //检测是否全选
                        CheckBox checkBox = mViewManager.findView(R.id.checkbox);
                        checkBox.setChecked(isCheckedAll());
                    }
                });
    }

    /**
     * 初始化 页码 选择弹出框
     */
    private void initSelectPage() {
        List<Integer> pageNumberList = new ArrayList<>();
        for (int i = 1; i <= pageNumber; i++) {
            pageNumberList.add(i);
        }
        View view = LayoutInflater.from(mInstance)
                .inflate(R.layout.pop_transform_contacts_choose_page, null, false);
        RecyclerView choosePage = view.findViewById(R.id.choosePage);
        RecyclerAdapterHelper<Integer> mHelper = new RecyclerAdapterHelper<>(choosePage);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecorationForGrid(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_transform_contacts_choose_page, pageNumberList, new RecyclerAdapterHelper.CommonConvert<Integer>() {
                    @Override
                    public void convert(CommonViewHolder holder, Integer mData, int position) {
                        holder.setText(R.id.pageNo, "第" + mData + "页");
                    }
                }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(CommonViewHolder holder, Integer data, int position) {
                getContactByPage(data - 1);
                mChoosePagePop.dissmiss();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, Integer data, int position) {
                return false;
            }
        });
        mChoosePagePop = new CustomPopWindow.PopupWindowBuilder(mInstance)
                .setView(view)
                .create();
    }


    /**
     * 显示选择页的弹出框
     *
     * @param view
     */
    public void showChoosePage(View view) {
        if (mChoosePagePop != null) {
            mChoosePagePop.showAsDropDown(curPageNumber, 0, 10);
        }
    }

    /**
     * 检测是否全选
     *
     * @return
     */
    private boolean isCheckedAll() {
        boolean checkedAll = true;
        if (mContacts.size() == 0) {
            checkedAll = false;
        } else {
            for (Contact contact : mContacts) {
                if (!contact.isChecked()) {
                    checkedAll = false;
                }

                int index = mCheckedContacts.indexOf(contact);
                if (!contact.getIsInvited()){
                    if (contact.isChecked() && index < 0) {
                        mCheckedContacts.add(contact);
                    } else if (!contact.isChecked() && index >= 0) {
                        mCheckedContacts.remove(contact);
                    }
                }

            }
        }
        mViewManager.setText(R.id.checkedContactNumber, "（" + mCheckedContacts.size() + "）");
        return checkedAll;
    }

    /**
     * 全选
     *
     * @param view
     */
    public void checkAllContacts(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();
        //计算没有被选中的联系人数量
//        int count = 0;
//        for (Contact contact : mContacts) {
//            if (!contact.isChecked()) {
//                count++;
//            }
//        }
//
//        if (count + mCheckedContacts.size() > 100) {
//            showToast("每次转换最多勾选 " + ContactManager.PAGE_LIMIT + " 人");
//            checkBox.setChecked(false);
//            return;
//        }

        //全选或取消全选
        for (Contact contact : mContacts) {
            contact.setChecked(checked);
        }
        RecyclerView transformRecycler = mViewManager.findView(R.id.transformRecycler);
        transformRecycler.getAdapter().notifyDataSetChanged();
        isCheckedAll();
    }

    /**
     * 获取选中的联系人
     *
     * @return
     */
    private List<ContactDto> getCheckedContact() {
        List<ContactDto> checkedContacts = new ArrayList<>();
        for (Contact contact : mCheckedContacts) {
            if (contact.isChecked()) {
                checkedContacts.add(new ContactDto(contact.getId(), contact.getContactName(), contact.getPhone()));
            }
        }
        return checkedContacts;
    }

    /**
     * 转换人脉
     *
     * @param view
     */
    public void transformContact(View view) {

        if (mCheckedContacts.size() > 100) {
            showToast("每次转换最多勾选 " + ContactManager.PAGE_LIMIT + " 人");
            return;
        }

        List<ContactDto> checkedContacts = getCheckedContact();
        if (checkedContacts.size() == 0) {
            showToast("请选择转换的人脉！");
            return;
        }

        getPresenter().getContactsPayOrder(checkedContacts);
    }

    @Override
    public void showContracts(LoadMoreWrapper wrapper, List<Contact> contacts) {

    }

    @Override
    public void showPayContactOrder(PayOrderContactDto payOrderContactDto) {
        List<ContactDto> checkedContacts = getCheckedContact();
        List<ContactDto> invitedContacts = payOrderContactDto.getBookList();

        List<ContactDto> transformContacts = new ArrayList<>();
        boolean isTransform;
        for (ContactDto contactDto : checkedContacts) {
            isTransform = true;
            for (ContactDto contactDto1 : invitedContacts) {
                if (contactDto.getPhone().equals(contactDto1.getPhone())) {
                    isTransform = false;
                }
            }
            if (isTransform) {
                transformContacts.add(contactDto);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrder", payOrderContactDto);
        bundle.putParcelableArrayList("checkedContacts", (ArrayList<? extends Parcelable>) transformContacts);
        startActivity(PayTransformContactActivity.class, bundle);
    }
}
