package com.siiberad.newsapi.services

import com.siiberad.newsapi.BuildConfig
import com.siiberad.newsapi.model.NewsModel
import com.siiberad.newsapi.model.SourceModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsService {
    private val api = Retrofit.Builder()
        .baseUrl(BuildConfig.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsApi::class.java)

    fun getSources(): Single<SourceModel> {
        return api.getSources()
    }

    fun searchArticle(q: String, pageSize: String): Single<NewsModel> {
        return api.searchArticle(q, pageSize)
    }

    fun getArticle(sources: String, page: String): Observable<NewsModel> {
        return api.getArticle(sources, page)
    }


}