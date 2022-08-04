package com.africastalking.route

import com.africastalking.model.DataState
import com.africastalking.security.JwtConfig
import com.africastalking.security.SessionIdPrincipalForMenu
import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }


    //JWT
/*    JwtConfig.initialize("africastalking-app")
    install(Authentication){
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asInt()
                if(claim != null){
                    SessionIdPrincipalForMenu(claim)
                }else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(DataState.Error("Invalid Token"))
            }
        }
    }*/
}
