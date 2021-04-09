package com.ori.learnsquare1.business.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.ImageUtil

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 14:37
 * 类说明:
 */
class ArticleAdapter(list: MutableList<ArticleValue.DatasBean>) :
    BaseMultiItemQuickAdapter<ArticleValue.DatasBean, BaseViewHolder>(list) {

    init {
        addItemType(Constant.ITEM_ARTICLE_NO_PIC, R.layout.article_item_no_pic)
        addItemType(Constant.ITEM_ARTICLE_WITH_PIC, R.layout.article_item_with_pic)
    }


    override fun convert(holder: BaseViewHolder, item: ArticleValue.DatasBean?) {
        when(holder.itemViewType) {
            //不带图片
            Constant.ITEM_ARTICLE_NO_PIC -> {
                item?.run {
                    if (type == 1) {
                        //置顶
                        holder.setGone(R.id.tv_top_flag, false)
                    }else {
                        holder.setGone(R.id.tv_top_flag, true)
                    }

                    holder.setText(R.id.tv_author, if (!TextUtils.isEmpty(author)) author else shareUser)
                    holder.setText(R.id.tv_publish_time, niceDate)
                    holder.setText(R.id.tv_title, title)
                    holder.setText(R.id.tv_article_tip, superChapterName)

                    holder.setImageResource(R.id.iv_collect, if (item.collect) R.mipmap.ic_collect else R.mipmap.ic_uncollect)
                }
            }

            //带图片
            Constant.ITEM_ARTICLE_WITH_PIC -> {
                item?.run {
                    //图片
                    ImageUtil.loadRadius(holder.getView(R.id.iv_title), envelopePic, 20)
                    holder.setText(R.id.tv_author, if (!TextUtils.isEmpty(author)) author else shareUser)
                    holder.setText(R.id.tv_publish_time, niceDate)
                    holder.setText(R.id.tv_title, title)
                    holder.setText(R.id.tv_article_tip, superChapterName)

                    holder.setImageResource(R.id.iv_collect, if (item.collect) R.mipmap.ic_collect else R.mipmap.ic_uncollect)
                }
            }
        }
        holder.addOnClickListener(R.id.iv_collect)
    }
}