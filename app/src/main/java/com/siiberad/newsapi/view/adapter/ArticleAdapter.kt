package com.siiberad.newsapi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siiberad.newsapi.R
import com.siiberad.newsapi.model.ArticlesItem
import kotlinx.android.synthetic.main.list_item_article.view.*

class ArticleAdapter(private var data: ArrayList<ArticlesItem>) :
    RecyclerView.Adapter<ArticleAdapter.Holder>() {
    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(data: ArticlesItem?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_article, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onClick(data[position])
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: ArticlesItem) {
            itemView.txt_date.text = data.publishedAt
            itemView.txt_title.text = data.title
            itemView.txt_desc.text = data.description
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .into(itemView.img_bg)
        }
    }

    fun refreshAdapter(l: List<ArticlesItem>) {
        this.data.addAll(l)
        notifyItemRangeChanged(0, this.data.size)
    }
}