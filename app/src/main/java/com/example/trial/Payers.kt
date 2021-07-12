package com.example.trial



class Payers{
    var payerName: String? = null
    var payerAmt: String? = null

    constructor() {}
    //store payers name and amount for events
    constructor(payerName: String?, payerAmt: String?) {
        this.payerName = payerName
        this.payerAmt = payerAmt
    }
}