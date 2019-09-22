package gymlog.services

import gymlog.exceptions.DatabaseOperationFailedException
import gymlog.models.User
import gymlog.security.WebSecurityConfig
import gymlog.utils.DatabaseUtils
import javax.sql.DataSource

object UsersService {

    private const val USERS_TABLE = "gymlog_db.users"

    private const val getUserQuery = "select * from $USERS_TABLE where username = ?;"
    private const val insertUserQuery = "insert into $USERS_TABLE (username, password, enabled) values (?, ?, ?);"

    private const val USER_ENABLED = 1

    fun checkIfUserExists(dataSource: DataSource, user: User): Boolean {
        val params = mapOf(1 to user.username)
        val results = DatabaseUtils.doQuery(dataSource, getUserQuery, params)
        return results.any()
    }

    fun insertUser(dataSource: DataSource, user: User): User {
        val encoder = WebSecurityConfig.passwordEncoder()
        val params = mapOf(1 to user.username, 2 to encoder.encode(user.password), 3 to USER_ENABLED )
        val result = DatabaseUtils.doUpdate(dataSource, insertUserQuery, params)
        return if (result == 1) user else throw DatabaseOperationFailedException()
    }

}