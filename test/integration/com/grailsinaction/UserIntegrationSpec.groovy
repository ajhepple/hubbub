package com.grailsinaction



import spock.lang.*

/**
 *
 */
class UserIntegrationSpec extends Specification {

    // Dumbster Spring bean
    def dumbster

    def setup() {
    }

    def cleanup() {
    }

    void "Saving our first user to the database"() {
        
        given: "A brand new user"
        def joe = new User(loginId: 'joe', passwordHash: 'secret',
                homepage: 'http://www.grailsinaction.com')

        when: "the user is saved"
        joe.save()

        then: "it saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }

    void "Password should not be blank" () {

        /* The use of spring-security plugin in Chapter 11 affected this test */
//        given: "A brand new user"
//        def fred = new User(loginId: 'fred', passwordHash: '', homepage: '')
//
//        when: "the user is saved"
//        fred.save()
//
//        then: "it should not be saved successfully."
//        fred.errors.errorCount >= 0
//        fred.errors.getFieldError('passwordHash') != null   // cannot call fieldError('password') as this only
//                                                        // is not a 'property' i.e. method without arguments
//        fred.id == null
    }

    void "Updating a saved user changes its properties" () {

        given: "An existing user"
        def existingUser = new User(loginId: 'joe', passwordHash: 'secret',
                homepage: 'http://www.grailsinaction.com')
        existingUser.save(failOnError: true)

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.loginId = 'jane'
        foundUser.save(failOnError: true)

        then: "the change is reflected in the database"
        User.get(existingUser.id).loginId == 'jane'
    }

    void "Deleting an existing user removes it from the database" () {

        given: "An existing user"
        def user = new User(loginId: 'joe', passwordHash: 'secret',
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
        def user = new User(loginId: 'joe', passwordHash: 'tiny', homepage: 'not-a-url')
    
        when: "The user is validated"
        user.validate()

        then:
        user.hasErrors()
        // No longer true since chapter 11
        //"size.toosmall" == user.errors.getFieldError("passwordHash").code
        //"tiny" == user.errors.getFieldError("passwordHash").rejectedValue
        "url.invalid" == user.errors.getFieldError("homepage").code
        "not-a-url" == user.errors.getFieldError("homepage").rejectedValue
        !user.errors.getFieldError("loginId")
    }

    def "Recovering from a failed save by fixing invalid properties" () {
        
        given: "A uer that has invalid properties" 
        def joe = new User(loginId: 'me', passwordHash: 'tiny', homepage: 'not-a-url')
        assert joe.save() == null
        assert joe.hasErrors()

        when: "We fix the invalid properties"
        joe.clearErrors()
        assert !joe.hasErrors()
        joe.loginId = "joe"
        joe.homepage = "http://www.joessuperiorfacts.com"
        joe.validate()

        then: "The user saves and validates."
        !joe.hasErrors()
        joe.save()
    }

    def "Saving a user with similar username and password causes an error" () {
    
        /* The introduction of spring-security in Chapter 11 affected this test */
//        given: "A user with similar username and password"
//        def fred = new User(loginId: 'freddy', passwordHash: 'freddy', homepage: 'http://www.fred.com')
//    
//        when: "The user is saved"
//        fred.save()
//    
//        then: "There is an appropriate password error code"
//        fred.errors.getFieldError("passwordHash").code == "equals.username"
    }

    def "Ensure a user can follow other users" () {
    
        given: "A set of baseline users" 
        def joe = new User(loginId: 'joe', passwordHash: 'secret').save()
        def jane = new User(loginId: 'jane', passwordHash: 'secret').save()
        def jill = new User(loginId: 'jill', passwordHash: 'secret').save()
    
        when: "Joe follows Jand and Jill, and Jill follows Jane"
        joe.addToFollowing(jane)
        joe.addToFollowing(jill)
        jill.addToFollowing(jane)
    
        then: "Following counts must be consistent"
        2 == joe.following.size()
        1 == jill.following.size()
        0 == jane.following.size() //causes NullPointerException unless following is declared as an empty set.
    }

    def "Welcome email is generated and sent" () {

        given: "An empty inbox"
        dumbster.reset()

        and: "a user controller and request params"
        def userController = new UserController()
        userController.params.email = "tester@email.com"

        when: "A welcome email is sent"
        //userController.welcomeEmail("tester@email.com")
        userController.welcomeEmail()

        then: "It appears in their inbox"
        dumbster.getMessageCount() == 1
        //def msg = dumbster.getMessages().first()
        def msg = dumbster.messages.first()
        msg.subject == "Welcome to Hubbub!"
        msg.to == "tester@email.com"
        msg.body =~ /The Hubbub Team/
    }
}
