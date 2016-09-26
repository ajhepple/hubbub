package com.grailsinaction

class UtilTagLib {
    static defaultEncodeAs = 'html'
    static encodeAsForTags = [certainBrowser: 'raw']
    static namespace = "hub"

    /**
     * A generic tag that enables broswer specific output.
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
}
