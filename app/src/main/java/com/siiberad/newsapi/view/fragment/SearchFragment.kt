package com.siiberad.newsapi.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.siiberad.newsapi.R
import com.siiberad.newsapi.model.ArticlesItem
import com.siiberad.newsapi.view.DetailActivity
import com.siiberad.newsapi.view.adapter.ArticleAdapter
import com.siiberad.newsapi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    lateinit var mvm: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            mvm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        }
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_search.isFocusableInTouchMode = true;
        et_search.requestFocus()
        et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                mvm.getSearch(et_search.text.toString())
                return@OnKeyListener true
            }
            false
        })
        initObserve()
    }

    private fun initObserve() {
        mvm.searchItem.observe(viewLifecycleOwner, {
            val articleAdapter = ArticleAdapter(it as ArrayList<ArticlesItem>)
            articleAdapter.mOnItemClickListener = object : ArticleAdapter.OnItemClickListener {
                override fun onClick(data: ArticlesItem?) {
                    activity?.let { it -> DetailActivity.show(it, data) }
                }
            }
            rv_search.apply {
                layoutManager = LinearLayoutManager(context)
                articleAdapter.notifyDataSetChanged()
                adapter = articleAdapter
            }
        })
    }
}