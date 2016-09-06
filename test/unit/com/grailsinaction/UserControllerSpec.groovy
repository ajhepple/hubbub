package com.grailsinaction

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(UserController)
@Mock([User, Profile])
class UserControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "Registering a user with known good parameters"() {
    
        given: "a set of user parameters"
        params.with {
            loginId = "glen_a_smith"
            password = "winnning"
            homepage = "http://blogs.bytecode.com.au/glen"
        }
    
        and: "a set of profile parameters"
        params['profile.fullName'] = "Glen Smith"
        params['profile.email'] = "glen@bytecode.com.au"
        params['profile.homepage'] = "http://blogs.bytecode.au/glen"
    
        when: "the user is registered"
        request.method = "POST"
        controller.register()
    
        then: "the user is created and the browser redirected"
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1
    }
}
