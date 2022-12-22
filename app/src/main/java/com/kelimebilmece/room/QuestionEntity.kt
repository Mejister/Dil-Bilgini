package com.kelimebilmece.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class  QuestionEntity (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name="id")
    val id: Int=0,
    @ColumnInfo (name="questions")
    val question :String="",
    @ColumnInfo (name="answers")
    val answer :String=""
)

