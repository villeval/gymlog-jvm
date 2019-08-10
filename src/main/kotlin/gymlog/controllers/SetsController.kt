package gymlog.controllers

import gymlog.exceptions.DuplicateItemException
import gymlog.services.SetsDatabase
import gymlog.models.HttpResponse
import gymlog.models.InputSet
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
    // todo: evaluate database choice
    // todo: Dockerfile and deployment script

    @CrossOrigin
    @RequestMapping("/api/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "userId") userId: String, @RequestParam(value = "skip") skip: Int?, @RequestParam(value = "limit") limit: Int?) = SetsDatabase.getSets(gymlogDataSource!!, userId, skip ?: 0, limit ?: 50)

    @CrossOrigin
    @RequestMapping("/api/sets/{userId}", method = [(RequestMethod.POST)])
    fun addSets(@PathVariable(required = true, value = "userId") userId: String?, @RequestBody set: InputSet) =
            if(userId != null) {
                if(SetsDatabase.addSet(gymlogDataSource!!, userId, set)) HttpResponse("ok","added new set for user $userId") else HttpResponse("failure", "user id required")
                // todo: check this exception
            } else throw DuplicateItemException()

    @CrossOrigin
    @RequestMapping("/api/sets/{userId}/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?) =
        if (userId == null || setId == null) HttpResponse("invalid request","set id and user id are required for deletion")
        else {
            if(SetsDatabase.deleteSet(gymlogDataSource!!, setId, userId)) HttpResponse("success", "set deleted") else HttpResponse("failure", "deletion failed")
        }
}
