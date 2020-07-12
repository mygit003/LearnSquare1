package com.ori.learnsquare1.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.ui.GlideRoundTransform

/**
 * 创建人 zhengpf
 * 时间 2020/5/9
 * 类说明:
 */
object ImageUtil {


    /**
     * 默认加载
     */
    fun load(imageView: ImageView, url:String?){
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                //.placeholder(R.mipmap.image_holder)
                //.error(R.mipmap.image_holder)
                .into(imageView)
        }
    }

    /**
     * 加载圆角图片
     */
    fun loadRadius(imageView: ImageView, url:String?, round:Int){
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(GlideRoundTransform(imageView.context,round))
                //.placeholder(R.mipmap.internet_error)
                .into(imageView)
        }

    }

    /**
     * 加载圆形图片
     */
    fun loadRound(imageView: ImageView, url:String, round:Int){
        Glide.with(imageView.context)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.mipmap.internet_error)
            .transform(RoundedCorners(8))
            .into(imageView)
    }

    /**
     * 清除缓存
     */
    fun clearCache(){
//        Glide.get(BaseApplication.getContext()).clearMemory()
        System.gc()
    }


}