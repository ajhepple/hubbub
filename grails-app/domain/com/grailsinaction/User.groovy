package com.grailsinaction

class User {

    transient springSecurityService

    String loginId
    String passwordHash
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String homepage
    Date dateCreated
    Set following = []

    static hasOne = [profile: Profile]
    static hasMany = [posts: Post, tags: Tag, following: User]
    static mapping = {
        posts(sort: 'dateCreated', order: 'desc')
    }
    static constraints = {
        // note that blank: false is stronger than nullable: false
        // (as nullable: false does not prevent an empty string)
        // and that all domain object properties have nullable: false
        // by default.
        loginId blank: false, size: 3..20, unique: true        
        homepage url: true, nullable: true
        posts() // no validation but these empty methods calls to
        tags()  // affect the ordering of fields in scaffolding UI
        profile nullable: true
    }
    // static searchable = true    // i.e. with the searchable plugin (simplest way to declare)
    static searchable = {
        except = ['passwordHash']    // index all fields except passwordHash
        // alternatively, using the 'only' declaration
        //only = ['loginId','homepage','following']
    }

    static transients = ['springSecurityService']

    String toString () { "[${loginId}] ${profile ? profile.displayString : ""}" }

    String getDisplayString () { toString() }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this)*.role
    }

}
