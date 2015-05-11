package demo

import org.springframework.security.core.context.SecurityContextHolder

import javax.annotation.security.RolesAllowed

@RolesAllowed(["ROLE_CLIENT"])
class ProfileController {

    def index() {
        render SecurityContextHolder.context?.authentication?.principal
    }
}
