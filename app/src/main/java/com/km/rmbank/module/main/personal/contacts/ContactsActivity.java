package com.km.rmbank.module.main.personal.contacts;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.ContactsModel;
import com.km.rmbank.mvp.presenter.ContractsPresenter;
import com.km.rmbank.mvp.view.ContractsView;
import com.km.rmbank.utils.ContractUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactsActivity extends BaseActivity<ContractsView,ContractsPresenter> implements ContractsView {

//    @BindView(R.id.letterHint)
//    RTextView letterHint;
//    @BindView(R.id.letterSide)
//    LetterSideBar letterSide;

//    @BindView(R.id.nestedScrollView)
//    NestedScrollView nestedScrollView;

    @BindView(R.id.contactsRecycler)
    RecyclerView contactsRecycler;
    @BindView(R.id.linkManRecycler)
    RecyclerView linkManRecycler;
    private List<ContractDto> contractDtoList;
    private List<ContractDto> linkManList;
    private LinearLayoutManager mLinearLayoutManager;


    private boolean move = false;
    private int mIndex = 0;
    private boolean isScrolling = false;


    //一键邀请
    private boolean isInvitation = false;



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
        contractDtoList = getIntent().getParcelableArrayListExtra("contractList");
        linkManList = getIntent().getParcelableArrayListExtra("linkManList");

        if(linkManList != null){
            mViewManager.setText(R.id.invitation,"一键邀请");
            mViewManager.setText(R.id.invitation1,"(可邀请" + linkManList.size()+"人)");
        }
//        initLetterSideBar();
        initRecycler();
    }

//    private void initLetterSideBar() {
//        letterSide.setOnSideBarTouchListener(new LetterSideBar.SideBarTouchListener() {
//            @Override
//            public void onTouch(String letter, boolean isTouch) {
//                letterHint.setText(letter);
//                letterHint.setVisibility(View.VISIBLE);
//                if (!isTouch) {
//                    Observable.timer(1000, TimeUnit.MILLISECONDS)
//                            .subscribeOn(Schedulers.computation())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Consumer<Long>() {
//                                @Override
//                                public void accept(Long aLong) throws Exception {
//                                    letterHint.setVisibility(View.GONE);
//                                }
//                            });
//                }
//
//                //从列表的 数据中 找到 姓名首字母 相对应的 位置
//                int position = 0;
//                for (int i = 0; i < contractDtoList.size(); i++) {
//                    ContractDto contractDto = contractDtoList.get(i);
//                    String pinyin = contractDto.getPersonNamePinyin().substring(0, 1);
//                    if (letter.equals(pinyin.toUpperCase())) {
//                        position = i;
//                        break;
//                    }
//                }
//
//                //滚动
//                moveToPosition(position);
//            }
//        });
//    }

    private void initRecycler() {
        contractDtoList = orderByLetter(contractDtoList);
        RecyclerAdapterHelper<ContractDto> mHelper = new RecyclerAdapterHelper<>(contactsRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_personal_contacts, contractDtoList, new RecyclerAdapterHelper.CommonConvert<ContractDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, final ContractDto mData, int position) {
                        TextView status = holder.findView(R.id.status);
                        holder.setText(R.id.nickName, mData.getNickName());

                        holder.setText(R.id.personPhone, mData.getPhone());

                        CheckBox checkBox = holder.findView(R.id.checkbox);
                        checkBox.setChecked(mData.isChecked());
                        checkBox.setVisibility(View.GONE);
                        status.setVisibility(View.VISIBLE);
                        String pStatus = mData.getStatus();
                        status.setTextColor(mInstance.getResources().getColor(R.color.text_color_block8));
                        if ("1".equals(pStatus)){//被别人绑定
                            status.setText("已被别人绑定");
                        } else if ("2".equals(pStatus)){//自己绑定
                            status.setText("已绑定");
                        } else {
                            status.setText("");
                        }

//                        checkBox.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mData.setChecked(!mData.isChecked());
//                                mViewManager.setText(R.id.invitation,"邀请(" + getInvitationContracts().size() + "人)");
//                            }
//                        });
                    }
                }).create();

