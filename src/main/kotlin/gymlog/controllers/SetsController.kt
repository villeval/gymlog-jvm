package gymlog.controllers

import gymlog.exceptions.PathVariableNotFoundException
import gymlog.utils.HttpConfig
import gymlog.services.SetsDatabase
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
        return SetsDatabase.getSets(gymlogDataSource!!, userId, skip ?: 0, limit ?: 50)
    }

    @CrossOrigin
    @RequestMapping("/api/sets/{userId}", method = [(RequestMethod.POST)])
    fun addSets(@PathVariable(required = true, value = "userId") userId: String?, @RequestBody set: Sets.SetRow): Sets.SetRow {
        if (userId == null) throw PathVariableNotFoundException()
        else {
            return SetsDatabase.addSet(gymlogDataSource!!, userId, set)
        }
    }

    @CrossOrigin
    @RequestMapping("/api/sets/{userId}/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?): Sets.SetRow {
        if (userId == null || setId == null) throw PathVariableNotFoundException()
        else {
            return SetsDatabase.deleteSet(gymlogDataSource!!, setId, userId)
        }
    }
}
