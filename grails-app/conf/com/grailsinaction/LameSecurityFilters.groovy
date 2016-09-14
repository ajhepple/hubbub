package com.grailsinaction

class LameSecurityFilters {

    def filters = {

        /* check session user for actions that require login */
        secureActions(controller:'post', action:'(addPost|deletePost)') {
            before = {
                if (params.impersonateId) {
                    session.user = User.findByLoginId(params.impersonateId)
                }
                if (!session.user) {
                    redirect(controller: 'login', action: 'form')
                    return false
                }
            }
            after = { Map model ->
                //We could augment the model here if we wished.
                //Any additions will be available to the view.
                //NB Avoid changing existing model values, this is bad practice.
            }
            afterView = { 
                log.debug "Finished running ${controllerName}/${actionName}"
            }
        }
    }
}
