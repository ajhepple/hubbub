package com.grailsinaction

class UserController {

    static scaffold = true

    def mailService
    def springSecurityService
    
    // Navigation plugin config
    static navigation = [
        [group:'tabs', action:'search', order: 90],
        [action: 'advancedSearch', order: 95],
        // isVisible accepts a boolean closure e.g. { isSignedIn() }
        [action: 'register', order: 99, isVisible: { true }]
    ]

    def search () {}

    def searchResults (String loginId) {
        def users = User.where{loginId =~ "%${loginId}%"}.list()
        [users: users, term: params.loginId, totalUsers: User.count()]
    }
    
    def advancedSearch () {}

    def advancedSearchResults () {
        def profileProperties = Profile.metaClass.properties*.name
        def profiles = Profile.withCriteria {
            "${params.queryType}" {
                params.each { field, value ->
                    if(profileProperties.contains(field) && value) {
                        ilike field, "%${value}%"
                    }
                }
            }
        }
        return [profiles: profiles]
    }

    def register () {
        if (request.method == "POST") {
            def user = new User(params)
            user.passwordHash = springSecurityService.encodePassword(params.password)
            if (user.validate() && user.save()) {
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [user: user]
            }
        }
    }

    def register2 (UserRegistrationCommand urc) {
        if (urc.hasErrors()) {
            render view: "register", model: [user: urc]
        } else {
            def user = new User(urc.properties)
            user.passwordHash = springSecurityService.encodePassword(params.password)
            user.profile = new Profile(urc.properties)
            if (user.validate() && user.save()) {
                flash.message = "Welcome aboard, ${urc.fullName ?: urc.loginId}"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [user: user]
            }
        }
    }
    def update () {
        // Get the logged-in user and attach it to this request's scope
        // TODO ** Not implemented yet **
        def user = session.user?.refresh()

        if (user) {
            // update the user with a blacklist technique
            bindData(user, params, ['loginId','passwordHash'])
            // update their profile using a whitelist technique
            user.profile.properties['email','fullName'] = params
            if (user.save()) {
                flash.message = "Successfully updated user"
            } else {
                flash.message = "Failed to update user"
            }
            [user: user]
        } else {
            response.sendError(404)
        }
    }

    // GET user/profile/$id
    def profile(String id) {
        def user = User.findByLoginId(id)
        if (user) {
            [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }

    // GET user/welcome-Email?email="recipient@example.com"
    def welcomeEmail (String email) {
        if (email) {
            mailService.sendMail {
                to email
                subject "Welcome to Hubbub!"
                // Explicitly define a GSP view (recommended) ...
                html view: "/user/welcomeEmail", model: [email: email]
                // ... or declare a view inline. 
//                text """
//                Hi, ${email}. Great to have you on board.
//                The Hubbub Team.
//                """
            }
            flash.message = "Welcome aboard, we have sent you a welcome email"
        }
        redirect(uri: "/")
    }
}

class UserRegistrationCommand {
    String loginId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    static constraints = {
        importFrom Profile
        importFrom User
        password(size: 6..8, blank: false,
            validator: { pwd, urc ->
                return pwd != urc.loginId
            }
        )
        passwordRepeat(nullable: false,
                validator: { pwd2, urc ->
                    return pwd2 == urc.password
                }
        )
    }
}
