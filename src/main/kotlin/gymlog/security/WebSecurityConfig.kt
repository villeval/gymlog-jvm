package gymlog.security

import gymlog.security.jwt.JWTAuthenticationFilter
import gymlog.security.jwt.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder
import org.springframework.security.core.userdetails.User.UserBuilder

@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // disable caching
        http.headers().cacheControl()

        // todo: logout
        http.csrf().disable() // disable csrf for requests
                .authorizeRequests()
                .antMatchers("/").permitAll() // todo: check if needed at all later
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // filtering login requests
                .addFilterBefore(JWTLoginFilter(url = "/login", authenticationManager = authenticationManager()), UsernamePasswordAuthenticationFilter::class.java)
                // filter other requests to check JWT header
                .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // create a default account
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}password")
                .roles("ADMIN")
    }
}