package com.test.kelimebilgini.room

import androidx.room.TypeConverter

class DaoConverter {
    @TypeConverter
    fun toListOfStrings(stringValue: String): List<String>? {
        return stringValue.split(",").map { it }
    }

    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>?): String {
        return listOfString?.joinToString(separator = ",") ?: ""
    }
}