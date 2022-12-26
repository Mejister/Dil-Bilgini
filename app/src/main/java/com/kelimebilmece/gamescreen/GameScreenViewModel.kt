package com.kelimebilmece.gamescreen


import android.os.CountDownTimer
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.kelimebilmece.R
import com.google.firebase.firestore.FirebaseFirestore
import com.kelimebilmece.repository.QuestionRepository
import com.kelimebilmece.room.QuestionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.checkerframework.checker.units.qual.Time
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt




data class GamesScreenDataState(
    val questionlist: List<QuestionEntity> = emptyList(),
    val questionNumber: Int = 0,
    val point: Int = 0,
    val totalPoint: Int = 0,
    val userAnswer: String = "",
    val questionEntity: QuestionEntity = QuestionEntity(),
    val tipAnswer: String = "",
    //süre
             val job: Job? = null,
            // The current elapsed time, in milliseconds
             val elapsedTime:Long = 0L,
            // The total duration of the timer, in milliseconds
             val duration:Int = 120,
            // Whether the timer is currently paused
             val paused:Boolean = false,






)
const val StartingTime:Long=120L
@HiltViewModel
class GameScreenViewModel @Inject constructor(

    private val questionRepository: QuestionRepository,
    firestore: FirebaseFirestore


) : ViewModel() {

    val _state = MutableStateFlow(GamesScreenDataState())
    private val _uiState = MutableStateFlow<GameScreenUiState>(GameScreenUiState.Game)
    val uiState: StateFlow<GameScreenUiState> = _uiState


    init {
        viewModelScope.launch {
            questionRepository.saveQuestionstoRoom()
            getQuestions()
            start()

        }
    }

    fun start() {
        // Create a new Job for the timer
        val job1 = viewModelScope.launch {
            // Set the elapsed time and duration of the timer
            var elapsedTime = 0L


            // Run the timer loop
            while (elapsedTime <= StartingTime) {
                delay(1000)
                elapsedTime += 1
                // Update the elapsed time and the time remaining
                _state.update {
                    it.copy(
                        duration = (StartingTime - elapsedTime).toInt(),
                        elapsedTime = elapsedTime
                    )
                }
            }
        }

        // Save the job to the _state object
        _state.update {
            it.copy(job = job1)
        }
    }
    fun pause() {
        // Cancel the current job
        _state.value.job?.cancel()


        // Set the paused flag to true
        _state.update {
            it.copy(paused = true)
        }
        val job2= viewModelScope.launch {
            delay(10000)    // burda butonun bekletme süresi
            if (_state.value.userAnswer.lowercase(Locale("tr")) == _state.value.questionEntity.answer.lowercase(Locale("tr"))){
                CheckAnswer()
            }
            else {
                _state.update {
                    it.copy(
                        totalPoint = it.totalPoint-(it.point)
                    )
                }
                NextQuestion()
            }

            resume()
        }
        _state.update {
            it.copy(
                job = job2
            )
        }

    }

    fun resume() {

        _state.value.job?.cancel()
        // Check if the timer is paused
        if (_state.value.paused ) {
            // Create a new job to continue the timer
            val job3 = viewModelScope.launch {
                while (_state.value.elapsedTime <= StartingTime) {
                    delay(1000)
                    // Update the elapsed time
                    _state.update {
                        it.copy(elapsedTime = it.elapsedTime + 1L,
                            duration = (StartingTime - _state.value.elapsedTime).toInt())
                    }

                    if (_state.value.duration==0){
                        _uiState.value=GameScreenUiState.Result
                    }
                }
            }

            // Save the job to the _state object and set the paused flag to false
            _state.update {
                it.copy(job = job3, paused = false)
            }
        }
    }

    /*
    fun reset() {
        // Cancel the current job and reset the elapsed time and paused flag
        _state.value.job?.cancel()
        _state.update {
            it.copy(elapsedTime = 0L, paused = false)
        }
    } reset
    */

    private fun getQuestions() = viewModelScope.launch {
        questionRepository.getQuestions().collect { _questions ->
            _state.update {
                it.copy(
                    questionlist = _questions,
                    questionEntity = _questions[0],
                    tipAnswer = "_".repeat(_questions[0].answer.length)
                )
            }
            QuestionPointCalculation()
        }
            }

    private fun QuestionPointCalculation() {
        _state.update {
            it.copy(point = it.questionlist[it.questionNumber+1].answer.length * 100)
        }


    }

     fun SucccesfullyAnswerPoint() {
         resume()
        _state.update {
            it.copy(totalPoint = it.point + it.totalPoint)

        }


    }

    fun CheckAnswer() {
      if(_state.value.paused){
          if (_state.value.userAnswer.lowercase(Locale("tr")) == _state.value.questionEntity.answer.lowercase(Locale("tr"))
          )
          {
              SucccesfullyAnswerPoint()
              NextQuestion()


          } else {
              _state.update {
                  it.copy(userAnswer = "")
              }

          }
      }
        else {
          pause()
      }
    }

    //make it private
    fun NextQuestion(){
        if(_state.value.questionNumber>12) {
            _uiState.value=GameScreenUiState.Result
            return



        }

        QuestionPointCalculation()
        _state.update {
            it.copy(
                questionEntity = it.questionlist[it.questionNumber+1],
                questionNumber = it.questionNumber + 1,
                userAnswer = "",
                tipAnswer = "_".repeat( it.questionlist[it.questionNumber +1].answer.length)
            )
        }

    }

    fun Tip() {
            val randomLetterNumber:Int=Random.nextInt(0,(_state.value.questionEntity.answer.length ))
            val randomLetter:String=_state.value.questionEntity.answer.get(randomLetterNumber).toString()
            val replaceWith=_state.value.tipAnswer.toCharArray()
        while(replaceWith[randomLetterNumber]=='_')  {  replaceWith[randomLetterNumber]=randomLetter.toCharArray()[0] }
        _state.update {

            it.copy(
                point = it.point - 100,
                tipAnswer =replaceWith.joinToString(""),
                )
        }
    }




    fun setAnswerText(userAnswer: String) {
        _state.update {
            it.copy(userAnswer = userAnswer)
        }
    }

}


sealed class GameScreenUiState {
    object Game : GameScreenUiState()
    object Result : GameScreenUiState()
}



/*


    - ilk soru tip çalışmıyor
    - ipucu butonunun while döngüsü
   - hakkında kısımda yazacaklar? -> tel dili türkçe öner, genel oyun kuralları, yapımcı


 - extra jokerler?
   Hatalı soru bildir Butonu??
   ---new---







*/


