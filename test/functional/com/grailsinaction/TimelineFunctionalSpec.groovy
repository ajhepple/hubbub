package com.grailsinaction

import geb.spock.GebReportingSpec
import spock.lang.Stepwise

@Stepwise
class TimelineFunctionalSpec extends GebReportingSpec {

    def "Check that timeline loads for user 'phil'"() {
        when: "we load phil's timeline"
        go "users/phil"

        then: "the page displays Phil's full name"
        $("div#new-post h3").text() == "What is Phil Potts hacking on right now?"
    }

    def "Submitting a new (synchronous) post" () {
        given: "I log in and start at my timeline page"
        login "frankie", "testing"
        go "users/frankie"

        when: "I enter a new message and post it using the synchronous form"
        $("#post-content").value("This is a test post from Geb")
        $("#new-post").find("input", id: "post").click()

        then: "I see the new post in the timeline"
        waitFor {
            $("div.post-text", text: "This is a test post from Geb")
        }
    }

    def "Submitting a new (asynchronous) post" () {
        given: "I log in and start at my timeline page"
        login "frankie", "testing"
        go "users/frankie"

        when: "I enter a new message and post it using the ajax form"
        $("#ajax-post-content").value("This is a test ajax post from Geb")
        $("#new-post").find("input", id: "ajax-post").click()

        then: "I see the new post in the timeline"
        waitFor {
            $("div.post-text", text: "This is a test ajax post from Geb")
        }
    }

    private login(String username, String password) {
        go "login/form"
        $("input[name='loginId']").value(username)
        $("input[name='password']").value(password)
        $("input[type='submit']").click()
    }
}
