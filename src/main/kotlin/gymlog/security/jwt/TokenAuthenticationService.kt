package gymlog.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationService(jwtSecret: String) {

    private val expirationTime = 1000 * 60 * 60 * 24 * 1 // 1 day
    private val secret = jwtSecret
    private val tokenPrefix = "Bearer"
    private val headerString = "Authorization"

    fun addAuthentication(response: HttpServletResponse, username: String) {
        // generate token
        val jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
        response.addHeader(headerString, "$tokenPrefix $jwt")
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(headerString)
        val username =  if(token != null) {
            // parse the token
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.substring(6))
                .body
                .subject
        } else null
        // successful retrieval of the user
        return if(username != null) AuthenticatedUser(username) else null
    }
}