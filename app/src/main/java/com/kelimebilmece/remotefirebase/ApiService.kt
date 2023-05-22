package com.test.kelimebilgini.remotefirebase


import com.google.firebase.firestore.FirebaseFirestore
import com.test.kelimebilgini.model.QuestionClass
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,


    ) {

    /*   private val _firebaseStateFlow: MutableStateFlow<FirebaseState> = MutableStateFlow(InitialState)
       val firebaseStateFlow: StateFlow<FirebaseState> = _firebaseStateFlow*/


    suspend fun getQuestions(): List<QuestionClass> {
        val collectionReference = firebaseFirestore
            .collection(Questions)
        val tempList = mutableListOf<QuestionClass>()
        collectionReference.get().await().let { querySnapshot ->
            if (querySnapshot != null && !querySnapshot.isEmpty) {


                val questions = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.toObject(QuestionClass::class.java) ?: QuestionClass("", "")
                }
                tempList.addAll(questions)
            }

        }

        return tempList


    }


    companion object {
        private const val AppInfo = "AppInfo"
        private const val Questions = "Questions"
    }
}


sealed class FirebaseState

object InitialState : FirebaseState()
data class ErrorState(val errorMessage: String) : FirebaseState()
data class SuccessState(val QuestionAns: List<QuestionClass?>) : FirebaseState()