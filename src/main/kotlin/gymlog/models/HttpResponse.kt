package gymlog.models

class HttpResponse() {
    lateinit var status: String
    lateinit var message: String

    constructor(
        status: String,
        message: String) : this() {
            this.status = status
            this.message = message
    }
}