package demo

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.util.Holders

@Integration
@Rollback
class ProfileSpec extends AuthenticatedSpec {

    void "get profile"() {
        when:
        def response = rest.get("${baseUrl}/profile") {
            header("Authorization", accessToken)
        }

        then:
        response.status == 200
    }
}
