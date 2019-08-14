package gymlog.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No matching content found")
class NoContentException: RuntimeException()