package gymlog.controllers

import gymlog.exceptions.PathVariableNotFoundException
import gymlog.services.SetsService
import gymlog.models.Sets
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import javax.sql.DataSource

@RestController
class SetsController {

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    // todo: user authentication for these routes (private), maybe jwt like in node app?
    // todo: check the rest resource paths

    @CrossOrigin
    @RequestMapping("/api/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "userId") userId: String, @RequestParam(value = "skip") skip: Int?, @RequestParam(value = "limit") limit: Int?): Sets.Sets {
        return SetsService.getSets(gymlogDataSource!!, userId, skip ?: 0, limit ?: 50)
    }

    @CrossOrigin
    @RequestMapping("/api/sets", method = [(RequestMethod.POST)])
    fun addSets(@RequestBody set: Sets.SetRow): Sets.SetRow {
        if (set.userId == null) throw PathVariableNotFoundException()
        else {
            return SetsService.addSet(gymlogDataSource!!, set.userId, set)
        }
    }

    @CrossOrigin
    @RequestMapping("/api/sets/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "setId") setId: String?): Sets.SetRow {
        // todo: when user authentication is added for routes, add logic that only sets that belong to current user can be deleted
        if (setId == null) throw PathVariableNotFoundException()
        else {
            return SetsService.deleteSet(gymlogDataSource!!, setId)
        }
    }
}
