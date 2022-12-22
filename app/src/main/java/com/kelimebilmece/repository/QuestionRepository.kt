package com.kelimebilmece.repository


import com.kelimebilmece.model.QuestionClass
import com.kelimebilmece.remotefirebase.FirebaseState
import com.kelimebilmece.room.QuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow



interface QuestionRepository{

       suspend fun saveQuestionstoRoom()
        fun getQuestions():Flow<List<QuestionEntity>>
        suspend fun instertQuestionsEntity(questionEntity: List<QuestionEntity>)

}
