package demo

import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

class RegisterController {

    static allowedMethods = [save: "POST"]

    @Transactional
    def save(RegisterCommand signup) {
        log.debug("Content-Type: " + request.getHeader("Content-Type"))
        log.debug("Accept: " + request.getHeader("Accept"))
        if (!signup.hasErrors()) {
            Person person = Person.findByUsername(signup.username)
            if (!person) {
                person = Person.findByEmail(signup.email)
                if (!person) {
                    person = new Person(username: signup.username, password: signup.password, fullName: signup.fullName, email: signup.email).save(flush: true)
                    def userRole = SecurityRole.findByAuthority('ROLE_CLIENT')
                    PersonSecurityRole.create(person, userRole)
                } else {
                    response.status = 400
                    def error = [
                        [
                            field: 'email',
                            'rejected-value': signup.email,
                            message: 'A user already exists with this email address'
                        ]
                    ]
                    def results = [errors: error]
                    render results as JSON
                    return
                }
            } else {
                response.status = 400
                def error = [
                    [
                        field: 'username',
                        'rejected-value': signup.username,
                        message: 'A user already exists with this username'
                    ]
                ]
                def results = [errors: error]
                render results as JSON
                return
            }
            respond person, [status: CREATED]
        } else {
            response.status = 400
            render signup.errors as JSON
        }
    }
}

class RegisterCommand {
    String fullName
    String username
    String email
    String password

    static constraints = {
        username(blank: false, unique: true)
        email(blank: false, unique: true, email: true)
        password(blank: false)
        fullName(blank: false)
    }
}
