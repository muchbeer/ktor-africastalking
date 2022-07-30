package com.africastalking.route

import com.africastalking.data.DatabaseFactory
import com.africastalking.data.USSDSessionTable
import com.africastalking.model.*
import com.africastalking.repository.Repository
import com.africastalking.repository.RepositoryImpl
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.ktorm.database.Database
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

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
                val saveUssd =   repository.insertUSSD(
                    USSDModel(
                        sessionId = sessionID, phoneNumber = phoneNumber, networkCode = networkCode,
                        serviceCode = serviceCode, text= responseSave ))

                call.application.log.info("the new entry is $saveUssd")
                call.respondText(response)


            } else {
                val saveUssd =   repository.insertUSSDMenu(
                    USSDSessions( sessionId = sessionID, text= responseSave ))

                call.application.log.info("the value save is $saveUssd")
                call.respondText(response)
            }
        }

        get("/ussdlist") {
            val addAllSession = mutableListOf<AllSessionModel>()
            val listOfUssd = repository.retrieveAllUSSDSession()
            listOfUssd.forEach { ussdModel ->
                val listSessions = repository.retrieveAllUSSDSessionByID(ussdModel.sessionId)
              addAllSession.add(AllSessionModel(
                  sessionId = ussdModel.sessionId,
                  menuSession = listSessions
              ))

            }
            call.respond(addAllSession)
        }

        get("/ussdsessionlist") {
            val listOfUssd = repository.retrieveAllUSSDSession()
            call.respond(listOfUssd)
        }

        get("/ussdmenuList") {
            val listOfMenu = repository.retrieveAllUSSD()
            call.respond(listOfMenu)
        }
    }
}
