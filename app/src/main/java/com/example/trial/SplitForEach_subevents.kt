package com.example.trial

class SplitForEach_subevents {

        var sub_name: String = ""
        var sub_amt: Float = 0.0F

        constructor() {}

        //store members name and amount after split for subevents
        constructor(sub_name: String, sub_amt: Float) {
                this.sub_name = sub_name
                this.sub_amt = sub_amt
        }
}