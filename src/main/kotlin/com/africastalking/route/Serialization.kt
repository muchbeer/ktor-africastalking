package com.africastalking.route

import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*


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
