package com.escom.tarea2.views.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.escom.tarea2.views.MainActivity
import com.escom.tarea2.views.R
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastAction.observe(viewLifecycleOwner) { action ->
            binding.tvGlobalState.text = "Última acción: $action"
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            binding.tvTotalItems.text = "Elementos en la colección: ${items.size}"
        }

        val mainActivity = activity as? MainActivity

        binding.btnGoSection1.setOnClickListener { mainActivity?.navigateTo(R.id.menu_text_inputs) }
        binding.btnGoSection2.setOnClickListener { mainActivity?.navigateTo(R.id.menu_buttons) }
        binding.btnGoSection3.setOnClickListener { mainActivity?.navigateTo(R.id.menu_selection) }
        binding.btnGoSection4.setOnClickListener { mainActivity?.navigateTo(R.id.menu_lists) }
        binding.btnGoSection5.setOnClickListener { mainActivity?.navigateTo(R.id.menu_feedback) }
        binding.btnGoSection6.setOnClickListener { mainActivity?.navigateTo(R.id.menu_containers) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
