package com.test.kelimebilgini.mapper

import com.test.kelimebilgini.model.QuestionClass
import com.test.kelimebilgini.room.QuestionEntity
import javax.inject.Inject

class QuestionMapper @Inject constructor():EnitityMapper<List<QuestionEntity>,List<QuestionClass>> {
    override fun fromEntity(entity: List<QuestionEntity>): List<QuestionClass> {
        return entity.map {
            QuestionClass(questions= it.question, answers = it.answer)
        }
    }

    override fun toEntity(domainModel: List<QuestionClass>): List<QuestionEntity> {
       return domainModel.map {
           QuestionEntity(question = it.questions, answer = it.answers)
       }
    }
}