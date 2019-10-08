package com.pavelvorobyev.atostest.view.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.businesslogic.repository.Status
import com.pavelvorobyev.atostest.view.base.BaseActivity
import com.pavelvorobyev.atostest.view.main.MainActivity
import com.pavelvorobyev.atostest.view.signin.SignInActivity

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.response.observe(this,
            Observer {
                consumeResponse(it)
            })

        viewModel.getAuthorizations()
    }

    private fun consumeResponse(r: RepositoryResponse<Int>) {
        when (r.status) {
            Status.SUCCESS -> consumeSuccess(r.data!!)
            else -> consumeError()
        }
    }

    private fun consumeSuccess(c: Int) {
        Handler().postDelayed({
            when  {
                c >= 1 -> MainActivity.start(this)
                else -> SignInActivity.start(this)
            }
        }, 1500)
    }

    private fun consumeError() {
        SignInActivity.start(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }
}