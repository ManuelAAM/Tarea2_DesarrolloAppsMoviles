package com.escom.tarea2.views.ui.section2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.escom.tarea2.views.R
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.FragmentButtonsBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class ButtonsFragment : Fragment() {

    private var _binding: FragmentButtonsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun notifyButtonAction(buttonName: String) {
            val message = "Respuesta: Se pulsó el botón '$buttonName'"
            binding.tvButtonFeedback.text = message
            viewModel.setLastAction(message)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }

        // 1. Jerarquía de botones
        binding.btnFilled.setOnClickListener { notifyButtonAction("Relleno (Contained/Filled)") }
        binding.btnOutlined.setOnClickListener { notifyButtonAction("Con Contorno (Outlined)") }
        binding.btnText.setOnClickListener { notifyButtonAction("Solo Texto (TextButton)") }

        // 2. Botones con ícono
        binding.btnIconButton.setOnClickListener { notifyButtonAction("Solo Ícono (Favorito)") }
        binding.btnIconText.setOnClickListener { notifyButtonAction("Ícono + Texto (Favorito)") }

        // 3. FAB normal y extendido
        binding.fabNormal.setOnClickListener { notifyButtonAction("FAB Estándar Circular") }
        binding.fabExtended.setOnClickListener { notifyButtonAction("FAB Extendido con Etiqueta") }

        // 4. Selector de alternancia (Toggle Group)
        binding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val selectedLabel = when (checkedId) {
                    R.id.btnToggleDay -> "Día"
                    R.id.btnToggleWeek -> "Semana"
                    R.id.btnToggleMonth -> "Mes"
                    else -> "Personalizado"
                }
                notifyButtonAction("Toggle Segmentado: $selectedLabel")
            }
        }

        // 5. Botón con estado de carga
        binding.btnLoading.setOnClickListener {
            binding.btnLoading.isEnabled = false
            binding.btnLoading.text = "Cargando..."
            binding.pbLoadingButton.visibility = View.VISIBLE
            binding.tvButtonFeedback.text = "Iniciando proceso asíncrono simulado..."

            Handler(Looper.getMainLooper()).postDelayed({
                _binding?.let { b ->
                    b.pbLoadingButton.visibility = View.GONE
                    b.btnLoading.isEnabled = true
                    b.btnLoading.text = "Iniciar Carga"
                    notifyButtonAction("Carga asíncrona completada con éxito")
                }
            }, 2000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
