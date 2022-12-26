package com.kelimebilmece.endscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kelimebilmece.R
import com.example.kelimebilmece.databinding.FragmentEndGameScreenBinding
import com.example.kelimebilmece.databinding.FragmentGameScreenBinding
import com.kelimebilmece.gamescreen.GameScreenViewModel
import com.kelimebilmece.gamescreen.GamesScreenDataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        binding.tvShowScore.text=arguments?.getInt("point").toString()
        binding.btnMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_endGameScreenFragment_to_entranceScreenFragment)

        }

    }
}
