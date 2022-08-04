package com.africastalking.security



class JwtConfig private constructor(secret: String) {

/*    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun createAccessToken(id: Int): String = JWT
        .create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim(CLAIM, id)
        .sign(algorithm)

    companion object{
        private const val ISSUER = "africastalking-app"
        private const val AUDIENCE = "africastalking-app"
        const val CLAIM = "id"

        lateinit var instance: JwtConfig
            private set

        fun initialize(secret: String){
            synchronized(this){
                if(!this::instance.isInitialized){
                    instance = JwtConfig(secret)
                }
            }
        }
    }*/
}