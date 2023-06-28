package com.example.android.asimov2023.ui.main

import TeacherListFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.android.asimov2023.R
import com.example.android.asimov2023.ui.auth.SignInActivity
import com.google.android.material.navigation.NavigationView

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        val isDirector = intent.getBooleanExtra("IS_DIRECTOR", true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Asimov"
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        if (isDirector) {
            navigationView.inflateMenu(R.menu.nav_menu_director)
        } else {
            navigationView.inflateMenu(R.menu.nav_menu_teacher)
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            if(isDirector){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DashboardDirectorFragment()).commit()
                navigationView.setCheckedItem(R.id.nav_dashboard_director)
            }else
            {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DashboardTeacherFragment()).commit()
                navigationView.setCheckedItem(R.id.nav_dashboard_teacher)
            }

        }
    }

    //Crear fragmento (no activity) para las vistas
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            //director menu
            R.id.nav_dashboard_director -> replaceFragment(DashboardDirectorFragment())

            R.id.nav_announce_director -> replaceFragment(AnnouncementsDirectorFragment())
            R.id.nav_teachers_director-> replaceFragment(TeacherListFragment())
            R.id.nav_courses_director -> replaceFragment(CoursesDirectorFragment())
            R.id.nav_competences_director -> replaceFragment(CompetenceListFragment())

            //teacher menu
            R.id.nav_dashboard_teacher -> replaceFragment(DashboardTeacherFragment())
            R.id.nav_courses_teacher -> replaceFragment(CourseListFragment())
            R.id.nav_competences_teacher-> replaceFragment(CompetenceListFragment())
            R.id.nav_announce_teacher -> replaceFragment(AnnouncementsTeacherFragment())


            R.id.nav_logout -> {Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager =supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}