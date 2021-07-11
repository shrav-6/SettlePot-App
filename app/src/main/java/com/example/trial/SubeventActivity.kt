package com.example.trial

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.*
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_subevents.*
import kotlinx.android.synthetic.main.activity_transaction_page.*
import maes.tech.intentanim.CustomIntent.customType
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.lang.Math.abs
import java.util.*
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter



class SubeventActivity : AppCompatActivity() {

    companion object{
        var subeventnamecounter: Int = 0
        var subbuttonflag = 0
    }
    var readpList_subevents: MutableList<Payers_subevent?>? = mutableListOf()
    var readnpList_subevents: MutableList<NonPayers_subevent?>? = mutableListOf()
    var splitforeach : MutableList<SplitForEach_subevents?> = mutableListOf() //class has members name and amt
    var x:Long = 0
    var y:Long = 0
    var sid: String? = null
    var eid: String? = null
    lateinit var subevent: subevents
    private lateinit var subref: DatabaseReference
    private lateinit var ReadNameref : DatabaseReference
    private lateinit var GetPayersref_subevents: DatabaseReference
    private lateinit var GetNonPayersref_subevents: DatabaseReference

    lateinit var layoutList: LinearLayout
    lateinit var headingdata: String
    var snametext: String? = null
    var status = 1
    private val STORAGE_CODE=1001
    private var topay: MutableList<SplitForEach_subevents> = mutableListOf()
    private var toreceive: MutableList<SplitForEach_subevents> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subevents)

        var subeventobj: subevents?

        val receivesubeventid = intent
        if (receivesubeventid.hasExtra("newsubeventid")) {
            eid = receivesubeventid.getStringExtra("eventforsub_id")
            sid = receivesubeventid.getStringExtra("newsubeventid")
        } else if (receivesubeventid.hasExtra("Backfromrolestosubevent eid")) {
            eid = receivesubeventid.getStringExtra("Backfromrolestosubevent eid")
            sid = receivesubeventid.getStringExtra("Backfromrolestosubevent sid")
        } else if (receivesubeventid.hasExtra("backtosubevents - eid")) {
            eid = receivesubeventid.getStringExtra("backtosubevents - eid")
            sid = receivesubeventid.getStringExtra("backtosubevents - sid")
        } else if (receivesubeventid.hasExtra("readsubeventidview - eid")) {
            eid = receivesubeventid.getStringExtra("readsubeventidview - eid")
            sid = receivesubeventid.getStringExtra("readsubeventidview - sid")
        }

        ReadNameref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("SubEvents").child(sid.toString())
            .child("SubEvent details")
        var readsubeventdetails = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    subeventobj = snapshot.getValue(subevents::class.java)
                    subevent_name.setText(subeventobj?.sname.toString())
                    snametext = subeventobj?.sname.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        ReadNameref.addValueEventListener(readsubeventdetails)



        backbutton_subevents.setOnClickListener {
            val back_intent = Intent(this, EventActivity::class.java)
            back_intent.putExtra("eventidbackfromsubevent", eid)
            startActivity(back_intent)
            customType(this, "right-to-left")
            finish()
        }

        savesubeventname.setOnClickListener {
            subbuttonflag = 1
            subref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Events").child(eid.toString()).child("SubEvents").child(sid.toString())
                .child("SubEvent details")
            var subeventename = subevent_name.text.toString().trim()
            if (TextUtils.isEmpty(subeventename)) {
                subeventnamecounter++
                subeventename = "Subevent ${subeventnamecounter}"
            }
            subevent = subevents(sid, subeventename)
            subref.setValue(subevent).addOnCompleteListener {
                Toast.makeText(baseContext, "Saved changes successfully!", Toast.LENGTH_LONG).show()
            }
        }

        addroles_subevents.setOnClickListener {
            if (subbuttonflag == 1) {
                val addrolessubevents_intent = Intent(this, rolesSubevent::class.java)
                addrolessubevents_intent.putExtra("Currenteventid", eid)
                addrolessubevents_intent.putExtra("Currentsubeventid", sid)
                startActivity(addrolessubevents_intent)
                customType(this, "left-to-right")
                finish()
            }
            else
                Toast.makeText(baseContext,"Save SubEvent name first to reference it later!",Toast.LENGTH_SHORT).show()
        }

        close_subevent.setOnClickListener {

            if (subbuttonflag == 1) {

                var pcount: Long = 0
                GetPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                        .child(eid.toString()).child("SubEvents").child(sid.toString()).child("Roles SubEvents").child("Payers")
                val getpayersdata_subevents = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            x = 0
                            readpList_subevents?.clear()
                            pcount = (snapshot.childrenCount)
                            for (counterobj in snapshot.children) {
                                val payerobj_subevents: Payers_subevent? = counterobj.getValue(Payers_subevent::class.java)
                                readplist(payerobj_subevents, pcount)
//                            Log.d("Reading payerslist : Name and pcount is:  ", "${payerobj_subevents?.payerName_subevent.toString()} and $pcount")
                            }
                        } else if (!snapshot.exists()) {
                            Toast.makeText(baseContext, "Enter valid roles with atleast one payer indicating the total amount", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)", Toast.LENGTH_SHORT).show()
                    }
                }
                GetPayersref_subevents.addValueEventListener(getpayersdata_subevents)

            }
            else
                Toast.makeText(baseContext,"Save SubEvent name first to reference it later!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun readplist(samplepobj: Payers_subevent?, pc: Long) {


        readpList_subevents?.add(samplepobj)
        Log.d("Added sampleobj to readpayerslist","${readpList_subevents?.get(x.toInt())?.payerName_subevent}")
        x++
        Log.d("inside readplist","Value of x after ++X is: $x")

        if (x==pc) {
            var npcount: Long = 0
            GetNonPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("SubEvents").child(sid.toString())
                .child("Roles SubEvents")
                .child("Non Payers")
            val getnonpayersdata_subevents = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        y=0
                        readnpList_subevents?.clear()
                        npcount = snapshot.childrenCount
                        for (counterobj in snapshot.children) {
                            val nonpayerobj_subevents: NonPayers_subevent? =
                                counterobj.getValue(NonPayers_subevent::class.java)
                            readnplist(nonpayerobj_subevents, npcount)
//                            Log.d("Reading nonpayerslist : Name and npcount is:  ", "${nonpayerobj_subevents?.nonpayerName_subevent.toString()} and $npcount")

                        }
                    }
                    else{
                        closesubevent()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)", Toast.LENGTH_SHORT).show()
                }
            }
            GetNonPayersref_subevents.addValueEventListener(getnonpayersdata_subevents)

        }
    }

    private fun readnplist(samplenpobj: NonPayers_subevent?, npc: Long) {

//        Log.d("Reading nonpayerslist : Name is:  ", "${samplenpobj?.nonpayerName_subevent.toString()} ")
        readnpList_subevents?.add(samplenpobj)
//        Log.d("Added sampleobj to readnonpayerslist","${readnpList_subevents?.get(y.toInt())?.nonpayerName_subevent}")
        y++

//        Log.d("inside readnplist","Value of y after ++y is: $y")

        if(y==npc)
        {
//
//            Log.d("Line 188, inside y==npc in readnplist: PAYERSLIST","${readpList_subevents.toString()}")
//            Log.d("Line 188, inside y==npc in readnplist","${readnpList_subevents.toString()}")
            closesubevent()
        }

    }



    private fun closesubevent() {

        var amountsum : Float = 0.0F

        for(payer in readpList_subevents!!) {
            amountsum += (payer?.payerAmt_subevent).toString().toFloat()
        }

        println("Size of readnplist: ${readnpList_subevents!!.size}")
        var totalmembers_subevent = (readpList_subevents!!.size) + (readnpList_subevents!!.size)


        var split_subevent = amountsum/totalmembers_subevent

        for(pcount_sub in 0 until (readpList_subevents!!.size)) {
//            Log.d("line 225 inside for loop: ", "pcount $pcount_sub")
            var samplepobj = SplitForEach_subevents(readpList_subevents!!.get(pcount_sub)?.payerName_subevent.toString() , split_subevent - (readpList_subevents!!.get(pcount_sub)?.payerAmt_subevent.toString().toFloat()))
            splitforeach.add(samplepobj)

        }

        for(randomvariable in 0 until (readnpList_subevents!!.size)) {
            var samplenpobj = SplitForEach_subevents(readnpList_subevents!!.get(randomvariable)?.nonpayerName_subevent.toString() , split_subevent )
            splitforeach.add(samplenpobj)
        }

        for(i in 0..(splitforeach.size)-1) {
            if(splitforeach.get(i)?.sub_amt!! < 0F)
            {
                toreceive.add(splitforeach.get(i)!!)
                println("To Receive: Name: ${splitforeach[i]?.sub_name.toString()} Amount: ${abs(splitforeach[i]?.sub_amt!!.toFloat())}")
            }
            else if(splitforeach.get(i)?.sub_amt!! > 0F)
            {
                topay.add(splitforeach.get(i)!!)
                println("To Pay: Name: ${splitforeach[i]?.sub_name.toString()} Amount: ${abs(splitforeach[i]?.sub_amt!!.toFloat())}")
            }
            else if(splitforeach.get(i)?.sub_amt == 0F)
            {
                println("Majaaa maadi ${splitforeach[i]?.sub_name.toString()}")
            }
        }
        if(topay.size == 0 && toreceive.size == 0)  {
            Toast.makeText(baseContext, "No transactions to be made", Toast.LENGTH_SHORT).show()
        } else {
            closeanimation()
        }
    }

    private fun closeanimation() {
        setContentView(R.layout.activity_closinganimation)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            closesubeventview()
        }, 1500)
    }

    private fun closesubeventview(){
        setContentView(R.layout.activity_transaction_page)

        layoutList = findViewById(R.id.layout_list_transactionpage)
        layoutList!!.clearAnimation()

        //add ename or sname of the event to view
        snameView()
        status = 1

        //add heading "To pay to settlepot account" to view
        if(topay.size != 0) {
            headingView()
        }

        //add to pay list to view
        for(member in topay) {
            membersView(member)
        }

        //add heading "To receive to settlepot account" to view
        status = 0
        if(toreceive.size != 0) {
            headingView()
        }

        //add to receive list to view
        for(member in toreceive) {
            membersView(member)
        }

        //go to subevent activity page
        backbutton_transactionpage.setOnClickListener {
            val gotosubeventactivity = Intent(this, EventActivity::class.java)
            gotosubeventactivity.putExtra("fromtransactionpage","$eid")
            startActivity(gotosubeventactivity)
            customType(this,"right-to-left")
            finish()
        }

        //share as pdf
        shareaspdf.setOnClickListener {
            //minimum sdk version required for writing on external storage(phone)
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                } else {
                    savePDF()
                }
            }
            else{
                savePDF()
            }
        }
    }

    private fun snameView() {
        val snameview: View = layoutInflater.inflate(R.layout.row_add_heading_transactionpage,null,false)
        val sname = snameview.findViewById<View>(R.id.transaction_heading) as TextView
        sname.setText(snametext)
        sname.setTextColor(Color.rgb(171,120,82))
        sname.setTypeface(null, Typeface. BOLD)
        layoutList!!.addView(snameview)
    }

    private fun headingView() {
        val headingview: View = layoutInflater.inflate(R.layout.row_add_heading_transactionpage,null,false)
        val headingname = headingview.findViewById<View>(R.id.transaction_heading) as TextView
        if (status == 1) {
            headingdata = "To Pay to the pot"
        } else {
            headingdata = "To Receive from the pot"
        }
        headingname.setText(headingdata)
        headingname.setTextColor(Color.rgb(60,210,0))
        layoutList!!.addView(headingview)
    }

    private fun membersView(member: SplitForEach_subevents) {
        val membersview: View = layoutInflater.inflate(R.layout.row_add_member_nameamt,null,false)
        val membersname = membersview.findViewById<View>(R.id.edit_membername) as TextView
        val membersamt = membersview.findViewById<View>(R.id.edit_memberamt) as TextView

        membersname.setText(member.sub_name)
        membersamt.setText(String.format("%.2f",abs(member.sub_amt)))
        layoutList!!.addView(membersview)
    }

    private fun savePDF() {

        try {
            val mDoc = Document(PageSize.A4, 5f, 5f, 5f, 5f)
            val fntSize: Float
            val fntSizesubheading = 14f
            val lineSpacing: Float
            fntSize = 16f
            lineSpacing = 10f

            //get date
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val date = "$day" + "_" + "$month" + "_" + "$year"

            //set file name and path
            val mFileName = "Settlepot_${snametext}_$date"
            val mFilePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/" + mFileName + ".pdf"


            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()


            //add logo
            val d = resources.getDrawable(R.drawable.settlepotlogo)
            val bitDw = d as BitmapDrawable
            val bmp = bitDw.bitmap
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val image = Image.getInstance(stream.toByteArray())
            image.alignment = Element.ALIGN_CENTER
            mDoc.add(Paragraph("\n\n\n"))
            mDoc.add(image)


            //val doc = Document()

            val p = Paragraph(
                Phrase(
                    lineSpacing, "\n\nTransaction page for $snametext\n\n",
                    FontFactory.getFont(FontFactory.COURIER, fntSize)
                )
            )
            p.alignment = Element.ALIGN_CENTER
            mDoc.add(p)



            //to pay list
            if(topay.size != 0) {
                val pp = Paragraph(
                    Phrase(
                        lineSpacing, "\n\n\nTo Pay to the Pot\n\n\n",
                        FontFactory.getFont(FontFactory.COURIER, fntSizesubheading)
                    )
                )
                pp.alignment = Element.ALIGN_CENTER
                mDoc.add(pp)
            }


            val tablepay = PdfPTable(2) // 2 columns.
            tablepay.setWidthPercentage(45F)
            tablepay.setSpacingBefore(10F)
            val columnWidths = floatArrayOf(2f, 1f)
            tablepay.setWidths(columnWidths)

            for (member in topay) {
                var cellname = PdfPCell(Paragraph("${member.sub_name}"))
                var cellamt = PdfPCell(Paragraph(String.format("%.2f",abs(member.sub_amt))))
                cellamt.setPadding(5f)
                cellname.setPadding(5f)
                tablepay.addCell(cellname).setHorizontalAlignment(Element.ALIGN_BASELINE)
                tablepay.addCell(cellamt).setHorizontalAlignment(Element.ALIGN_RIGHT)
            }
            mDoc.add(tablepay)

            //to receive list
            if(toreceive.size != 0) {
                val rr = Paragraph(
                    Phrase(
                        lineSpacing, "\n\n\nTo Receive from the Pot\n\n\n",
                        FontFactory.getFont(FontFactory.COURIER, fntSizesubheading)
                    )
                )
                rr.alignment = Element.ALIGN_CENTER
                mDoc.add(rr)
            }

            val tablereceive = PdfPTable(2) // 2 columns.
            tablereceive.setWidthPercentage(45F)
            tablereceive.setSpacingBefore(10F)
            tablereceive.setWidths(columnWidths)
            for (member in toreceive) {
                var cellname = PdfPCell(Paragraph("${member.sub_name}"))
                var cellamt = PdfPCell(Paragraph(String.format("%.2f",abs(member.sub_amt))))
                cellamt.setPadding(5f)
                cellname.setPadding(5f)
                tablereceive.addCell(cellname).setHorizontalAlignment(Element.ALIGN_BASELINE)
                tablereceive.addCell(cellamt).setHorizontalAlignment(Element.ALIGN_RIGHT)
            }
            mDoc.add(tablereceive)

            mDoc.close()
            Toast.makeText(
                this,
                "Transaction page: $mFileName.pdf\n is created to \n$mFilePath",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Log.e("error", "$e")
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode)
        {   //grant permissions to file manager
            STORAGE_CODE -> {
                if(grantResults.isNotEmpty()  &&  grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else{
                    Toast.makeText(this,"Permission denied!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}