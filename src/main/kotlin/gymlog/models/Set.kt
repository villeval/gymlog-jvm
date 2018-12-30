package gymlog.models

import java.sql.Date
import java.sql.Timestamp

class Set() {
    lateinit var id: String
    lateinit var userId: String
    var weight: Double = 0.0
    lateinit var exercise: String
    var reps: Int = 0
    lateinit var createdDate: Date
    lateinit var lastModifiedDate: Timestamp

    constructor(
        id: String,
        userId: String,
        exercise: String,
        weight: Double,
        reps: Int,
        createdDate: Date,
        lastModifiedDate: Timestamp
    ) : this() {

        this.id = id
        this.userId = userId
        this.weight = weight
        this.exercise = exercise
        this.reps = reps
        this.createdDate = createdDate
        this.lastModifiedDate = lastModifiedDate
    }
}