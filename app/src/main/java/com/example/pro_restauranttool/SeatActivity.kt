package com.example.pro_restauranttool

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_seat.*


class SeatActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)


        getWaitingTime()

        switch1.setOnClickListener(this)

        button.setOnClickListener{

            //read value from EditText to a String variable

            val msg: String = editText.text.toString()

            //check if the EditText have values or not
            if (msg.trim().length>0){

                val seat = Intent(this, tablesActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(seat)

            } else {
                Toast.makeText(this, "Eingabe fehlt", Toast.LENGTH_SHORT).show()
            }

        }


        getTable()


        button2.setOnClickListener{

            val msg: String = editText.text.toString()


            // input exists -> clear activity
            if (msg.trim().length>0){

                val seat = Intent(this, SeatActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(seat)

            // else go to main again
            } else {
                val backToMain = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(backToMain)
            }

        }


    }

    override fun onClick(v: View?) {

        when(switch1.isChecked){

            true -> {

                switch1.text = "Ja"
                // Plätze im Gastgarten vorschlagen

            }

            false -> {
                switch1.text = "Nein"
                // Plätze drinnen
            }

        }

    }


    fun getTable (){

        // get random empty table

        val tablenumber = 4

        tableNummer.setText("$tablenumber")

    }


    fun getWaitingTime(){

        // app gets waiting Time from occupied seats

        val number = 7

        waitingTime.setText("$number min")

    }


}