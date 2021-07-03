package com.example.trial

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_payers_subevent.*
import java.util.*


class ActivityPayers_subevent : AppCompatActivity() {

    var payersList_subevent: ArrayList<Payers_subevent> = ArrayList()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_subevent)

        recycler_payers_subevent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        payersList_subevent = intent.extras!!.getSerializable("list") as ArrayList<Payers_subevent>
        recycler_payers_subevent.setAdapter(PayersAdapter_subevent(payersList_subevent))
    }
}