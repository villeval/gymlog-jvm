package gymlog.models

class InputSet() {
    var weight: Double = 0.0
    lateinit var exercise: String
    var reps: Int = 0

    constructor(
            weight: Double,
            exercise: String,
            reps: Int) : this() {

        this.weight = weight
        this.exercise = exercise
        this.reps = reps

    }
}