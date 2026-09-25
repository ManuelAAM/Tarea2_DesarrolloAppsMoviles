package com.escom.tarea2.views.ui.section4

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.escom.tarea2.views.data.CatalogItem
import com.escom.tarea2.views.data.SectionItem
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.FragmentListsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class ListsFragment : Fragment() {

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedCatalogViewModel by activityViewModels()

    private lateinit var verticalAdapter: CatalogListAdapter
    private lateinit var gridAdapter: CatalogGridAdapter
    private lateinit var sectionAdapter: CatalogSectionAdapter
    private lateinit var pagerAdapter: ListsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVerticalList()
        setupGridLayout()
        setupSectionList()
        setupViewPager()
        setupTabs()

        viewModel.items.observe(viewLifecycleOwner) { items ->
            verticalAdapter.updateList(items)
            gridAdapter.updateList(items)

            if (items.isEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE
                binding.rvVerticalList.visibility = View.GONE
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.rvVerticalList.visibility = View.VISIBLE
            }
        }

        binding.btnResetList.setOnClickListener {
            viewModel.resetItems()
            Snackbar.make(binding.root, "Lista restaurada con elementos iniciales", Snackbar.LENGTH_SHORT).show()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
                viewModel.setLastAction("Lista actualizada mediante Pull-to-Refresh")
                Snackbar.make(binding.root, "Colección recargada con éxito", Snackbar.LENGTH_SHORT).show()
            }, 1200)
        }
    }

    private fun setupVerticalList() {
        verticalAdapter = CatalogListAdapter(mutableListOf()) { item ->
            showDetailDialog(item)
        }
        binding.rvVerticalList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvVerticalList.adapter = verticalAdapter

        // Swipe to Dismiss con ItemTouchHelper
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removedItem = verticalAdapter.getItem(position)
                viewModel.removeItem(removedItem)

                Snackbar.make(binding.root, "Se eliminó '${removedItem.title}'", Snackbar.LENGTH_LONG)
                    .setAction("Deshacer") {
                        viewModel.restoreItem(position, removedItem)
                    }
                    .show()
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.rvVerticalList)
    }

    private fun setupGridLayout() {
        gridAdapter = CatalogGridAdapter(emptyList()) { item ->
            showDetailDialog(item)
        }
        binding.rvGrid.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGrid.adapter = gridAdapter
    }

    private fun setupSectionList() {
        val sectionItems = listOf(
            SectionItem("CONTROLES DE TEXTO", isHeader = true),
            SectionItem("EditText Estándar", false, "Entrada alfanumérica básica", "Básico"),
            SectionItem("Input con Validación", false, "Mensajes de error reactivos", "Avanzado"),
            SectionItem("BOTONES Y DISPARADORES", isHeader = true),
            SectionItem("MaterialButton Relleno", false, "Alto énfasis de acción", "Primario"),
            SectionItem("Extended FAB", false, "Flotante con icono y rótulo", "Elevado"),
            SectionItem("COLECCIONES Y VISTAS", isHeader = true),
            SectionItem("RecyclerView", false, "Reciclaje de memoria de vistas", "Lista"),
            SectionItem("GridLayout", false, "Disposición matricial", "Mosaico")
        )
        sectionAdapter = CatalogSectionAdapter(sectionItems) { item ->
            AlertDialog.Builder(requireContext())
                .setTitle(item.title)
                .setMessage("Detalle del elemento de sección: ${item.description}")
                .setPositiveButton("Cerrar", null)
                .show()
        }
        binding.rvSections.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSections.adapter = sectionAdapter
    }

    private fun setupViewPager() {
        val pages = listOf(
            Pair("Pestaña 1: Arquitectura de Vistas", "Android Views procesa layouts XML en tiempo de inflado formando un árbol jerárquico de View y ViewGroup."),
            Pair("Pestaña 2: Reciclaje Eficiente", "RecyclerView reutiliza instancias de ViewHolder para renderizar listas infinitas sin agotar memoria."),
            Pair("Pestaña 3: Gestos y Deslizamientos", "ViewPager2 está construido sobre RecyclerView, permitiendo transiciones suaves y gestos táctiles naturales.")
        )
        pagerAdapter = ListsPagerAdapter(pages)
        binding.viewPagerLists.adapter = pagerAdapter
    }

    private fun setupTabs() {
        binding.tabLayoutLists.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.swipeRefreshLayout.visibility = View.VISIBLE
                        binding.rvGrid.visibility = View.GONE
                        binding.rvSections.visibility = View.GONE
                        binding.viewPagerLists.visibility = View.GONE
                    }
                    1 -> {
                        binding.swipeRefreshLayout.visibility = View.GONE
                        binding.rvGrid.visibility = View.VISIBLE
                        binding.rvSections.visibility = View.GONE
                        binding.viewPagerLists.visibility = View.GONE
                    }
                    2 -> {
                        binding.swipeRefreshLayout.visibility = View.GONE
                        binding.rvGrid.visibility = View.GONE
                        binding.rvSections.visibility = View.VISIBLE
                        binding.viewPagerLists.visibility = View.GONE
                    }
                    3 -> {
                        binding.swipeRefreshLayout.visibility = View.GONE
                        binding.rvGrid.visibility = View.GONE
                        binding.rvSections.visibility = View.GONE
                        binding.viewPagerLists.visibility = View.VISIBLE
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun showDetailDialog(item: CatalogItem) {
        AlertDialog.Builder(requireContext())
            .setTitle(item.title)
            .setMessage("ID: ${item.id}\nCategoría: ${item.category}\n\nDescripción:\n${item.description}")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setPositiveButton("Aceptar") { dialog, _ ->
                viewModel.setLastAction("Inspección de detalle: ${item.title}")
                dialog.dismiss()
            }
            .setNegativeButton("Cerrar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
