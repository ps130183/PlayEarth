package com.km.rmbank.module.main.personal.contacts;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.customview.LetterSideBar;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.mvp.model.ContactsModel;
import com.km.rmbank.mvp.presenter.ContractsPresenter;
import com.km.rmbank.mvp.view.ContractsView;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.List;

public class ContactsActivity extends BaseActivity<ContractsView, ContractsPresenter> implements ContractsView {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_contacts;
    }

    @Override
    protected ContractsPresenter createPresenter() {
        return new ContractsPresenter(new ContactsModel());
    }

    @Override
    public String getTitleContent() {
        return "通讯录";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        initRecycler();
    }

    private void initRecycler() {
        final MRecyclerView<Contact> contactRecycler = mViewManager.findView(R.id.contactRecycler);
        contactRecycler.addContentLayout(R.layout.item_personal_contacts, new ItemViewConvert<Contact>() {
            @Override
            public void convert(@NonNull BViewHolder holder, Contact mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.nickName, mData.getContactName());
                holder.setText(R.id.personPhone, mData.getPhone());
            }
        }).create();

        contactRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                final Contact data = (Contact) mData;
                DialogUtils.showDefaultAlertDialog("是否拨打电话 " + data.getPhone() + "?", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri phone = Uri.parse("tel:" + data.getPhone());
                        intent.setData(phone);
                        startActivity(intent);
                    }
                });
            }
        });

        getPresenter().getContracts(null, 1);
        initLetterSideBar();

        contactRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = llm.findFirstVisibleItemPosition();
                LetterSideBar sideBar = mViewManager.findView(R.id.letterSideBar);
                sideBar.setTouchLetter(contactRecycler.getAllDatas().get(position).getContactNameFirstLetter());
            }
        });
    }

    private void initLetterSideBar() {
        LetterSideBar sideBar = mViewManager.findView(R.id.letterSideBar);
        sideBar.setOnSideBarTouchListener(new LetterSideBar.SideBarTouchListener() {
            @Override
            public void onTouch(String letter, boolean isTouch) {
                if (!isTouch) {
                    int position = getPositionByLetter(letter);
                    if (position < 0) {
                        return;
                    }
                    RecyclerView contactRecycler = mViewManager.findView(R.id.contactRecycler);
                    LinearLayoutManager llm = (LinearLayoutManager) contactRecycler.getLayoutManager();
                    //定位到指定项如果该项可以置顶就将其置顶显示
                    llm.scrollToPositionWithOffset(position, 0);
                }
            }
        });
    }

    private int getPositionByLetter(String letter) {
        MRecyclerView<Contact> contactRecycler = mViewManager.findView(R.id.contactRecycler);
        List<Contact> mContact = contactRecycler.getAllDatas();
        int position = -1;
        for (int i = 0; i < mContact.size(); i++) {
            Contact contact = mContact.get(i);
            if (letter.toLowerCase().equals(contact.getContactNameFirstLetter())) {
                position = i;
                break;
            }
        }

        return position;
    }

    @Override
    public void showContracts(LoadMoreWrapper wrapper, List<Contact> contacts) {
        MRecyclerView<Contact> contactRecycler = mViewManager.findView(R.id.contactRecycler);
        contactRecycler.loadDataOfNextPage(contacts);
    }

    @Override
    public void showPayContactOrder(PayOrderContactDto payOrderContactDto) {

    }

    /**
     * 打开转换人脉页面
     *
     * @param view
     */
    public void openTransformContact(View view) {
        startActivity(TransformContactsActivity.class);
    }
}
