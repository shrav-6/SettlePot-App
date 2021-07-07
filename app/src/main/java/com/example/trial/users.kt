package com.example.trial

class users {
    var id: String? = null
    var name: String? = null
    var mailid: String? = null
    var phoneno: String? = null

    constructor(){
    }

    constructor(id: String?, name: String?, mailid: String?, phoneno: String?) {
        this.id = id
        this.name = name
        this.mailid = mailid
        this.phoneno = phoneno
    }
}
