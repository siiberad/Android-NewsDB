package com.siiberad.newsapi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.siiberad.newsapi.R
import com.siiberad.newsapi.model.SourcesItem
import com.siiberad.newsapi.view.ArticleActivity
import com.siiberad.newsapi.view.adapter.PageAdapter
import com.siiberad.newsapi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_news_inside.*

class NewsInsideFragment(private val category: String) : Fragment() {

    lateinit var mvm: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            mvm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        }
        return inflater.inflate(R.layout.fragment_news_inside, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        mvm.sourcesItem.observe(viewLifecycleOwner, {
            val groupSource = it.groupBy { it.category }
            val pageAdapter = PageAdapter(groupSource[category] ?: error(""))
            pageAdapter.mOnItemClickListener = object : PageAdapter.OnItemClickListener {
                override fun onClick(data: SourcesItem?) {
                    if (data?.id != null) activity?.let {
                        ArticleActivity.show(
                            it,
                            data.id.toString()
                        )
                    }
                    else Toast.makeText(context, "Id tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            rv_news.apply {
                layoutManager = LinearLayoutManager(context)
                pageAdapter.notifyDataSetChanged()
                adapter = pageAdapter
            }
        })
    }
}