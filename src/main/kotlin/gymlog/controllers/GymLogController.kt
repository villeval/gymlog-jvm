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

    @Autowired
    private lateinit var database: SetsDatabase

    @CrossOrigin
    @RequestMapping("/gymlog/sets", method = [(RequestMethod.GET)])
    fun getSets(@RequestParam(value = "userId") userId: String) = database.getSets(userId)

    @RequestMapping("/gymlog/{userId}/sets", method = [(RequestMethod.POST)])
    fun addSets(@PathVariable(required = true, value = "userId") userId: String?, @RequestBody set: InputSet) =
            if (database.addSet(set)) HttpResponse("ok","added new set for user $userId")
            else throw DuplicateItemException()

    @RequestMapping("gymlog/{userId}/sets/{setId}", method = [(RequestMethod.DELETE)])
    fun deleteSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?) =
        if (userId != null && setId != null) HttpResponse("ok","deleted set $setId for user $userId (not really)")
        else throw PathVariableNotFoundException()

    @RequestMapping("gymlog/{userId}/sets/{setId}", method = [(RequestMethod.PUT)])
    fun updateSets(@PathVariable(required = true, value = "userId") userId: String?, @PathVariable(required = true, value = "setId") setId: String?, @RequestBody set: InputSet?) =
            if (userId != null && setId != null) HttpResponse("ok","updated set $setId for user $userId (not really)")
            else throw PathVariableNotFoundException()
}
