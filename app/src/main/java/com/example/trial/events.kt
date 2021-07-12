package com.example.trial

class events {
    var eid: String? = ""
    var ename: String = ""

    constructor() {}
    //store event name and id
    constructor(eid: String?, ename: String) {
        this.eid = eid
        this.ename = ename
    }
}