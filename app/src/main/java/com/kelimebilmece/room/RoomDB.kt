package com.test.kelimebilgini.room

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities =[QuestionEntity::class] , version = 1, exportSchema = false)
   // @TypeConverters(DaoConverter::class)
    abstract class RoomDB: RoomDatabase() {
    abstract fun DataDao(): DataDao
}