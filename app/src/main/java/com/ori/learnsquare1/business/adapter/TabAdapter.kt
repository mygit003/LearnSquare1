package com.ori.learnsquare1.business.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.listener.OnTabClickListener
import com.ori.learnsquare1.common.ui.MySimplePagerTitleView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * 创建人 zhengpf
 * 时间 2020/5/11
 * 类说明:
 */
class TabAdapter(list: MutableList<String>) : CommonNavigatorAdapter() {

    private var tabList = list

    private var onTabClickListener: OnTabClickListener? = null

    fun setOnTabClickListenr(onTabClickListener: OnTabClickListener) {
        this.onTabClickListener = onTabClickListener
    }

    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val msptv = MySimplePagerTitleView(context)
        msptv.text = tabList[index]
        msptv.textSize = 16f
        msptv.setPadding(60, 0, 60, 0)
        msptv.normalColor = context!!.resources.getColor(R.color.text_2)
        msptv.selectedColor = context!!.resources.getColor(R.color.theme)
        msptv.setOnClickListener {
            if (null != onTabClickListener) {
                onTabClickListener?.onTabClick(it, index)
            }
        }

        msptv.setOnSelectedListener(object : MySimplePagerTitleView.OnSelectedListener{
            override fun onSelected(index: Int, totalCount: Int) {
                val tp = msptv.paint
                tp.isFakeBoldText = true
            }

            override fun deSelected(index: Int, totalCount: Int) {
                val tp = msptv.paint
                tp.isFakeBoldText = true
            }

        })

        return msptv
    }

    override fun getCount(): Int {
        return tabList.size
    }

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_EXACTLY
        indicator.lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
        indicator.lineWidth = UIUtil.dip2px(context, 20.0).toFloat()
        indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
        indicator.startInterpolator = AccelerateInterpolator()
        indicator.endInterpolator = DecelerateInterpolator(2.0f)
        indicator.setColors(context!!.resources.getColor(R.color.theme))
        return indicator
    }
}