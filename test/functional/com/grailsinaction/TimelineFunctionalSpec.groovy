package com.grailsinaction

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import com.grailsinaction.pages.*

@Stepwise
class TimelineFunctionalSpec extends GebReportingSpec {

    def "Check that personal timeline redirects to login" () {
        when: "we try to access the timeline page" 
        via PersonalTimelinePage
    
        then: "we are redirected to the login page"
        at LoginPage
    }

    def "Check that timeline loads for user 'phil'"() {
        when: "we load phil's timeline"
        login "frankie", "testing"
        //go "users/phil"               //without using Geb pages
        to TimelinePage, "phil"   // using Geb pages

        then: "the page displays Phil's full name"
        //without using Geb pages
        $("div#new-post h3").text() == "What is Phil Potts hacking on right now?"
        //with Geb pages
        whatHeading.text() == "What is Phil Potts hacking on right now?"
    }

    def "Submitting a new (synchronous) post" () {
        given: "I log in and start at my timeline page"
        login "frankie", "testing"
        to TimelinePage, "frankie"

        when: "I enter a new message and post it using the synchronous form"
        def message = uniqueMessage("sync post")
        //$("#post-content").value(message)     //Without Geb page
        newPostContent.value(message)           //With Geb page
        //$("#new-post").find("input", id: "post").click()        //Without
        submitSyncPostButton.click()                              //With

        then: "I see the new post in the timeline"
        waitFor {
            // returns a value (ie Groovy truth) if matching element exists
            //$("div.post-text", text: message) //without Geb pages
            posts(message)                      //with Geb pages
        }
    }

    def "Submitting a new (asynchronous) post" () {
        given: "I log in and start at my timeline page"
        login "frankie", "testing"
        to TimelinePage, "frankie"

        when: "I enter a new message and post it using the ajax form"
        def message = uniqueMessage("ajax post")
        newAjaxPostContent.value(message)
        submitAsyncPostButton.click()

        then: "I see the new post in the timeline"
        waitFor {
            posts(message)
        }
    }

    private login(String username, String password) {
        to LoginPage
        loginIdInputField.value(username)
        passwordInputField.value(password)
        submitButton.click()
    }

    private uniqueMessage(String prefix="Test message") {
        "[${new Date().toTimestamp()}] $prefix"
    }
}
