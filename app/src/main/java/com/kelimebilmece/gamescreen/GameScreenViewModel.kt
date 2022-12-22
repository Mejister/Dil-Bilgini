package com.kelimebilmece.gamescreen


import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.kelimebilmece.repository.QuestionRepository
import com.kelimebilmece.room.QuestionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt




data class GamesScreenDataState(
    val questionlist: List<QuestionEntity> = emptyList(),
    val questionNumber: Int = 0,
    val point: Int = 0,
    val totalPoint: Int = 0,
    val userAnswer: String = "",
    val questionEntity: QuestionEntity = QuestionEntity(),
    val time: Int = 120,
    val tipAnswer: String = "",
    val timer:Int=10000,



    // Tip

)

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
          //  CountDownTimer()


        }


    }
    // timer: CountDownTimer, pause:Boolean
   /* fun CountDownTimer( ){
        time()
    }
    fun time(){
       _state.update {
           var left=timer
           viewModelScope.launch(Dispatchers.IO) {
               repeat((timer/1000).toInt()){
                   delay(1000)
                   left-=1000
               }
           }
       }

    }*/



    /*val timer = object : CountDownTimer(_state.value.time * 1000L + 1000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            _state.update {
                it.copy(time = (millisUntilFinished / 1000).toInt())
            }

        }
        *//* override fun onPause(millisUntilFinished: Long){
             _state.update {
                 it.copy(time = (millisUntilFinished/0).toInt())
             }

         }*//*

        override fun onFinish() {
            viewModelScope.launch {
                _uiState.emit(GameScreenUiState.Result)
            }

        }
    }*/

    val timeRemaining = (_state.value.time * 1000L + 1000)

    val mainTimer = object : CountDownTimer(_state.value.time * 1000L + 1000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            _state.update {
                it.copy(time = (millisUntilFinished / 1000).toInt())
            }
        }

        override fun onFinish() {
            viewModelScope.launch {
                _uiState.emit(GameScreenUiState.Result)

            }

        }
    }

    val secondaryTimer = object : CountDownTimer(10000, 1000) {

        override fun onTick(millisUntilFinished: Long) {



        }

        override fun onFinish() {

            if (CheckAnswer().equals(true)) {
                this.cancel()
            }


            mainTimer.start()
        }

    }



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
            it.copy(point = it.questionlist[it.questionNumber].answer.length * 100)
        }


    }
    val totalyPoint= _state.value.totalPoint
     fun SucccesfullyAnswerPoint() {
        _state.update {
            it.copy(totalPoint = it.point + it.totalPoint)
        }


    }

    fun CheckAnswer() {
        if (_state.value.userAnswer.lowercase(Locale("tr")) == _state.value.questionEntity.answer.lowercase(
                Locale("tr")
            )
        ) {
            SucccesfullyAnswerPoint()
            QuestionPointCalculation()
            NextQuestion()

        } else {
            _state.update {
                it.copy(userAnswer = "")
            }

        }
    }

    //make it private
    fun NextQuestion(){
        _state.update {
            it.copy(
                questionEntity = it.questionlist[it.questionNumber],
                questionNumber = it.questionNumber + 1,
                userAnswer = "",
                tipAnswer = "_".repeat( it.questionlist[it.questionNumber  ].answer.length)

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


    - boş olanların yerine belirlediğimizde nasıl tekrar eski haline geriririz  randomla olmuyor
    - ipucu butonunun while döngüsü
   - hakkında kısımda yazacaklar?
   - onaylaya bastığında ek süre verip, o sürede bilemezse - puan

  - extra jokerler?
   Hatalı soru bildir Butonu??
   ---new---
   db yi sildiğinde yeniden yükleyemiyor, 1. sdk'da yüklüyor 2. inden yüklemiyor
   sdk 31 de türkçe karakter girilmiyor
   ilk soruyu 3 kez soruyor
   aynı soru 2 defa gelebilir ?
   ortadaki soruyu bildiğinde ilk soru puanı veriyor
   son soruya cevap verince program çöküyor

*/


