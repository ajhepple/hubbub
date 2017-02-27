package com.grailsinaction



import spock.lang.*

/**
 *
 */
class PostIntegrationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "Adding posts to user, links post to that user" () {
        given: "A brand new user"
        def user = new User(loginId: 'joe', passwordHash: 'secret')
        user.save(failOnError: true)

        when: "Several posts are added to the user"
        user.addToPosts(new Post(content: "First post... W00t!"))
        user.addToPosts(new Post(content: "Second post..."))
        user.addToPosts(new Post(content: "Third post..."))

        then: "The user has a list of posts attached"
        3 == User.get(user.id).posts.size()
    }

    def "Ensure posts linked to a user can be retrieved" () {
    
        given: "A user with several posts"
        def user = new User(loginId: 'joe', passwordHash: 'secret')
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content: "Second"))
        user.addToPosts(new Post(content: "Third"))
        user.save(failOnError: true)
    
        when: "The user is retrieved by the id"
        def foundUser = User.get(user.id)
        def sortedPostContent = foundUser.posts.collect{ it.content }.sort()
    
        then: "The posts appear on the retrieved user"
        sortedPostContent == ['First', 'Second', 'Third']
    }

    def "Exercise tagging several posts with various tags" () {
    
        given: "A user with a set of tags"
        def user = new User(loginId: 'joe', passwordHash: 'secret')
        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)
        user.save(failOnError: true)
    
        when: "The user tags two fresh posts"
        def groovyPost = new Post(content: "A groovy post")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)
        def grailsPost = new Post(content: "A groovy on rails post")
        user.addToPosts(grailsPost)
        grailsPost.addToTags(tagGroovy)
        grailsPost.addToTags(tagGrails)
    
        then: "The two tags should be accessible via user"
        user.tags*.name.sort() == ['grails', 'groovy']
        1 == groovyPost.tags.size()
        2 == grailsPost.tags.size()
        2 == user.tags.size()
    }

    def "Deleting a post that reuses a tag should not delete that tag" () {
        given: "A user and two tags"
        def user = new User(loginId: 'joe', passwordHash: 'secret')
        def tagOne = new Tag(name: 'one')
        def tagTwo = new Tag(name: 'two')
        user.addToTags(tagOne)
        user.addToTags(tagTwo)
        user.save(failOnError: true)
    
        when: "The user tags two fresh posts, using one tag on both posts"
        def postOne = new Post(content: "First post")
        user.addToPosts(postOne)
        def postTwo = new Post(content: "Second post")
        user.addToPosts(postTwo)
        postOne.addToTags(tagOne)
        postOne.addToTags(tagTwo)
        postTwo.addToTags(tagOne)
    
        and: "The first post is deleted"
        postTwo.delete(flush: true)
    
        then: "The tag should still exist and belong to the original post"
        user.tags.size() == 2
        tagOne in postOne.tags
    }
}
