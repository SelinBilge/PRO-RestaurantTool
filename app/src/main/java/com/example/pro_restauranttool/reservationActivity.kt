package com.example.pro_restauranttool

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reservation.*

class reservationActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)



        switch3.setOnClickListener(this)


        button3.setOnClickListener{

            //read value from EditText to a String variable
            val msg1: String = editText2.text.toString()
            val msg2: String = editText3.text.toString()
            val msg3: String = editText4.text.toString()

            //check if the EditText have values or not
            if (msg1.trim().length>0 && msg2.trim().length>0 && msg3.trim().length>0){
                checkInput()
                checkReservation()

            } else {
                Toast.makeText(this, "Eingabe fehlt", Toast.LENGTH_SHORT).show()
            }

        }


        button4.setOnClickListener{

            //read value from EditText to a String variable
            val msg1: String = editText2.text.toString()
            val msg2: String = editText3.text.toString()
            val msg3: String = editText4.text.toString()

            // input doesn't exists -> go to main again
            if (msg1.trim().length==0 && msg2.trim().length==0 && msg3.trim().length==0){

                val backToMain = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(backToMain)


            // else clear activity
            } else {

                val cancelReservation = Intent(this, reservationActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(cancelReservation)
            }

        }


    }

    private fun checkInput() {

        // kontrolle ob eingaben richtig gemacht wurden

    }


    private fun checkReservation() {

        // kontrolle ob zu dieser Zeit noch ein Platz frei ist
        val test = false

        if (!test){
            Toast.makeText(this, "Zu dieser Zeit gibt es keinen freien Tisch", Toast.LENGTH_SHORT).show()

        } else {
            // neue Reservierung anlegen
        }

    }


    override fun onClick(v: View?) {

        when(switch3.isChecked){

            true -> {
                switch3.text = "Ja"
                // Plätze im Gastgarten vorschlagen

            }

            false -> {
                switch3.text = "Nein"
                // Plätze drinnen
            }

        }
    }


}
