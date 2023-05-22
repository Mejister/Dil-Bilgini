package com.test.kelimebilgini.repository


import com.test.kelimebilgini.room.QuestionEntity
import kotlinx.coroutines.flow.Flow


interface QuestionRepository {

    suspend fun saveQuestionstoRoom()
    fun getQuestions(): Flow<List<QuestionEntity>>
    suspend fun instertQuestionsEntity(questionEntity: List<QuestionEntity>)

}
