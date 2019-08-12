package gymlog.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Mandatory path variable missing")
class PathVariableNotFoundException: RuntimeException()