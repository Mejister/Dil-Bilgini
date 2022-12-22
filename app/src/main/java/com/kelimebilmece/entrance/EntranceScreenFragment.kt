package com.kelimebilmece.entrance

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.kelimebilmece.BuildConfig
import com.example.kelimebilmece.R
import com.example.kelimebilmece.databinding.FragmentEntranceScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kelimebilmece.MainActivity


class EntranceScreenFragment : Fragment() {
    private lateinit var binding: FragmentEntranceScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntranceScreenBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnYeniOyun.setOnClickListener {
            findNavController().navigate(R.id.action_entranceScreenFragment_to_gameScreenFragment)
        }


            binding.btnHakkNda.setOnClickListener {
                onAlertDialog()
            }
        binding.btnAyarlar.setOnClickListener {
            findNavController().navigate(R.id.action_entranceScreenFragment_to_settingsFragment)
        }

    }


    fun onAlertDialog() {

        MaterialAlertDialogBuilder(requireContext())

            .setTitle("Hakkında")
            .setMessage("Versin:${BuildConfig.VERSION_NAME} \n" +
                    "asdasdasdsadasdasdsal  kwdalkndalkndwalkdandlansdlandwkajwndlakwndalkdnawkldnaklwndalwkdnalwkndalwdknalwdqwldafkalkfhakdjfaf")
            .setPositiveButton(
            "Anladım") { dialog, id ->
            dialog.cancel()
        }
        .show()
    }

}



