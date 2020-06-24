package com.example.pro_restauranttool

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.room_plan.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentOne : Fragment() {
    lateinit var listener: tableListener
    var db = Firebase.firestore


    interface tableListener {
        fun availableTableClicked(table_id: Int, documents: QuerySnapshot)
        fun takenTableClicked(table_id: Int, documents: QuerySnapshot, till: Time)
        fun showAllReservations()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Saving the Instance in the TableActivity
        val parentActivity = activity
        (parentActivity as TablesActivity).fragmentOne = this
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.room_plan, container, false)
        getTables(view)
        // Add Listener for showReservationButton
        view.findViewById<Button>(R.id.showAllReservations).setOnClickListener {
            listener.showAllReservations()
        }
        return view
    }


    /**
     * Sucht in der Datenbank nach einer Liste mit allen Tables die drinnen sind
     * Ruft die Methode iterateTables() mit einem Array mit allen Tables auf
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTables(view: View) {
        db.collection("table")
            .whereEqualTo("outdoors", false)
            .get()
            .addOnSuccessListener { documents ->
                var arr = IntArray(documents.size())
                for ((index, document) in documents.withIndex()) {
                    arr[index] = document.id.toInt();
                }
                if(arr.isNotEmpty()) {
                    iterateTables(arr, view)
                }
            }
            .addOnFailureListener{
                Log.i("myFirestore", it.message.toString())
            }
    }

    /**
     * Ruft fü jeden Tisch die Methode buildTable auf, die feststellt ob der Tisch frei ist
     * findet außerdem über das tab Feld!!!! der View heraus welche id die View für den
     * Tisch hat und übergibt dies VIew an die Methode
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun iterateTables(arr: IntArray, view: View) {
        val time = Time.getTime()
        val date = Time.getTodaysDate()
        for(tableId in arr) {
            val viewId = "table$tableId"
            val resId = resources.getIdentifier(viewId, "id", context?.packageName)
            val tableView = view.findViewById<ImageView>(resId)
            //To tell if the table is reserved at the moment, the duration is set to 0
            //and the time and date to now
            buildTable(tableId, date, time, 0 , tableView)
        }
    }

    /**
     * Schaut in der Datenbak ob ein Table reserviert ist und setzt im Anschluss abhängig
     * vom Ergebniss das Src Image für die übergebene View und setzt den richtigen onClick
     * listener. Übergibt auch das Ergebnis der Datenbank abfrage also alle Reservierungen für den
     * heutigen Tag
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun buildTable(tableId: Int, date: String, time: Time, duration: Int, tableView: ImageView): Boolean {
        val newResTill = time.addTime(duration)
        db.collection("reservation")
            .whereEqualTo("table_id", tableId)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                var taken = false;
                var reservedTill = Time(0,0)
                for(document in documents) {
                    val  resTill = Time.getTime(document["till"] as String)
                    val  resFrom = Time.getTime(document["from"] as String)
                    val  ended = document["ended"] as Boolean
                    if(time.compareTime(resTill)
                        && resFrom.compareTime(newResTill)
                        && !ended) {
                        reservedTill = resTill
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
                //Tisch Images auswählen
                var seatCount = tableView.tag.toString().toInt()
                var drawId = getDrawableId(seatCount, taken)
                tableView.setImageResource(drawId)

                if(taken) {
                    tableView.setOnClickListener{
                        listener.takenTableClicked(tableId, documents, reservedTill)
                    }
                } else {
                    tableView.setOnClickListener{
                        listener.availableTableClicked(tableId, documents)
                    }
                }
            }
        return true;
    }

    /**
     * Setzt TablesActivity als den Listner für onCklick Events auf die Tische
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as tableListener
    }

    /**
     * Wählt abhänig von bestzt nicht besetzt und der Sitzplatzanzahl das richtige TischImage aus
     */
    fun getDrawableId(seatCount:Int, taken:Boolean):Int {
       if(taken) {
           when(seatCount) {
               2 -> return R.drawable.table_lightgrey2
               4 -> return R.drawable.table_lightgrey4
               6 -> return R.drawable.table_lightgrey6
               8 -> return R.drawable.table_lightgrey8
           }
       } else {
           when(seatCount) {
               2 -> return R.drawable.table_white2
               4 -> return R.drawable.table_white4
               6 -> return R.drawable.table_white6
               8 -> return R.drawable.table_white8
           }
       }
        return R.drawable.table_darkgrey2
    }
}