package com.project.productmanagement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.productmanagement.ApplicationApp
import com.project.productmanagement.R
import java.lang.Appendable

class FragmentProductExtract : Fragment() {

    lateinit var rv_product_extract:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_extract, container, false)

        initComponents(view)

        val listStock = ApplicationApp.instance.helper?.findStock(0) ?: mutableListOf()

        rv_product_extract.adapter = AdapterProductExtract(view.context,listStock)
        rv_product_extract.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    fun initComponents(view: View){

        rv_product_extract = view.findViewById(R.id.rv_product_extract)

    }
}

