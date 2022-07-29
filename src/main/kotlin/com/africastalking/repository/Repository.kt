package com.africastalking.repository

import com.africastalking.data.USSDMenu
import com.africastalking.model.USSDModel


interface Repository {
    suspend fun retrieveAllUSSD(): List<USSDModel>

    suspend fun findUSSDSessionById(msessionID: String): USSDModel?

    suspend fun updateSessionId(msessionID: String, mUSSD: USSDModel): USSDModel

    suspend fun insertUSSD(ussdModel: USSDModel): USSDModel

    suspend fun insertUSSDMenu(ussdMenu : USSDMenu)

    suspend fun ussdMenu(text : String) : String

    suspend fun processTextResponse(text: String) : String

}