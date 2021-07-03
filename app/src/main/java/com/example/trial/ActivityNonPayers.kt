package com.example.trial

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_nonpayers.*
import java.util.*


class ActivityNonPayers : AppCompatActivity() {

    var nonpayersList: ArrayList<NonPayers> = ArrayList()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayers)

        recycler_nonpayers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        nonpayersList = intent.extras!!.getSerializable("list") as ArrayList<NonPayers>
        recycler_nonpayers.setAdapter(NonPayersAdapter(nonpayersList))
    }
}