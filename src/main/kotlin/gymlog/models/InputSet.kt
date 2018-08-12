package gymlog.models

class InputSet() {
    lateinit var userId: String
    var weight: Int = 0
    lateinit var exercise: String
    var reps: Int = 0

    constructor(
            userId: String,
            weight: Int,
            exercise: String,
            reps: Int) : this() {

        this.userId = userId
        this.weight = weight
        this.exercise = exercise
        this.reps = reps

    }
}