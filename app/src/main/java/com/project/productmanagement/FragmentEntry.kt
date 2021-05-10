package com.project.productmanagement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.productmanagement.model.Stock

class FragmentEntry: Fragment() {

    lateinit var codprod: TextView
    lateinit var qtde: TextView
    lateinit var btnEntry: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry,container, false)

        initComponents(view)

        btnEntry.setOnClickListener {

            val codprodInsert:Int = Integer.parseInt(codprod.text.toString())
            val qtdeInsert:Double = qtde.text.toString().toDouble()
            val dateInsert = "10/06/2021"

            var stock:Stock = Stock(codprodInsert,qtdeInsert,dateInsert,0.0)
            ApplicationApp.instance.helper?.insertStock(stock)

            var intent = Intent(view.context, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    fun initComponents(view: View){
        codprod = view.findViewById(R.id.entry_codprod)
        qtde = view.findViewById(R.id.entry_qtde)
        btnEntry = view.findViewById(R.id.btnEntry)
    }

}