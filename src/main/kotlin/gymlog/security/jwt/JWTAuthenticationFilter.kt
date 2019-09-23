package gymlog.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import java.io.IOException

class JWTAuthenticationFilter(jwtSecret: String) : GenericFilterBean() {

    private val secret = jwtSecret

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val authentication = TokenAuthenticationService(jwtSecret = secret).getAuthentication(request as HttpServletRequest)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}