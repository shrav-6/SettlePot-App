package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ratio_split_input.*
import kotlinx.android.synthetic.main.ratiosplitbill.*
import maes.tech.intentanim.CustomIntent.customType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class RatioSplitInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratio_split_input)

        computepreviousbutton.setOnClickListener {
            var gotoRatioSplitmainpageintent = Intent(this, RatioSplitmainpage::class.java)
            startActivity(gotoRatioSplitmainpageintent)
            customType(this, "right-to-left")
            finish()
        }
        computenextbutton.setOnClickListener {
            var flag=0
            var activityname = ratiosplitactivityname.text.toString()
            if(activityname.isNullOrBlank())
            {
                activityname = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
            }
            if(category1number.text.toString().isNullOrBlank() || category1number.text.toString().toInt() == 0)
                flag=1
            if(category2number.text.toString().isNullOrBlank() || category2number.text.toString().toInt() == 0)
                flag=1
            if(category1price.text.toString().isNullOrBlank() || category1price.text.toString().toFloat() == 0F)
                flag=1
            if(category2price.text.toString().isNullOrBlank() || category2price.text.toString().toFloat() == 0F)
                flag=1
            if(totalprice.text.toString().isNullOrBlank() || totalprice.text.toString().toFloat() == 0F)
                flag=1

            var cat1no = category1number.text.toString().toInt()
            var cat2no = category2number.text.toString().toInt()
            var cat1price = category1price.text.toString().toFloat()
            var cat2price = category2price.text.toString().toFloat()
            var totprice = totalprice.text.toString().toFloat()

            if(flag==0) {
                computeRatioSplit(activityname, cat1no, cat2no, cat1price, cat2price, totprice)
            }
            else
                Toast.makeText(baseContext,"Enter valid non-zero details",Toast.LENGTH_SHORT).show()

        }
    }

    private fun computeRatioSplit(dummyactivityname: String, xno: Int, yno: Int, x: Float, y: Float, t: Float) {
        setContentView(R.layout.ratiosplitbill)
        tv_ratiosplitactivityname.setText(dummyactivityname)

        var datandtimeratiosplit = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM))
        tv_ratiosplitdateandday.setText(datandtimeratiosplit)


        var ratio = x/y
        var splitforcat2 = t/((xno*ratio)+(yno))
        var splitforcat1 = (t - (yno*splitforcat2)) / xno
//        println("Split for category 1: $splitforcat1")
//        println("Split for catgory 2: $splitforcat2")


        cat1perpay.setText(String.format("%.2f",splitforcat1))
        cat2perpay.setText(String.format("%.2f",splitforcat2))

        backtoratiosplitinputpage.setOnClickListener {
            val backtoratiosplitinputpageintent = Intent(this, RatioSplitInput::class.java)
            startActivity(backtoratiosplitinputpageintent)
            customType(this,"left-to-right")
            finish()
        }
    }

}