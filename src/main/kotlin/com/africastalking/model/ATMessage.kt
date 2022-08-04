package com.africastalking.model

data class ATMessage(
    val AfricasTalkingResponse: AfricasTalkingResponse
)

data class AfricasTalkingResponse(
    val SMSMessageData: SMSMessageData
)

data class SMSMessageData(
    val Message: String,
    val Recipients: Recipients
)

data class Recipients(
    val Recipient: Recipient
)
data class Recipient(
    val cost: String,
    val messageId: String,
    val messageParts: Int,
    val number: String,
    val status: String,
    val statusCode: Int
)

data class SMSInfo(
    val number: String,
    val message: String
)