package gymlog.controllers

import gymlog.exceptions.DuplicateItemException
import gymlog.exceptions.PathVariableNotFoundException
import gymlog.models.HttpResponse
import gymlog.implementations.SetsDatabase
import gymlog.models.InputSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class GymLogController {

    @CrossOrigin
    @RequestMapping("/gymlog/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "userId") userId: String, @RequestParam(value = "skip") skip: Int?, @RequestParam(value = "limit") limit: Int?) =
            SetsDatabase.getSets(userId, skip ?: 0, limit ?: 50)

    @RequestMapping("/gymlog/{userId}/sets", method = [(RequestMethod.POST)])
    fun addSets(@PathVariable(required = true, value = "userId") userId: String?, @RequestBody set: InputSet) =
            if(userId != null) {
                if(SetsDatabase.addSet(userId, set)) HttpResponse("ok","added new set for user $userId") else HttpResponse("failure", "user id required")
            } else throw DuplicateItemException()

    @RequestMapping("gymlog/{userId}/sets/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?) =
        if (userId == null || setId == null) HttpResponse("invalid request","set id and user id are required for deletion")
        else {
            SetsDatabase.deleteSet(setId, userId)
            HttpResponse("success", "deleted set")
        }

    @RequestMapping("gymlog/{userId}/sets/{setId}", method = [(RequestMethod.PUT)])
    fun updateSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?, @RequestBody set: InputSet?) =
            if (userId == null || setId == null || set == null) HttpResponse("invalid request","set id and user id are required for update")
            else {
                SetsDatabase.updateSet(setId, userId, set)
                HttpResponse("success", "set updated")
            }
}
