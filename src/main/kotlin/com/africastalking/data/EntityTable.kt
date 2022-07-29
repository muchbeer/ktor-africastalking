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

object USSDMenu : Table<UssdMenuEntity>("ussd_menu") {
    val sessionId = varchar("sessionId").primaryKey().bindTo { it.sessionId }
    val phoneNumber = varchar("phoneNumber").bindTo { it.phoneNumber }
    val textResponse = varchar("textResponse").bindTo { it.textResponse }
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
    val phoneNumber : String
    val textResponse : String
}

