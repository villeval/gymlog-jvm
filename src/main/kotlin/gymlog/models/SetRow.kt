package gymlog.models

import java.math.BigDecimal
import java.util.Date

class SetRow() {
    lateinit var id: String
    lateinit var userId: String
    var weight: BigDecimal = BigDecimal(0.0)
    lateinit var exercise: String
    var reps: Int = 0
    lateinit var createdDate: Date

    constructor(
        id: String,
        userId: String,
        exercise: String,
        weight: BigDecimal,
        reps: Int,
        createdDate: Date
    ) : this() {

        this.id = id
        this.userId = userId
        this.weight = weight
        this.exercise = exercise
        this.reps = reps
        this.createdDate = createdDate
    }
}