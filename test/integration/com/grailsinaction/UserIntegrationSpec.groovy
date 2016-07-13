package com.grailsinaction



import spock.lang.*

/**
 *
 */
class UserIntegrationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Saving our first user to the database"() {
        
        given: "A brand new user"
        def joe = new User(loginId: 'joe', password: 'secret',
                homepage: 'http://www.grailsinaction.com')

        when: "the user is saved"
        joe.save()

        then: "it saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }

    void "Password should not be blank" () {
        given: "A brand new user"
        def fred = new User(loginId: 'fred', password: '', homepage: '')

        when: "the user is saved"
        fred.save()

        then: "it should not be saved successfully."
        fred.errors.errorCount >= 0
        fred.errors.getFieldError('password') != null   // cannot call fieldError('password') as this only
                                                        // is not a 'property' i.e. method without arguments
        fred.id == null
    }

    void "Updating a saved user changes its properties" () {

        given: "An existing user"
        def existingUser = new User(loginId: 'joe', password: 'secret',
                homepage: 'http://www.grailsinaction.com')
        existingUser.save(failOnError: true)

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.password = 'sesame'
        foundUser.save(failOnError: true)

        then: "the change is reflected in the database"
        User.get(existingUser.id).password == 'sesame'
    }

    void "Deleting an existing user removes it from the database" () {

        given: "An existing user"
        def user = new User(loginId: 'joe', password: 'secret',
                homepage: 'http://www.grailsinaction.com')
        user.save(failOnError: true)

        when: "The user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush: true)

        then: "The user is removed from the database"
        !User.exists(foundUser.id)
    }

    def "Saving a user with invalid properties causes an error" () {
        
        given: "A user which fails several field validations"
        def user = new User(loginId: 'joe', password: 'tiny', homepage: 'not-a-url')
    
        when: "The user is validated"
        user.validate()

        then:
        user.hasErrors()
        "size.toosmall" == user.errors.getFieldError("password").code
        "tiny" == user.errors.getFieldError("password").rejectedValue
        "url.invalid" == user.errors.getFieldError("homepage").code
        "not-a-url" == user.errors.getFieldError("homepage").rejectedValue
        !user.errors.getFieldError("loginId")
    }

    def "Recovering from a failed save by fixing invalid properties" () {
        
        given: "A uer that has invalid properties" 
        def chuck = new User(loginId: 'chuck', password: 'tiny', homepage: 'not-a-url')
        assert chuck.save() == null
        assert chuck.hasErrors()

        when: "We fix the invalid properties"
        chuck.clearErrors()
        assert !chuck.hasErrors()
        chuck.password = "fistcuff"
        chuck.homepage = "http://www.chucknorrisfacts.com"
        chuck.validate()

        then: "The user saves and validates."
        !chuck.hasErrors()
        chuck.save()
    }

    def "Saving a user with similar username and password causes an error" () {
    
        given: "A user with similar username and password"
        def fred = new User(loginId: 'freddy', password: 'freddy', homepage: 'http://www.fred.com')
    
        when: "The user is saved"
        fred.save()
    
        then: "There is an appropriate password error code"
        fred.errors.getFieldError("password").code == "equals.username"
    }
}
