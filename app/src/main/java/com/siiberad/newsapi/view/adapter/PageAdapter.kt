package com.siiberad.newsapi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siiberad.newsapi.R
import com.siiberad.newsapi.model.SourcesItem
import kotlinx.android.synthetic.main.list_item.view.*

class PageAdapter(private var data: List<SourcesItem>) :
    RecyclerView.Adapter<PageAdapter.Holder>() {
    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(data: SourcesItem?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
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
        fun bind(data: SourcesItem) {
            itemView.txt_category.text = data.category
            itemView.txt_name.text = data.name
            itemView.txt_desc.text = data.description
        }
    }
}