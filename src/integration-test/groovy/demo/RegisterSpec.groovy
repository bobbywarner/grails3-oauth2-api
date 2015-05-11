package demo

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.util.Holders
import spock.lang.Specification
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class RegisterSpec extends Specification {
    String baseUrl
    RestBuilder rest
    String accessToken

    def setup() {
        baseUrl = Holders.grailsApplication.config.grails.serverURL
        rest = new RestBuilder()
        def response = rest.post("${baseUrl}/oauth/token?grant_type=client_credentials") {
            auth('demo-client', '123456')
            accept("application/json")
        }

        response.status == 200
        assert 'read write' == response.json.scope
        assert 'bearer' == response.json.token_type
        accessToken = "Bearer $response.json.access_token"
    }

    void "successful signup"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'John Doe'
                username = 'johndoe'
                email = 'johndoe@gmail.com'
                password = 'password'
            }
        }

        then:
        response.status == 201
        response.json.fullName == 'John Doe'
        response.json.username == 'johndoe'
        response.json.email == 'johndoe@gmail.com'
    }

    void "email address already taken"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'Bobby Warner'
                username = 'bobby'
                email = 'bobbywarner@gmail.com'
                password = 'password'
            }
        }

        then:
        response.status == 400
        response.json.errors.first().message == "A user already exists with this email address"
    }

    void "username already taken"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'Bobby Warner'
                username = 'bobbywarner'
                email = 'bobby@gmail.com'
                password = 'password'
            }
        }

        then:
        response.status == 400
        response.json.errors.first().message == "A user already exists with this username"
    }

    void "invalid email address"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'John Doe'
                username = 'johndoe'
                email = 'not valid email'
                password = 'password'
            }
        }

        then:
        response.status == 400
        response.json.errors != null
    }

    void "unsuccessful signup"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
        }

        then:
        response.status == 400
        response.json.errors != null
    }
}
