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

    // Cache this action using the userTimeline cache and a custom key
    // ** I later concluded that the session object is not avialable
    // ** in the context of this cache annotaion.
    // @CachePut(value='userTimeline', key='#session.user.loginId')
    def personal () {
        def user = session.user?.refresh()

        if (user) {
            render view: 'timeline', model: [user: user]
        } else {
            redirect(uri: '/login')
        }
    }

    @Cacheable('globalTimeline')
    def global () {
        params.max = params.int('max', 6)  //default value 6 if max not present
        [posts: Post.list(params), postCount: Post.count()]
    }

    def tinyUrl (String fullUrl) {
        def longUrl = fullUrl?.encodeAsURL()
        def tinyUrl = new URL("http://tinyurl.com/api-create.php?url=${longUrl}").text
        render(contentType:"application/json") {
            urls(small: tinyUrl, full: fullUrl)
        }
    }
}
