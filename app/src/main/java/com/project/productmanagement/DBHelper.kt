package com.project.productmanagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.project.productmanagement.model.Product
import com.project.productmanagement.model.Stock
import java.sql.SQLException

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    VERSION
) {

    companion object {
        val VERSION: Int = 8
        val DB_NAME = "products_management.db"
        val DB_PRAGMA_FOREIGN_KEY = "PRAGMA FOREIGN_KEYS = ON;"

        //TABLES
        val TABLE_PRODUCTS_NAME = "products"
        val TABLE_STOCK_NAME = "stock"

        //COLLUM
        val COLLUM_CODPROD = "codprod"
        val COLLUM_NAME_PROD = "name"
        val COLLUM_QTDE = "qtde"
        val COLLUM_DATE = "date"
    }

    //CREATE
    val TABLE_PRODUTCS_CREATE = "" +
            "CREATE TABLE IF NOT EXISTS $TABLE_PRODUCTS_NAME (" +
            "$COLLUM_CODPROD INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "$COLLUM_NAME_PROD TEXT NOT NULL)"

    val TABLE_STOCK_CREATE = "" +
            "CREATE TABLE IF NOT EXISTS $TABLE_STOCK_NAME (" +
            "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "$COLLUM_CODPROD INTEGER NOT NULL UNIQUE," +
            "$COLLUM_QTDE REAL NOT NULL DEFAULT 0," +
            "$COLLUM_DATE TEXT NOT NULL," +
            "FOREIGN KEY ($COLLUM_CODPROD) REFERENCES $TABLE_PRODUCTS_NAME($COLLUM_CODPROD)" +
            ")"

    //DROP
    val TABLE_PRODUTS_DROP = "DROP TABLE IF EXISTS $TABLE_PRODUCTS_NAME"
    val TABLE_STOCK_DROP = "DROP TABLE IF EXISTS $TABLE_STOCK_NAME"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TABLE_PRODUTCS_CREATE)
        db?.execSQL(TABLE_STOCK_CREATE)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL(DB_PRAGMA_FOREIGN_KEY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(TABLE_PRODUTS_DROP)
            db?.execSQL(TABLE_STOCK_DROP)
        }
        onCreate(db)
    }

    fun insertProduct(product: Product) {
        var db = writableDatabase

        try {
            var content = ContentValues()
            content.put(COLLUM_CODPROD, product.codprod)
            content.put(COLLUM_NAME_PROD, product.name)
            db.insert(TABLE_PRODUCTS_NAME, null, content)
        }catch (ex: SQLException){
            ex.printStackTrace()
        }catch (ex: SQLiteConstraintException){
            ex.printStackTrace()
        }finally {
            db.close()
        }
    }

    fun findProduct(idProduct: Int): List<Product> {
        var db = readableDatabase

        var listProducts = mutableListOf<Product>()
        var where = "$COLLUM_CODPROD = ?"
        var args = arrayOf("$idProduct")

        try {

            var cursor =
                db.query(TABLE_PRODUCTS_NAME, null,where, args, null, null, null)
            if (cursor == null) {
                db.close()
                return mutableListOf()
            }
            while (cursor.moveToNext()) {
                var product = Product(
                    cursor.getInt(cursor.getColumnIndex(COLLUM_CODPROD)),
                    cursor.getString(cursor.getColumnIndex(COLLUM_NAME_PROD))
                )
                listProducts.add(product)
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }

        finally {
            db.close()
        }
        return listProducts
    }

    fun insertStock(stock: Stock){
        var db = writableDatabase

        try{
            var content = ContentValues()
            content.put(COLLUM_CODPROD, stock.codprod)
            content.put(COLLUM_QTDE, stock.qtde)
            content.put(COLLUM_DATE, stock.date)
            db.insertOrThrow(TABLE_STOCK_NAME,null, content)
        }catch (ex: SQLException){
            ex.printStackTrace()
        }catch (ex: SQLiteConstraintException){
            ex.printStackTrace()
        }
        finally {
            db.close()
        }
    }

    fun findStock(codprod:Int) : List<Stock>{
        val db = readableDatabase

        val listStock = mutableListOf<Stock>()
        val where = "$COLLUM_CODPROD = ?"
        val args = arrayOf("$codprod")

        try {
            val cursor = db.query(TABLE_STOCK_NAME,null,where, args,null, null,null)

            if(cursor == null){
                db.close()
                return mutableListOf()
            }
            while (cursor.moveToNext()){

                var stock = Stock(
                    cursor.getInt(cursor.getColumnIndex(COLLUM_CODPROD)),
                    cursor.getDouble(cursor.getColumnIndex(COLLUM_QTDE)),
                    cursor.getString(cursor.getColumnIndex(COLLUM_DATE)),
                    0.0
                )
                listStock.add(stock)
            }

        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return listStock
    }
}