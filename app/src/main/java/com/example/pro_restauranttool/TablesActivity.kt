package com.example.pro_restauranttool


import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_tables2.*
import kotlinx.android.synthetic.main.dialog_all_reservations.*
import kotlinx.android.synthetic.main.dialog_table_available.*
import kotlinx.android.synthetic.main.dialog_table_available.kidsTable
import kotlinx.android.synthetic.main.dialog_table_available.seatCount
import kotlinx.android.synthetic.main.dialog_table_taken.*

//TODO alle Reservierungen anzeigen
//TODO nächste Reservierung ändert sich nur bei Layout refresh
class TablesActivity : AppCompatActivity(),
        FragmentOne.tableListener,
        ReservationListAdapter.DeleteReservationListener,
        AllReservationListAdapter.DeleteReservationListener{
    lateinit var dialog: AlertDialog
    var taken = false
    var db = Firebase.firestore

    @Override
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tables2)

        //Toolbar Titel anzeigen
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true);
        toolbar.title = "Tischplan"

        //Adabter für das Tab Layout
        val fragmentAdapter = PageAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    /**
     * Wird aufgerufen wenn alle Reservierungen angezeigt werden solln
     */
    override fun showAllReservations() {
        //Create the view for the Dialog
        val dialogAvailable = LayoutInflater.from(this).inflate(R.layout.dialog_all_reservations, null)
        //Create and show AlertDialogBuilder
        dialog = AlertDialog.Builder(this)
                .setView(dialogAvailable)
                .setTitle("Alle Reservierungen")
                .show()
        getAllReservations()
    }

    /**
     * Wird aufgerufen wenn ein Tisch angecklickt wird, der frei ist
     * Erstellt einen Dialog, füllt diesen mit Daten und setzt onClick Listener
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun availableTableClicked(
        table_id: Int,
        documents: QuerySnapshot
    ) {
        taken = false
        //Create the view for the Dialog
        val dialogAvailable = LayoutInflater.from(this).inflate(R.layout.dialog_table_available, null)
        //Create and show AlertDialogBuilder
        dialog = AlertDialog.Builder(this)
                .setView(dialogAvailable)
                .setTitle("Tisch $table_id")
                .show()
        //Function to get Data from the Database and fill the Dialog with it
        fillDialog(table_id)
        //If the passed ReservationList for today for this Table is not empty, the next
        //Reservation is searched for the nextReservation Field
        if(documents.isEmpty) {
            dialog.nextReservation.text = "heute keine"
        } else {
            var nextResTime = getNextReservation(documents)
            if(nextResTime == null) {
                dialog.nextReservation.text = "heute keine"
            } else {
                dialog.nextReservation.text = nextResTime.toString()
            }
        }
        //Listener for Reservation Tab
        val addResView = dialog.res_layout
        dialog.toogleReservation.setOnClickListener {
            if(addResView.visibility == View.VISIBLE) {
                addResView.visibility = View.GONE
            } else {
                addResView.visibility = View.VISIBLE
            }
        }
        //Listener for reservation List Tab
        dialog.toogleReservationList.setOnClickListener{
            if(dialog.dialogReservationList.visibility == View.VISIBLE
                    ||dialog.emptyReservationList.visibility == View.VISIBLE) {
                dialog.dialogReservationList.visibility = View.GONE
                dialog.emptyReservationList.visibility = View.GONE
            } else {
                showReservations(table_id)
            }
        }
        //Listener for adding a new Reservation
        dialog.reserveButton.setOnClickListener {
            if(dialog.resDuration.text.isNotEmpty() && dialog.resSeatCount.text.isNotEmpty()) {
                val duration = dialog.resDuration.text.toString().toInt()
                val seats = dialog.resSeatCount.text.toString().toInt()
                checkReservation(Time.getTime(), duration, seats,  table_id, documents)
            } else {
                Toast.makeText(this, "Bitte eine gültige Dauer in Minuten und eine Personenanzahl ein", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Wird aufgerufen wenn ein Tisch angecklickt wird, der besetzt ist
     * Erstellt einen Dialog, füllt diesen mit Daten und setzt onClick Listener
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun takenTableClicked(
            table_id: Int,
            documents: QuerySnapshot,
            till: Time
    ) {
        taken = true
        //Create the view for the Dialog
        val dialogAvailable = LayoutInflater.from(this).inflate(R.layout.dialog_table_taken, null)
        //Build and show the Dialog Builder
        dialog = AlertDialog.Builder(this)
                .setView(dialogAvailable)
                .setTitle("Tisch $table_id")
                .show()
        //Gets data for the Table and fills the Dialog
        fillDialog(table_id)
        dialog.reservedTill.text = till.toString()
        //Listener for ending a Reservation
        dialog.clearTable.setOnClickListener {
            endReservation(table_id)
        }
        //Listener for reservation List Tab
        dialog.toogleReservationListTaken.setOnClickListener{
            if(dialog.dialogReservationListTaken.visibility == View.VISIBLE
                    ||dialog.emptyReservationListTaken.visibility == View.VISIBLE) {
                dialog.dialogReservationListTaken.visibility = View.GONE
                dialog.emptyReservationListTaken.visibility = View.GONE
            } else {
                showReservations(table_id)
            }
        }
    }

    /**
     * Sucht in der Datenbank nach dem Tisch und setzt die Felder im Dialog entsprechend
     */
    fun fillDialog(tableId: Int) {
        db.collection("table").document(tableId.toString())
                .get()
                .addOnSuccessListener {document ->
                    if(document != null) {
                        dialog.seatCount.text = (document["seats"] as Long).toInt().toString()
                        if(document["kids_table"] as Boolean) {
                            dialog.kidsTable.text = "Ja"
                        } else {
                            dialog.kidsTable.text = "Keine"
                        }
                    } else {
                        Log.i("myFirestore", "Dokument nicht gefunden")
                    }
                }
                .addOnFailureListener{
                    Log.i("myFirestore", it.message.toString())
                }
    }

    /**
     * Behandelt die Aufgabe einer neuen Reservierung bei einem freien Tisch
     * Checkt ob keine Reservierung in der vorgesehenen Dauer ist und ruft falls
     * alles passt die methode reserveTable auf
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkReservation(time: Time, duration: Int, seats: Int, tableId:Int, documents: QuerySnapshot) {
        val newResTill = time.addTime(duration)
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
                        && !(document["ended"] as Boolean)) {
                        taken = true;
                    }
                }
                if(taken) {
                    Toast.makeText(this, "Zu dieser Zeit ist schon reserviert", Toast.LENGTH_SHORT).show()
                } else if(!Time.getTime().isBetween(Time(11,30), Time(23,0))) {
                    Toast.makeText(this, "Zu dieser Zeit kann nicht reserviert werden", Toast.LENGTH_SHORT).show()
                }else {
                    reserveTable(tableId, Time.getTodaysDate(), time, duration, seats)
                }
    }

    /**
     * Reserviert den Table, nachdem von der Methode checkReservation überprüft wurde
     * ob es möglich ist, Ruft refresh auf um das Layout zu aktualisieren
     */
    fun reserveTable(table: Int, date: String, from: Time,duration: Int, seats: Int) {
        val till = from.addTime(duration)
        val reservation = mutableMapOf<String, Any>()
        reservation["table_id"] = table
        reservation["date"] = date
        reservation["from"] = from.toString()
        reservation["till"] = till.toString()
        reservation["duration"] = duration
        reservation["name"] = ""
        reservation["seats_reserved"] = seats
        reservation["ended"] = false
        db.collection("reservation").add(reservation)
        refresh()
    }

    /**
     * Beendet eine Reservierung durch das setzen des Feldes ended auf true
     * Sucht jene Reservierung mit der Tisch Id die vor dem jetztigen Zeitpunkt angefangen hat
     * und beendet diese, ruft refresh() auf um das Layout zu aktualisieren
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun endReservation(tableId: Int) {
        db.collection("reservation")
            .whereEqualTo("table_id", tableId)
            .whereEqualTo("date", Time.getTodaysDate())
            .whereEqualTo("ended", false)
            .get()
            .addOnSuccessListener {documents ->
                val now = Time.getTime()
                for(document in documents) {
                    val startTime = Time.getTime(document["from"] as String)
                    if(startTime.compareTime(now)) {
                        val newDuration = now.getDuration(startTime)
                        val newEndTime = now
                        val id = document.id
                        db.collection("reservation").document(id)
                            .update(mapOf(
                                "till" to newEndTime.toString(),
                                "duration" to newDuration,
                                "ended" to true
                            ))
                            .addOnSuccessListener {
                                refresh()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
    }

    /**
     * Sucht nach allen Reservierungen für einen Tisch, die noch vor dem jetztigen Zeitpunkt liegen
     * Feld: ended auf true, erzeugt eine ArrayList vom Typ ReservationData und bildet mit einer
     * RecyclerView die Liste falls sie nicht leer ist ab
     */
    fun showReservations(tableId: Int) {
        db.collection("reservation")
                .whereEqualTo("table_id", tableId)
                .whereEqualTo("ended", false)
                .get().addOnSuccessListener {documents ->
                    var reservationArray = arrayListOf<ReservationData>()
                    for(document in documents) {
                        var newReservation = ReservationData(
                                (document["table_id"] as Long).toInt(),
                                document["date"] as String,
                                Time.getTime(document["from"] as String),
                                Time.getTime(document["till"] as String),
                                (document["duration"] as Long).toInt(),
                                document.id
                        )
                        reservationArray.add(newReservation)
                    }
                    reservationArray.sortDescending()
                    //Makes Fields visible, depending if the List is empty
                    if(taken) {
                        if(reservationArray.isEmpty()) {
                            dialog.emptyReservationListTaken.visibility = View.VISIBLE
                            dialog.dialogReservationListTaken.visibility = View.GONE
                        } else {
                            dialog.dialogReservationListTaken.visibility = View.VISIBLE
                            val cardRecyclerView = dialog.dialogReservationListTaken
                            cardRecyclerView.layoutManager = LinearLayoutManager(this)
                            val adapter  = ReservationListAdapter(reservationArray)
                            cardRecyclerView.adapter = adapter
                        }
                    } else {
                        if(reservationArray.isEmpty()) {
                            dialog.emptyReservationList.visibility = View.VISIBLE
                            dialog.dialogReservationList.visibility = View.GONE
                        } else {
                            dialog.dialogReservationList.visibility = View.VISIBLE
                            val cardRecyclerView = dialog.dialogReservationList
                            cardRecyclerView.layoutManager = LinearLayoutManager(this)
                            val adapter  = ReservationListAdapter(reservationArray)
                            cardRecyclerView.adapter = adapter
                        }
                    }
                }
    }

    /**
     * Sucht die nächste Reserverung, vor dem jetztigen Zeitpunkt, aber nur für heute
     * documents beeinhaltet alle Reservierungen für den heutigen Tag
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextReservation(documents: QuerySnapshot): Time? {
        var time = Time(23,59)
        var retTime: Time? = null
        for(document in documents) {
            var cTime = Time.getTime(document["from"] as String)
            if(cTime.compareTime(time)
                    && !cTime.compareTime(Time.getTime())) {
                time = cTime
                retTime = cTime
            }
        }
        return retTime
    }

    /**
     * Holt sich alle Tischreservierungen die noch nicht beendet sind und füllt die RecyclerView
     */
    fun getAllReservations() {
        db.collection("reservation")
                .whereEqualTo("ended", false)
                .get().addOnSuccessListener { documents ->
                    var reservationArray = arrayListOf<ReservationData>()
                    for (document in documents) {
                        var newReservation = ReservationData(
                                (document["table_id"] as Long).toInt(),
                                document["date"] as String,
                                Time.getTime(document["from"] as String),
                                Time.getTime(document["till"] as String),
                                (document["duration"] as Long).toInt(),
                                document.id,
                                document["name"] as String
                        )
                        reservationArray.add(newReservation)
                    }
                    reservationArray.sortDescending()
                    if(reservationArray.isEmpty())  {
                        dialog.noReservationsAtAll.visibility = View.VISIBLE
                    } else {
                        dialog.noReservationsAtAll.visibility = View.GONE
                        val cardRecyclerView = dialog.allReservationsView
                        cardRecyclerView.layoutManager = LinearLayoutManager(this)
                        val adapter  = AllReservationListAdapter(reservationArray)
                        cardRecyclerView.adapter = adapter
                    }
                }
    }

    /**
     * Erneuert das Layout
     */
    fun refresh() {
        dialog.hide()
        val fragmentAdapter = PageAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    /**
     * Löscht die Reservierung mit der TischId und erneuert anschließend die RecyclerView
     * Wird vom Adabter der Recycler View aus gecalled
     */
    override fun deleteReservation(reservationId: String, tableId: Int, allList: Boolean) {
        db.collection("reservation").document(reservationId).delete().addOnSuccessListener {
                if(allList) {
                    getAllReservations()
                } else {
                    showReservations(tableId)
                }
            }
        }

}
