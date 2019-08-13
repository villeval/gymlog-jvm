package gymlog.models

import java.math.BigDecimal
import java.util.*

object Sets {
    data class Sets(val total: Int?,
                    val skip: Int?,
                    val limit: Int?,
                    val sets: List<SetRow>)
    
    data class SetRow(val id: String?,
                      val userId: String?,
                      val weight: BigDecimal?,
                      val exercise: String?,
                      val repetitions: Int?,
                      val createdDate: Date?)
}