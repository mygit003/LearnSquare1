package com.ori.learnsquare1.common.ui;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * 创建人 zhengpf
 * 时间 2020/5/11
 * 类说明:
 */
public class MySimplePagerTitleView extends ColorTransitionPagerTitleView {

    private OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.onSelectedListener = listener;
    }

    public MySimplePagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        if (null != onSelectedListener) {
            onSelectedListener.onSelected(index, totalCount);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        if (null != onSelectedListener) {
            onSelectedListener.deSelected(index, totalCount);
        }
    }


    public interface OnSelectedListener {
        void onSelected(int index, int totalCount);
        void deSelected(int index, int totalCount);
    }
}
