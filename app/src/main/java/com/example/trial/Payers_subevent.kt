package com.example.trial



class Payers_subevent  {
    var payerName_subevent: String? = null
    var payerAmt_subevent: String? = null

    constructor() {}
    //store payers name and amount for subevents
    constructor(payerName_subevent: String?, payerAmt_subevent: String?) {
        this.payerName_subevent = payerName_subevent
        this.payerAmt_subevent = payerAmt_subevent
    }
}