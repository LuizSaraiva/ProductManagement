package com.project.productmanagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
        val VERSION: Int = 11
        val DB_NAME = "products_management.db"
        val DB_PRAGMA_FOREIGN_KEY = "PRAGMA FOREIGN_KEYS = ON;"

        //TABLES
        val TABLE_PRODUCTS_NAME = "products"
        val TABLE_STOCK_NAME = "stock"
        val TABLE_LOGMOV_NAME = "logmov"

        //TRIGGERS NAME
        val TRG_MOV_INSERT_NAME = "TRG_LOGMOV_INSERT"
        val TRG_MOV_UPDATE_NAME = "TRG_LOGMOV_UPDATE"
        val TRG_INSERT_STOCK = "TRG_INSERT_STOCK"

        //COLLUM
        val COLLUM_CODPROD = "codprod"
        val COLLUM_NAME_PROD = "name"
        val COLLUM_QTDE = "qtde"
        val COLLUM_DATE = "date"
        val COLLUM_OPER = "oper"
        val COLLUM_QTDE_BEFORE = "qtdbef"
        val COLLUM_QTDE_AFTER = "qtdaft"
        val COLLUM_NAME = "name"
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

    val TABLE_LOGMOV_CREATE = "" +
            "CREATE TABLE IF NOT EXISTS $TABLE_LOGMOV_NAME (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLLUM_OPER TEXT NOT NULL," +
            "$COLLUM_CODPROD INTEGER NOT NULL," +
            "$COLLUM_QTDE_BEFORE REAL NOT NULL," +
            "$COLLUM_QTDE_AFTER REAL NOT NULL," +
            "$COLLUM_DATE TEXT NOT NULL)"

    //DROP
    val TABLE_PRODUTS_DROP = "DROP TABLE IF EXISTS $TABLE_PRODUCTS_NAME"
    val TABLE_STOCK_DROP = "DROP TABLE IF EXISTS $TABLE_STOCK_NAME"
    val TABLE_LOGMOV_DROP = "DROP TABLE IF EXISTS $TABLE_LOGMOV_NAME"

    //TRIGGER
    val TRG_LOGMOV_INSERT_CREATE = "" +
            "CREATE TRIGGER IF NOT EXISTS $TRG_MOV_INSERT_NAME " +
            "AFTER INSERT ON $TABLE_STOCK_NAME " +
            "" +
            "BEGIN " +
            "INSERT INTO $TABLE_LOGMOV_NAME (" +
            "$COLLUM_OPER," +
            "$COLLUM_CODPROD," +
            "$COLLUM_QTDE_BEFORE," +
            "$COLLUM_QTDE_AFTER," +
            "$COLLUM_DATE" +
            ") VALUES (" +
            "'E'," +
            "NEW.$COLLUM_CODPROD," +
            "0," +
            "NEW.$COLLUM_QTDE," +
            "DATETIME('NOW')" +
            ");" +
            "END;"

    val TRG_INSERT_STOCK_CREATE = "" +
            "CREATE TRIGGER IF NOT EXISTS $TRG_INSERT_STOCK " +
            "AFTER INSERT ON $TABLE_PRODUCTS_NAME " +
            "" +
            "BEGIN " +
            "INSERT INTO $TABLE_STOCK_NAME (" +
            "$COLLUM_CODPROD," +
            "$COLLUM_QTDE," +
            "$COLLUM_DATE" +
            ") VALUES (" +
            "NEW.$COLLUM_CODPROD," +
            "0," +
            "DATETIME('NOW')" +
            ");" +
            "END;"


    val TRG_LOGMOV_UPDATE_CREATE = "" +
            "CREATE TRIGGER IF NOT EXISTS $TRG_MOV_UPDATE_NAME " +
            "BEFORE UPDATE ON $TABLE_STOCK_NAME " +
            "" +
            "BEGIN " +
            "INSERT INTO $TABLE_LOGMOV_NAME (" +
            "$COLLUM_OPER," +
            "$COLLUM_CODPROD," +
            "$COLLUM_QTDE_BEFORE," +
            "$COLLUM_QTDE_AFTER," +
            "$COLLUM_DATE" +
            ") VALUES (" +
            "CASE WHEN OLD.$COLLUM_QTDE > NEW.$COLLUM_QTDE THEN 'S' ELSE 'E' END," +
            "OLD.$COLLUM_CODPROD," +
            "OLD.$COLLUM_QTDE," +
            "NEW.$COLLUM_QTDE," +
            "DATETIME('NOW')" +
            ");" +
            "END;"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TABLE_PRODUTCS_CREATE)
        db?.execSQL(TABLE_STOCK_CREATE)
        db?.execSQL(TABLE_LOGMOV_CREATE)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL(DB_PRAGMA_FOREIGN_KEY)
        db?.execSQL(TRG_LOGMOV_INSERT_CREATE)
        db?.execSQL(TRG_LOGMOV_UPDATE_CREATE)
        db?.execSQL(TRG_INSERT_STOCK_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(TABLE_PRODUTS_DROP)
            db?.execSQL(TABLE_STOCK_DROP)
            db?.execSQL(TABLE_LOGMOV_DROP)
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
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } catch (ex: SQLiteConstraintException) {
            ex.printStackTrace()
        } finally {
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
                db.query(TABLE_PRODUCTS_NAME, null, where, args, null, null, null)
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
        } finally {
            db.close()
        }
        return listProducts
    }

    fun findStock(codprod: Int? = null, findId: Boolean = false): List<Stock> {
        val db = readableDatabase

        val listStock = mutableListOf<Stock>()
        var where: String? = null
        var args: Array<String> = arrayOf()

        val query: String

        if (findId) {
            query = "SELECT $TABLE_PRODUCTS_NAME.NAME, $TABLE_STOCK_NAME.* " +
                    "FROM $TABLE_STOCK_NAME , $TABLE_PRODUCTS_NAME " +
                    "WHERE $TABLE_PRODUCTS_NAME.$COLLUM_CODPROD = $TABLE_STOCK_NAME.$COLLUM_CODPROD " +
                    "AND $COLLUM_CODPROD = ?"
            args = arrayOf("$codprod")
        } else {
            query = "SELECT $TABLE_PRODUCTS_NAME.NAME, $TABLE_STOCK_NAME.* " +
                    "FROM $TABLE_STOCK_NAME , $TABLE_PRODUCTS_NAME " +
                    "WHERE $TABLE_PRODUCTS_NAME.$COLLUM_CODPROD = $TABLE_STOCK_NAME.$COLLUM_CODPROD "
        }

        try {

            val cursor = db.rawQuery(query, args, null)

            if (cursor == null) {
                db.close()
                return mutableListOf()
            }
            while (cursor.moveToNext()) {

                var stock = Stock(
                    cursor.getInt(cursor.getColumnIndex(COLLUM_CODPROD)),
                    cursor.getString(cursor.getColumnIndex(COLLUM_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(COLLUM_QTDE)),
                    cursor.getString(cursor.getColumnIndex(COLLUM_DATE)),
                    0.0
                )
                listStock.add(stock)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return listStock
    }

    fun updateStock(stock: Stock) {

        val itemStock = findStock(stock.codprod)
        val stockBef = itemStock[0].qtde
        val stockNew = stockBef + stock.qtde
        val db = writableDatabase

        val where = "$COLLUM_CODPROD = ?"
        val args = arrayOf<String>("${stock.codprod}")

        try {
            val content = ContentValues()
            content.put(COLLUM_QTDE, stockNew)
            content.put(COLLUM_DATE, stock.date)
            db.update(TABLE_STOCK_NAME, content, where, args)
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            db.close()
        }
    }
}