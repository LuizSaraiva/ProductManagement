package com.project.productmanagement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.productmanagement.ApplicationApp
import com.project.productmanagement.R
import com.project.productmanagement.model.LogProd

class FragmentLog: Fragment() {

    lateinit var rv_log:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log, container, false)

        initComponents(view)

        val listLog = ApplicationApp.instance.helper?.findLog() ?: mutableListOf()
        rv_log.adapter = AdapterLog(view.context,listLog)
        rv_log.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)

        return view
    }

    fun initComponents(view: View){
        rv_log = view.findViewById(R.id.rv_log)
    }

}