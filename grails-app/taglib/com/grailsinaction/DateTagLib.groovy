package com.grailsinaction

class DateTagLib {
    static defaultEncodeAs = 'html'
    static encodeAsForTags = [dateFromNow: 'raw']
    static namespace = "hub"    //allows use as <hub:tagName

    /** All custom tags are implemented this way; as a closure that
     *  processes the tag's attributes and outputs HTML to an 
     *  output stream called out. */
    def dateFromNow = { attrs ->
        out << "<span "
        attrs.each {k,v ->
            if (v != null) { out <<  "${k}=\"${v}\" "}
        }
        out << ">${getRelativeDate(attrs.date)}</span>"
    }

    /**
     * Generates a description of a date relative to the current time.
     * E.g. 19 hours 5 minutes 1 second ago
     */
    protected String getRelativeDate(Date date) {
        final def periods =[second: 1000L, minute: 60*1000L, hour: 60*60*1000L, day: 24*60*60*1000L]
        final def now = new Date()
        def diff = Math.abs(now.time - date.time)
        def relativeDate = ""

        // iterate over the periods map calculating how many, n, 
        // of each period have passed, building the relative date
        // string as we go
        long n = 0
        periods.sort{-it.value}.each { period, duration ->
            n = Math.floor(diff / duration)
            if(n) {
                relativeDate += n + " ${period}" + (n > 1 ? "s " : " ")
                diff %= duration
            }
        }

        // complete the string
        if (!relativeDate) {
            relativeDate = "Right now"
        } else {
            relativeDate += (diff < 0) ? "from now" : "ago"

        }
        return relativeDate
    }
}
