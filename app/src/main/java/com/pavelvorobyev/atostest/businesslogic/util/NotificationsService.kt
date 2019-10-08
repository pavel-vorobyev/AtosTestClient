package com.pavelvorobyev.atostest.businesslogic.util

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pavelvorobyev.atostest.AtosTest
import com.pavelvorobyev.atostest.businesslogic.repository.AuthorizationRepository
import com.pavelvorobyev.atostest.di.module.ServiceModule
import javax.inject.Inject
import android.app.ActivityManager
import android.content.Context
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.pojo.Reservation
import com.pavelvorobyev.atostest.view.room.RoomActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotificationsService : Service() {

    companion object {

        fun isRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (NotificationsService::class.java.name == service.service.className) {
                    return true
                }
            }
            return false
        }

        fun run(context: Context) {
            val intent = Intent(context, NotificationsService::class.java)
            context.startService(intent)
        }

    }

    private val component by lazy {
        (application as AtosTest).applicationComponent
            .plus(ServiceModule())
    }

    @Inject
    lateinit var repository: AuthorizationRepository

    private lateinit var socket: Socket
    private val gson = Gson()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        component.inject(this)

        socket = IO.socket("https://atos.maflic.com/v1/notifications")
        socket.connect()
        println("CONNECTED ${socket.connected()}")

        socket.on("message") {
            println(it[0].toString())
            val reservation = gson.fromJson(it[0].toString(), Reservation::class.java)

            val title = when (reservation.status) {
                "confirmed" -> applicationContext.getString(R.string.your_request_has_been_accepted)
                "declined" -> applicationContext.getString(R.string.your_request_has_been_declined)
                else -> ""
            }

            val message = applicationContext.getString(R.string.office_manager_sent_you_a_feedback)
            val intent = Intent(applicationContext, RoomActivity::class.java).apply {
                putExtra("room", reservation.roomId)
            }

            NotificationsManager.showNotification(applicationContext, intent, title, message)
        }

        repository.getAuthorizationsFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                socket.emit("authorize", it[0].user.u_id)
            }
    }

    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socket.disconnect()
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

}