package com.grailsinaction

import grails.transaction.Transactional

class PostException extends RuntimeException {
    String message
    Post post
}

@Transactional
class PostService {

    /**
     * Creates and saves a {@link Post} for a given {@link User}. 
     *
     * @param   loginId the {@link User.loginId}
     * @param   content the textual content of the post
     * @returns the newly created {@link Post} object
     * @throws  PostException if the either the user or post content
     * are invalid.
     */
    Post createPost (String loginId, String content) {
        def user = User.findByLoginId(loginId)
        if (user) {
            def post = new Post(content: content)
            user.addToPosts(post)
            if (post.validate() && user.save()) {
                return post
            } else {
                throw new PostException(
                        message: "Invalid or empty post", 
                        post: post)
            }
        }
        throw new PostException(message: "Invalid User Id")
    }
}

