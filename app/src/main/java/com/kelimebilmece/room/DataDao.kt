package com.test.kelimebilgini.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao{
    @Insert
    suspend fun InsertAll(questions:List<QuestionEntity>)

    @Query ("SELECT * FROM questions")
    fun giveMeSomething():Flow<List<QuestionEntity>>

}