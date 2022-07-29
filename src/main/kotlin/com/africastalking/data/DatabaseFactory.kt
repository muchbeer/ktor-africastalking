package com.africastalking.data

import org.ktorm.database.Database

object DatabaseFactory {


    private val mUsername = "l4zyoysfvd9grh21"
    private val mPassword = "ueqwv1ul48c59d5f"

    fun  init() : Database {

        val ktormDb : Database
        val jdbcUrl = "jdbc:mysql://dt3bgg3gu6nqye5f.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/mvh6ud3uy4vh6n28?autoReconnect=true&useSSL=false"
        ktormDb = Database.connect(
            url =  jdbcUrl  ,
            driver = "com.mysql.cj.jdbc.Driver",
            user = mUsername,
            password = mPassword

        )
        return ktormDb
    }
}