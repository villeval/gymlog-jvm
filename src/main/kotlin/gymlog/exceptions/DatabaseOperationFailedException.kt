package gymlog.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Database operation failed")
class DatabaseOperationFailedException: RuntimeException()