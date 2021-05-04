package com.project.productmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var nav: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_main)

        nav = findViewById(R.id.nav)
        toolbar = findViewById(R.id.toolbar)
        drawer = findViewById(R.id.drawer_main)


        nav.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_entry -> {
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}