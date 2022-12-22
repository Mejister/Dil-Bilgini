package com.kelimebilmece.endscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kelimebilmece.R
import com.example.kelimebilmece.databinding.FragmentEndGameScreenBinding
import com.example.kelimebilmece.databinding.FragmentGameScreenBinding
import com.kelimebilmece.gamescreen.GamesScreenDataState
import kotlinx.coroutines.launch


class EndGameScreenFragment : Fragment() {
   private lateinit var binding: FragmentEndGameScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmentEndGameScreenBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            apply {

                binding.btnMainMenu.setOnClickListener {
                    findNavController().navigate(R.id.action_endGameScreenFragment_to_entranceScreenFragment)
                }

                binding.tvShowScore.text = GamesScreenDataState().totalPoint.toString()


        }
    }


        fun newInstance(param1: String, param2: String) =
            EndGameScreenFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
