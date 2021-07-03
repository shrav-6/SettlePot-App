package com.example.trial

import java.io.Serializable

class Payers : Serializable {
    var payerName: String? = null
    var payerAmt: String? = null

    constructor() {}
    constructor(payerName: String?, payerAmt: String?) {
        this.payerName = payerName
        this.payerAmt = payerAmt
    }
}