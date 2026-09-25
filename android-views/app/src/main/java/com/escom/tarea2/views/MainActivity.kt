package com.escom.tarea2.views

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.escom.tarea2.views.data.SharedCatalogViewModel
import com.escom.tarea2.views.databinding.ActivityMainBinding
import com.escom.tarea2.views.ui.home.HomeFragment
import com.escom.tarea2.views.ui.section1.TextInputsFragment
import com.escom.tarea2.views.ui.section2.ButtonsFragment
import com.escom.tarea2.views.ui.section3.SelectionFragment
import com.escom.tarea2.views.ui.section4.ListsFragment
import com.escom.tarea2.views.ui.section5.FeedbackFragment
import com.escom.tarea2.views.ui.section6.ContainersFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SharedCatalogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_theme -> {
                    toggleTheme()
                    true
                }
                R.id.action_info -> {
                    showAboutDialog()
                    true
                }
                else -> false
            }
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            navigateTo(menuItem.itemId)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        if (savedInstanceState == null) {
            navigateTo(R.id.menu_home)
        }
    }

    fun navigateTo(menuId: Int) {
        val (fragment, title) = when (menuId) {
            R.id.menu_home -> Pair(HomeFragment(), "Catálogo UI (Inicio)")
            R.id.menu_text_inputs -> Pair(TextInputsFragment(), "1. Entrada de Texto")
            R.id.menu_buttons -> Pair(ButtonsFragment(), "2. Botones y Acciones")
            R.id.menu_selection -> Pair(SelectionFragment(), "3. Elementos de Selección")
            R.id.menu_lists -> Pair(ListsFragment(), "4. Listas y Colecciones")
            R.id.menu_feedback -> Pair(FeedbackFragment(), "5. Retroalimentación")
            R.id.menu_containers -> Pair(ContainersFragment(), "6. Contenedores")
            else -> Pair(HomeFragment(), "Catálogo UI (Inicio)")
        }

        binding.topAppBar.title = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        binding.navigationView.setCheckedItem(menuId)
    }

    private fun toggleTheme() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Información Institucional")
            .setMessage(
                "Instituto Politécnico Nacional\n" +
                "Escuela Superior de Cómputo (ESCOM)\n\n" +
                "Materia: Desarrollo de Aplicaciones Móviles Nativas\n" +
                "Semestre: 2027-1\n" +
                "Tarea 2: Catálogo de Elementos Básicos de Interfaz de Usuario\n\n" +
                "Alumno: Aragón Martínez Manuel Alejandro\n" +
                "Boleta: 2023630411\n" +
                "Grupo: 7CV4\n" +
                "Profesor: Gabriel Hurtado Avilés"
            )
            .setPositiveButton("Aceptar", null)
            .show()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment !is HomeFragment) {
                navigateTo(R.id.menu_home)
            } else {
                super.onBackPressed()
            }
        }
    }
}
