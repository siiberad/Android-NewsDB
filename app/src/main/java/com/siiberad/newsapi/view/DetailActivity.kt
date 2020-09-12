package com.siiberad.newsapi.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.siiberad.newsapi.R
import com.siiberad.newsapi.model.ArticlesItem
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar_webview.*

class DetailActivity : AppCompatActivity() {

    companion object {
        fun show(activity: Activity, articlesItem: ArticlesItem?) {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putExtra("DATA", articlesItem)
                })
            }
            activity.startActivityForResult(intent, 91)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()
        init()
    }

    private fun init() {
        val data = intent?.extras?.getParcelable<ArticlesItem>("DATA")
        Glide.with(this).load(data?.urlToImage).into(img_cover)
        txt_date.text = data?.publishedAt?.dropLast(10)
        txt_author.text = data?.author
        txt_title.text = data?.title
        txt_desc.text = data?.description
        txt_link.setOnClickListener { WebViewActivity.show(this, data?.url) }
        txt_toolbar.text = data?.source?.name
        backBtn.setOnClickListener { finish() }
    }
}