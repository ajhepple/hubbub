package com.grailsinaction

import grails.plugin.cache.*

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
    def springSecurityService

    def home () {
        if(!params.id) {
            params.id = "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }

    @CachePut(value='userTimeline')     // default cache key is concatonation of parameters
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
/*
    // Adding a new post means that a cached timeline must be evicted
    @CacheEvict(value='userTimeline', key='#id')
    def addPost (String id, String content) {
        try {
            def newPost = postService.createPost(id,content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }

    // Adding a new post means that a cached timeline must be evicted
    @CacheEvict(value='userTimeline', key='#id')
    def addPostAjax (String id, String content) {
        try {
            def newPost = postService.createPost(
                    session.user.loginId, content)
            def recentPosts = Post.findAllByUser(
                    session.user,
                    [sort: 'dateCreated', order: 'desc', max: 20])
            render template: 'postEntry',
                    collection: recentPosts,
                    var: 'post'
        } catch (PostException pe) {
            render {
                div(class: "errors", pe.message ?: "unfathomable error")
            }
        }
    }
*/

    def addPostAjax (String content) {
        def user = springSecurityService.currentUser
        try {
            def newPost = postService.createPost(user.loginId, content)
            def recentPosts = Post.findAllByUser(
                    user,
                    [sort: 'dateCreated', order: 'desc', max: 20])
            render template: 'postEntry',
                    collection: recentPosts,
                    var: 'post'
        } catch (PostException pe) {
            render {
                div(class: "errors", pe.message ?: "unfathomable error")
            }
        }
    }

    // Adding a new post means that a cached timeline must be evicted
    // @CacheEvict(value='userTimeline', key='#id')
    def addPost (String content) {
        def user = springSecurityService.currentUser
        try {
            def newPost = postService.createPost(user.loginId, content)
            flash.message = "Added new post (using sss): ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: user.loginId)
    }

    // Cache this action using the userTimeline cache and a custom key
    // ** I later concluded that the session object is not avialable
    // ** in the context of this cache annotation.
    // @CachePut(value='userTimeline', key='#session.user.loginId')
    def personal () {
        //def user = session.user?.refresh()
        def user = springSecurityService.currentUser

//        if (user) {
            render view: 'timeline', model: [user: user]
//        } else {
//            redirect(uri: '/login')
//        }
    }

    //@Cacheable('globalTimeline')
    def global () {
        params.max = params.int('max', 6)  //default value 6 if max not present
        [ currentUser: springSecurityService.currentUser,
                posts: Post.list(params), postCount: Post.count()]
    }

    def tinyUrl (String fullUrl) {
        def longUrl = fullUrl?.encodeAsURL()
        def tinyUrl = new URL("http://tinyurl.com/api-create.php?url=${longUrl}").text
        render(contentType:"application/json") {
            urls(small: tinyUrl, full: fullUrl)
        }
    }

    // An action for searching posts by tags which uses a command object.
    // The command object demonstrates parameter constraints which provide
    // security against malicious attack via POST data manipulation.
    //
    // TODO Implement a view for this action.
    def tagSearch(TagSearchCommand cmd) {
        if (cmd.hasErrors()) {
            render status: 400, text: "Invalid search parameters"
            return
        }
        def results = Post.findAllByTag(cmd.tag, [max: cmd.max])
    }
}

class TagSearchCommand {
    String tag
    int max
    int offset
    
    // Recommended way to apply validation checks to URL parameters.
    // Particularly if the command object can be re-used by multiple actions.
    static constraints = {
        max min: 0, max: 500
        offset min: 0, max: 500
    }
}
