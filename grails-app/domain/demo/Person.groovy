package demo

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class Person {

    String username
    String email
    String password
    String fullName

    static constraints = {
        username(blank: false, unique: true)
        email(blank: false, unique: true, email: true)
        password(blank: false)
        fullName(blank: false)
    }

    Set<SecurityRole> getSecurityRoles() {
        PersonSecurityRole.findAllByPerson(this).collect { it.securityRole }
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
        password = passwordEncoder.encode(password)
    }
}
