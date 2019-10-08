package com.pavelvorobyev.atostest.view.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.reactivex.Observable
import java.lang.Exception
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        viewModel = ViewModelProviders.of(this)
            .get((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
        viewModel.initDI()
    }

    protected fun setActionBarTitle(title: String) {
        try {
            supportActionBar?.title = title
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun enableBackButton() {
        try {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    @LayoutRes
    abstract fun getLayout(): Int

}