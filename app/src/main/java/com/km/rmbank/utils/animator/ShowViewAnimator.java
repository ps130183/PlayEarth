package com.km.rmbank.utils.animator;

import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kamangkeji on 17/4/2.
 */

public class ShowViewAnimator {

    /**
     * 给recyclerview添加 隐藏或显示的动画
     * return boolean true:显示  false:隐藏
     */
    private boolean isShow;
    private int height;
    private AnimatorViewWrapper viewWrapper;
    private boolean showView(final View recyclerView){
        recyclerView.setVisibility(View.VISIBLE);
        isShow = !isShow;
        ObjectAnimator objectAnimator;
        if (viewWrapper == null){
            viewWrapper = new AnimatorViewWrapper(recyclerView);
        }
        int height = viewWrapper.getHeight();
        LogUtils.d("rvMember height = " + viewWrapper.getHeight() + "   " + height);
        if (isShow){//目前是显示状态   去显示
            objectAnimator = ObjectAnimator.ofInt(viewWrapper,"height",0,height);
            objectAnimator.setDuration(300);
            objectAnimator.start();
        } else {//目前是隐藏状态  去隐藏
            objectAnimator = ObjectAnimator.ofInt(viewWrapper,"height",height,0);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(View.GONE);
                }
            });
        }
        return isShow;
    }

    public void showViewByAnimator(View view, final onHideListener hideListener){
        Observable.just(view)
                .map(new Function<View, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull View view) throws Exception {
                        return showView(view);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Thread.sleep(300);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (!aBoolean && hideListener != null){
                            hideListener.hide();
                        }
                    }
                });
    }

    public interface onHideListener{
        void hide();
    }

}
