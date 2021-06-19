package com.project.productmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.project.productmanagement.model.Product
import com.project.productmanagement.view.FragmentEntry
import com.project.productmanagement.view.FragmentLog
import com.project.productmanagement.view.FragmentPrice
import com.project.productmanagement.view.FragmentProductExtract

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var nav: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: Toolbar

    lateinit var fragEntry: FragmentEntry
    lateinit var fragProductExtract: FragmentProductExtract
    lateinit var fragLog: FragmentLog
    lateinit var fragPrice:FragmentPrice

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
            var search = ApplicationApp.instance.helper?.findProduct(product.codprod)
            println(search)
            if(search?.size == 0){
                ApplicationApp.instance.helper?.insertProduct(product)
            }
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
            R.id.item_product_extract ->{
                fragProductExtract = FragmentProductExtract()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, fragProductExtract)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            R.id.item_log ->{
                fragLog = FragmentLog()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, fragLog)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            R.id.item_price ->{
                fragPrice = FragmentPrice()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, fragPrice)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    val mockProduct = arrayListOf<Product>(
        Product(1, "MACA"),
        Product(2, "CHOCOLATE"),
        Product(3, "BANANA"),
        Product(4, "SUCO")
    )
}