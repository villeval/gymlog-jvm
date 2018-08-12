package gymlog.implementations

import gymlog.models.InputSet
import gymlog.models.Set
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SetsDatabase {
    private val sets = mutableListOf<Set>()

    @PostConstruct
    private fun init() {
        val setsForInsert = listOf(
                        Set(1, "Ville123", 100, "Squat", 6),
                        Set(2, "Ville123", 100, "Squat", 6),
                        Set(3, "Ville123", 100, "Squat", 6),
                        Set(4, "Ville123", 100, "Squat", 11),
                        Set(5, "Ville123", 80, "Stiff leg deadlift", 10),
                        Set(6, "Ville123",80, "Stiff leg deadlift", 10),
                        Set(7, "Ville123", 80, "Stiff leg deadlift", 10),
                        Set(8, "Ville123", 32, "Lunges", 10),
                        Set(9, "Ville123", 32, "Lunges", 10),
                        Set(10, "Ville123", 32, "Lunges", 10),
                        Set(11, "Ville123", 0, "Hanging leg raises", 10),
                        Set(12, "Ville123", 0, "Hanging leg raises", 10),
                        Set(13, "Ville123", 0, "Hanging leg raises", 10),
                        Set(14, "Ville123", 45, "Calf machine", 10),
                        Set(15, "Ville123", 45, "Calf machine", 10),
                        Set(16, "Ville123", 45, "Calf machine", 10),
                        Set(17, "Ville123", 50, "Ab crunches with cable", 10),
                        Set(18, "Ville123", 50, "Ab crunches with cable", 10),
                        Set(19, "Ville123", 50, "Ab crunches with cable", 10)
                )
        setsForInsert.map{ set -> sets.add(set)}
    }

    //fun getSets(userId: String) = sets.filter { it.userId == userId }

    fun getSets(userId: String): String {
        MySQLJDBCUtil.getConnection()
        return MySQLJDBCUtil.executeMySqlQuery()
    }

    fun addSet(inputSet: InputSet): Boolean {
        val set = Set(System.nanoTime(), inputSet.userId, inputSet.weight, inputSet.exercise, inputSet.reps)
        sets.add(set)
        return true
    }
}