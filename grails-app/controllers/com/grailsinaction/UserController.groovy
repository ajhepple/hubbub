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
}
