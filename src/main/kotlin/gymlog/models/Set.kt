package gymlog.models

import java.sql.Timestamp

class Set() {
    lateinit var id: String
    lateinit var userId: String
    var weight: Double = 0.0
    lateinit var exercise: String
    var reps: Int = 0
    lateinit var lastModifiedDate: Timestamp

    constructor(
        id: String,
        userId: String,
        exercise: String,
        weight: Double,
        reps: Int,
        lastModifiedDate: Timestamp
    ) : this() {

        this.id = id
        this.userId = userId
        this.weight = weight
        this.exercise = exercise
        this.reps = reps
        this.lastModifiedDate = lastModifiedDate
    }
}