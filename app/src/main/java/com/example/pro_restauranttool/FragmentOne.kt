package com.example.pro_restauranttool

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_tables.*
import kotlinx.android.synthetic.main.activity_tables.view.*
import kotlinx.android.synthetic.main.dialog_tablewindow.*
import kotlinx.android.synthetic.main.dialog_tablewindow.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param1"

lateinit var tab1: ImageButton
lateinit var tab2: ImageButton
lateinit var tab3: ImageButton
lateinit var tab4: ImageButton
lateinit var tab5: ImageButton
lateinit var tab6: ImageButton
lateinit var tab7: ImageButton
lateinit var tab8: ImageButton
lateinit var tab9: ImageButton
lateinit var tab10: ImageButton
lateinit var tab11: ImageButton
lateinit var tab12: ImageButton

var tableNr: String = ""
var seatNr: String = ""
var seatChild: Boolean = false
var seatOutdoor: Boolean = false
var status: Int = 0

// STATUS INT? STRING?


/**
 * A simple [Fragment] subclass.
 */
class FragmentOne : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.activity_tables, container, false)
        //val view: View = inflater!!.inflate(R.layout.activity_tables, container, false)


        tab1 = view.table1
        tab2 = view.table2
        tab3 = view.table3
        tab4 = view.table4
        tab5 = view.table5
        tab6 = view.table6
        tab7 = view.table7
        tab8 = view.table8
        tab9 = view.table9
        tab10 = view.table10
        tab11 = view.table11
        tab12 = view.table12




        tab1.setOnClickListener{
            Log.i("TAG", "Button1")
            tableNr = "1"
            seatNr = "2"
            seatChild = false
            status = 0
            displayInfos()

        }



        tab2.setOnClickListener {
            Log.i("TAG", "Button2")
            tableNr = "2"
            seatNr = "2"
            seatChild = false
            status = 0
            displayInfos()


        }

       tab3.setOnClickListener {
           Log.i("TAG", "Button3")
           tableNr = "3"
           seatNr = "8"
           seatChild = false
           status = 0
           displayInfos()


       }
       tab4.setOnClickListener {
           Log.i("TAG", "Button4")
           tableNr = "4"
           seatNr = "6"
           seatChild = false
           status = 0
           displayInfos()

       }
       tab5.setOnClickListener {
           Log.i("TAG", "Button5")
           tableNr = "5"
           seatNr = "4"
           seatChild = false
           status = 0
           displayInfos()

       }

       tab6.setOnClickListener {
           Log.i("TAG", "Button6")
           tableNr = "6"
           seatNr = "4"
           seatChild = false
           status = 0
           displayInfos()

       }

       tab7.setOnClickListener {
           Log.i("TAG", "Button7")
           tableNr = "7"
           seatNr = "4"
           seatChild = false
           status = 0
           displayInfos()

       }

       tab8.setOnClickListener {
           Log.i("TAG", "Button8")
           tableNr = "8"
           seatNr = "4"
           seatChild = false
           status = 0
           displayInfos()


       }

       tab9.setOnClickListener {
           Log.i("TAG", "Button9")
           tableNr = "9"
           seatNr = "6"
           seatChild = false
           status = 0
           displayInfos()


       }
       tab10.setOnClickListener {
           Log.i("TAG", "Button10")
           tableNr = "10"
           seatNr = "2"
           seatChild = false
           status = 0
           displayInfos()


       }
       tab11.setOnClickListener {
           Log.i("TAG", "Button11")
           tableNr = "11"
           seatNr = "2"
           seatChild = false
           status = 0
           displayInfos()

       }

       tab12.setOnClickListener {
           Log.i("TAG", "Button12")
           tableNr = "12"
           seatNr = "4"
           seatChild = false
           status = 0
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
        mDialogView.tabNummer.setText(tableNr)  // table nummer
        mDialogView.tabSeats.setText(seatNr)    // seat count

        if(seatChild){  // has seats for children
            mDialogView.tabChild.setText("JA")

        } else {        // has NO seats for children
            mDialogView.tabChild.setText("NEIN")
        }

        if(seatOutdoor){    // seats are outside
            mDialogView.tabOutdoor.setText("JA")

        } else {          // seats are inside
            mDialogView.tabOutdoor.setText("NEIN")
        }

        if(status == 0){
            mDialogView.tabStatus.setText("FREI")

        } else if (status == 1){
            mDialogView.tabStatus.setText("RESERVIERT")

        } else {
            mDialogView.tabStatus.setText("BESETZT")

        }


    }


}