//        contactsRecycler.addOnScrollListener(new RecyclerViewListener());
//        linkManList = new ArrayList<>();
        RecyclerAdapterHelper<ContractDto> linkManHelper = new RecyclerAdapterHelper<>(linkManRecycler);
        linkManHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_personal_contacts, linkManList, new RecyclerAdapterHelper.CommonConvert<ContractDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, final ContractDto mData, int position) {
                        TextView status = holder.findView(R.id.status);
                        holder.setText(R.id.nickName, mData.getNickName());
                        holder.setText(R.id.personPhone, mData.getPhone());

                        CheckBox checkBox = holder.findView(R.id.checkbox);
                        checkBox.setChecked(mData.isChecked());
                        checkBox.setVisibility(isInvitation ? View.VISIBLE : View.GONE);
                        status.setVisibility(isInvitation ? View.GONE : View.VISIBLE);
                        String pStatus = mData.getStatus();
                        status.setTextColor(mInstance.getResources().getColor(R.color.text_color_blue2));
                        if ("0".equals(pStatus) || "3".equals(pStatus)){//未绑定
                            status.setText("可邀请");
                        } else {
                            status.setText("");
                        }

                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.setChecked(!mData.isChecked());
                                mViewManager.setText(R.id.invitation,"邀请");
                                mViewManager.setText(R.id.invitation1,"(已选择" + getInvitationContracts().size()+"人)");
                            }
                        });
                    }
                }).create();

//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY > oldScrollY) {
//                    // 向下滑动
//                }
//
//                if (scrollY < oldScrollY) {
//                    // 向上滑动
//                }
//
//                if (scrollY == 0) {
//                    // 顶部
//                }
//
//                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    // 底部
//                }
//            }
//        });

//        getAllContracts();

    }

    /**
     * 获取所有要邀请的  朋友
     * @return
     */
    private List<ContractDto> getInvitationContracts(){
        List<ContractDto> contractDtos = new ArrayList<>();
        for (ContractDto contractDto : linkManList){
            if (contractDto.isChecked()){
                contractDtos.add(contractDto);
            }
        }
        return contractDtos;
    }

//    /**
//     * 移动到指定位置
//     *
//     * @param index
//     */
//    public void moveToPosition(int index) {
//        if (mLinearLayoutManager == null) {
//            mLinearLayoutManager = (LinearLayoutManager) contactsRecycler.getLayoutManager();
//        }
//        mIndex = index;
//        //获取当前recycleView屏幕可见的第一项和最后一项的Position
//        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
//        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
//        if (index <= firstItem) {
//            contactsRecycler.smoothScrollToPosition(index);
//        } else if (index <= lastItem) {
//            int top = contactsRecycler.getChildAt(index - firstItem).getTop();
//            contactsRecycler.smoothScrollBy(0, top);
//        } else {
//            contactsRecycler.smoothScrollToPosition(index);
//            move = true;
//        }
//
//        isScrolling = true;
//    }

    @Override
    public void showContracts(List<ContractDto> contractDtos,List<ContractDto> linkManList) {
        contractDtoList.clear();

        contractDtoList.addAll(contractDtos);
        contactsRecycler.getAdapter().notifyDataSetChanged();

        this.linkManList.clear();
        this.linkManList.addAll(linkManList);
        linkManRecycler.getAdapter().notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void showPayOrder(PayOrderDto payOrderDto,int number) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrderDto",payOrderDto);
        bundle.putInt("payType",3);
        bundle.putInt("number",number);
        startActivity(PaymentActivity.class,bundle);
    }

