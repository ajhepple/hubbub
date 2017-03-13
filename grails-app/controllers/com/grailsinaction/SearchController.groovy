package com.grailsinaction

class SearchController {

    def springSecurityService

    /* A search action that makes use of the Searchable plugin's highlighter
     * feature. The 'withHighlighter' closure takes a highlighter object that
     * is used to hold the word that was highlighted along with its 
     * surrounding text, an index counter (used to track the hit number), and
     * the search result object itself.
     * This closure is used by the Post.search method and the highlights are
     * subsequently available for use in the view.
     * https://grails.org/wiki/Searchable%20Plugin%20-%20Searching%20-%20Highlighting */
    def search() {
        def query = params.q
        if (!query) return [:]
        try {
            // add suggest-query for misspelled terms (see Post.groovy also)
            params.suggestQuery = true
            
            // define a highlighting closure
            params.withHighlighter = { highlighter, index, sr ->
                // lazy-init the list of highlighted search results
                if (!sr.highlights) {
                    sr.highlights = []
                }
                // store highlighted text; "content" is a searchable
                // property of the Post domain class
                def matchedFragment = highlighter.fragment("content")
                sr.highlights[index] = "..." + (matchedFragment ?: "") + "..."
            }

            // apply restriction to logged-in user's posts only, if necessary
            if (params.justMine) {
                user = springSecurityService.currentUser
                query += " loginId:${user.loginId}"
            }

            // Finaly, perform search using one of the overloaded methods
            // provided by the Searchable plugin;
            // DomainClass#search(String query, Map options)
            return [searchResult: Post.search(query, params)]
        } catch (e) {
            println e.stackTrace()
            return [searchError: true]
        }
    }
}
