package com.test.kelimebilgini.settings


import android.content.Context
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kelimebilgini.databinding.FragmentSettingsBinding
import com.test.kelimebilgini.main.MainActivity


class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefences =
            requireContext().getSharedPreferences("GeneralSetting", Context.MODE_PRIVATE)
        val editor = prefences.edit()
        binding.swAudio.isChecked = prefences.getBoolean("sound", true)
        binding.swAudio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("sound", true)
                (requireActivity() as MainActivity).playSound()
            } else {
                editor.putBoolean("sound", false)
                (requireActivity() as MainActivity).stopSound()
            }
            editor.apply()
        }

    }

}




