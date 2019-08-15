package gymlog.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority


class AuthenticatedUser internal constructor(private val name: String) : Authentication {
    private var authenticated = true

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return null
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }

    override fun isAuthenticated(): Boolean {
        return this.authenticated
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(b: Boolean) {
        this.authenticated = b
    }

    override fun getName(): String {
        return this.name
    }
}