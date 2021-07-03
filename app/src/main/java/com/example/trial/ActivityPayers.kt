package com.example.trial

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_payers.*
import java.util.*


class ActivityPayers : AppCompatActivity() {

    var payersList: ArrayList<Payers> = ArrayList()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers)

        recycler_payers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        payersList = intent.extras!!.getSerializable("list") as ArrayList<Payers>
        recycler_payers.setAdapter(PayersAdapter(payersList))
    }
}