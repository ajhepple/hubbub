package com.grailsinaction

class PostController {
    static scaffold = true

    def timeline () {
        def user = User.findByLoginId(params.id)
        if (!user) {
            response.sendError(404)
        } else {
            //render(view: "timeline", model:[user: user])
            // or, if the view name matches the controller action, simply... 
            [user: user] 
        }
    }

    def addPost () {
        def user = User.findByLoginId(params.id)
        if(user) {
            def post = new Post(params)
            user.addToPosts(post)
            if (user.save()) {
                flash.message = "New post created"
            } else {
                flash.message = "Invalid or empty post"
            }
            redirect(action: 'timeline', id: params.id)
        } else {
            flash.message = "Invalid user"
            redirect(controller: 'user', action: 'index')
        }
    }
}
