package com.example.daprbackgrounddataglobal.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.daprbackgrounddataglobal.room.Application
import com.example.daprbackgrounddataglobal.room.student
import java.util.concurrent.Executors

/*
* Copyright (C) 2017 Google Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/ /**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.  In a real
 * app, consider exporting the schema to help you with migrations.
 */
@Database(entities = [student::class,Application::class] , version = 1, exportSchema = false)
abstract class database : RoomDatabase() {
    abstract fun detailsDao(): dao?

    companion object {
        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: database? = null
        private const val NUMBER_OF_THREADS = 4

        private val lock = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(lock) {
            INSTANCE ?: getDatabase(context).also {
                INSTANCE = it
            }

        }

        fun getDatabase(context: Context): database? =

            Room.databaseBuilder(
                context.applicationContext,
                database::class.java, "word_database"
            )
                .build()
    }
}



