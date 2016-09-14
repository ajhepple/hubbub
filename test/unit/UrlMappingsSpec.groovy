import com.grailsinaction.*
import spock.lang.*

@TestFor(UrlMappings)
@Mock(PostController)
class UrlMappingsSpec extends Specification {

    def "Ensure basic mapping of timeline and user URLs"() {
        expect:
        assertForwardUrlMapping(url, controller: controller, action: action) {
            id = paramId
        }
        assertReverseUrlMapping(controller: controller, action: action, url) {
            if(paramId) id = paramId
        }

        where:
        url                     | controller    | action        | paramId
        '/timeline'             | 'post'        | 'personal'    | null
        '/users/fred'           | 'post'        | 'timeline'    | 'fred'
    }

    def "Ensure reverse mapping for post timeline actions"() {
        expect:
        assertReverseUrlMapping(controller: controller, action: action, expectedUrl) {
            if(paramId) id = paramId
        }

        where:
        controller    | action        | paramId | expectedUrl              
        'post'        | 'personal'    | null      | '/timeline'              
        'post'        | 'timeline'    | 'fred'  | '/users/fred'            
    }

}
