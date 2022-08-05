package com.africastalking.repository

import com.africastalking.data.SmsTable
import com.africastalking.data.SmsTable.cost
import com.africastalking.data.SmsTable.message
import com.africastalking.data.SmsTable.messageId
import com.africastalking.data.SmsTable.messageParts
import com.africastalking.data.SmsTable.number
import com.africastalking.data.SmsTable.status
import com.africastalking.model.ATMessage
import com.africastalking.model.Recipient
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.XML
import org.ktorm.database.Database
import org.ktorm.dsl.insert

class SMSRepositoryImpl(private val ktormDB : Database) : SMSRepository {
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

    override suspend fun saveMessage(atMessage: ATMessage) : Recipient?{
        val recepient = atMessage.AfricasTalkingResponse.SMSMessageData.Recipients.Recipient

        val smsId : Int = ktormDB.insert(SmsTable) {
            set(messageId, recepient.messageId)
            set(number, recepient.number)
            set(message, atMessage.AfricasTalkingResponse.SMSMessageData.Message)
            set(messageParts, recepient.messageParts)
            set(cost, recepient.cost)
            set(status, recepient.status)
        }

      return  if (smsId >= 0) Recipient(cost = recepient.cost, messageId = recepient.messageId,
        messageParts = recepient.messageParts, number = recepient.number,
        status = recepient.status, statusCode = 200) else null
    }
}