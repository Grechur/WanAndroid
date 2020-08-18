package com.grechur.wanandroid.api;



import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.History;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.model.entity.UserInfo;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;
import com.grechur.wanandroid.model.entity.navigation.NaviArticle;
import com.grechur.wanandroid.model.entity.project.ProjectBean;
import com.grechur.wanandroid.model.entity.project.ProjectData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Chris on 2017/11/30.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 15000;

    String HOST = "https://www.wanandroid.com/";


    @FormUrlEncoded
    @POST("user/login")
    Observable<BasicResponse<UserInfo>> getLogin(@Field("username") String username, @Field("password") String password);


    @GET("article/list/{index}/json")
    Observable<BasicResponse<MainArticle>> getMainArticle(@Path("index") int index);

    @GET("banner/json")
    Observable<BasicResponse<List<BannerItem>>> getBanner();

    @GET("tree/json")
    Observable<BasicResponse<List<Knowledge>>> getKnowledge();

    @GET("navi/json")
    Observable<BasicResponse<List<NaviArticle>>> getNavigation();

    @GET("project/tree/json")
    Observable<BasicResponse<List<ProjectBean>>> getProject();

    @GET("project/list/{index}/json")
    Observable<BasicResponse<ProjectData>> getProjectData(@Path("index") int index, @Query("cid") int id);

//    http://www.wanandroid.com/article/list/0/json?cid=60
    @GET("article/list/{index}/json")
    Observable<BasicResponse<MainArticle>> getKnowArticle(@Path("index") int index, @Query("cid") int id);

    @GET("friend/json")
    Observable<BasicResponse<List<History>>> getMostUse();

    @GET("hotkey/json")
    Observable<BasicResponse<List<History>>> getHotCode();

    //http://www.wanandroid.com/article/query/0/json
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BasicResponse<MainArticle>> getSearchList(@Path("page") int page, @Field("k") String k);

}
