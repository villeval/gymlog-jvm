package gymlog.services

import gymlog.exceptions.DatabaseOperationFailedException
import gymlog.models.User
import gymlog.security.WebSecurityConfig
import gymlog.security.jwt.TokenAuthenticationService
import gymlog.utils.DatabaseUtils
import javax.sql.DataSource

object UsersService {

    private const val USERS_TABLE = "gymlog_db.users"
    private const val AUTHORITIES_TABLE = "gymlog_db.authorities"

    private const val USER_ENABLED = 1

    private const val getUserQuery = "select * from $USERS_TABLE where username = ?;"
    private const val insertUserQuery = "insert into $USERS_TABLE (username, password, enabled) values (?, ?, $USER_ENABLED);"
    private const val insertUserAuthoritiesQuery = "insert into $AUTHORITIES_TABLE (username, authority) values (?, 'ROLE_USER');"

    fun checkIfUserExists(dataSource: DataSource, user: User): Boolean {
        val params = mapOf(1 to user.username)
        val results = DatabaseUtils.doQuery(dataSource, getUserQuery, params)
        return results.any()
    }

    fun registerUser(dataSource: DataSource, user: User): User {
        val encoder = WebSecurityConfig.passwordEncoder()
        val userParams = mapOf(1 to user.username, 2 to encoder.encode(user.password))
        val userResult = DatabaseUtils.doUpdate(dataSource, insertUserQuery, userParams)
        val authorityResult = DatabaseUtils.doUpdate(dataSource, insertUserAuthoritiesQuery, mapOf(1 to user.username))
        return if(userResult == 1 && authorityResult == 1) user else throw DatabaseOperationFailedException()
    }


}