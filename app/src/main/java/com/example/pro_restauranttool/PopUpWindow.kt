package com.example.pro_restauranttool

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

class PopUpWindow : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_popupwindow)


        // get height and width from display
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width:Int = displayMetrics.widthPixels
        val height:Int = displayMetrics.heightPixels


        window.setLayout( ((width*0.8).toInt()), (height*0.8).toInt())


    }



}