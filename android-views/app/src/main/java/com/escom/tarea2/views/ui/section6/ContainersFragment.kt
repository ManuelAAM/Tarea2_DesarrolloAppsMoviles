package com.escom.tarea2.views.ui.section6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.FragmentContainersBinding
import com.google.android.material.snackbar.Snackbar

class ContainersFragment : Fragment() {

    private var _binding: FragmentContainersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContainersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miniBottomNav.setOnItemSelectedListener { item ->
            val title = item.title
            Snackbar.make(binding.root, "Navegación simulada: $title", Snackbar.LENGTH_SHORT).show()
            viewModel.setLastAction("BottomNav pulsado: $title")
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
