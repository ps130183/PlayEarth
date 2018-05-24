package com.km.rmbank.module.main.personal.contacts;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.customview.LetterSideBar;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.ContactsModel;
import com.km.rmbank.mvp.presenter.ContractsPresenter;
import com.km.rmbank.mvp.view.ContractsView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.ContractUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ContactsActivity extends BaseActivity<ContractsView,ContractsPresenter> implements ContractsView {

    private int curPage = 0;
    private List<Contact> mContact;
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

    private void initRecycler(){
        mContact = new ArrayList<>();
        RecyclerView contactRecycler = mViewManager.findView(R.id.contactRecycler);
        RecyclerAdapterHelper<Contact> mHelper = new RecyclerAdapterHelper<>(contactRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_personal_contacts, mContact, new RecyclerAdapterHelper.CommonConvert<Contact>() {
            @Override
            public void convert(CommonViewHolder holder, Contact mData, int position) {
                holder.setText(R.id.nickName,mData.getContactName());
                holder.setText(R.id.personPhone,mData.getPhone());
            }
        }).create();
        getPresenter().getContracts(null,curPage);
        initLetterSideBar();

        contactRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = llm.findFirstVisibleItemPosition();
                LetterSideBar sideBar = mViewManager.findView(R.id.letterSideBar);
                sideBar.setTouchLetter(mContact.get(position).getContactNameFirstLetter());
            }
        });
    }

    private void initLetterSideBar(){
        LetterSideBar sideBar = mViewManager.findView(R.id.letterSideBar);
        sideBar.setOnSideBarTouchListener(new LetterSideBar.SideBarTouchListener() {
            @Override
            public void onTouch(String letter, boolean isTouch) {
                if (!isTouch){
                    int position = getPositionByLetter(letter);
                    if (position < 0){
                        return;
                    }
                    RecyclerView contactRecycler = mViewManager.findView(R.id.contactRecycler);
                    LinearLayoutManager llm = (LinearLayoutManager) contactRecycler.getLayoutManager();
                    //定位到指定项如果该项可以置顶就将其置顶显示
                    llm.scrollToPositionWithOffset(position,0);
                }
            }
        });
    }

    private int getPositionByLetter(String letter){
        int position = -1;
        for (int i = 0; i < mContact.size();i++){
            Contact contact = mContact.get(i);
            if (letter.toLowerCase().equals(contact.getContactNameFirstLetter())){
                position = i;
                break;
            }
        }

        return position;
    }

    @Override
    public void showContracts(LoadMoreWrapper wrapper, List<Contact> contacts) {
        if (wrapper != null){
            wrapper.setLoadMoreFinish(contacts.size());
        }
        mContact.addAll(contacts);
        RecyclerView contactRecycler = mViewManager.findView(R.id.contactRecycler);
        contactRecycler.getAdapter().notifyDataSetChanged();
        if (contacts.size() > 0){
            curPage++;
            getPresenter().getContracts(null,curPage);
        }
    }

    @Override
    public void showPayContactOrder(PayOrderContactDto payOrderContactDto) {

    }

    /**
     * 打开转换人脉页面
     * @param view
     */
    public void openTransformContact(View view) {
        startActivity(TransformContactsActivity.class);
    }
}
