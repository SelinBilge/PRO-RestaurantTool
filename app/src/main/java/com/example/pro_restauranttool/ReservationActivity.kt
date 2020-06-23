package com.example.pro_restauranttool

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_reservation.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

//TODO Eingebeprüfung verbessern
//TODO Tagg bei Reservierungen einfügen, Tischnummer-Option

class ReservationActivity : AppCompatActivity(), View.OnClickListener {
    var db = FirebaseFirestore.getInstance()
    lateinit var dialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        dateInput.setOnClickListener(this)
        timeInput.setOnClickListener(this)
        submitButton.setOnClickListener {
            validateInput(it)
        }
        cancelButton.setOnClickListener {
            cancel()
        }
    }

    /**
     * Checkt ob alles korrekt eingegeben wurde, falls nicht, wird eine Fehlermeldung
     * ausgegeben,
     * Ist alles korrekt Eingegben worden wird checkTables aufgerufen
     */
    private fun validateInput(view: View) {
        //read value from EditText to a String variable
        val date: String = dateInput.text.toString()
        val time: String = timeInput.text.toString()
        val personCount: String = personCountInput.text.toString()
        val duration: String = durationInput.text.toString()
        val seats: String = personCountInput.text.toString()
        val name: String = nameInput.text.toString()

        //check if the EditText have values or not
        if (date.trim().isEmpty()
            || time.trim().isEmpty()
            || personCount.trim().isEmpty()
            || duration.trim().isEmpty()
            || seats.trim().isEmpty()) {
            //TODO check if Input has the right Format
            Toast.makeText(this, "Eingabe fehlt", Toast.LENGTH_SHORT).show()
            return;
        }
        val choosenTime = Time.getTime(timeInput.text.toString())
        var start = Time(11,30)
        val stop = Time(23,0)
        val maxSeats = 10;
        // check persons
        if (seats.toInt() > maxSeats) {
            Toast.makeText(this, "Überprüfe die Anzahl der Personen", Toast.LENGTH_SHORT).show()
            return;
        }
        // check if the reservation is on the same day
        var testDate = Time.getTodaysDate()
        if (dateInput.text.toString() == Time.getTodaysDate()) {
            start = Time.getTime()
        }
        //TODO könnte sein das mit einem Minutenwechsel die Eingabe für jetzt ungültig wird
        if (!choosenTime.isBetween(start, stop)) {
            timeInput.text.clear()
            Toast.makeText(this, "Wähle eine gültige Uhrzeit", Toast.LENGTH_SHORT).show()
            return
        }
        //Check Options
        var outdoorTable = outdoorsSwitch.isChecked
        var kidsTable = kidsSwitch.isChecked
        dialog = MaterialDialog(this)
            .show {
                title(text = "Tisch wird gesucht")
                message(text = "Haben sie einen Augenblick geduld")
            }
        //All fields are set correctly, beginn search for a table
        checkTables(choosenTime, date, duration.toInt(), seats.toInt(), outdoorTable, kidsTable, name);
    }


    /**
     * Wird von checkTables mit einem Array aufgerufen, der jende table ids enthält
     * die den Anforderungen entsprechen
     * Ruft solange checkReservation auf bis er einen Table aus dem Array gefunden hat, der frei ist
     */
    private fun getTable(index: Int, time: Time, date: String, duration: Int, found: Boolean, tableArray: ArrayList<TableData>, seats: Int, name: String) {
        if(found) {
            val tableId = tableArray[index].id
            reserveTable(tableId, date, time, duration, seats, name)
        } else if(index >= tableArray.size) {
            dialog.show()
            dialog.title(text = "Kein Tisch gefunden")
            dialog.message(text = "Es sind zu dieser Zeit leider keine Plätze mehr frei")
            dialog.positiveButton(text = "Zurück")
        } else {
            checkReservation(index, date, time, duration, tableArray, seats, name)
        }
    }


    /**
     * Gibt einen Array zurück mit jenen Tisch ids, die den Anforderungen entsprechen
     */
    //TODO so sortieren, dass wenn man einen Tisch für 2 bestellt keinen für 8 Personen bekommt
    fun checkTables(time: Time, date: String, duration: Int, seats: Int, outdoors: Boolean, kids: Boolean, name: String) {
        db.collection("table")
            .whereGreaterThanOrEqualTo("seats", seats)
            .whereEqualTo("outdoors", outdoors)
            .whereEqualTo("kids_table", kids)
            .get()
            .addOnSuccessListener { documents ->
                val arr = arrayListOf<TableData>()
                for (document in documents) {
                    arr.add(TableData(document.id.toInt(), (document["seats"] as Long).toInt()))
                }
                arr.sort()
                if(arr.isNotEmpty()) {
                    getTable(0, time, date, duration, false, arr, seats, name)
                } else {
                    dialog.show()
                    dialog.title(text = "Kein Tisch gefunden")
                    dialog.message(text = "Es wurde leider kein passender Tisch gefunden")
                    dialog.positiveButton(text = "Zurück")
                }
            }
            .addOnFailureListener{
                Log.i("myFirestore", it.message.toString())
            }
    }

    /**
     * Checkt ob der Tisch mit der eingerechneten dauer frei ist, checkt auch ob
     * noch Gäste auf einem Tisch sitzen und noch nicht ausgechecked haben
     */
    fun checkReservation(index: Int, date: String, time: Time, duration: Int, tableArray: ArrayList<TableData>, seats: Int, name:String): Boolean {
        val newResTill = time.addTime(duration);
        db.collection("reservation")
            .whereEqualTo("table_id", tableArray[index].id)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                var taken = false;
                for(document in documents) {
                    val  resTill = Time.getTime(document["till"] as String)
                    val  resFrom = Time.getTime(document["from"] as String)
                    val  ended = document["ended"] as Boolean
                    if(time.compareTime(resTill)
                        && resFrom.compareTime(newResTill)
                        && !ended) {
                        taken = true;
                    }
                    //Reservation has ended but Guests have not checked out
                    //Only counts if date is today
                    if(resTill.compareTime(Time.getTime())
                        && Time.getTodaysDate() == date
                        && !(document["ended"] as Boolean)) {
                        taken = true;
                    }
                }
                if(taken) {
                    val newIndex = index+1;
                    getTable(newIndex, time, date, duration, false, tableArray, seats, name);
                } else {
                    getTable(index, time, date, duration, true, tableArray, seats, name);
                }
            }
        return true;
    }


    /**
     * Reserviert einen Tisch für eine bestimmte Dauer, Reservierung muss manuell wieder
     * aufgehoben werden
     */
    fun reserveTable(table: Int, date: String, from: Time,duration: Int, seats: Int, name: String) {
        clearFields()
        val till = from.addTime(duration)
        dialog.show()
        dialog.title(text = "Suche erfolgreich")
        dialog.message(text = "Der Tisch $table wurde am $date, von $from bis $till für $seats Personen reserviert")
        dialog.positiveButton(text = "Ok")
        val reservation = mutableMapOf<String, Any>()
        reservation["table_id"] = table
        reservation["date"] = date
        reservation["from"] = from.toString()
        reservation["till"] = till.toString()
        reservation["duration"] = duration
        reservation["seats_reserved"] = seats
        reservation["name"] = name
        reservation["ended"] = false
        db.collection("reservation").add(reservation)
    }





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dateInput -> {
                // create calender
                val myCalendar = Calendar.getInstance()
                // set day, month and year in edit Text
                val datePickerOrDataSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        // display new date
                        updateLabel(myCalendar, dateInput)
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

            R.id.timeInput -> {
                if (dateInput.text.toString().trim().isEmpty()) {
                    timeInput.text.clear()
                    Toast.makeText(
                        this,
                        "Bitte gib ein Datum ein bevor du die Uhrzeit eingibst",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val cal = Calendar.getInstance()
                    val timeSetListener =
                        TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                            cal.set(Calendar.HOUR_OF_DAY, hour)
                            cal.set(Calendar.MINUTE, minute)
                            updateTimeLabel(cal, timeInput)
                        }
                    val timePickerDialog = TimePickerDialog(
                        this,
                        timeSetListener,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        true
                    )
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
    }

    @RequiresApi
    private fun updateTimeLabel(myCalendar: Calendar, dateEditText: EditText) {
        val myFormat: String = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dateEditText.setText(sdf.format(myCalendar.time))

    }


    @RequiresApi
    private fun updateLabel(myCalendar: Calendar, dateEditText: EditText) {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        dateEditText.setText(sdf.format(myCalendar.time))
    }

    private fun clearFields() {
        dateInput.text.clear()
        timeInput.text.clear()
        personCountInput.text.clear()
        durationInput.text.clear()
        personCountInput.text.clear()
        nameInput.text.clear()
    }


    private fun cancel() {
        //read value from EditText to a String variable
        val msg1: String = dateInput.text.toString()
        val msg2: String = timeInput.text.toString()
        val msg3: String = personCountInput.text.toString()
        // input doesn't exists -> go to main again
        if (msg1.trim().isEmpty() && msg2.trim().isEmpty() && msg3.trim().isEmpty()) {
            val backToMain = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(backToMain)
            // else clear activity
        } else {
            clearFields()
        }
    }
}
