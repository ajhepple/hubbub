package com.grailsinaction

import com.grailsinaction.pages.*
import geb.spock.GebReportingSpec

class AccessControlFunctionalSpec extends GebReportingSpec {
    
    void "Anonymous access to home page"() {
        expect: "Unauthenticated user can access global timeline"
        to GlobalTimelinePage
    }

    void "Anonymous access to restricted page"() {
        when: "Unauthenticated user attempts to access a user's timeline page"
        via TimelinePage, "phil"
    
        then: "the user is redirected to the login page"
        at LoginPage
    
        when: "the user logs in"
        login "frankie", "testing"
    
        then: "he or she can access the timeline page"
        to TimelinePage, "phil"
    }

    private login(String username, String password) {
        to LoginPage
        loginIdInputField = username
        passwordInputField = password
        signInButton.click()
    }

}
