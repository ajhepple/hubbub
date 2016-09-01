package com.grailsinaction

class Post {

    String content
    Date dateCreated

    /* Provide some explicit configuration for how this domain
     * class maps to the database table */
    static mapping = {
        // ensures that all db queries return posts in a meaninful order
        // *** Does not appear to be working in Grails 2.3.7 ***
        sort dateCreated: "desc" 
    }

    static belongsTo = [user: User]
    static hasMany = [tags: Tag]
    static constraints = {
        content blank: false
    }

    String getDisplayString () { content.take(20) }
}