//    /**
//     * recyclerview滚动监听
//     */
//    class RecyclerViewListener extends RecyclerView.OnScrollListener {
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//            if (isScrolling && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                isScrolling = false;
//                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
//                if (0 <= n && n < contactsRecycler.getChildCount()) {
//                    int top = contactsRecycler.getChildAt(n).getTop();
//                    contactsRecycler.smoothScrollBy(0, top);
//                }
//
//            }
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            if (isScrolling) {
//                if (move) {
//                    move = false;
//                    int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
//                    if (0 <= n && n < contactsRecycler.getChildCount()) {
//                        int top = contactsRecycler.getChildAt(n).getTop();
//                        contactsRecycler.scrollBy(0, top);
//                    }
//                }
//            } else {
//                //根据当前 最顶层的 联系人姓名首字母  指定相应的 字母
//                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int position = lm.findFirstCompletelyVisibleItemPosition();
//                ContractDto contractDto = contractDtoList.get(position);
//                if (contractDto == null || TextUtils.isEmpty(contractDto.getPersonNamePinyin())){
//                    return;
//                }
//                String pinyin = contractDto.getPersonNamePinyin().substring(0, 1);
//                letterSide.setTouchLetter(pinyin);
//            }
//
//
//        }
//    }

    /**
     * 获取所有的手机联系人信息
     */
//    private void getAllContracts() {
//        showLoading();
//        Observable.just(1)
//                .map(new Function<Integer, List<ContractDto>>() {
//                    @Override
//                    public List<ContractDto> apply(Integer integer) throws Exception {
//                        return ContractUtils.getAllPhoneContracts(mInstance);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<ContractDto>>() {
//                    @Override
//                    public void accept(List<ContractDto> personalDynamicDtos) throws Exception {
//                        getPresenter().getContracts(ContractUtils.getAllPhoneContracts(mInstance));
//                    }
//                });
//    }

    private static Comparator<ContractDto> comparator = new Comparator<ContractDto>() {
        @Override
        public int compare(ContractDto o1, ContractDto o2) {
            CharSequence p1 = o1.getPersonNamePinyin().subSequence(0, 1);
            CharSequence p2 = o2.getPersonNamePinyin().subSequence(0, 1);

            if (p1.charAt(0) > p2.charAt(0)) {
                return 1;
            } else if (p1.charAt(0) == p2.charAt(0)) {
                return 0;
            }
            return -1;
        }
    };

    /**
     * 根据汉字首字母排序
     *
     * @param contractDtoList
     * @return
     */
    private List<ContractDto> orderByLetter(List<ContractDto> contractDtoList) {
        //将人名转换为拼音
        for (ContractDto contractDto : contractDtoList) {
            String pinyin = ContractUtils.HanziToPinyin(contractDto.getNickName());
            contractDto.setPersonNamePinyin(pinyin);
        }
        Collections.sort(contractDtoList, comparator);

        return contractDtoList;
    }

    /**
     * 取消邀请
     * @param view
     */
    @OnClick(R.id.cancel)
    public void cancelInvitation(View view){
        if (isInvitation){
            isInvitation = false;
            view.setVisibility(View.GONE);
            mViewManager.setText(R.id.invitation,"一键邀请");
            mViewManager.setText(R.id.invitation1,"(可邀请" + linkManList.size()+"人)");
            linkManRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 一建邀请
     * @param view
     */
    @OnClick(R.id.rlInvitation)
    public void invitation(View view){
        if (!isInvitation){
            isInvitation = true;
            mViewManager.findView(R.id.cancel).setVisibility(View.VISIBLE);
            mViewManager.setText(R.id.invitation,"邀请");
            mViewManager.setText(R.id.invitation1,"(已选择" + getInvitationContracts().size()+"人)");
            linkManRecycler.getAdapter().notifyDataSetChanged();
        } else {
//            List<ContractDto> contractDtos = getInvitationContracts();
//            showToast("当前邀请人数为：" + contractDtos.size() + "人");
//            startActivity(PaymentActivity.class);
            List<String> phones = new ArrayList<>();
            for (ContractDto contractDto : linkManList){
                if (contractDto.isChecked()){
                    phones.add(contractDto.getPhone());
                }
            }
            getPresenter().getContactsPayOrder(phones);
        }
    }
}
