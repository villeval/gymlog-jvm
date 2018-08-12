package gymlog.models

class Set() {
    var id: Long = 0
    lateinit var userId: String
    var weight: Int = 0
    lateinit var exercise: String
    var reps: Int = 0

    constructor(
        id: Long,
        userId: String,
        weight: Int,
        exercise: String,
        reps: Int) : this() {

        this.id = id
        this.userId = userId
        this.weight = weight
        this.exercise = exercise
        this.reps = reps

    }
}