package com.izel.sorteadordenumeros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.izel.sorteadordenumeros.databinding.FragmentResultadoSorteioBinding
import kotlin.random.Random

class ResultadoSorteioFragment : Fragment() {

    private var _binding: FragmentResultadoSorteioBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultadoSorteioBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvInfoContent.text = getString(R.string.primeiro_resultado, "10")

            gerarTextoDeNumeroSorteado()
        }
    }

    fun FragmentResultadoSorteioBinding.gerarTextoDeNumeroSorteado() {
        val numeroSorteadoTextView = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = Random.nextInt(100).toString()
            setTextAppearance(R.style.TextAppearance_RobotoMono_Overline)
            textSize = 48f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.content_brand))
        }

        root.addView(numeroSorteadoTextView)
        flowResultNumbersHelper.referencedIds =
            flowResultNumbersHelper.referencedIds.plus(numeroSorteadoTextView.id)
    }
}