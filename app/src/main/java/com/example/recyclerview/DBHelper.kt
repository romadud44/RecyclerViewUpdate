package com.example.recyclerview

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.Serializable

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), Serializable {
    companion object {
        private const val DATABASE_NAME = "WARDROBE_DATABASE"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "wardrobe_table"
        const val KEY_NAME = "name"
        const val KEY_INFO = "info"
        const val KEY_IMAGE = "image"
        const val KEY_ID = "id"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val WARDROBE_TABLE = ("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_INFO + " TEXT, " +
                KEY_IMAGE + " TEXT" + ")")
        db?.execSQL(WARDROBE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addThing(thing: Thing) {
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, thing.id)
        contentValues.put(KEY_NAME, thing.name)
        contentValues.put(KEY_INFO, thing.info)
        contentValues.put(KEY_IMAGE, thing.image)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun removeAll() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    @SuppressLint("Range", "Recycle")
    fun readThing(context: Context): MutableList<Thing> {
        val thingList: MutableList<Thing> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return thingList
        }
        var thingId: Int
        var thingName: String
        var thingInfo: String
        var thingImage: String
        if (cursor.moveToFirst()) {
            do {
                thingId = cursor.getInt(cursor.getColumnIndex("id"))
                thingName = cursor.getString(cursor.getColumnIndex("name"))
                thingInfo = cursor.getString(cursor.getColumnIndex("info"))
                thingImage = cursor.getString(cursor.getColumnIndex("image"))
                val thing = Thing(
                    id = thingId,
                    name = thingName,
                    info = thingInfo,
                    image = thingImage
                )
                thingList.add(thing)
            } while (cursor.moveToNext())

        }
        Toast.makeText(context, "${thingList.size}", Toast.LENGTH_LONG).show()
        return thingList
    }

    fun updateThing(thing: Thing) {
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, thing.id)
        contentValues.put(KEY_NAME, thing.name)
        contentValues.put(KEY_INFO,  thing.info)
        contentValues.put(KEY_IMAGE, thing.image)
        val db = this.writableDatabase
        db.update(TABLE_NAME, contentValues, "id=" + thing.id, null)
        db.close()
    }

    fun deleteThing(thing: Thing) {
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, thing.id)
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=" + thing.id, null)
        db.close()
    }


}