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
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
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
        def user = session.user?.attach()

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
}
