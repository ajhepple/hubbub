package com.grailsinaction

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll
import grails.plugin.springsecurity.SpringSecurityService

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
            passwordHash = "winnning"
            homepage = "http://blogs.bytecode.com.au/glen"
        }
    
        and: "a set of profile parameters"
        params['profile.fullName'] = "Glen Smith"
        params['profile.email'] = "glen@bytecode.com.au"
        params['profile.homepage'] = "http://blogs.bytecode.au/glen"
    
        and: "a mock security service"
        controller.springSecurityService = Stub(SpringSecurityService) {
            encodePassword("winning") >> "HFDJDKALSJDF"
        }

        when: "the user is registered"
        request.method = "POST"
        controller.register()
    
        then: "the user is created and the browser redirected"
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1
    }

    @Unroll
    def "Registration command object for #loginId validates correctly"() {
    
        given: "a mocked command object"
        def urc = new UserRegistrationCommand()
    
        and: "a set of initial values from the spock test"
        urc.loginId = loginId
        urc.password = password
        urc.passwordRepeat = passwordRepeat
        urc.fullName = "Your Name Here"
        urc.email = "someone@nowhere.net"
    
        when: "the validator is invoked"
        def isValidRegistration = urc.validate()
    
        then: "the appropriate fields are flagged as errors"
        isValidRegistration == anticipatedValid
        urc.errors.getFieldError(fieldInError)?.code == errorCode
    
        where:
        loginId | password      | passwordRepeat        | anticipatedValid      | fieldInError          | errorCode
        "glen"  | "password"    | "no-match"            | false                 | "passwordRepeat"      | "validator.invalid"
        "peter" | "password"    | "password"            | true                  | null                  | null
        "a"     | "password"    | "password"            | false                 | "loginId"             | "size.toosmall"
    }

    def "Invoking the register action via a command object"() {
    
        given: "A configured command object"
        def urc = new UserRegistrationCommand()
        urc.with {
            loginId = "glen_a_smith"
            fullName = "Glen Smith"
            email = "glen@example.com"
            password = "password"
            passwordRepeat = "password"
        }
    
        and: "which has been validated"
        urc.validate()
    
        and: "a mock security service"
        controller.springSecurityService = Stub(SpringSecurityService) {
            encodePassword("password") >> "HFDJDKALSJDF"
        }

        when: "the register action is invoked"
        controller.register2(urc)
    
        then: "the user is registered and the browser redirected"
        !urc.hasErrors()
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1
    }
}
