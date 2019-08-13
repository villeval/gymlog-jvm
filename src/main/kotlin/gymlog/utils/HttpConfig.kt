package gymlog.utils

object HttpConfig {
    data class HttpError(val status: String = "failed", val message: String = "Operation failed.")

    fun getError(): HttpError {
        return HttpError()
    }
}