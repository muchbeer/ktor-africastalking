package com.africastalking.repository


import com.africastalking.data.USSDSessionTable
import com.africastalking.data.UssdTable
import com.africastalking.data.UssdTable.menuPrimaryKey
import com.africastalking.data.UssdTable.networkCode
import com.africastalking.data.UssdTable.phoneNumber
import com.africastalking.data.UssdTable.serviceCode
import com.africastalking.data.UssdTable.sessionId
import com.africastalking.data.UssdTable.text
import com.africastalking.model.USSDModel
import com.africastalking.model.USSDSessions
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*

class RepositoryImpl(private val ktormDB : Database) : Repository {
    override suspend fun retrieveAllUSSD(): List<USSDModel> {
        return ktormDB.sequenceOf(UssdTable).toList().map {
            USSDModel(
                sessionId = it.sessionId,
                phoneNumber = it.phoneNumber,
                networkCode = it.networkCode,
                serviceCode = it.serviceCode,
                text = it.text
            )
        }
    }

    override suspend fun retrieveAllUSSDSession(): List<USSDSessions> {
        return ktormDB.sequenceOf(USSDSessionTable).toList().map {
            USSDSessions(sessionId = it.sessionId, text = it.textResponse)
        }
    }

    override suspend fun retrieveAllUSSDSessionByID(msessionID: String): List<USSDModel> {
      return  ktormDB.from(UssdTable).select()
            .where { sessionId like "%$msessionID" }
            .map { ussdMenu->
                USSDModel(
                    sessionId = ussdMenu[sessionId].toString(),
                    phoneNumber =ussdMenu[phoneNumber].toString(),
                    networkCode = ussdMenu[networkCode].toString(),
                    serviceCode = ussdMenu[serviceCode].toString(),
                    text = ussdMenu[text].toString()
                )
            }
    }

    override suspend fun findUSSDSessionById(msessionID: String): USSDSessions? {

    return ktormDB.sequenceOf(USSDSessionTable).firstOrNull {
            it.sessionId eq msessionID
        }?.let {
            USSDSessions(
                sessionId = it.sessionId,
                 text = it.textResponse
            )
        }
    }

    override suspend fun updateSessionId(msessionID: String, mUSSD: USSDModel): USSDModel {
        val updteType = ktormDB.update(UssdTable) {
            set(sessionId, mUSSD.sessionId)
            set(phoneNumber, mUSSD.phoneNumber)
            set(serviceCode, mUSSD.serviceCode)
            set(networkCode, mUSSD.networkCode)
            set(text, mUSSD.text)

            where { it.sessionId eq msessionID }
        }
        return USSDModel(
            sessionId = mUSSD.sessionId,
            phoneNumber = mUSSD.phoneNumber,
            networkCode = mUSSD.networkCode,
            serviceCode = mUSSD.serviceCode,
            text = mUSSD.text
        )
    }

    override suspend fun insertUSSD(ussdModel: USSDModel): USSDModel {

        val randomID = (100..10000).random()
        val ussdID: Int = ktormDB.insert(UssdTable) {
            set(menuPrimaryKey, "$randomID")
            set(sessionId, ussdModel.sessionId)
            set(phoneNumber, ussdModel.phoneNumber)
            set(networkCode, ussdModel.networkCode)
            set(serviceCode, ussdModel.serviceCode)
            set(text, ussdModel.text)
        }


        return if (ussdID >= 0) USSDModel(
            sessionId = ussdModel.sessionId,
            phoneNumber = ussdModel.phoneNumber,
            networkCode = ussdModel.networkCode,
            serviceCode = ussdModel.serviceCode,
            text = ussdModel.text
        ) else USSDModel("2", "0755", "123", "000", "nothing entered")
    }

    override suspend fun insertUSSDMenu(ussdMenu: USSDSessions) : USSDSessions {
       ktormDB.insert(USSDSessionTable) {
           set(USSDSessionTable.sessionId, ussdMenu.sessionId)
           set(USSDSessionTable.textResponse, ussdMenu.text)
       }

        return USSDSessions(sessionId = ussdMenu.sessionId, text = ussdMenu.text)
    }

    override suspend fun ussdMenu(text: String): String {

        val response = StringBuilder()

        if (text.isEmpty()) {
            // This is the first request. Note how we start the response with CON
            response.append("CON What would you like to check\n1. My account\n2. My phone number")

        } else if (text.contentEquals("1")) {
            // Business logic for first level response
            response.append("CON Choose account information you want to view\n1. Account number");

        } else if (text.contentEquals("2")) {
            // Business logic for first level response
            // This is a terminal request. Note how we start the response with END
            val ussdPhoneNumber = "255757022731"
            response.append("END Your phone number is ");
            response.append(ussdPhoneNumber)

        } else if (text.contentEquals("1*1")) {
            // This is a second level response where the user selected 1 in the first instance

            val accountNumber = "ACC100101"
            response.append("END Your account number is "); // This is a terminal request. Note how we start the response with END
            response.append(accountNumber)
        }
        return response.toString()
    }

    override suspend fun processTextResponse(text: String): String {
      return  if (text.isEmpty()) {
          "What would you like to check"
        } else if (text.contentEquals("1")) {
            "Client Choose My account"
      } else if (text.contentEquals("2")) {
          "Client Choose My phone Number"
      } else if (text.contentEquals("1*1")) {
          "End Provide User : ACC100101"
      } else if (text.contentEquals("1*2")) {
          "End Provide PhoneNumber : 255757022731"
      } else {
          "Error"
      }
    }

}