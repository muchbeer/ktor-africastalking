package com.africastalking.route

import com.africastalking.data.DatabaseFactory
import com.africastalking.data.USSDSessionTable
import com.africastalking.model.DataState
import com.africastalking.model.SmsContent
import com.africastalking.model.USSDModel
import com.africastalking.model.USSDSessions
import com.africastalking.repository.Repository
import com.africastalking.repository.RepositoryImpl
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.ktorm.database.Database

fun Application.configureRouting() {

log.info("Enter configureRoute")
    val ktormDb : Database = DatabaseFactory.init()

    val repository : Repository = RepositoryImpl(ktormDb)

    routing {
        get("/") {
            println("print Ktor server enter at services")
            call.respondText("Working with Ktor Africastalking API")
        }

        get("muchbeer") {
            call.respondText("Start working on USSD")
        }

        post("/ussd") {
            val ussdParameters = call.receiveParameters()
            val sessionID = ussdParameters["sessionId"].toString()
            val phoneNumber = ussdParameters["phoneNumber"].toString()
            val networkCode = ussdParameters["networkCode"].toString()
            val serviceCode = ussdParameters["serviceCode"].toString()
            val text = ussdParameters["text"].toString()

            val response : String = repository.ussdMenu(text)
            val responseSave : String = repository.processTextResponse(text)



            val checkSession = repository.findUSSDSessionById(sessionID)

            if (checkSession !=null)  {
                val saveUssd =   repository.insertUSSDMenu(
                    USSDSessions( sessionId = sessionID, text= responseSave ))

                call.application.log.info("the value save is $saveUssd")
                call.respondText(response)
            } else {
                val saveUssd =   repository.insertUSSD(
                    USSDModel(
                        sessionId = sessionID, phoneNumber = phoneNumber, networkCode = networkCode,
                        serviceCode = serviceCode, text= responseSave ))

                call.application.log.info("the new entry is $saveUssd")
                call.respondText(response)
            }

        }

        get("/ussdlist") {
            val listOfUssd = repository.retrieveAllUSSD()
            listOfUssd.forEach { ussdModel ->
                val listSessions = repository.findUSSDSessionById(ussdModel.sessionId)

                call.respond(mapOf(ussdModel.sessionId to listSessions))
            }
        }

    }
}
