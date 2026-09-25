package com.escom.tarea2.views.ui.section3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.escom.tarea2.views.R
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.FragmentSelectionBinding
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SelectionFragment : Fragment() {

    private var _binding: FragmentSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Casillas de verificación
        binding.cbSimple.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setLastAction("Términos: ${if (isChecked) "Aceptados" else "Rechazados"}")
        }

        binding.cbTriState.addOnCheckedStateChangedListener { _, state ->
            val desc = when (state) {
                MaterialCheckBox.STATE_CHECKED -> "Marcado completo"
                MaterialCheckBox.STATE_INDETERMINATE -> "Indeterminado / Parcial"
                else -> "Desmarcado"
            }
            viewModel.setLastAction("Tri-State: $desc")
        }

        // 2. RadioGroup
        binding.radioGroupLevel.setOnCheckedChangeListener { _, checkedId ->
            val level = when (checkedId) {
                R.id.rbBasic -> "Nivel Básico"
                R.id.rbIntermediate -> "Nivel Intermedio"
                R.id.rbAdvanced -> "Nivel Avanzado"
                else -> "Desconocido"
            }
            viewModel.setLastAction("Nivel seleccionado: $level")
        }

        // 3. Switch
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setLastAction("Notificaciones: ${if (isChecked) "Activadas" else "Desactivadas"}")
        }

        // 4. Slider simple (Conexión cruzada con la Sección 5)
        viewModel.progressValue.observe(viewLifecycleOwner) { progress ->
            binding.sliderSingle.value = progress.toFloat()
            binding.tvSliderValue.text = "Valor de progreso: $progress%"
        }

        binding.sliderSingle.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val intVal = value.toInt()
                binding.tvSliderValue.text = "Valor de progreso: $intVal%"
                viewModel.setProgressValue(intVal)
                viewModel.setLastAction("Progreso modificado desde S3: $intVal% (Impacta Sección 5)")
            }
        }

        // RangeSlider
        binding.rangeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            if (values.size >= 2) {
                binding.tvRangeSliderValue.text = "Rango de valores: ${values[0].toInt()} a ${values[1].toInt()}"
            }
        }

        // 5. Spinner
        val categories = resources.getStringArray(R.array.demo_categories)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerSelection.adapter = spinnerAdapter
        binding.spinnerSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = categories[position]
                viewModel.setSelectedCategory(selected)
                viewModel.setLastAction("Categoría seleccionada en Spinner: $selected")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 6. DatePicker y TimePicker
        var selectedDateStr = ""
        var selectedTimeStr = ""

        binding.btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                selectedDateStr = formatter.format(Date(selection))
                binding.tvDateTimeResult.text = "Fecha: $selectedDateStr | Hora: ${selectedTimeStr.ifEmpty { "Pendiente" }}"
                viewModel.setLastAction("Fecha elegida: $selectedDateStr")
            }
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }

        binding.btnTimePicker.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Selecciona la hora")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                selectedTimeStr = String.format(Locale.getDefault(), "%02d:%02d hrs", timePicker.hour, timePicker.minute)
                binding.tvDateTimeResult.text = "Fecha: ${selectedDateStr.ifEmpty { "Pendiente" }} | Hora: $selectedTimeStr"
                viewModel.setLastAction("Hora elegida: $selectedTimeStr")
            }
            timePicker.show(parentFragmentManager, "TIME_PICKER")
        }

        // 7. Chips de filtro
        binding.chipGroupFilters.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChips = checkedIds.mapNotNull { id ->
                group.findViewById<Chip>(id)?.text?.toString()
            }
            viewModel.setLastAction("Filtros activos: ${selectedChips.joinToString(", ")}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
