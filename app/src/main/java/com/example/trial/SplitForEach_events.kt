package com.example.trial

class SplitForEach_events {

    var e_name: String = ""
    var e_amt: Float = 0.0F

    constructor() {}

    //store members name and amount after split for events
    constructor(e_name: String, e_amt: Float) {
        this.e_name = e_name
        this.e_amt = e_amt
    }
}