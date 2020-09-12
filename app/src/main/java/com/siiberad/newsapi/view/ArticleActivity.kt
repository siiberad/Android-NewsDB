package com.siiberad.newsapi.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siiberad.newsapi.R
import com.siiberad.newsapi.model.ArticlesItem
import com.siiberad.newsapi.view.adapter.ArticleAdapter
import com.siiberad.newsapi.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    lateinit var avm: ArticleViewModel

    companion object {
        fun show(activity: Activity, id: String?) {
            val intent = Intent(activity, ArticleActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putString("ID", id)
                })
            }
            activity.startActivityForResult(intent, 91)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_article)
        avm = ViewModelProvider(this).get(ArticleViewModel::class.java)
        val id = intent?.extras?.getString("ID").toString()
        avm.getDataArticle(id)
        initObverse()
        initListener(id)

    }

    private fun initObverse() {
        avm.loading.observe(this, {
            if (it) Toast.makeText(this, "Loading ..", Toast.LENGTH_SHORT).show()
        })
        avm.dataArticle.observe(this, {
            val articleAdapter = ArticleAdapter(it as ArrayList<ArticlesItem>)
            articleAdapter.mOnItemClickListener = object : ArticleAdapter.OnItemClickListener {
                override fun onClick(data: ArticlesItem?) {
                    DetailActivity.show(this@ArticleActivity, data)
                }
            }

            avm.dataArticleAdd.observe(this, {
                articleAdapter.refreshAdapter(it)
            })

            rv_article.apply {
                layoutManager = LinearLayoutManager(context)
                articleAdapter.notifyDataSetChanged()
                adapter = articleAdapter
            }
        })
    }

    private fun initListener(id: String) {
        rv_article.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (isLastPosition && avm.page < avm.totalPage) {
                    avm.page = avm.page.plus(1)
                    avm.fetchArticle(id)
                }
            }
        })
    }
}