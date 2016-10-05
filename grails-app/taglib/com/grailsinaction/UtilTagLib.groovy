package com.grailsinaction

class UtilTagLib {
    static defaultEncodeAs = 'html'
    static encodeAsForTags = [certainBrowser: 'raw']
    static namespace = "hub"

    /**
     * A generic tag that enables broswer specific output.
     *
     * Usage: 
     *          <hub:certainBrowser userAgent='Mozilla'>
     *              This text (the tag body) is only ouput when the browser's
     *              request includes a user agent header that contains the 
     *              pattern 'Mozilla'
     *          </hub:certainBrowser>
     */
    def certainBrowser = { attrs, body ->
        if (request.getHeader('User-Agent') =~ attrs.userAgent) {
            out << body() 
        }
    }

    /**
     * A tag for a tiny Thumbnail of a user's profile image.
     *
     * Usage:
     *          <hub:tinyThumbnail loginId="userLoginId"/>
     */
    def tinyThumbnail = { attrs ->
        def loginId = attrs.loginId
        out << "<img src='"
        // reuse the existing Grails tag 'createLink'
        out << g.createLink(action: "tiny", controller: "image", id: loginId)
        out << "' alt='${loginId}'"
    }
}
