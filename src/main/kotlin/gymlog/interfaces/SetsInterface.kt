package gymlog.interfaces

import gymlog.exceptions.DuplicateItemException
import gymlog.services.SetsDatabase
import gymlog.models.HttpResponse
import gymlog.models.InputSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import javax.sql.DataSource

@RestController
class SetsInterface {

    @Autowired
    @Qualifier("gymlogDataSource")
    private val gymlogDataSource: DataSource? = null


    @CrossOrigin
    @RequestMapping("/api/gymlog/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "userId") userId: String, @RequestParam(value = "skip") skip: Int?, @RequestParam(value = "limit") limit: Int?) =
            SetsDatabase.getSets(gymlogDataSource!!, userId, skip ?: 0, limit ?: 50)

    @CrossOrigin
    @RequestMapping("/api/gymlog/{userId}/sets", method = [(RequestMethod.POST)])
    fun addSets(@PathVariable(required = true, value = "userId") userId: String?, @RequestBody set: InputSet) =
            if(userId != null) {
                if(SetsDatabase.addSet(gymlogDataSource!!, userId, set)) HttpResponse("ok","added new set for user $userId") else HttpResponse("failure", "user id required")
            } else throw DuplicateItemException()

    @CrossOrigin
    @RequestMapping("/api/gymlog/{userId}/sets/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?) =
        if (userId == null || setId == null) HttpResponse("invalid request","set id and user id are required for deletion")
        else {
            SetsDatabase.deleteSet(gymlogDataSource!!, setId, userId)
            HttpResponse("success", "deleted set")
        }

    @CrossOrigin
    @RequestMapping("/api/gymlog/{userId}/sets/{setId}", method = [(RequestMethod.PUT)])
    fun updateSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?, @RequestBody set: InputSet?) =
            if (userId == null || setId == null || set == null) HttpResponse("invalid request","set id and user id are required for update")
            else {
                SetsDatabase.updateSet(gymlogDataSource!!, setId, userId, set)
                HttpResponse("success", "set updated")
            }
}
