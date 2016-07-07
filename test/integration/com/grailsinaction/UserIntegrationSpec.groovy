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
}
