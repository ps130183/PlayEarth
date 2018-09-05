package com.ps.mrcyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.click.OnLoadMoreErrorListener;
import com.ps.mrcyclerview.click.OnLongClickItemListener;
import com.ps.mrcyclerview.delegate.FooterDelegate;
import com.ps.mrcyclerview.delegate.HeaderDelegate;
import com.ps.mrcyclerview.delegate.ItemDelegate;
import com.ps.mrcyclerview.delegate.MoreDelegate;
import com.ps.mrcyclerview.delegate.MoreErrorDelegate;
import com.ps.mrcyclerview.delegate.MoreFinishDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PengSong on 18/6/1.
 */

public class RecyclerAdapter<D> extends RecyclerView.Adapter<BViewHolder> implements IAdapter<D> {

    private static final String TAG = "RecyclerAdapter";
    private Context mContext;
    //主要item的布局文件ID，可多个
    private Map<Integer,Integer> mContentLayouts;
    //主要Item对应的数据操作
    private Map<Integer,ItemViewConvert> mItemConverts;
    //主数据集合
    private ArrayList<D> mDatas;

    //Header Views
    private SparseArray<HeaderDelegate> mHeaderDelegates;
    private Map<Integer,ItemViewConvert> mHeaderConvert;

    //Footer Views
    private SparseArray<FooterDelegate> mFooterDelegates;
    private Map<Integer,ItemViewConvert> mFooterConvert;

    //加载更多
    protected MoreDelegate moreDelegate;
    private @LayoutRes int moreLayoutRes;
    private @LayoutRes int moreFinishLayoutRes;
    private @LayoutRes int moreErrorLayoutRes;
    private boolean loadMore = true;//是否有加载更多 默认true  有

    protected SparseArray<Integer> mDataSizeArray;


    private OnClickItemListener onClickItemListener;
    private OnLongClickItemListener onLongClickItemListener;
    private OnLoadMoreErrorListener onLoadMoreErrorListener;


