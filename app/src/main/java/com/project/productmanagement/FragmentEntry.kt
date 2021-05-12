package com.project.productmanagement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.project.productmanagement.model.Stock
import java.text.SimpleDateFormat
import java.util.*

class FragmentEntry : Fragment() {

    lateinit var codprod: TextView
    lateinit var qtde: TextView
    lateinit var btnEntry: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        initComponents(view)

        btnEntry.setOnClickListener {

            val codprodInsert: Int = Integer.parseInt(codprod.text.toString())
            val qtdeInsert: Double = qtde.text.toString().toDouble()

            val date = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val dateInsert = dateFormat.format(date)

            var stock: Stock = Stock(codprodInsert, qtdeInsert, dateInsert, 0.0)

            var search = ApplicationApp.instance.helper?.findStock(stock.codprod)

            if (search?.size == 0) {
                ApplicationApp.instance.helper?.insertStock(stock)
                Toast.makeText(activity?.baseContext,getString(R.string.prod_add),Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(activity?.baseContext,getString(R.string.prod_exists),Toast.LENGTH_LONG).show()
            }

            var intent = Intent(view.context, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    fun initComponents(view: View) {
        codprod = view.findViewById(R.id.entry_codprod)
        qtde = view.findViewById(R.id.entry_qtde)
        btnEntry = view.findViewById(R.id.btnEntry)
    }

}