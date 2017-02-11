package com.grailsinaction

class SearchController {

    def search() {
        def query = params.q
        if (!query) return [:]
        try {
            // DomainClass#search(String query, Map options)
            return [searchResult: Post.search(query, params)]
        } catch (e) {
            return [searchError: true]
        }
    }
}
