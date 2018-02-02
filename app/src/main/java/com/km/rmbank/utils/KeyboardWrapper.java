package com.km.rmbank.utils;

import android.graphics.Rect;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by kamangkeji on 17/8/11.
 */

public class KeyboardWrapper {

    private ViewGroup mRootView;
    private int mVisibleHeight;
    private int mCurWindowHeight;
    private boolean mIsKeyboardShow;

    private OnKeyboardChangeListener KeyboardListener;

    public KeyboardWrapper(ViewGroup mRootView) {
        this.mRootView = mRootView;
        setKeybordListener();
    }

    private void setKeybordListener() {
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mRootView.getWindowVisibleDisplayFrame(r);

                int visibleHeight = r.height();


                if (mVisibleHeight == 0) {
                    mVisibleHeight = visibleHeight;
                    mCurWindowHeight = visibleHeight;
                    return;
                }

                if (mVisibleHeight == visibleHeight && mVisibleHeight == mCurWindowHeight) {
                    return;
                }

                mVisibleHeight = visibleHeight;

                // Magic is here
                if (mVisibleHeight != mCurWindowHeight) {
                    mIsKeyboardShow = true;
                    if (KeyboardListener != null){
                        KeyboardListener.showKeyBoard(mCurWindowHeight - mVisibleHeight);
                    }
                } else {
                    mIsKeyboardShow = false;
                    if (KeyboardListener != null){
                        KeyboardListener.hideKeyboard(mCurWindowHeight - mVisibleHeight);
                    }
                }
            }
        });
    }

    /**
     * 输入法 是否显示
     * @return
     */
    public boolean isKeyBoardShow(){
        return mIsKeyboardShow;
    }

    /**
     * 获取输入法的高度
     * @return
     */
    public int getKeybordHeight(){
        return mCurWindowHeight - mVisibleHeight;
    }

    public interface OnKeyboardChangeListener {
        void showKeyBoard(int keyboardHeight);
        void hideKeyboard(int keyboardHeight);
    }

    public void setKeyboardListener(OnKeyboardChangeListener keyboardListener) {
        this.KeyboardListener = keyboardListener;
    }
}
