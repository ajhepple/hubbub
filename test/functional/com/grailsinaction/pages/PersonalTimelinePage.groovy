package com.grailsinaction.pages

/**
 * This page is implemented as a specialisation of the 
 * TimelinePage by using Java's inheritance. However, Geb
 * provides a module concept whereby parts of pages (that
 * may or may not correspond to Grails view templates) can
 * be defined and reused. Geb modules could have been
 * uses as an alternative way of re-using the Timeline
 * content and at definitions.
 *
 * @see <a href="http://www.gebish.org/manual/current/#modules">The Book of Geb</a>
 */
class PersonalTimelinePage extends TimelinePage {

    static url = "timeline"

}
