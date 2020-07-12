package com.ori.learnsquare1.business.util

import com.ori.learnsquare.business.entity.*
import com.ori.learnsquare1.common.base.http.BaseResponse
import retrofit2.http.*

/**
 * 创建人 zhengpf
 * 时间 2020/7/2
 * 类说明:
 */
interface ApiService {


    /*************************************首页 start*******************************************/
    /**
     * 热门模块
     * 获取文章列表
     * 参数说明:
     * page：页码，拼接在连接中，从0开始。
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResponse<ArticleValue>


    /**
     * 热门模块
     * 获取首页置顶文章数据
     */
    @GET("/article/top/json")
    suspend fun getTopArticleList(): BaseResponse<MutableList<ArticleValue.DatasBean>>

    /**
     * 首页
     * 轮播图
     */
    @GET("/banner/json")
    suspend fun getBannerList(): BaseResponse<MutableList<BannerValue>>


    /**
     * 最新
     * 文章列表
     */
    @GET("/article/listproject/{page}/json")
    suspend fun getLastestList(@Path("page") page: Int): BaseResponse<ArticleValue>

    /**
     * 广场
     * 文章列表
     */
    @GET("/user_article/list/{page}/json")
    suspend fun getUserArticleList(@Path("page") page: Int): BaseResponse<ArticleValue>



    /**
     * 项目
     * tab
     */
    @GET("/project/tree/json")
    suspend fun getProjectTabList(): BaseResponse<MutableList<TabValue>>


    /**
     * 项目
     * 文章列表
     */
    @GET("/project/list/{pageNum}/json")
    suspend fun getProjectList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int) : BaseResponse<ArticleValue>

    /**
     * 获取公众号 tab
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getAccountsTabList(): BaseResponse<MutableList<TabValue>>


    /**
     * 获取公众号下的文章列表
     * 参数说明:
     * cid:公众号 ID,拼接在 url 中，eg:405
     * pageNum:公众号页码,拼接在url 中，eg:1
     */
    @GET("/wxarticle/list/{id}/{pageNum}/json")
    suspend fun getAccountList(@Path("id") cid: Int, @Path("pageNum") pageNum: Int): BaseResponse<ArticleValue>







    /*************************************首页 start*******************************************/



    /**
     * 体系tab列表
     */
    @GET("/tree/json")
    suspend fun getSystemTabList() : BaseResponse<MutableList<SystemValue>>


    /**
     * 体系tab下的文章
     * 参数说明:
     * cid:分类的id，上述二级目录的id
     * pageNum:页码,拼接在链接上，从0开始。
     */
    @GET("/article/list/{pageNum}/json")
    suspend fun getSystemCateArticle(@Path("pageNum") pageNum:Int, @Query("cid") cid:Int)
            : BaseResponse<ArticleValue>





    /**
     * 导航
     */
    @GET("/navi/json")
    suspend fun getNavigationTabList() : BaseResponse<MutableList<NavigationValue>>

















    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): BaseResponse<UserValue>


    /**
     * 登出
     */
    @GET("/user/logout/json")
    fun logout(): BaseResponse<Any>


    /**
     * 注册
     */
    @POST("/user/register")
    fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): BaseResponse<Any>


    /**
     * 获取个人积分
     */
    @GET("/lg/coin/userinfo/json")
    fun getAccountData(): BaseResponse<UserAccountValue>


    /**
     * 排名
     */
    @GET("/coin/rank/{pageNum}/json")
    fun getMyRank(@Path("pageNum") pageNum: Int): BaseResponse<RankValue>

    /**
     * 积分记录
     */
    @GET("/lg/coin/list/{pageNum}/json")
    fun getIntegralRecord(@Path("pageNum") pageNum: Int): BaseResponse<IntegrationRecordValue>

    /**
     * 我分享的文章
     */
    @GET("/user/lg/private_articles/{pageNum}/json")
    fun getMyArticle(@Path("pageNum") pageNum: Int): BaseResponse<MyArticleValue>

    /**
     * 我分享的文章
     */
    @POST("/lg/user_article/delete/{id}/json")
    fun deleteMyArticle(@Path("id") id: Int): BaseResponse<Any>

    /**
     * 分享文章
     */
    @POST("/lg/user_article/add/json")
    fun shareArticle(
        @Query("title") title: String,
        @Query("link") link: String
    ): BaseResponse<Any>


    /**
     * 获取收藏文章数据
     */
    @GET("/lg/collect/list/{page}/json")
    fun getMyCollectData(@Path("page") pageNo: Int): BaseResponse<CollectValue>


    /**
     * 收藏
     */
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id")id:Int):BaseResponse<Any>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: Int): BaseResponse<Any>
}