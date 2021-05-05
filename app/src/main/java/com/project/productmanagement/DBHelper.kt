package com.project.productmanagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.project.productmanagement.model.Product

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    VERSION
) {

    companion object {
        val VERSION: Int = 3
        val DB_NAME = "produts_management.db"

        val TABLE_PRODUCTS_NAME = "products"
        val COLLUM_CODPROD = "codprod"
        val COLLUM_NAME_PROD = "name"
    }

    val TABLE_PRODUTCS_CREATE = "" +
            "CREATE TABLE IF NOT EXISTS $TABLE_PRODUCTS_NAME (" +
            "CODPROD INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT NOT NULL)"

    val TABLE_PRODUTS_DROP = "DROP TABLE IF EXISTS $TABLE_PRODUCTS_NAME"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("CREATEDTABLE", TABLE_PRODUCTS_NAME)
        db?.execSQL(TABLE_PRODUTCS_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db?.execSQL(TABLE_PRODUTS_DROP)
        }
        onCreate(db)
    }

     fun insertProduct(product: Product) {
        var db = writableDatabase

        var content = ContentValues()
        content.put(COLLUM_CODPROD, product.codprod)
        content.put(COLLUM_NAME_PROD, product.name)
        db.insert(TABLE_PRODUCTS_NAME, null, content)
        db.close()
    }

}