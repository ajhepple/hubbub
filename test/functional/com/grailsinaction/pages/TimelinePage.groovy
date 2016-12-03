package com.grailsinaction.pages

/** 
 * A Geb Page for modelling the actual HTML page. This model
 * facilitates changes to the HTML page by abstracting the 
 * HTML detail away from the tests themselves. This allows
 * changes to the HTML without multiple tests being affected.
 * Intead, only the Geb Page model needs to be updated to 
 * reflect the HTML. Yet another example of the three-tiered
 * architecture in action.
 *
 * ----------------------
 * | TEST | TEST | TEST |
 * ----------------------
 * |      GEB PAGE      |
 * ----------------------
 * |     VIEW (HTML)    |
 * ----------------------
 */
class TimelinePage extends geb.Page {

    static url = "users"

    /**
     * A list of closures that usually, though not necessarily, 
     * return a geb.navigator.Navigator object. These closures
     * abstract the HTML away from the tests themselves.
     */
    static content = {
        whatHeading { $("#new-post h3") }
        newPostContent { $('#post-content') }
        newAjaxPostContent { $('#ajax-post-content') }
        submitSyncPostButton { $("#new-post").find("input", id: "post") }
        submitAsyncPostButton { $("#new-post").find("input", id: "ajax-post") }
        posts { content ->
            if (content) $("div.post-text", text: content)
            else $("div.post-entry")
        }
    }

    /**
     * This 'at' closure contains a list of assertions that
     * hold true iff this page is the current geb page.
     * I.e. in this example, they verify that the HTML page
     * currently loaded by Geb, is a user timeline page.
     */
    static at = {
        title.contains("Timeline for ")
        $("#all-posts")
    }
}
