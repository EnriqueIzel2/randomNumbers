package com.izel.sorteadordenumeros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.izel.sorteadordenumeros.databinding.FragmentResultadoSorteioBinding
import kotlinx.coroutines.launch

class ResultadoSorteioFragment : Fragment() {
    private val viewModel: SorteioViewModel by activityViewModels()

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
            tvInfoContent.text = getString(R.string.numero_sorteio, "10")

            lifecycleScope.launch {
                viewModel.uiState.collect { uiState ->
                    tvInfoContent.text =
                        getString(R.string.numero_sorteio, uiState.currentDrawNumber.toString())

                    clearLastDrewNumber()

                    uiState.drawNumbers.forEach { drawNumber ->
                        generateDrawNumberTextView(drawNumber)
                    }
                }
            }
        }
    }

    private fun FragmentResultadoSorteioBinding.generateDrawNumberTextView(drawNumber: Int) {
        val drawNumberTextView = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = drawNumber.toString()
            setTextAppearance(R.style.TextAppearance_RobotoMono_Overline)
            textSize = 48f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.content_brand))
        }

        root.addView(drawNumberTextView)
        flowResultNumbersHelper.referencedIds =
            flowResultNumbersHelper.referencedIds.plus(drawNumberTextView.id)
    }

    private fun FragmentResultadoSorteioBinding.clearLastDrewNumber() {
        flowResultNumbersHelper.referencedIds.forEach {
            root.removeView(root.findViewById(it))
        }
    }
}