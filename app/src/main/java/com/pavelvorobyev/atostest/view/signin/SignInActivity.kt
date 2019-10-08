package com.pavelvorobyev.atostest.view.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.pojo.Authorization
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.businesslogic.repository.Status
import com.pavelvorobyev.atostest.view.base.BaseActivity
import com.pavelvorobyev.atostest.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity<SignInViewModel>() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

    }

    private lateinit var errorDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.response.observe(this,
            Observer {
                consumeResponse(it)
            })

        usernameInputView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onUsername(s.toString())
                setSignInBtnState()
            }

        })

        passwordInputView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswod(s.toString())
                setSignInBtnState()
            }

        })

        errorDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setPositiveButton("OK") { _, _ ->

            }
            .create()

        signInBtnView.setOnClickListener {
            viewModel.signIn()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_sign_in
    }

    private fun setSignInBtnState() {
        signInBtnView.isEnabled = usernameInputView.text.toString().isNotEmpty() &&
                passwordInputView.text.toString().isNotEmpty()
    }

    private fun consumeResponse(r: RepositoryResponse<Authorization>) {
        when (r.status) {
            Status.LOADING -> consumeLoading()
            Status.SUCCESS -> MainActivity.start(this)
            Status.ERROR -> consumeError(r.error)
        }
    }

    private fun consumeLoading() {
        signInBtnView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun consumeError(e: ApiError?) {
        progressBar.visibility = View.GONE
        signInBtnView.visibility = View.VISIBLE

        when {
            e?.pointer == "data" && e.reason == "WRONG" -> {
                errorDialog.apply {
                    setMessage(getString(R.string.wrong_username_or_password))
                    show()
                }
            }
            else -> {
                errorDialog.apply {
                    setMessage(getString(R.string.unknown_error))
                    show()
                }
            }
        }
    }

}