    public RecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
        mContentLayouts = new HashMap<>();
        mItemConverts = new HashMap<>();
        mDataSizeArray = new SparseArray<>();
    }

    @NonNull
    @Override
    public BViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(viewType,parent,false);
        return new BViewHolder(mContext,itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull BViewHolder holder, int position) {
        bindView(holder,position,null);
    }

    @Override
    public void onBindViewHolder(@NonNull BViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads == null){
            super.onBindViewHolder(holder, position, payloads);
        } else {//刷新
            bindView(holder,position,payloads);
        }
    }

    /**
     * 绘制列表数据
     * @param holder
     * @param position
     * @param payloads
     */
    private void bindView(@NonNull BViewHolder holder, int position, @NonNull List<Object> payloads){
        int headerCount = 0;
        if (mHeaderConvert != null && mHeaderConvert.size() > 0){
            headerCount = mHeaderConvert.size();
        }
        //header Item
        if (position < headerCount && mHeaderConvert.containsKey(mHeaderDelegates.get(position).getItemViewRes())){
            mHeaderConvert.get(mHeaderDelegates.get(position).getItemViewRes())
                    .convert(holder,null,position,payloads);
            return;
        }

        //正常的item
        int itemPosition = position - headerCount;
        if (itemPosition < mDatas.size() && mItemConverts.size() > 0){
            ItemDelegate delegate = (ItemDelegate) mDatas.get(itemPosition);
            mItemConverts.get(delegate.getItemViewRes()).convert(holder,delegate,itemPosition,payloads);

            //单击事件
            addClickListener(holder.getConvertView(),itemPosition,delegate);
            //长按事件
            addLongClickListener(holder.getConvertView(),itemPosition,delegate);
            return;
        }

        //加载更多
        int moreCount = 0;
        if (moreDelegate != null){
            moreCount = 1;
        }
        if (isLoadMore() && position == (mDatas.size() + headerCount) && moreDelegate instanceof MoreErrorDelegate) {//加载更多失败
            addLoadMoreErrorListener(holder.getConvertView());
            return;
        }

        //footerItemView
        if (position >= headerCount + mDatas.size() + moreCount && mFooterConvert != null && mFooterConvert.size() > 0){
            int footerItemPosition =  position - headerCount - mDatas.size() - moreCount;
            mFooterConvert.get(mFooterDelegates.get(footerItemPosition).getItemViewRes())
                    .convert(holder,null,footerItemPosition,payloads);
            return;
        }
    }



    @Override
    public int getItemCount() {
        int count = mDatas.size();
        if (mHeaderDelegates != null && mHeaderDelegates.size() > 0){
            count += mHeaderDelegates.size();
        }
        if (isLoadMore() && moreDelegate != null){
            count += 1;
        }

        if (mFooterDelegates != null && mFooterDelegates.size() > 0){
            count += mFooterDelegates.size();
        }
        return count;
    }


    @Override
    public int getItemViewType(int position) {
        //headerItem
        int headerCount = 0;
        if (mHeaderDelegates != null && mHeaderDelegates.size() > 0){//有header
            headerCount = mHeaderConvert.size();
        }
        if (position < headerCount){//有header
            HeaderDelegate delegate = mHeaderDelegates.get(position);
            return delegate.getItemViewRes();
        }

        //正常的item
        int itemPostion = position - headerCount;
        if (itemPostion < mDatas.size()){
            Object itemData = mDatas.get(itemPostion);
            return ((ItemDelegate) itemData).getItemViewRes();
        }

        //加载更多  加载更多或加载完更多
        int moreCount = 0;
        if (moreDelegate != null){
            moreCount = 1;
        }
        if (moreCount > 0 && position == headerCount + mDatas.size()){
            return moreDelegate.getItemViewRes();
        }

        //footerItemView
        if (mFooterDelegates != null && mFooterDelegates.size() > 0){
            int footerItemPosition = position - headerCount - mDatas.size() - moreCount;
            FooterDelegate delegate = mFooterDelegates.get(footerItemPosition);
            return delegate.getItemViewRes();
        }
        return super.getItemViewType(position);
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager){//网格布局
            final GridLayoutManager glm = (GridLayoutManager) lm;
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (!mItemConverts.containsKey(viewType)){
                        return glm.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            int position = holder.getLayoutPosition();
            boolean isFullSpan = true;
            int viewType = getItemViewType(position);
            if (mItemConverts.containsKey(viewType)){
                isFullSpan = false;
            }
            p.setFullSpan(isFullSpan);
        }
    }

    /**
     * 设置单击事件监听
     * @param position
     * @param mData
     */
    private void addClickListener(View contentView, final int position, final ItemDelegate mData){
        if (onClickItemListener != null){
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.clickItem(mData,position);
                }
            });
        }
    }

    /**
     * 设置长按事件监听
     * @param contentView
     * @param position
     * @param mData
     */
    private void addLongClickListener(View contentView, final int position, final Object mData){
        if (onLongClickItemListener != null){
            contentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickItemListener.longClickItem(mData,position);
                    return true;
                }
            });
        }
    }

    /**
     * 加载更多失败，点击重试监听
     */
    private void addLoadMoreErrorListener(View loadMoreErrorView){
        if (onLoadMoreErrorListener != null){
            loadMoreErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onLoadMoreErrorListener.loadMoreError(getNextPage());
                        }
                    },2000);
                    removeMoreDelegate();
                    addMoreDelegate();
                }
            });
        }
    }


    /**
     * 添加布局文件
     * @param contentLayoutRes
     */
    public void addContentLayout(@LayoutRes int contentLayoutRes,ItemViewConvert<D> convert){
        if (!mContentLayouts.containsKey(contentLayoutRes)){
            mContentLayouts.put(contentLayoutRes,contentLayoutRes);
        }
        if (!mItemConverts.containsKey(contentLayoutRes)){
            mItemConverts.put(contentLayoutRes,convert);
        }
    }

    /**
     * 添加 header布局文件
     * @param headerLayoutRes
     * @param convert
     */
    public void addHeaderLayout(@LayoutRes int headerLayoutRes,ItemViewConvert convert){
        if (mHeaderConvert == null){
            mHeaderConvert = new HashMap<>();
        }
        if (!mHeaderConvert.containsKey(headerLayoutRes)){
            mHeaderConvert.put(headerLayoutRes,convert);
            if (mHeaderDelegates == null){
                mHeaderDelegates = new SparseArray<>();
            }
            mHeaderDelegates.put(mHeaderConvert.size() - 1,new HeaderDelegate(headerLayoutRes));
        }
    }

    /**
     * 添加footer布局文件
     * @param footerLayoutRes
     * @param convert
     */
    public void addFooterLayout(@LayoutRes int footerLayoutRes,ItemViewConvert convert){
        if (mFooterConvert == null){
            mFooterConvert = new HashMap<>();
        }
        if (!mFooterConvert.containsKey(footerLayoutRes)){
            mFooterConvert.put(footerLayoutRes,convert);
            if (mFooterDelegates == null){
                mFooterDelegates = new SparseArray<>();
            }
            mFooterDelegates.put(mFooterConvert.size() - 1,new FooterDelegate(footerLayoutRes));
        }
    }

    /**
     * 更新数据
     * @param datas
     */
    public void loadDataOfNextPage(List<D> datas){
        mDatas.addAll(datas);
        mDataSizeArray.append(mDataSizeArray.size(),datas.size());
        removeMoreDelegate();
        if (mDataSizeArray.size() > 1 && mDataSizeArray.get(mDataSizeArray.size() - 1) < mDataSizeArray.get(mDataSizeArray.size() - 2)){
            //已加载完所有数据
            addMoreFinishDelegate();
        }
        if (mDataSizeArray.size() > 1){
            notifyItemRangeInserted(mDatas.size(),datas.size());
        } else {
            notifyDataSetChanged();
        }

    }

    /**
     * 插入一条数据
     * @param data
     */
    public void insert(D data){
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public void update(D data,int position,Object payloads){
        mDatas.set(position,data);
        if (mHeaderDelegates != null && mHeaderDelegates.size() > 0){
            position += mHeaderConvert.size();
        }
        notifyItemChanged(position,payloads);
    }

    /**
     * 删除数据
     * @param position
     */
    public void delete(int position){
        if (position >= 0 && position < getDataSize()){
            int headerCount = mHeaderDelegates == null ? 0 : mHeaderDelegates.size();
            mDatas.remove(position);
            notifyItemRemoved(position + headerCount);
            notifyItemRangeChanged(position + headerCount,mDatas.size() - position);
        }
    }

    /**
     * 删除数据
     * @param data
     */
    public void delete(D data){
        if (mDatas.contains(data)){
            int headerCount = mHeaderDelegates == null ? 0 : mHeaderDelegates.size();
            int position = mDatas.indexOf(data);
            mDatas.remove(data);
            notifyItemRemoved(position + headerCount);
            notifyItemRangeChanged(position + headerCount,mDatas.size() - position);
        }
    }

    /**
     * 检索指定数据 在列表中的位置
     * @param data
     * @return
     */
    public int indexOf(D data){
        if (mDatas.contains(data)){
            return mDatas.indexOf(data);
        }
        return -1;
    }


    /**
     * 获取当前列表的数据内容
     * @return
     */
    public List<D> getAllDatas(){
        return mDatas;
    }

    public int getDataSize(){
        return mDatas.size();
    }

    public int getNextPage(){
        return mDataSizeArray.size() + 1;
    }

    public void setMoreLayoutRes(int moreLayoutRes) {
        this.moreLayoutRes = moreLayoutRes;
    }

    public void setMoreFinishLayoutRes(int moreFinishLayoutRes) {
        this.moreFinishLayoutRes = moreFinishLayoutRes;
    }

    public void setMoreErrorLayoutRes(int moreErrorLayoutRes) {
        this.moreErrorLayoutRes = moreErrorLayoutRes;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }

    public boolean isLoadMore() {
        return loadMore;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public void setOnLongClickItemListener(OnLongClickItemListener onLongClickItemListener) {
        this.onLongClickItemListener = onLongClickItemListener;
    }

    public void setOnLoadMoreErrorListener(OnLoadMoreErrorListener onLoadMoreErrorListener) {
        this.onLoadMoreErrorListener = onLoadMoreErrorListener;
    }

    /**
     * 添加 加载更多
     */
    public void addMoreDelegate(){
        if (this.moreDelegate == null && moreLayoutRes > 0){
            this.moreDelegate = new MoreDelegate(moreLayoutRes);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public MoreDelegate getMoreDelegate() {
        return moreDelegate;
    }

    /**
     * 移除 加载更多
     */
    public void removeMoreDelegate(){
        this.moreDelegate = null;
    }

    /**
     * 加载更多失败
     */
    public void loadMoreError(){
        removeMoreDelegate();
        if (this.moreDelegate == null && moreErrorLayoutRes > 0){
            moreDelegate = null;
            moreDelegate = new MoreErrorDelegate(moreErrorLayoutRes);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(getHeaderSize() + mDatas.size());
                }
            });
        }
    }

    /**
     * 加载完所有数据
     */
    public void addMoreFinishDelegate(){
        if (moreDelegate == null && moreFinishLayoutRes > 0){
            moreDelegate = new MoreFinishDelegate(moreFinishLayoutRes);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(getHeaderSize() + mDatas.size());
                }
            });
        }
    }

    /**
     * 清空数据
     */
    public void clear(){
        if (mDatas != null){
            mDatas.clear();
        }
        if (mDataSizeArray != null){
            mDataSizeArray.clear();
        }
    }

    @Override
    public SparseArray<Integer> getDataSizeArray() {
        return mDataSizeArray;
    }

    /**
     * 是否是headerView
     * @param position
     * @return
     */
    public boolean isHeaderView(int position){
        if (mHeaderDelegates != null && mHeaderDelegates.size() > 0 && position < mHeaderDelegates.size()){
            return true;
        }
        return false;
    }

    /**
     * 是否是 主内容View
     * @param position
     * @return
     */
    public boolean isContentView(int position){
        int headerCount = mHeaderDelegates != null ? mHeaderDelegates.size() : 0;
        int itemPosition = position - headerCount;
        if (itemPosition >= 0 && itemPosition < mDatas.size()){
            return true;
        }
        return false;
    }

    /**
     * 是否是加载更多View
     * @param position
     * @return
     */
    public boolean isLoadMoreView(int position){
        int headerCount = mHeaderDelegates != null ? mHeaderDelegates.size() : 0;
        if (moreDelegate != null && position == (headerCount + mDatas.size())){
            return true;
        }
        return false;
    }

    /**
     * 是否是footerView
     * @param position
     * @return
     */
    public boolean isFooterView(int position){
        int headerCount = mHeaderDelegates != null ? mHeaderDelegates.size() : 0;
        int moreCount = moreDelegate == null ? 0 : 1;
        if (mFooterDelegates != null && position >= (headerCount + moreCount + mDatas.size())){
            return true;
        }
        return false;
    }

    /**
     * 获取header 数量
     * @return
     */
    public int getHeaderSize(){
        return mHeaderDelegates == null ? 0 : mHeaderDelegates.size();
    }
}
