package com.ori.learnsquare1.business.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 9:56
 * 类说明:
 */
@Entity(tableName = "browse_history")
class BrowseHistoryValue() {

    //ID 表示标识主键 不能用int
    // autoincrement = true 表示主键会自增
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id", typeAffinity = ColumnInfo.INTEGER, index = true)
    @NonNull
    var _id: Int = 0

    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.TEXT)
    var userId: String? = ""

    @ColumnInfo(name = "article_id", typeAffinity = ColumnInfo.INTEGER)
    var articleId: Int = 0

    @ColumnInfo(name = "article_title", typeAffinity = ColumnInfo.TEXT)
    var articleTitle: String? = ""

    @ColumnInfo(name = "article_author", typeAffinity = ColumnInfo.TEXT)
    var articleAuthor: String? = ""

    @ColumnInfo(name = "article_publish_time", typeAffinity = ColumnInfo.TEXT)
    var articlePublishTime: String? = ""

    @ColumnInfo(name = "article_chapter", typeAffinity = ColumnInfo.TEXT)
    var articleChapter: String? = ""

    @ColumnInfo(name = "article_link", typeAffinity = ColumnInfo.TEXT)
    var articleLink: String? = ""

    @ColumnInfo(name = "article_link_pic", typeAffinity = ColumnInfo.TEXT)
    var articleLinkPic:String? = ""

    @ColumnInfo(name = "article_collect_status", typeAffinity = ColumnInfo.INTEGER)
    var articleCollect: Int = 0

    @ColumnInfo(name = "browse_time", typeAffinity = ColumnInfo.TEXT)
    var browseTime: String? = ""


    constructor(_id: Int, userId: String, articleId: Int, articleTitle: String,
                articleAuthor: String, articlePublishTime: String, articleChapter: String,
                articleLink: String, articleLinkPic:String, articleCollect: Int, browseTime: String) : this() {
        this._id = _id
        this.userId = userId
        this.articleId = articleId
        this.articleTitle = articleTitle
        this.articleAuthor = articleAuthor
        this.articlePublishTime = articlePublishTime
        this.articleChapter = articleChapter
        this.articleLink = articleLink
        this.articleLinkPic = articleLinkPic
        this.articleCollect = articleCollect
        this.browseTime = browseTime
    }



    override fun toString(): String {
        return  "{HistoryValue: id:$_id, userId:$userId, articleId:$articleId, " +
                "articleTitle:$articleTitle, articleAuthor:$articleAuthor, articlePublishTime:$articlePublishTime, " +
                "articleChapter:$articleChapter, articleLink:$articleLink, articleLinkPic:$articleLinkPic, " +
                "articleCollect:$articleCollect,  browseTime:$browseTime}"
    }
}