package com.grailsinaction

class UserController {

    static scaffold = true
    
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
            bindDate(user, params, ['loginId','password'])
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
    
        passwordRepeat(nullable: false,
                validator: { passwd2, urc ->
                    return passwd2 == urc.password
                })
    }
}
