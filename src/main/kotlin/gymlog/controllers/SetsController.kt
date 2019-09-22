package gymlog.controllers

import gymlog.exceptions.PathVariableNotFoundException
import gymlog.exceptions.UnauthorizedAccessException
import gymlog.services.SetsService
import gymlog.models.Sets
import gymlog.security.jwt.TokenAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.sql.DataSource

@RestController
class SetsController {

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    @CrossOrigin
    @RequestMapping("/api/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "skip") skip: Int?, @RequestParam(value = "limit") limit: Int?, request: HttpServletRequest): Sets.Sets {
        val authentication = TokenAuthenticationService().getAuthentication(request)
        val username = authentication?.name ?: throw Exception("Username extraction failed")
        return SetsService.getSets(gymlogDataSource!!, username, skip ?: 0, limit ?: 50)
    }

    @CrossOrigin
    @RequestMapping("/api/sets", method = [(RequestMethod.POST)])
    fun addSets(@RequestBody set: Sets.SetRow, request: HttpServletRequest): Sets.SetRow {
        if (set.userId == null) throw PathVariableNotFoundException()
        else {
            val authentication = TokenAuthenticationService().getAuthentication(request)
            if(authentication?.name != set.userId) throw UnauthorizedAccessException()
            return SetsService.addSet(gymlogDataSource!!, set.userId, set)
        }
    }

    @CrossOrigin
    @RequestMapping("/api/sets/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "setId") setId: String?, request: HttpServletRequest): Sets.SetRow {
        if (setId == null) throw PathVariableNotFoundException()
        else {
            val authentication = TokenAuthenticationService().getAuthentication(request)
            if(authentication?.name !is String) throw Exception("Username extraction failed")
            return SetsService.deleteSet(gymlogDataSource!!, setId, authentication.name)
        }
    }
}
