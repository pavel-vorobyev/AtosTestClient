package com.pavelvorobyev.atostest.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pavelvorobyev.atostest.R
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM: BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
            .get((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
        viewModel.initDI()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    @LayoutRes
    abstract fun getLayout(): Int

}