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
    @Qualifier("mysqldatasource")
    private val mySqlDataSource: DataSource? = null


    @CrossOrigin
    @RequestMapping("/gymlog/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "userId") userId: String, @RequestParam(value = "skip") skip: Int?, @RequestParam(value = "limit") limit: Int?) =
            SetsDatabase.getSets(mySqlDataSource!!, userId, skip ?: 0, limit ?: 50)

    @RequestMapping("/gymlog/{userId}/sets", method = [(RequestMethod.POST)])
    fun addSets(@PathVariable(required = true, value = "userId") userId: String?, @RequestBody set: InputSet) =
            if(userId != null) {
                if(SetsDatabase.addSet(mySqlDataSource!!, userId, set)) HttpResponse("ok","added new set for user $userId") else HttpResponse("failure", "user id required")
            } else throw DuplicateItemException()

    @RequestMapping("gymlog/{userId}/sets/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?) =
        if (userId == null || setId == null) HttpResponse("invalid request","set id and user id are required for deletion")
        else {
            SetsDatabase.deleteSet(mySqlDataSource!!, setId, userId)
            HttpResponse("success", "deleted set")
        }

    @RequestMapping("gymlog/{userId}/sets/{setId}", method = [(RequestMethod.PUT)])
    fun updateSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?, @RequestBody set: InputSet?) =
            if (userId == null || setId == null || set == null) HttpResponse("invalid request","set id and user id are required for update")
            else {
                SetsDatabase.updateSet(mySqlDataSource!!, setId, userId, set)
                HttpResponse("success", "set updated")
            }
}
