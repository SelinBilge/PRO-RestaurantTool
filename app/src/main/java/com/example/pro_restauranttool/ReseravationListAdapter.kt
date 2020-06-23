package com.example.pro_restauranttool

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.restaurant_list_card.view.*

class ReservationListAdapter(private val list: List<ReservationData>): RecyclerView.Adapter<ReservationListAdapter.viewHolder>() {
    private lateinit var parentView: ViewGroup


    interface DeleteReservationListener {
        fun deleteReservation(reservationId: String, tableId: Int, allList: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationListAdapter.viewHolder {
        parentView = parent
        val v = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_card, parent, false)
        return viewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var time = "${list[position].date}, ${list[position].from} - ${list[position].till}"
        holder.time.text = time
        holder.remove.setOnClickListener {
            var testCont = parentView.context
            ((parentView).context as DeleteReservationListener).deleteReservation(list[position].reservationId, list[position].id, false)
        }
    }

    class viewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view: View = v
        var time = view.reservationTime
        var remove = view.removeReservation
    }
}