package demo

import org.apache.commons.lang.builder.HashCodeBuilder

class PersonSecurityRole implements Serializable {

    private static final long serialVersionUID = 1L

    Person person
    SecurityRole securityRole

    boolean equals(other) {
        if (!(other instanceof PersonSecurityRole)) {
            return false
        }

        other.person?.id == person?.id &&
                other.securityRole?.id == securityRole?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (person) builder.append(person.id)
        if (securityRole) builder.append(securityRole.id)
        builder.toHashCode()
    }

    static PersonSecurityRole get(long personId, long securityRoleId) {
        PersonSecurityRole.where {
            person == Person.load(personId) &&
                    securityRole == SecurityRole.load(securityRoleId)
        }.get()
    }

    static boolean exists(long personId, long securityRoleId) {
        PersonSecurityRole.where {
            person == Person.load(personId) &&
                    securityRole == SecurityRole.load(securityRoleId)
        }.count() > 0
    }

    static PersonSecurityRole create(Person person, SecurityRole securityRole, boolean flush = false) {
        def instance = new PersonSecurityRole(person: person, securityRole: securityRole)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(Person u, SecurityRole r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = PersonSecurityRole.where {
            person == Person.load(u.id) &&
                    securityRole == SecurityRole.load(r.id)
        }.deleteAll()

        if (flush) { PersonSecurityRole.withSession { it.flush() } }

        rowCount > 0
    }

    static void removeAll(Person u, boolean flush = false) {
        if (u == null) return

        PersonSecurityRole.where {
            person == Person.load(u.id)
        }.deleteAll()

        if (flush) { PersonSecurityRole.withSession { it.flush() } }
    }

    static void removeAll(SecurityRole r, boolean flush = false) {
        if (r == null) return

        PersonSecurityRole.where {
            securityRole == SecurityRole.load(r.id)
        }.deleteAll()

        if (flush) { PersonSecurityRole.withSession { it.flush() } }
    }

    static constraints = {
        securityRole validator: { SecurityRole r, PersonSecurityRole ur ->
            if (ur.person == null) return
            boolean existing = false
            PersonSecurityRole.withNewSession {
                existing = PersonSecurityRole.exists(ur.person.id, r.id)
            }
            if (existing) {
                return 'personSecurityRole.exists'
            }
        }
    }

    static mapping = {
        id composite: ['securityRole', 'person']
        version false
    }
}
