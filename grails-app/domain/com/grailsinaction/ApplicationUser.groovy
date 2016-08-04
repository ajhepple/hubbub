package com.grailsinaction

/**
 * An application user is expected to be a third-party application
 * that consumes the Hubbub API services.
 */
class ApplicationUser {

    String applicationName
    String password
    String apiKey

    static constraints = {
        
        // example of reusing constraints from another domain class
        importFrom User, include: ['password']
        /* alternatively, the following will import all constraints for all
         * properties of User whose names match those of ApplicationUser */
        // importFrom User

        applicationName blank: false, unique: true
        apiKey blank: false

    }
}
