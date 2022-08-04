package com.africastalking.route

import com.africastalking.data.DatabaseFactory
import com.africastalking.model.*
import com.africastalking.repository.SMSRepository
import com.africastalking.repository.SMSRepositoryImpl
import com.africastalking.repository.USSDRepository
import com.africastalking.repository.USSDRepositoryImpl
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.ktorm.database.Database

fun Application.configureRouting() {

log.info("Enter configureRoute")
    val ktormDb : Database = DatabaseFactory.init()

    val USSDRepository : USSDRepository = USSDRepositoryImpl(ktormDb)
    val smsRepository : SMSRepository = SMSRepositoryImpl(ktormDb)

    routing {
        post("/sms") {
            val smses = call.receive<SMSInfo>()
            val response : ATMessage = smsRepository.sendSMS(
                atPhone = smses.number, atMessage = smses.message )

            smsRepository.saveMessage(response)
            call.respond(response)
        }

    }
    routing {
        get("/") {
            println("print Ktor server enter at services")
            call.respondText("Working with Ktor Africastalking API")
        }

        post("/ussd") {
            val ussdParameters = call.receiveParameters()
            val sessionID = ussdParameters["sessionId"].toString()
            val phoneNumber = ussdParameters["phoneNumber"].toString()
            val networkCode = ussdParameters["networkCode"].toString()
            val serviceCode = ussdParameters["serviceCode"].toString()
            val text = ussdParameters["text"].toString()

            val response : String = USSDRepository.ussdMenu(text)
            val responseSave : String = USSDRepository.processTextResponse(text)

            val checkSession = USSDRepository.findUSSDSessionById(sessionID)

            if (checkSession !=null)  {
                val saveUssd =   USSDRepository.insertUSSD(
                    USSDModel(
                        sessionId = sessionID, phoneNumber = phoneNumber, networkCode = networkCode,
                        serviceCode = serviceCode, text= responseSave ))

                call.application.log.info("the new entry is $saveUssd")
                call.respondText(response)


            } else {
                val saveUssd =   USSDRepository.insertUSSDMenu(
                    USSDSessions( sessionId = sessionID, text= responseSave ))

                call.application.log.info("the value save is $saveUssd")
                call.respondText(response)
            }
        }

        get("/ussdlist") {
            val addAllSession = mutableListOf<AllSessionModel>()
            val listOfUssd = USSDRepository.retrieveAllUSSDSession()
            listOfUssd.forEach { ussdModel ->
                val listSessions = USSDRepository.retrieveAllUSSDSessionByID(ussdModel.sessionId)
              addAllSession.add(AllSessionModel(
                  sessionId = ussdModel.sessionId,
                  menuSession = listSessions
              ))
            }
            call.respond(addAllSession)
        }

        get("/ussdsessionlist") {
            val listOfUssd = USSDRepository.retrieveAllUSSDSession()
            call.respond(listOfUssd)
        }

        get("/ussdmenuList") {
            val listOfMenu = USSDRepository.retrieveAllUSSD()
            call.respond(listOfMenu)
        }
    }
}
