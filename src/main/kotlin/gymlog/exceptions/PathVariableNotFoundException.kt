package gymlog.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Path variable missing.")
class PathVariableNotFoundException: RuntimeException() {}