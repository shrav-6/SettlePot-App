package com.example.trial

class NonPayers {
    var nonpayerName: String? = null


    constructor() {}
    //save non-payername for events
    constructor(nonpayerName: String?) {
        this.nonpayerName = nonpayerName

    }
}