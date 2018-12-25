package gymlog.models

class Sets() {
    var total: Int? = null
    var skip: Int? = null
    var limit: Int? = null
    lateinit var sets: List<Set>

    constructor(
            total: Int,
            skip: Int,
            limit: Int,
            sets: List<Set>
    ) : this() {
        this.total = total
        this.skip = skip
        this.limit = limit
        this.sets = sets
    }
}