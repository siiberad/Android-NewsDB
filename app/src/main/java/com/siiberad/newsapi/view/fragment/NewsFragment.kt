package com.siiberad.newsapi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.siiberad.newsapi.R
import com.siiberad.newsapi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    lateinit var mvm: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            mvm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        }
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        mvm.sourcesItem.observe(viewLifecycleOwner, { list ->
            val groupSource = list.groupBy { it.category }
            val keys = groupSource.map { it.key }
            viewPager.adapter = NewsAdapter(childFragmentManager, keys)
            tablayout.setupWithViewPager(viewPager)
            tablayout.addOnTabSelectedListener(onTabSelected())
            for (i in 0 until tablayout.tabCount) {
                tablayout.getTabAt(i)
            }
        })
    }

    private fun onTabSelected() = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            viewPager!!.currentItem = tab!!.position
        }

        override fun onTabReselected(p0: TabLayout.Tab?) {}

        override fun onTabUnselected(p0: TabLayout.Tab?) {}
    }

    class NewsAdapter(fm: FragmentManager, private val categories: List<String?>) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(page: Int): Fragment {
            return NewsInsideFragment(categories[page]!!)
        }

        override fun getCount(): Int = categories.size

        override fun getPageTitle(page: Int): CharSequence? {
            return categories[page]
        }
    }

}