package com.siiberad.newsapi.services

import com.siiberad.newsapi.BuildConfig
import com.siiberad.newsapi.model.NewsModel
import com.siiberad.newsapi.model.SourceModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("X-Api-Key:" + BuildConfig.API_KEY)
    @GET(URL.GET_SOURCE)
    fun getSources(): Single<SourceModel>

    @Headers("X-Api-Key:" + BuildConfig.API_KEY)
    @GET(URL.GET_ARTICLE_BY_SOURCE)
    fun searchArticle(
        @Query("q") q: String,
        @Query("pageSize") pageSize: String
    ): Single<NewsModel>

    @Headers("X-Api-Key:" + BuildConfig.API_KEY)
    @GET(URL.GET_ARTICLE_BY_SOURCE)
    fun getArticle(
        @Query("sources") sources: String,
        @Query("page") page: String
    ): Observable<NewsModel>
}