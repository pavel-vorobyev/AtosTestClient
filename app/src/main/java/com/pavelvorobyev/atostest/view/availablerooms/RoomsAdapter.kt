package com.pavelvorobyev.atostest.view.availablerooms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import kotlinx.android.synthetic.main.view_item_room.view.*

class RoomsAdapter : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    private var items: List<Room> = ArrayList()
    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder =
            RoomViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_item_room, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = items[position]

        holder.itemView.titleView.text = room.name
        holder.itemView.seatsCountView.text =
                holder.itemView.resources.getString(R.string.seats).format(room.seatsCount)
        holder.itemView.projectorView.visibility =
                when (room.withProjector) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }

        holder.itemView.boardView.visibility =
                when (room.withBoard) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }

        holder.itemView.setOnClickListener {
            if (callback != null)
                callback?.onItem(room)
        }
    }

    fun updateItems(items: List<Room>) {
        this.items = items
        notifyDataSetChanged()
    }

    class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface Callback {
        fun onItem(item: Room)
    }

}