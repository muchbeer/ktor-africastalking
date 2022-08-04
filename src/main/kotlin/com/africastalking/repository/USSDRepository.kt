package com.africastalking.repository

import com.africastalking.model.USSDModel
import com.africastalking.model.USSDSessions


interface USSDRepository {
    suspend fun retrieveAllUSSD(): List<USSDModel>

    suspend fun retrieveAllUSSDSession() : List<USSDSessions>

    suspend fun retrieveAllUSSDSessionByID(msessionID: String) : List<USSDModel>

    suspend fun findUSSDSessionById(msessionID: String): USSDSessions?

    suspend fun updateSessionId(msessionID: String, mUSSD: USSDModel): USSDModel

    suspend fun insertUSSD(ussdModel: USSDModel): USSDModel

    suspend fun insertUSSDMenu(ussdMenu : USSDSessions) : USSDSessions

    suspend fun ussdMenu(text : String) : String

    suspend fun processTextResponse(text: String) : String

    suspend fun rumishoMenu(text: String) : String
}