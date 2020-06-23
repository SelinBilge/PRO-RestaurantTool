package com.example.pro_restauranttool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //createTables()

        seats.setOnClickListener{
            Log.i("LOG", "seats was clicked")
            val seat = Intent(this, TablesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(seat)

        }


        menu.setOnClickListener{
            Log.i("LOG", "menu was clicked")
            val menu = Intent(this, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(menu)
        }


        reservation.setOnClickListener{
            Log.i("LOG", "reservation was clicked")
            val reservation = Intent(this, ReservationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(reservation)
        }


        statistic.setOnClickListener{
            Log.i("LOG", "statistic was clicked")
            val statistic = Intent(this, StatisticActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(statistic)
        }



    }

    fun createTables() {
        val tables = arrayListOf<Any>()
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 2,
            "table_id" to 0
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 2,
            "table_id" to 1
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 2,
            "table_id" to 2
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 8,
            "table_id" to 3
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 6,
            "table_id" to 4
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 4,
            "table_id" to 5
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 4,
            "table_id" to 6
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 4,
            "table_id" to 7
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 4,
            "table_id" to 8
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 6,
            "table_id" to 9
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 2,
            "table_id" to 10
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 2,
            "table_id" to 11
        ))
        tables.add(hashMapOf(
            "kids_table" to false,
            "outdoors" to false,
            "seats" to 4,
            "table_id" to 12
        ))
        for(i in 1 until tables.size) {
            var testValue = tables.get(i)
            var testIndex = i
            db.collection("table").document(i.toString()).set(testValue)
        }
    }



}