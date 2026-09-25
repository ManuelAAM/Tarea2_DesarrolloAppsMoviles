package com.escom.tarea2.views.ui.section1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.FragmentTextInputsBinding
import com.google.android.material.snackbar.Snackbar

class TextInputsFragment : Fragment() {

    private var _binding: FragmentTextInputsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextInputsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Validación de entrada dinámica
        binding.etValidation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.toString() ?: ""
                if (text.isNotEmpty() && text.length < 5) {
                    binding.tilValidation.error = "Error: Mínimo 5 caracteres (actual: ${text.length})"
                } else {
                    binding.tilValidation.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 6. Sugerencias automáticas con AutoCompleteTextView
        val suggestions = arrayOf(
            "Desarrollo de Aplicaciones Móviles Nativas",
            "Sistemas Distribuidos",
            "Arquitectura de Computadoras",
            "Compiladores y Lenguajes",
            "Ingeniería de Software",
            "Inteligencia Artificial"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        binding.actvSuggestions.setAdapter(adapter)

        // 7. Barra de búsqueda interactiva
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tvSearchResult.text = "Consulta enviada: \"${query ?: ""}\""
                viewModel.setLastAction("Búsqueda ejecutada: ${query ?: ""}")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.tvSearchResult.text = "Escribiendo búsqueda: \"${newText ?: ""}\""
                return true
            }
        })

        // 8. Conexión entre Secciones: Agregar nuevo dato a Sección 4
        binding.btnAddToSection4.setOnClickListener {
            val title = binding.etSimple.text?.toString()?.trim()
            val notes = binding.etMultiline.text?.toString()?.trim()

            if (title.isNullOrEmpty()) {
                binding.etSimple.error = "Ingresa un nombre primero"
                Snackbar.make(view, "Debes capturar al menos el nombre simple", Snackbar.LENGTH_SHORT).show()
            } else {
                val description = if (!notes.isNullOrEmpty()) notes else "Elemento capturado en la Sección 1"
                viewModel.addItem(title, description, "Formulario S1")
                binding.etSimple.text?.clear()
                binding.etMultiline.text?.clear()
                Snackbar.make(view, "¡Elemento '$title' agregado a la Sección 4!", Snackbar.LENGTH_LONG)
                    .setAction("Ver") {
                        // Navegación rápida si se desea
                    }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
