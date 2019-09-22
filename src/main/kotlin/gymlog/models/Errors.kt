package gymlog.models

data class UserErrors(
        val username: String?,
        val password: String?
)

data class SetErrors(
        val exercise: String?,
        val repetitions: String?,
        val weight: String?
)