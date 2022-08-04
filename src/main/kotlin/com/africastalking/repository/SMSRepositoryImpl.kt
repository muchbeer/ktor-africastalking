package com.africastalking.repository

import com.africastalking.model.ATMessage
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.XML

class SMSRepositoryImpl : SMSRepository {
    override suspend fun sendSMS(atPhone: String, atMessage: String): ATMessage {
        val httpClient = HttpClient(CIO)
        val gson = Gson()
        val PRETTY_PRINT_INDENT_FACTOR = 8

        val response : String = httpClient.submitForm(

            url = "https://api.africastalking.com/version1/messaging",
            formParameters = Parameters.build {
                append("username", "muchbeerx")
                append("to", atPhone)
                append("message", atMessage)
                append("from", "CHARLES DAY")
            }, block = {
                headers {
                    append("apiKey", "eabefe5adbeb4cd13112ec092ad9a883a0f38f75ba49c20d44869ca6ca22474c")
                }
            }

        ).bodyAsText()

        val jsonObj = XML.toJSONObject(response)
        val jsonPrettyPrintString = jsonObj.toString(PRETTY_PRINT_INDENT_FACTOR)
        val responseObject =   gson.fromJson(jsonPrettyPrintString, ATMessage::class.java)

        return responseObject
    }

    override suspend fun saveMessage(atMessage: ATMessage) {
        TODO("Not yet implemented")
    }
}