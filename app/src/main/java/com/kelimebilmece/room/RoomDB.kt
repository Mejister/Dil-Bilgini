package com.kelimebilmece.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities =[QuestionEntity::class] , version = 1, exportSchema = false)
   // @TypeConverters(DaoConverter::class)
    abstract class RoomDB: RoomDatabase() {
    abstract fun DataDao(): DataDao
}