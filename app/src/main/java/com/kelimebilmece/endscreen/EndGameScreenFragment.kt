package com.test.kelimebilgini.endscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.kelimebilgini.R
import com.test.kelimebilgini.databinding.FragmentEndGameScreenBinding
import com.test.kelimebilgini.gamescreen.GameScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EndGameScreenFragment : Fragment() {
    private lateinit var binding: FragmentEndGameScreenBinding
    private val viewModel by viewModels<GameScreenViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEndGameScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvShowScore.text = arguments?.getInt("point").toString()
        binding.tvShowTime.text = getString(R.string.kalan_sure, arguments?.getInt("time"))
        binding.btnMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_endGameScreenFragment_to_entranceScreenFragment)

        }

    }
}
