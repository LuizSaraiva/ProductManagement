package com.project.productmanagement.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.productmanagement.R
import com.project.productmanagement.model.Stock
import org.w3c.dom.Text

class AdapterProductExtract(
    val context: Context,
    val listStockProduct: List<Stock>
) : RecyclerView.Adapter<ViewHolderProductExtract>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProductExtract {
        return ViewHolderProductExtract(
            LayoutInflater.from(context)
                .inflate(R.layout.item_product_extract, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderProductExtract, position: Int) {
        val stock:Stock = listStockProduct[position]

        with(holder){
            productCode.text = stock.codprod.toString()
            qt.text = stock.qtde.toString()
            price.text = stock.punit.toString()
        }
    }

    override fun getItemCount(): Int = listStockProduct.size
}

class ViewHolderProductExtract(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val productCode = itemView.findViewById<TextView>(R.id.item_product_code)
    val qt = itemView.findViewById<TextView>(R.id.item_qt)
    val price = itemView.findViewById<TextView>(R.id.item_price)
}

