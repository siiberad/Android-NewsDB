package com.siiberad.newsapi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class NewsModel(
    val totalResults: Int? = null,
    val articles: List<ArticlesItem>? = null,
    val status: String? = null
)

@Parcelize
data class Source(
    val name: String? = null,
    val id: @RawValue Any? = null
) : Parcelable

@Parcelize
data class ArticlesItem(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
) : Parcelable

