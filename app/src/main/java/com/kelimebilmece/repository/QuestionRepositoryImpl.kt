package com.kelimebilmece.repository

import com.kelimebilmece.mapper.QuestionMapper
import com.kelimebilmece.remotefirebase.ApiService
import com.kelimebilmece.remotefirebase.FirebaseState
import com.kelimebilmece.room.DataDao
import com.kelimebilmece.room.QuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class QuestionRepositoryImpl @Inject constructor(private val firebaseApi: ApiService,private val dataDao: DataDao) :QuestionRepository {
private val questionMapper=QuestionMapper()

    override suspend fun saveQuestionstoRoom() {
      firebaseApi.getQuestions().let {
          dataDao.InsertAll(questionMapper.toEntity(it))
      }

    }

    override fun getQuestions(): Flow<List<QuestionEntity>> {


        return dataDao.giveMeSomething().map {
            val tempList= mutableListOf<QuestionEntity>()
            for(i in 4..10) {
                it.filter {
                    it.answer.length==i
                }.shuffled().take(2).let { _questions->
                    tempList.addAll(_questions)
                }
            }
            tempList
        }

    }

    override suspend fun instertQuestionsEntity(questionEntity: List<QuestionEntity>) {
        dataDao.InsertAll(questionEntity)
    }


}



