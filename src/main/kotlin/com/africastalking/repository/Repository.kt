package com.africastalking.repository

import com.africastalking.data.USSDSessionTable
import com.africastalking.model.USSDModel
import com.africastalking.model.USSDSessions


interface Repository {
    suspend fun retrieveAllUSSD(): List<USSDModel>

    suspend fun findUSSDSessionById(msessionID: String): USSDSessions?

    suspend fun updateSessionId(msessionID: String, mUSSD: USSDModel): USSDModel

    suspend fun insertUSSD(ussdModel: USSDModel): USSDModel

    suspend fun insertUSSDMenu(ussdMenu : USSDSessions) : USSDSessions

    suspend fun ussdMenu(text : String) : String

    suspend fun processTextResponse(text: String) : String

}