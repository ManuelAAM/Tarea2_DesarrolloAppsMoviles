package com.escom.tarea2.views.ui.section5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.escom.tarea2.views.R
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.BottomSheetDemoBinding
import com.escom.tarea2.views.databinding.FragmentFeedbackBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Imagen remota con Coil y modos de escalado
        binding.ivRemote.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png") {
            crossfade(true)
            placeholder(R.drawable.ic_sample_image)
            error(R.drawable.ic_empty_box)
        }

        // 3. Indicadores de progreso vinculados con la Sección 3
        viewModel.progressValue.observe(viewLifecycleOwner) { progress ->
            binding.tvProgressLabel.text = "Progreso Lineal Determinado ($progress% sincronizado desde S3):"
            binding.progressLinearDetermined.progress = progress
            binding.progressCircularDetermined.progress = progress
        }

        // 4. Toast y Snackbar
        binding.btnShowToast.setOnClickListener {
            Toast.makeText(requireContext(), "Mensaje Toast breve (no interactivo)", Toast.LENGTH_SHORT).show()
            viewModel.setLastAction("Toast desplegado")
        }

        binding.btnShowSnackbar.setOnClickListener {
            Snackbar.make(binding.root, "Elemento archivado temporalmente", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    Toast.makeText(requireContext(), "Acción deshecha con éxito", Toast.LENGTH_SHORT).show()
                }
                .show()
            viewModel.setLastAction("Snackbar con acción mostrado")
        }

        // 5. Diálogo de confirmación
        binding.btnShowDialog.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmación de Acción")
                .setMessage("¿Deseas sincronizar todos los componentes del catálogo con el repositorio central?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Aceptar") { _, _ ->
                    viewModel.setLastAction("Diálogo: Confirmación aceptada")
                    Snackbar.make(binding.root, "Operación confirmada", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    viewModel.setLastAction("Diálogo: Operación cancelada")
                    dialog.dismiss()
                }
                .show()
        }

        // 6. Hoja Inferior (Modal BottomSheet)
        binding.btnShowBottomSheet.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val sheetBinding = BottomSheetDemoBinding.inflate(layoutInflater)
            bottomSheetDialog.setContentView(sheetBinding.root)

            sheetBinding.btnDismissSheet.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
            viewModel.setLastAction("BottomSheet modal abierto")
        }

        // Badge reactivo según la cantidad de items
        viewModel.items.observe(viewLifecycleOwner) { items ->
            binding.tvBadgeCounter.text = "${items.size} Registros"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
