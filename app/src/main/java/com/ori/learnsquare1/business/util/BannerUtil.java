package com.ori.learnsquare1.business.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;


public class BannerUtil {

    private static final String TAG = BannerUtil.class.getSimpleName();


    /**
     * 设置广告轮播属性
     * @param mBanner
     * @param imgList
     * @return
     */
    public static void setBannerAttrs(final Context context, BGABanner mBanner, List<String> imgList){
        //设置banner样式 无指示器
        mBanner.setIndicatorVisibility(true);
        if (imgList.size() > 1){
            //设置自动播放
            mBanner.setAutoPlayAble(true);
        }else {
            mBanner.setAutoPlayAble(false);
        }

        //设置图片集合
        mBanner.setData(imgList, null);

        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {

            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(context)
                        .load(model)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .fitCenter())
                        .into(itemView);
            }
        });
        //设置允许用户手动操作
        mBanner.setAllowUserScrollable(true);
        //设置banner动画效果
        mBanner.setTransitionEffect(TransitionEffect.Default);
        //设置页面展示时间 5s
        mBanner.setAutoPlayInterval(5000);
        //轮播页面切换时间 2s
        //mBanner.setPageChangeDuration(2000);
    }


    /**
     * 设置广告轮播属性
     * @param mBanner
     * @param imgList
     * @param duration 页面切换时间
    * @param interval  单个页面展示时长
     * @return
     */
    public static void setBannerAttrs(final Context context, BGABanner mBanner, List<?> imgList, int duration, int interval){
        //设置banner样式 无指示器
        mBanner.setIndicatorVisibility(true);
        if (imgList.size() > 1){
            //设置自动播放
            mBanner.setAutoPlayAble(true);
        }else {
            mBanner.setAutoPlayAble(false);
        }

        //设置图片集合
        mBanner.setData(imgList, null);

        //.placeholder().error().fallback()
        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {

            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(context)
                        .load(model)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .fitCenter())
                        .into(itemView);
            }
        });

        //设置允许用户手动操作
        mBanner.setAllowUserScrollable(false);
        //设置banner动画效果
        mBanner.setTransitionEffect(TransitionEffect.Default);
        //设置页面展示时间 10s
        mBanner.setAutoPlayInterval(interval);
        //轮播页面切换时间 6s
        mBanner.setPageChangeDuration(duration);
    }
}
