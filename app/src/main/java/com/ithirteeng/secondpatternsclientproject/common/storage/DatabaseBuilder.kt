package com.ithirteeng.secondpatternsclientproject.common.storage

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private const val DATABASE_NAME = "BankDatabase"

    fun build(context: Context): BankDatabase = Room
        .databaseBuilder(context, BankDatabase::class.java, DATABASE_NAME)
        .build()
}