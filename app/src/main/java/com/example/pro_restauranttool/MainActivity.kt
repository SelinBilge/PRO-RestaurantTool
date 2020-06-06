package com.example.pro_restauranttool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seats.setOnClickListener{
            Log.i("LOG", "seats was clicked")
            val seat = Intent(this, seatActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(seat)

        }


        menu.setOnClickListener{
            Log.i("LOG", "menu was clicked")
            val menu = Intent(this, menuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(menu)
        }


        reservation.setOnClickListener{
            Log.i("LOG", "reservation was clicked")
            val reservation = Intent(this, reservationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(reservation)
        }


        statistic.setOnClickListener{
            Log.i("LOG", "statistic was clicked")
            val statistic = Intent(this, statisticActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(statistic)
        }



    }



}