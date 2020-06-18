package com.example.pro_restauranttool

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tables.*

class tablesActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tables)

        table1.setOnClickListener(this)
        table2.setOnClickListener(this)
        table3.setOnClickListener(this)
        table4.setOnClickListener(this)
        table5.setOnClickListener(this)
        table6.setOnClickListener(this)


    }




    override fun onClick(v: View?) {

        when(v!!.id){

            R.id.table1 -> {
                Log.i("TAG", "table1")
            }

            R.id.table2 -> {
                Log.i("TAG", "table2")
            }

            R.id.table3 -> {
                Log.i("TAG", "table3")
            }

            R.id.table4 -> {
                Log.i("TAG", "table4")
            }

            R.id.table5 -> {
                Log.i("TAG", "table5")
            }

            R.id.table6 -> {
                Log.i("TAG", "table6")
            }

        }
    }


}
