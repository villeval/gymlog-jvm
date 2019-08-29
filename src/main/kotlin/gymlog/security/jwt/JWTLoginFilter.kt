package gymlog.security.jwt

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

class JWTLoginFilter(url: String, authenticationManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {
    private val tokenAuthenticationService: TokenAuthenticationService

    init {
        setAuthenticationManager(authenticationManager)
        tokenAuthenticationService = TokenAuthenticationService()
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = jacksonObjectMapper().readValue(request.inputStream, AccountCredentials::class.java)
        val token =  UsernamePasswordAuthenticationToken(credentials.username, credentials.password)
        return authenticationManager.authenticate(token)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        tokenAuthenticationService.addAuthentication(response, authResult.name)
    }
}

data class AccountCredentials(val username: String, val password: String)