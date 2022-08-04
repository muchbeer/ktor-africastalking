package com.africastalking.repository

import com.africastalking.model.ATMessage

interface SMSRepository {

    suspend fun sendSMS(atPhone: String, atMessage: String) : ATMessage

    suspend fun saveMessage(atMessage: ATMessage)
}