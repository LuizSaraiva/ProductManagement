package com.project.productmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.project.productmanagement.model.Product

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var nav: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: Toolbar

    lateinit var fragEntry: FragmentEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_main)

        initComponents()

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

        mockProduct.forEach { product ->
            ApplicationApp.instance.helper?.insertProduct(product)
        }


    }

    private fun initComponents() {
        nav = findViewById(R.id.nav)
        toolbar = findViewById(R.id.toolbar)
        drawer = findViewById(R.id.drawer_main)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_entry -> {
                fragEntry = FragmentEntry()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, fragEntry)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    val mockProduct = arrayListOf<Product>(
        Product(1, "MACA"),
        Product(1, "CHOCOLATE"),
        Product(1, "BANANA")
    )


}