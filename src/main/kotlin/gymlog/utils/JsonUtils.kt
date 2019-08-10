package gymlog.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat
import java.util.*


object JsonUtils {

    fun <T> jsonToObject(json: Any, toClass: Class<T>): T {
        val mapper = jacksonObjectMapper()
        mapper.setTimeZone(TimeZone.getTimeZone("Zulu"))
        return when (json) {
            is String -> mapper.readValue(json, toClass)
            is ByteArray -> mapper.readValue(json, toClass)
            else -> throw Exception("Unable to convert class: ${json.javaClass.name}")
        }
    }

    fun objectToJson(value: Any): String {
        val mapper = jacksonObjectMapper()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("Zulu")
        mapper.dateFormat = dateFormat
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)
    }
}