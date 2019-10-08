package com.pavelvorobyev.atostest.view.myrooms

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavelvorobyev.atostest.BuildConfig
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.businesslogic.repository.Status
import com.pavelvorobyev.atostest.view.availablerooms.RoomsAdapter
import com.pavelvorobyev.atostest.view.base.BaseFragment
import com.pavelvorobyev.atostest.view.room.RoomActivity
import kotlinx.android.synthetic.main.fragment_rooms.*

class MyRoomsFragment : BaseFragment<MyRoomsViewModel>() {

    private val adapter = RoomsAdapter()

    @SuppressLint("NewApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.callback = object : RoomsAdapter.Callback {
            override fun onItem(item: Room) {
                RoomActivity.start(activity!!, item)
            }
        }

        rootView.layoutManager = LinearLayoutManager(activity)
        rootView.adapter = adapter

        viewModel.response.observe(this,
            Observer {
                consumeResponse(it)
            })

        swipeRefreshLayoutView.setColorSchemeColors(
            when (BuildConfig.VERSION_CODE >= 23) {
                true -> resources.getColor(R.color.colorPrimary, activity?.theme)
                false -> resources.getColor(R.color.colorPrimary)
            })
        swipeRefreshLayoutView.setOnRefreshListener {
            viewModel.getMyRooms()
        }

        viewModel.getMyRooms()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_rooms
    }

    private fun consumeResponse(response: RepositoryResponse<List<Room>>) {
        when (response.status) {
            Status.LOADING -> consumeLoading()
            Status.SUCCESS -> consumeSuccess(response.data!!)
            Status.ERROR -> consumeError(response.error!!)
        }
    }

    private fun consumeLoading() {

    }

    private fun consumeSuccess(rooms: List<Room>) {
        swipeRefreshLayoutView.isRefreshing = false
        adapter.updateItems(rooms)
    }

    private fun consumeError(error: ApiError) {
        swipeRefreshLayoutView.isRefreshing = false
    }

}