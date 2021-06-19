package com.project.productmanagement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.productmanagement.ApplicationApp
import com.project.productmanagement.R
import org.w3c.dom.Text

class FragmentPrice : Fragment() {

    lateinit var codprod: EditText
    lateinit var price: EditText
    lateinit var descProd: TextView
    lateinit var qtd: TextView
    lateinit var btnSearch: Button
    lateinit var btnEntry: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_price, container, false)

        initComponents(view)

        btnSearch.setOnClickListener { applyValues() }


        return view
    }

    private fun initComponents(view: View) {

        codprod = view.findViewById(R.id.edt_codprod)
        price = view.findViewById(R.id.ed_price)
        descProd = view.findViewById(R.id.item_desc_prod)
        qtd = view.findViewById(R.id.item_qt)
        btnEntry = view.findViewById(R.id.btnOk)
        btnSearch = view.findViewById(R.id.btnSearch)
    }

    private fun applyValues() {

        val product =
            ApplicationApp.instance.helper?.findStock(codprod.text.toString().toInt(), true)

        cleanComponents()

        if (product?.size != 0) {
            product?.forEach { prod ->
                descProd.text = prod.name
                qtd.text = prod.qtde.toString()
                price.setText(prod.punit.toString())
            }
        }
    }

    private fun cleanComponents() {
        descProd.text = ""
        qtd.text = ""
    }
}