package com.siiberad.newsapi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.siiberad.newsapi.R
import com.siiberad.newsapi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mvm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        mvm = ViewModelProvider(this).get(MainViewModel::class.java)
        mvm.getSources()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        fab.setOnClickListener {
            when (NavHostFragment.findNavController(nav_host_fragment).currentDestination?.id) {
                R.id.SecondFragment -> {
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_search_24
                        )
                    )
                    navController.navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
                else -> {
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_work_outline_24
                        )
                    )
                    navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
        }
    }
}