package gymlog.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized resource access attempt")
class UnauthorizedAccessException: RuntimeException()