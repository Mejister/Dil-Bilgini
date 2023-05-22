package com.test.kelimebilgini.gamescreen

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.test.kelimebilgini.R
import com.test.kelimebilgini.databinding.FragmentGameScreenBinding
import dagger.hilt.android.AndroidEntryPoint
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
        binding.etAnswerText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)

        lifecycleScope.launch {


            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->

                        when (uiState) {
                            GameScreenUiState.Game -> {}
                            GameScreenUiState.Result -> {
                                findNavController().navigate(
                                    R.id.action_gameScreenFragment_to_endGameScreenFragment,
                                    bundleOf(
                                        "point" to viewModel._state.value.totalPoint,
                                        "time" to viewModel._state.value.duration
                                    )
                                )
                            }
                        }
                    }
                }


                launch {
                    viewModel._state.collect {
                        //             binding.tvAnswerText.setText(it.trueAnswer)
                        binding.btnOnayla.text = if (it.paused) "Onayla" else "Durdur"
                        binding.btnOnayla.setTextColor(
                            ResourcesCompat.getColor(
                                resources,
                                if (it.paused) R.color.greeny else R.color.reddy,
                                context?.theme
                            )
                        )
                        binding.etAnswerText.filters =
                            arrayOf(InputFilter.LengthFilter(it.questionEntity.answer.length))
                        binding.tvQestionText.text = it.questionEntity.question
                        binding.tvQuestionPointNumber.text = it.point.toString()
                        binding.tvTotalPointNumber.text = it.totalPoint.toString()
                        if (it.userAnswer.isEmpty()) {
                            binding.etAnswerText.setText("")
                        }
                        binding.tvWhichQuestion.text =
                            getString(R.string.question_text, (it.questionNumber + 1))

                        binding.mTextField.text = it.duration.toString()

                        binding.tvAnswerText.setText(it.tipAnswer)


                    }
                }
            }
        }

        //   binding.etAnswerText.clearComposingText()
        binding.etAnswerText.doAfterTextChanged {
            viewModel.setAnswerText(
                it.toString()
            )
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

        binding.btnipucu.setOnClickListener {
            viewModel.Tip()
        }

    }
}

