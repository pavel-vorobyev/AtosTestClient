package com.pavelvorobyev.atostest.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant

class MainActivity : BaseActivity<MainViewModel>() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.notificationService()

        val adapter = ViewPagerAdapter(supportFragmentManager,
            arrayOf(getString(R.string.available), getString(R.string.my)))
        viewPagerView.adapter = adapter
        tabLayout.setupWithViewPager(viewPagerView)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}
