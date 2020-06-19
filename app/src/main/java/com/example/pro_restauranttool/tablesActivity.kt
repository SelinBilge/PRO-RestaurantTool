package com.example.pro_restauranttool


import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tables2.*




class tablesActivity : AppCompatActivity() {


    @Override
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tables2)

        toolbar.setTitle("")
        setSupportActionBar(toolbar)

        val fragmentAdapter = PageAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)

    }

}




/*
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_tables)




table1.setOnClickListener(this)
table2.setOnClickListener(this)
table3.setOnClickListener(this)
table4.setOnClickListener(this)
table5.setOnClickListener(this)
table6.setOnClickListener(this)
table7.setOnClickListener(this)
table8.setOnClickListener(this)
table9.setOnClickListener(this)
table10.setOnClickListener(this)
table11.setOnClickListener(this)
table12.setOnClickListener(this)



}
*/
/*
    fun addImageButtons() {
        iB_topleft = findViewById(R.id.button_topleft) as ImageButton
        iB_topright = findViewById(R.id.button_topright) as ImageButton
        iB_bottomleft = findViewById(R.id.button_bottomleft) as ImageButton
        iB_bottomright = findViewById(R.id.button_bottomright) as ImageButton
        iB_next = findViewById(R.id.button_next) as ImageButton
        iB_repeat = findViewById(R.id.button_repeat) as ImageButton
    }

    fun setImageNextAndRepeat() {
        iB_topleft.setImageResource(R.drawable.aa)
        iB_topright.setImageResource(R.drawable.bb)
        iB_bottomleft.setImageResource(R.drawable.cc)
        iB_bottomright.setImageResource(R.drawable.dd)
        iB_next.setImageResource(R.drawable.next)
        iB_repeat.setImageResource(R.drawable.repeat)
    }

 */

/*
    var tableNr: String = "";

    override fun onClick(v: View?) {

        when(v!!.id){

            R.id.table1 -> {
                Log.i("TAG", "table1")
               // table1.setImageResource(R.drawable.table_white4)

                tableNr = "1";

                displayInfos()



            }

            R.id.table2 -> {
                Log.i("TAG", "table2")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table3 -> {
                Log.i("TAG", "table3")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table4 -> {
                Log.i("TAG", "table4")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table5 -> {
                Log.i("TAG", "table5")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table6 -> {
                Log.i("TAG", "table6")
               // table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table7 -> {
                Log.i("TAG", "table7")
                // table1.setImageResource(R.drawable.table_white4)

                tableNr = "1";

                displayInfos()



            }

            R.id.table8 -> {
                Log.i("TAG", "table8")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table9 -> {
                Log.i("TAG", "table9")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table10 -> {
                Log.i("TAG", "table10")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table11 -> {
                Log.i("TAG", "table11")
                //table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

            R.id.table12 -> {
                Log.i("TAG", "table12")
                // table1.setImageResource(R.drawable.table_white4)
                displayInfos()

            }

        }
    }


    fun displayInfos(){


        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_tablewindow, null)

        //AlertDialogBuilder

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Zusatz Infos:")



        //show dialog
        val mAlertDialog = mBuilder.show()

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

    }





}

 */
