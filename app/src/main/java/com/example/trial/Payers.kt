package com.example.trial



class Payers{
    var payerName: String? = null
    var payerAmt: String? = null

    constructor() {}
    constructor(payerName: String?, payerAmt: String?) {
        this.payerName = payerName
        this.payerAmt = payerAmt
    }
}