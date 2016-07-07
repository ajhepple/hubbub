package com.grailsinaction

class User {

    // User properties (these will be automagically persisted by grails)
    String loginId
    String password
    String homepage
    Date dateCreated

    static constraints = {
        password(blank: false)
        loginId(blank: false)
    }
}
