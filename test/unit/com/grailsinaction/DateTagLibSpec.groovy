package com.grailsinaction

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import groovy.time.TimeCategory

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(DateTagLib)
class DateTagLibSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    void "Conversion of #testName matches #expectedRelativeDate"() {

        expect:
        applyTemplate('<hub:dateFromNow date="${date}" />', 
                [date: testDate]) =~ expectedRelativeDate

        where:
        testName            | testDate                                          | expectedRelativeDate
        "Current Time"      | new Date()                                        | /Just now/
        "Now - 1 day"       | new Date().minus(1)                               | /1 day ago/
        "Now - 2 days"      | new Date().minus(2)                               | /2 days ago/
        "In the last hour"  | use(TimeCategory) { new Date() - 25.minutes}      | /25 minutes ago/
        "In the last min"   | use(TimeCategory) { 35.seconds.from.now }         | /Just now/
    }
}
