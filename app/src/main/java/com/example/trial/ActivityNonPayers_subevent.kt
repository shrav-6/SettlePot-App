package com.example.trial

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_non_payers_subevent.*
import java.util.*


class ActivityNonPayers_subevent : AppCompatActivity() {

    var nonpayersList_subevent: ArrayList<NonPayers_subevent> = ArrayList()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_payers_subevent)

        recycler_nonpayers_subevent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        nonpayersList_subevent = intent.extras!!.getSerializable("list") as ArrayList<NonPayers_subevent>
        recycler_nonpayers_subevent.setAdapter(NonPayersAdapter_subevent(nonpayersList_subevent))
    }
}