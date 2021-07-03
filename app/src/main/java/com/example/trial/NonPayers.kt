package com.example.trial

import java.io.Serializable

class NonPayers : Serializable {
    var nonpayerName: String? = null


    constructor() {}
    constructor(nonpayerName: String?) {
        this.nonpayerName = nonpayerName

    }
}