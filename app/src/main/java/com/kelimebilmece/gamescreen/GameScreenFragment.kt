package com.kelimebilmece.gamescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.kelimebilmece.R
import com.example.kelimebilmece.databinding.FragmentGameScreenBinding
import com.kelimebilmece.room.QuestionEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameScreenFragment : Fragment() {


    private lateinit var binding: FragmentGameScreenBinding
    private val viewModel by viewModels<GameScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameScreenBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiState.collect { uiState ->

                    when (uiState) {
                        GameScreenUiState.Game -> {}
                        GameScreenUiState.Result -> {
                            findNavController().navigate(R.id.action_gameScreenFragment_to_endGameScreenFragment, bundleOf("point" to viewModel._state.value.totalPoint))
                        }
                    }
                }
            }


                launch {
                    viewModel._state.collect{
                        binding.tvQestionText.text=it.questionEntity.question
                        binding.tvQuestionPointNumber.text=it.point.toString()
                        binding.tvTotalPointNumber.text=it.totalPoint.toString()
                        if(it.userAnswer.isEmpty()) {
                            binding.etAnswerText.setText("")
                        }
                        binding.tvWhichQuestion.text= getString(R.string.question_text, (it.questionNumber+1))

                        binding.mTextField.text=it.duration.toString()

                        binding.tvAnswerText.setText(it.tipAnswer)


                    }
                }
            }
        }
        binding.etAnswerText.doAfterTextChanged {
            viewModel.setAnswerText(
                it.toString())
        }
       /* //sill-------
        binding.btnHile.setOnClickListener {
            viewModel.NextQuestion()

        //    findNavController().navigate(R.id.action_gameScreenFragment_to_endGameScreenFragment,
         //      bundleOf("point" to viewModel._state.value.totalPoint))

        }*/

                     binding.btnOnayla.setOnClickListener {
                         viewModel.CheckAnswer()
                     }

            binding.btnipucu.setOnClickListener{
           viewModel.Tip()
        }

    }
}

