package com.africastalking.data

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UssdTable : Table<UssdEntity>("ussd"){
    val menuPrimaryKey = varchar("menuPrimaryKey").primaryKey().bindTo { it.menuPrimaryKey }
    val sessionId = varchar("sessionId").bindTo { it.sessionId }
    val phoneNumber = varchar("phoneNumber").bindTo { it.phoneNumber }
    val networkCode = varchar("networkCode").bindTo { it.networkCode }
    val serviceCode = varchar("serviceCode").bindTo { it.serviceCode }
    val text = varchar("text").bindTo { it.text }
}

object USSDSessionTable : Table<UssdMenuEntity>("ussdsession") {
    val sessionId = varchar("sessionId").primaryKey().bindTo { it.sessionId }
    val textResponse = varchar("textResponse").bindTo { it.textResponse }
}

object SmsTable : Table<SmsEntity>("sms") {
   val messageId = varchar("messageId").primaryKey().bindTo { it.messageId }
    val cost = varchar("cost").bindTo { it.cost }
    val messageParts = int("messageParts").bindTo { it.messageParts }
    val number = varchar("number").bindTo { it.number }
    val message = varchar("message").bindTo { it.message }
    val status = varchar("status").bindTo { it.status }
}

interface UssdEntity : Entity<UssdEntity> {
    companion object : Entity.Factory<UssdEntity>()
    val menuPrimaryKey : String
    val sessionId : String
    val phoneNumber : String
    val networkCode : String
    val serviceCode : String
    val text : String
}

interface UssdMenuEntity : Entity<UssdMenuEntity> {

    companion object : Entity.Factory<UssdMenuEntity>()
    val sessionId : String
    val textResponse : String
}


interface SmsEntity : Entity<SmsEntity> {
    companion object : Entity.Factory<SmsEntity>()
    val messageId : String
    val cost : String
    val  messageParts : Int
    val message : String
    val  number : String
    val  status : String
}
