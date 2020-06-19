package com.example.pro_restauranttool

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.activity_seat.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class ReservationActivity : AppCompatActivity(), View.OnClickListener {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        // access Items of the list
        val items = resources.getStringArray(R.array.SpinnerItems)


        switch3.setOnClickListener(this)

        // Calender
        editText2.setOnClickListener(this)

        // Timer
        editText3.setOnClickListener(this)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, items)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(this@ReservationActivity,
                        getString(R.string.selected_item), Toast.LENGTH_SHORT).show()
                 }

                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@ReservationActivity,
                        getString(R.string.selected_item) + " " +
                                "" + items[position], Toast.LENGTH_SHORT).show()
                }

            }
        }


        getWaitingTime()

        getTable()


        // Ok button
        button3.setOnClickListener{

            //read value from EditText to a String variable
            val msg1: String = editText2.text.toString()
            val msg2: String = editText3.text.toString()
            val msg3: String = editText4.text.toString()


            //check if the EditText have values or not
            if (msg1.trim().length>0 && msg2.trim().length>0 && msg3.trim().length>0 && spinner.selectedItem != null){

                checkInput()
                checkReservation()

            } else {
                Toast.makeText(this, "Eingabe fehlt", Toast.LENGTH_SHORT).show()
            }

        }


        // Abbrechen Button
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

                val cancelReservation = Intent(this, ReservationActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(cancelReservation)

            }

        }


    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkInput() {

        val maxSeats: Int = 150 // number of persons is not greater than max

        val timeFormat: String = "HH:mm:ss"  // check if input is in right format
        var start: LocalTime = LocalTime.parse("11:30:00")
        val stop: LocalTime = LocalTime.parse("23:00:00")
        val target = LocalTime.parse(editText3.text.toString())

        val  TimeisBetweenStartAndStop: Boolean = ((!target.isBefore(start) && target.isBefore(stop)))


        // check time
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyy HH:mm")
        val formatted = current.format(formatter)


       // DateUtils.isSameDay(editText2.text,formatted);
       // DateUtils.isSameDay(calender1,calender2);
       // DateUtils.isToday(date1);

            // abfrage funktioniert noch nicht ganz wenn man die uhrzeit am heutigen tag
            // kontollieren möchte (zb heute ist es 16:00 -> es kann kein tisch um 15:00 vorgeschlagen werden)

            // check if the reservation is on the same day
            if(editText2.text.toString() == formatted.toString()){


                // new min time
                var currentDateTime=LocalDateTime.now()
                var time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

                start = LocalTime.parse(time)

                // check if selected time is in timespan
                if (!TimeisBetweenStartAndStop){
                    editText3.text.clear()
                    Toast.makeText(this, "Überprüfe die Uhrzeit", Toast.LENGTH_SHORT).show()
                }

            // if reservation is not on the same day
            // check if reservation is in the opening hours
            } else if (!TimeisBetweenStartAndStop){

                editText3.text.clear()
                Toast.makeText(this, "Überprüfe die Uhrzeit", Toast.LENGTH_SHORT).show()

            }


         // check persons
         if(editText4.text.toString().toInt() > maxSeats ){
            Toast.makeText(this, "Überprüfe die Anzahl der Personen", Toast.LENGTH_SHORT).show()

        }


    }


    private fun checkReservation() {

        // kontrolle ob zu dieser Zeit noch ein Platz frei ist
        val test = false

        if (!test){
           Toast.makeText(this, "Zu dieser Zeit gibt es keinen freien Tisch", Toast.LENGTH_SHORT).show()

        } else {
            // neue Reservierung anlegen
            // bild von Tisch austauschen mit grau
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.editText2 -> {
                // create calender
                val myCalendar = Calendar.getInstance()

                // set day, month and year in edit Text
                val datePickerOrDataSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        // display new date
                        updateLabel(myCalendar, editText2)
                    }

                // show current date
                val dataPickerDialog = DatePickerDialog(
                    this, datePickerOrDataSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                )

                dataPickerDialog.datePicker.minDate = System.currentTimeMillis()
                dataPickerDialog.show()


            }

            R.id.editText3 -> {

                if(editText2.text.toString().trim().length==0){
                    editText3.text.clear()
                    Toast.makeText(this, "Bitte gib ein Datum ein bevor du die Uhrzeit eingibst", Toast.LENGTH_SHORT).show()

                } else {

                    val cal = Calendar.getInstance()
                    val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        updateTimeLabel(cal, editText3)
                    }

                    val timePickerDialog = TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)

                    val currentDateTime = LocalDateTime.now()
                    var timeHour = currentDateTime.format(DateTimeFormatter.ofPattern("HH")).toInt()
                    var timeMin = currentDateTime.format(DateTimeFormatter.ofPattern("mm")).toInt()

                    //val currentTimestamp = System.currentTimeMillis()
                    //val hour = System.

                    timePickerDialog.updateTime(timeHour, timeMin)
                    timePickerDialog.show()

                }


            }

        }

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

    @RequiresApi
    private fun updateTimeLabel(myCalendar: Calendar, dateEditText: EditText) {

        val myFormat: String = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dateEditText.setText(sdf.format(myCalendar.time))

    }



    @RequiresApi
    private fun updateLabel(myCalendar: Calendar, dateEditText: EditText) {

        val myFormat: String = "dd-MMM-yyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dateEditText.setText(sdf.format(myCalendar.time))
    }


    fun getWaitingTime(){

        // app gets waiting Time from occupied seats

        val number = 7

        waitTime.setText("$number min")

    }



    fun getTable (){

        // get random empty table

        val tablenumber = 4

        tableNum.setText("$tablenumber")

    }




}