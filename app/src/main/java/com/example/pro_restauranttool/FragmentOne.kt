package com.example.pro_restauranttool

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_tables.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param1"



/**
 * A simple [Fragment] subclass.
 */
class FragmentOne : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.activity_tables, container, false)


/*
        table1.setOnClickListener (this)
        //  displayInfos()

        table2.setOnClickListener {
            // displayInfos()


        }
        table3.setOnClickListener {
            //  displayInfos()

        }
        table4.setOnClickListener {
            // displayInfos()

        }
        table5.setOnClickListener {
            //  displayInfos()

        }
        table6.setOnClickListener {
            //  displayInfos()

        }
        table7.setOnClickListener {
            //  displayInfos()

        }
        table8.setOnClickListener {
            // displayInfos()

        }
        table9.setOnClickListener {
            //  displayInfos()

        }
        table10.setOnClickListener {
            //  displayInfos()

        }
        table11.setOnClickListener {
            //  displayInfos()

        }
        table12.setOnClickListener {
            //  displayInfos()

        }

 */



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        table1.setOnClickListener { view ->
            Log.d("TAG", "Button1")
        }


    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.table1 -> {
                Log.i("TAG", "Button1")
                // table1.setImageResource(R.drawable.table_white4)
                //tableNr = "1";
                //  displayInfos()
            }

            R.id.table2 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //   displayInfos()

            }

            R.id.table3 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //    displayInfos()

            }

            R.id.table4 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //    displayInfos()

            }

            R.id.table5 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //    displayInfos()

            }

            R.id.table6 -> {
                // table1.setImageResource(R.drawable.table_white4)
                //   displayInfos()

            }

            R.id.table7 -> {
                // table1.setImageResource(R.drawable.table_white4)
                //   displayInfos()

            }

            R.id.table8 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //   displayInfos()

            }

            R.id.table9 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //   displayInfos()

            }

            R.id.table10 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //    displayInfos()

            }

            R.id.table11 -> {
                //table1.setImageResource(R.drawable.table_white4)
                //     displayInfos()

            }

            R.id.table12 -> {
                // table1.setImageResource(R.drawable.table_white4)
                //    displayInfos()

            }



        }    }


}

    /*
       var tableNr: String = "";

       override fun onClick(v: View?) {




            */


/*
    fun displayInfos(){

        //Inflate the dialog with custom view
        //val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_tablewindow, null)

        //AlertDialogBuilder

        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mDialogView)
            .setTitle("Zusatz Infos:")



        //show dialog
        val mAlertDialog = mBuilder.show()

    }





    }








        /*
        when(tableNr){

            "1" -> {
                tableNummer.setText("1")
            }
        }

         */

        /*
        //login button click of custom layout
        mDialogView.dialogLoginBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val name = mDialogView.dialogNameEt.text.toString()
            val email = mDialogView.dialogEmailEt.text.toString()
            val password = mDialogView.dialogPasswEt.text.toString()
            //set the input text in TextView
            mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
         */

 */


