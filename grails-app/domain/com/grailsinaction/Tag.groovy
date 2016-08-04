package com.grailsinaction

class Tag {

    String name
    User user

    static hasMany = [posts: Post]
    static belongsTo = [User, Post]     // note that no back reference is necessary here
    static constraints = {
        name blank: false
    }

    String getDisplayString () { name }
}
