package com.grailsinaction

class SecurityFilters {

    def springSecurityService

    def filters = {

        profileChanges(controller: "profile", action: "edit|update") {
            
            /* True iff the user attempts to change their own profile */
            before = {
                def currentLoginId = springSecurityService.currentUser.loginId
                currentLoginId == Profile.get(params.id).user.loginId
            }
        }
    }
}
