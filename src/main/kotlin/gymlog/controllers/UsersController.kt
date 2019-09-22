package gymlog.controllers

import gymlog.models.User
import gymlog.models.UserErrors
import gymlog.services.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.sql.DataSource

@RestController
class UsersController {

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    @CrossOrigin
    @RequestMapping("/register", method = [(RequestMethod.POST)])
    fun registerUser(@RequestBody user: User): Any {
        return if(UsersService.checkIfUserExists(gymlogDataSource!!, user)) {
            UserErrors("Username already exists", null)
        } else {
            UsersService.registerUser(gymlogDataSource, user)
        }
    }
}
