package utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object TestUtils {

    @Throws(IOException::class)
    fun getSampleJsonAsString(sample: String): String {
        val encoded: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/samples/$sample"))
        val mapper = ObjectMapper()
        val string = encoded.toString(StandardCharsets.UTF_8)
        return mapper.readValue(string, JsonNode::class.java).toString()
    }
}