package com.grailsinaction

class PostController {
    static scaffold = true

    def timeline () {
        def user = User.findByLoginId(params.id)
        if (!user) {
            response.sendError(404)
        } else {
            render(view: "timeline", model:[user: user])
            /* or, if the view name matches the controller action, simply... */
            //[user: user] 
        }
    }
}
