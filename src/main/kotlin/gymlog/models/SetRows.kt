package gymlog.models

class SetRows() {
    var total: Int? = null
    var skip: Int? = null
    var limit: Int? = null
    lateinit var sets: List<SetRow>

    constructor(
            total: Int,
            skip: Int,
            limit: Int,
            sets: List<SetRow>
    ) : this() {
        this.total = total
        this.skip = skip
        this.limit = limit
        this.sets = sets
    }
}