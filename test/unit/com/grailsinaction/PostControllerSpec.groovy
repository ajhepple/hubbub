package com.grailsinaction

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PostController)
@Mock([User,Post])
class PostControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "Get a user's timeline given their id" () {
        given: "A user with posts in the db"
        User chuck = new User(
                loginId: "chuck_norris",
                password: "password")
        chuck.addToPosts(new Post(content: "A first post"))
        chuck.addToPosts(new Post(content: "A second post"))
        chuck.save(failOnError: true)

        and: "a loginId parameter"
        params.id = chuck.loginId

        when: "the timeline is invoked"
        def model = controller.timeline()

        then: "the user is in the returned model"
        model.user.loginId == "chuck_norris"
        model.user.posts.size() == 2
    }

    def "Check that non-existant users are handled with an error" () {
        given: "The id of a non-existant user"
        params.id = "this-user-id-does-not-exist"
    
        when: "the timeline is invoked"
        controller.timeline()
    
        then: "a 404 response is returned"
        response.status == 404
    }

    def "Adding a valid new post to the timeline" () {
        given: "A user with posts in the db"
        User chuck = new User(
                loginId: "chuck_norris",
                password: "password").save(failOnError: true)
    
        and: "A loginId parameter"
        params.id = chuck.loginId
    
        and: "Some content for the post"
        params.content = "Chuck Norris can unit test entire applications with a single asssert."
    
        when: "addPost is invoked"
        def model = controller.addPost()
    
        then: "our flash message and redirect confirms the success"
        flash.message == "New post created"
        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
        Post.countByUser(chuck) == 1
    }

    def "Atempting to add an empty post results in error" () {
        given: "A user with posts in the db"
        User chuck = new User(
                loginId: "chuck_norris",
                password: "password").save(failOnError: true)
    
        and: "A loginId parameter"
        params.id = chuck.loginId
    
        and: "Empty content for the post"
        params.content = ""
    
        when: "addPost is invoked"
        def model = controller.addPost()
    
        then: "Our flash message and redirect confirms the error"
        flash.message == "Invalid or empty post"
        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
        Post.countByUser(chuck) == 0
    }

    def "Attempting to add a post to for invalid user" () {
        given: "An invalid user id"
        params.id = "non_existant"
    
        and: "A valid post content"
        params.content = "A valid post."
    
        when: "addPost is invoked"
        def model = controller.addPost()
    
        then: "An flash message is available and redirect to user index"
        flash.message == "Invalid user"
        response.redirectedUrl == "/user/index"
    }

    @spock.lang.Unroll  //allows use of where clause parameters in test name
    def "Testing id of #suppliedId redirects to #expectedUrl" () {
        given:
        params.id = suppliedId
    
        when: "Controller is invoked"
        controller.home()
    
        then:
        response.redirectedUrl == expectedUrl
    
        where:
        suppliedId      |       expectedUrl
        'joe_cool'      |       '/post/timeline/joe_cool'
        null            |       '/post/timeline/chuck_norris'
    }
}
