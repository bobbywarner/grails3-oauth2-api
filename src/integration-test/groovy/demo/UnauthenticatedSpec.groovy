package demo

import grails.test.mixin.integration.Integration
import grails.transaction.*
import grails.util.Holders
import spock.lang.*
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

@Integration
@Rollback
class UnauthenticatedSpec extends Specification {
    String baseUrl
    RestBuilder rest

    def setup() {
        baseUrl = Holders.grailsApplication.config.grails.serverURL
        rest = new RestBuilder()
    }

    void "api is secure and returns 401"() {
        when:
        RestResponse response = rest.get(baseUrl)

        then:
        response.status == 401
    }
}
