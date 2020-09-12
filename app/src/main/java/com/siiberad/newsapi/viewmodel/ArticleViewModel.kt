package com.siiberad.newsapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.siiberad.newsapi.model.ArticlesItem
import com.siiberad.newsapi.model.NewsModel
import com.siiberad.newsapi.services.NewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class ArticleViewModel(application: Application) : BaseViewModel(application) {

    private val newsService = NewsService()
    private val disposable = CompositeDisposable()

    var page = 1
    var totalPage = 100

    val dataArticle = MutableLiveData<List<ArticlesItem>>()
    val dataArticleAdd = MutableLiveData<List<ArticlesItem>>()
    val loading = MutableLiveData<Boolean>()

    fun getDataArticle(source: String) {
        fetchArticle(source)
        loading.postValue(true)
    }

    fun fetchArticle(source: String) {
        disposable.add(
            newsService.getArticle(source, page.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<NewsModel>() {
                    override fun onError(e: Throwable) {
                        Log.d("d", e.message.toString())
                        loading.postValue(false)
                    }

                    override fun onNext(t: NewsModel) {
                        if (page == 1)
                            articleDataRecieved(t.articles!!)
                        else
                            articleAdd(t.articles!!)
                    }

                    override fun onComplete() {}
                })
        )
    }

    private fun articleDataRecieved(d: List<ArticlesItem>) {
        launch { dataArticle.postValue(d) }
    }

    private fun articleAdd(g: List<ArticlesItem>) {
        launch {
            dataArticleAdd.postValue(g)
        }
    }
}