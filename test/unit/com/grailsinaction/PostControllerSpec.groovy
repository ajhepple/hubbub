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
}
