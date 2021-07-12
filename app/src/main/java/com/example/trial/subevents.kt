package com.example.trial

class subevents {
    var sid: String? = ""
    var sname: String = ""

    constructor(){}
    //store subevents id and name
    constructor(sid: String?, sname: String) {
        this.sid = sid
        this.sname = sname
    }

}