package com.grailsinaction

class Profile {

    //User user // removed in favour of a 'belongsTo declaration
    byte[] photo        // byte[] type used for file upload
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    static belongsTo = [user: User]

    static constraints = {
        fullName blank: false
        bio nullable: true, maxSize: 1000
        homepage url: true, nullable: true
        email email: true, blank: false
        photo nullable: true, maxSize: 2 * 1024 * 1024  // 2 MB
        country nullable: true
        timezone nullable: true
        jabberAddress email: true, nullable: true
    }
}
