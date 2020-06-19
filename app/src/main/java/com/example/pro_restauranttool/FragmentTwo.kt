package com.example.pro_restauranttool

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_tables.view.*
import kotlinx.android.synthetic.main.activity_tablesoutdoor.view.*
import kotlinx.android.synthetic.main.dialog_tablewindow.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param1"


lateinit var tab13: ImageButton
lateinit var tab14: ImageButton
lateinit var tab15: ImageButton
lateinit var tab16: ImageButton
lateinit var tab17: ImageButton
lateinit var tab18: ImageButton
lateinit var tab19: ImageButton
lateinit var tab20: ImageButton


var tableNr2: String = ""
var seatNr2: String = ""
var seatChild2: Boolean = false
var seatOutdoor2: Boolean = true
var status2: Int = 0


/**
 * A simple [Fragment] subclass.
 */
class FragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.activity_tablesoutdoor, container, false)


        tab13 = view.table13
        tab14 = view.table14
        tab15 = view.table15
        tab16 = view.table16
        tab17 = view.table17
        tab18 = view.table18
        tab19 = view.table19
        tab20 = view.table20


        tab13.setOnClickListener {
            Log.i("TAG", "Button13")
            tableNr2 = "13"
            seatNr2 = "6"
            seatChild2 = false
            status2 = 0
            displayInfos()
        }

        tab14.setOnClickListener {
            Log.i("TAG", "Button14")
            tableNr2 = "14"
            seatNr2= "2"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        tab15.setOnClickListener {
            Log.i("TAG", "Button15")
            tableNr2 = "15"
            seatNr2 = "4"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        tab16.setOnClickListener {
            Log.i("TAG", "Button16")
            tableNr2 = "16"
            seatNr2 = "4"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        tab17.setOnClickListener {
            Log.i("TAG", "Button17")
            tableNr2 = "17"
            seatNr2 = "6"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        tab18.setOnClickListener {
            Log.i("TAG", "Button18")
            tableNr2 = "18"
            seatNr2 = "4"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        tab19.setOnClickListener {
            Log.i("TAG", "Button19")
            tableNr2 = "19"
            seatNr2 = "8"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        tab20.setOnClickListener {
            Log.i("TAG", "Button20")
            tableNr2 = "20"
            seatNr2 = "4"
            seatChild2 = false
            status2 = 0
            displayInfos()

        }

        return view

    }

    fun displayInfos(){


        //Create the view to show
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_tablewindow, null)


        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .setTitle("Zusatz Infos:")

        //show dialog
        val mAlertDialog = mBuilder.show()

        // Button for reservation
        mDialogView.reservation.setOnClickListener {
            Log.i("TAG", "Reservation Button")

            // make new reservation

        }

        // display informations for different table
        mDialogView.tabNummer.setText(tableNr2)  // table nummer
        mDialogView.tabSeats.setText(seatNr2)    // seat count

        if(seatChild2){  // has seats for children
            mDialogView.tabChild.setText("JA")

        } else {        // has NO seats for children
            mDialogView.tabChild.setText("NEIN")
        }

        if(seatOutdoor2){    // seats are outside
            mDialogView.tabOutdoor.setText("JA")

        } else {          // seats are inside
            mDialogView.tabOutdoor.setText("NEIN")
        }

        if(status2 == 0){
            mDialogView.tabStatus.setText("FREI")

        } else if (status2 == 1){
            mDialogView.tabStatus.setText("RESERVIERT")

        } else {
            mDialogView.tabStatus.setText("BESETZT")

        }

    }


}