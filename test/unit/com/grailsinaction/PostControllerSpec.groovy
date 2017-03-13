package com.grailsinaction

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import static grails.util.GrailsNameUtils.*
import grails.plugin.springsecurity.SpringSecurityService

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
                passwordHash: "asdfjkljk")
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

    def "Adding a new post to the timeline using a mocked PostService" () {
        given: "mock post and security services"
        def mockPostService = Mock(PostService)
        1 * mockPostService.createPost(_, _) >> new Post(content: "Mock post")
        controller.postService = mockPostService

        def securityService = Mock(SpringSecurityService)
        _ * securityService.getCurrentUser() >> new User(loginId: "joe_cool")
        controller.springSecurityService = securityService
        
        when: "controller is invoked"
        def result = controller.addPost("Mock post")

        then: "redirected to timeline, flash message tells us all is well"
        flash.message ==~ /Added new post.*Mock post*/
        response.redirectedUrl == '/users/joe_cool'
        // or, without the custom UrlMapping 
        // response.redirectedUrl == '/post/timeline/joe_cool'
    }

    def "Attempting to add an empty post results in error" () {
        given: "A mock security services"
        def securityService = Mock(SpringSecurityService)
        _ * securityService.getCurrentUser() >> new User(loginId: "chuck_norris")
        controller.springSecurityService = securityService
    
        and: "a mock PostService"
        def mockPostService = Mock(PostService)
        1 * mockPostService.createPost(_, null) >> { throw new PostException(message: "Invalid or empty post")}
        controller.postService = mockPostService

        when: "addPost is invoked"
        def model = controller.addPost(null)
    
        then: "Our flash message and redirect confirms the error"
        flash.message == "Invalid or empty post"
        response.redirectedUrl == "/users/chuck_norris"
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
        'joe_cool'      |       '/users/joe_cool'
        null            |       '/users/chuck_norris'
    }

    void "Check that the Post service is available and has the expected name" () {
        given: "A service class named PostService"

        expect: "its property name should be postService"
        getPropertyName('PostService') == 'postService'
    }

// (These security filters were superceded by Spring Security Service)
//    /** Testing the behavour of security filters defined in grails-app/conf */
//    def "Exercising security filter for unauthenticated user"() {
//        when:
//        //withFilters(action: "addPost") {
//            controller.addPost("A first post")
//        //}
//        
//        then:
//        response.redirectedUrl == '/login'
//    }
}
