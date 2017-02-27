package com.grailsinaction

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.Mock
//import grails.plugin.springsecurity.SpringSecurityService

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PostService)
@Mock([User,Post])
class PostServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Valid posts get saved and added to the user" () {

        given: "a new user in the db"
        new User(loginId: "chuck_norris",
                passwordHash: "password").save(failOnError: true)

        when: "the post is created by the service"
        def newPost = service.createPost("chuck_norris", "First post!")

        then: "the returned post can be referenced from the user"
        newPost.content == "First post!"
        User.findByLoginId("chuck_norris").posts.size() == 1
    }

    void "Invalid posts generate exceptional outcomes" () {
    
        given: "A new user in the db"
        new User(loginId: "chuck_norris",
                passwordHash: "password").save(failOnError: true)
    
        when: "an invalid post is attempted"
        def newPost = service.createPost("chuck_norris", null)
    
        then: "an exception is thrown and no post is saved."
        thrown(PostException)
    }
}
