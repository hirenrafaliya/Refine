package com.app.refine.utils

import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

object MongoUtils {

    fun getApp() = App(AppConfiguration.Builder("learnapplication-hlukn").build())
    fun getMongoClient()= getApp().currentUser()?.getMongoClient("mongodb-atlas")!!
    fun getDatabase() = getMongoClient().getDatabase("learn_db")!!

    //todo : solve problem of null db & client !! .?
}