package utils

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

object InvokeActions {

    @Throws(Exception::class)
    fun invokeGet(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap()): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            mvc.perform(MockMvcRequestBuilders.get(url).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
        } else {
            mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
        }
    }

    @Throws(Exception::class)
    fun invokeGetWithAuth(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap(), token: String): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            mvc.perform(MockMvcRequestBuilders.get(url).params(multiValueMap).header("Authorization", token).accept(MediaType.APPLICATION_JSON))
        } else {
            mvc.perform(MockMvcRequestBuilders.get(url).header("Authorization", token).accept(MediaType.APPLICATION_JSON))
        }
    }

    @Throws(Exception::class)
    fun invokePost(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap(), pathVariables: ArrayList<Any>, body: String): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.post(url).content(body).contentType(MediaType.APPLICATION_JSON).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0]).content(body).contentType(MediaType.APPLICATION_JSON).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0], pathVariables[1]).content(body).contentType(MediaType.APPLICATION_JSON).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        } else {
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.post(url).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0]).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0], pathVariables[1]).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        }
    }

    @Throws(Exception::class)
    fun invokePostWithAuth(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap(), pathVariables: ArrayList<Any>, body: String, token: String): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.post(url).content(body).contentType(MediaType.APPLICATION_JSON).header("Authorization", token).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0]).header("Authorization", token).content(body).contentType(MediaType.APPLICATION_JSON).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0], pathVariables[1]).header("Authorization", token).content(body).contentType(MediaType.APPLICATION_JSON).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        } else {
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.post(url).content(body).header("Authorization", token).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0]).header("Authorization", token).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.post(url, pathVariables[0], pathVariables[1]).header("Authorization", token).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        }
    }

    @Throws(Exception::class)
    fun invokeDelete(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap(), pathVariables: ArrayList<Any>): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.delete(url).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0]).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0], pathVariables[1]).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        } else {
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0]).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0], pathVariables[1]).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        }
    }

    @Throws(Exception::class)
    fun invokeDeleteWithAuth(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap(), pathVariables: ArrayList<Any>, token: String): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.delete(url).header("Authorization", token).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0]).header("Authorization", token).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0], pathVariables[1]).header("Authorization", token).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        } else {
            when (pathVariables.size) {
                0 -> mvc.perform(MockMvcRequestBuilders.delete(url).header("Authorization", token).accept(MediaType.APPLICATION_JSON))
                1 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0]).header("Authorization", token).accept(MediaType.APPLICATION_JSON))
                2 -> mvc.perform(MockMvcRequestBuilders.delete(url, pathVariables[0], pathVariables[1]).header("Authorization", token).accept(MediaType.APPLICATION_JSON))
                else -> throw java.lang.Exception("too many path variables")
            }
        }
    }

    @Throws(Exception::class)
    fun invokeAuthentication(mvc: MockMvc, url: String, body: String): ResultActions {
        return mvc.perform(MockMvcRequestBuilders.post(url).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    }

    private fun convertParamsToMultiValueMap(params: Map<String, Any>): MultiValueMap<String, String> {
        val multiValueMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.map { param ->
            val valueAsString = if(param.value !is String) param.value.toString() else param.value as String
            multiValueMap.add(param.key, valueAsString)
        }
        return multiValueMap
    }
}