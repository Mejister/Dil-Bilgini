package com.test.kelimebilgini.entrance

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.kelimebilgini.BuildConfig
import com.test.kelimebilgini.R
import com.test.kelimebilgini.databinding.FragmentEntranceScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
        binding.btnOynanS.setOnClickListener {
            PlayingAlert()
        }

    }


    fun onAlertDialog() {

        MaterialAlertDialogBuilder(requireContext())

            .setTitle("Hakkında")
            // .setMessage("Version:${BuildConfig.VERSION_NAME} \n" +  "dqwldafkalkfhakdjfaf ")

            .setMessage(
                Html.fromHtml(
                    "<b>Version</b>: <span>${BuildConfig.VERSION_NAME}</span>" +
                            "<br>" +
                            "<span>Öneri, görüş veya hatalı soru bildirmek için mail addresi ;</span>" +
                            "<br>" +
                            "<a href=\"mailto:dilbilgini2023@hotmail.com\">dilbilgini2023@hotmail.com</a>" +
                            " <br><br>" +
                            "<span> Programın yapımındaki destekleri için ; <b> Erdem KALYONCUYA </b> teşekkür ederim." +
                            " <br><br>" +
                            "\n" +
                            "<b> Yapımcı:UĞUR BÜYÜKÖZKAN</b>"

                )
            )


            .setPositiveButton(
                "Anladım"
            ) { dialog, id ->
                dialog.cancel()
            }
            .show()

    }

    fun PlayingAlert() {

        MaterialAlertDialogBuilder(requireContext())

            .setTitle("Oynanış")


            .setMessage(
                Html.fromHtml(
                    "<span>Oyunda <b>toplam süreniz 210 saniyedir </b>.Durdur butonuna bastığınızda oyun size düşünmek ve <b>cevabı girmek için 25 saniye</b> verir.Bu süre bittiğinde cevabınız kontol edilir ve doğruysa diğer soruya geçebilirsiniz. " +
                            "Doğru cevapta soru puanı kadar puan kazanırsınız,25 saniyelik süre bittiğinde,cevabı bulamamışsanız soru puanı kadar puan kaybedersiniz, cevabızı girdiğinizde, onayla butonuna basarak cevabınızı kontrol edebilirsiniz." +
                            "Her harf butonuna bastığınızda, soru puanından 100 puan eksilir.<b>İpucu olarak ortaya harfler çıksa bile doğru cevap için kelimenin tamamını yazmalısınız.Durdur butonuna bastığınızda (süre durdurulduğunda) harf butonu çalışmayacaktır</b>.Süreniz sona erdiğinde otomatik olarak sonuç ekranına yönlendirilirsiniz</span>" +
                            " <br><br>" +
                            "<span>mastar:Cevap mastar ile bitmektedir. (-mek ,-mak) </span>" +
                            " <br><br>" +
                            "<span>mecaz:Kelime mecaz anlamlı bir kelimedir. </span>" +
                            " <br><br>" +
                            "<span>eskimiş:Kelime günümüz Türkçesinde az kullanılmaktadır.</span>" +
                            " <br><br>" +
                            "<span>zıpır:Cevap alaylı bir biçimde tarif edilmiştir. </span>" +
                            " <br><br>" +
                            "<b>Keyifli Oyunlar ; </b>" +
                            " <br><br>" +
                            "<b>Öneriler</b>: <span>Türkçe klavye kullanmanız tavsiye edilir.Daha iyi bir deneyim için klavyenizin kelime önerilerini kapatınız.</span>"

                )
            )


            .setPositiveButton(
                "Anladım"
            ) { dialog, id ->
                dialog.cancel()
            }
            .show()

    }
}



