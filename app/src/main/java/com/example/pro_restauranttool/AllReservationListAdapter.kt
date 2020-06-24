package com.example.pro_restauranttool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.all_reservations_card.view.*

class AllReservationListAdapter(private val list: List<ReservationData>): RecyclerView.Adapter<AllReservationListAdapter.ViewHolder>() {
    private lateinit var parentView: ViewGroup


    interface DeleteReservationListener {
        fun deleteReservation(reservationId: String, tableId: Int, allList:Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReservationListAdapter.ViewHolder {
        parentView = parent
        val v = LayoutInflater.from(parent.context).inflate(R.layout.all_reservations_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var time = "${list[position].date}, ${list[position].from} - ${list[position].till}"
        holder.time.text = time
        if(list[position].date == Time.getTodaysDate()) {
            if(list[position].from.compareTime(Time.getTime())) {
                holder.view.setBackgroundResource(R.drawable.card_bg_now)
            } else {
                holder.view.setBackgroundResource(R.drawable.card_bg_today)
            }
        }
        var name = list[position].name
        if(name == "") {
            holder.name.text = "--"
        } else {
            holder.name.text = name
        }
        holder.table.text = list[position].id.toString()
        holder.remove.setOnClickListener {
            var testCont = parentView.context
            ((parentView).context as DeleteReservationListener).deleteReservation(list[position].reservationId, list[position].id, true)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view: View = v
        var time = view.dateTimeReservation
        var remove = view.removeReservationFromAll
        var name = view.reservationName
        var table = view.tableId
    }
}