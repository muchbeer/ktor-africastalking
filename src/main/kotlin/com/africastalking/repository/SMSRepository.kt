package com.africastalking.repository

import com.africastalking.model.ATMessage
import com.africastalking.model.Recipient

interface SMSRepository {

    suspend fun sendSMS(atPhone: String, atMessage: String) : ATMessage

    suspend fun saveMessage(atMessage: ATMessage) : Recipient?
}