package com.africastalking.model

data class SmsContent(
    val phone_number : String,
    val text_message : String
)

sealed class DataState<out R> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
    data class ErrorException(val exception: Exception) : DataState<Nothing>()
}

data class USSDModel(
    val sessionId : String,
    val phoneNumber : String,
    val networkCode : String,
    val serviceCode : String,
    val text : String
)

data class USSDSessions(
    val sessionId: String,
    val text : String
)