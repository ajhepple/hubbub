package com.grailsinaction

class PostController {
    static scaffold = true
    static defaultAction = 'home'

    // Navigation plugin config
    // I'm not sure that this is the best place to configure a GUI menu! 
    static navigation = [
        [group: 'tabs', action: 'personal', title: 'My Timeline', order: 0],
        [action: 'global', title: 'Global Timeline', order: 1]
    ]

    PostService postService

    def home () {
        if(!params.id) {
            params.id = "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }

    def timeline (String id) {
        def user = User.findByLoginId(id)
        if (!user) {
            response.sendError(404)
        } else {
            //render(view: "timeline", model:[user: user])
            // or, if the view name matches the controller action, simply... 
            [user: user] 
        }
    }

    def addPost (String id, String content) {
        try {
            def newPost = postService.createPost(id,content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }

    def personal () {
        def user = session.user?.refresh()

        if (user) {
            render view: 'timeline', model: [user: user]
        } else {
            redirect(uri: '/login')
        }
    }

    def global () {
        params.max = params.int('max', 6)  //default value 6 if max not present
        [posts: Post.list(params), postCount: Post.count()]
    }
}
