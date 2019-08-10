package gymlog.models

import java.math.BigDecimal

class InputSet() {
    var weight: BigDecimal = BigDecimal(0.0)
    lateinit var exercise: String
    var repetitions: Int = 0

    constructor(
            weight: BigDecimal,
            exercise: String,
            repetitions: Int) : this() {

        this.weight = weight
        this.exercise = exercise
        this.repetitions = repetitions

    }
}