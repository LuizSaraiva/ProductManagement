package com.project.productmanagement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

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