package com.siiberad.newsapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.siiberad.newsapi.model.ArticlesItem
import com.siiberad.newsapi.model.NewsModel
import com.siiberad.newsapi.model.SourceModel
import com.siiberad.newsapi.model.SourcesItem
import com.siiberad.newsapi.services.NewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val newsService = NewsService()
    private val disposable = CompositeDisposable()

    val sourcesItem = MutableLiveData<List<SourcesItem>>()
    fun getSources() = fetchSource()
    private fun fetchSource() {
        disposable.add(
            newsService.getSources()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SourceModel>() {
                    override fun onSuccess(t: SourceModel) {
                        resourseRecieved(t.sources!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("d", e.message.toString())

                    }
                })
        )
    }

    private fun resourseRecieved(d: List<SourcesItem>) {
        launch { sourcesItem.postValue(d) }
    }

    val searchItem = MutableLiveData<List<ArticlesItem>>()
    fun getSearch(key_word: String) = fetchSearch(key_word)
    private fun fetchSearch(key_word: String) {
        disposable.add(
            newsService.searchArticle(key_word, "100")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsModel>() {
                    override fun onSuccess(t: NewsModel) {
                        searchRecieved(t.articles!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("d", e.message.toString())

                    }
                })
        )
    }

    private fun searchRecieved(d: List<ArticlesItem>) {
        launch { searchItem.postValue(d) }
    }
}