package com.grailsinaction

import geb.spock.GebReportingSpec
import spock.lang.Stepwise

@Stepwise
class TimelineFunctionalSpec extends GebReportingSpec {

    def "Check that timeline loads for user 'phil'"() {
        when: "we load phil's timeline"
        go "users/phil"

        then: "the page displays Phil's full name"
        //$("#newPost h3").text() == "What is Phil Potss hacking on right now?"
        $("#newPost h3").text() == "What is Phil Potss hacking on right now?"
    }

    def "Submitting a new post" () {
        given "I log in and start at my timeline page"
        login "frankie", "testing"
        go "users/phil"

        when: "I enter a new message and post it"
        $("#postContent").value("This is a test post from Geb")
        $("#newPost").find("input", type: "button").click()

        then: "I see the new post in the timeline"
        waitFor {
            $("div.postText", text: "This is a test post from Geb").present
        }
    }

    private login(String username, String password) {
        go "login/form"
        $("input[name='loginId']").value(username)
        $("input[name='password']").value(password)
        $("input[type='submit']").click()
    }
}
