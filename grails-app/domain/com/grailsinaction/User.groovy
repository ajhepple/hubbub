package com.grailsinaction

class User {

    // User properties (these will be automagically persisted by grails)
    String loginId
    String password
    String homepage
    Date dateCreated
    Set following = []

    static hasOne = [profile: Profile]
    static hasMany = [posts: Post, tags: Tag, following: User]
    static mapping = {
        posts sort: 'dateCreated'
    }
    static constraints = {
        // note that blank: false is stronger than nullable: false
        // (as nullable: false does not prevent an empty string)
        // and that all domain object properties have nullable: false
        // by default.
        loginId blank: false, size: 3..20, unique: true
        password size: 6..8, blank: false, validator: { pwd, user ->
             (pwd != user.loginId) ? true : "equals.username"
        }
        homepage url: true, nullable: true
        profile nullable: true
    }
}
