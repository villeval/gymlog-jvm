package gymlog.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RestController

@RestController
class HeartbeatInterface {
    @CrossOrigin
    @RequestMapping("/api/heartbeat", method = [(GET)])
    fun getHeartbeat() = mapOf("status" to "ok")
}