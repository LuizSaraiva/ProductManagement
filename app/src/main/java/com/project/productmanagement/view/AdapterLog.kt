package com.project.productmanagement.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.productmanagement.R
import com.project.productmanagement.model.LogProd

class AdapterLog
    (
    val context: Context,
    val listLog: List<LogProd>
) : RecyclerView.Adapter<ViewModelLog>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelLog {
        val view = ViewModelLog(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_log, parent, false)
        )
        return view
    }

    override fun onBindViewHolder(holder: ViewModelLog, position: Int) {
        var itemLog:LogProd = listLog[position]
        with(holder){
            codprod.text = itemLog.codprod.toString()
            date.text = itemLog.date
            qtBef.text = itemLog.qtBef.toString()
            qtAft.text = itemLog.qtAft.toString()
            oper.text = itemLog.oper
        }

    }

    override fun getItemCount(): Int = listLog.size
}

class ViewModelLog(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val date = itemView.findViewById<TextView>(R.id.dt_mov)
    val codprod = itemView.findViewById<TextView>(R.id.codprod_mov)
    val qtBef = itemView.findViewById<TextView>(R.id.qt_bef_mov)
    val qtAft = itemView.findViewById<TextView>(R.id.qt_aft_mov)
    val oper = itemView.findViewById<TextView>(R.id.oper_mov)

}