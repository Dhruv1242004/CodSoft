package com.dhruv.quizapplication.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dhruv.quizapplication.model.FlagsModel
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper

class FlagsDao {

    fun getRandomTenRecords(helper:DatabaseCopyHelper) : ArrayList<FlagsModel> {

        val recordlist = ArrayList<FlagsModel>()
        val database : SQLiteDatabase = helper.writableDatabase
        val cursor : Cursor = database.rawQuery("SELECT * FROM flags ORDER BY RANDOM() LIMIT 10",null)

        val idindex = cursor.getColumnIndex("flag_id")
        val countryNameIndex = cursor.getColumnIndex("country_name")
        val flagNameIndex = cursor.getColumnIndex("flag_name")

        while(cursor.moveToNext()){

            val record = FlagsModel(
                cursor.getInt(idindex),
                cursor.getString(countryNameIndex),
                cursor.getString(flagNameIndex)
            )

            recordlist.add(record)

        }

        cursor.close()

        return recordlist

    }

    fun getRandomThreeRecords(helper:DatabaseCopyHelper,id:Int) : ArrayList<FlagsModel> {

        val recordlist = ArrayList<FlagsModel>()
        val database : SQLiteDatabase = helper.writableDatabase
        val cursor : Cursor = database.rawQuery("SELECT * FROM flags  WHERE flag_id !=? ORDER BY RANDOM() LIMIT 3",
            arrayOf(id.toString()))

        val idindex = cursor.getColumnIndex("flag_id")
        val countryNameIndex = cursor.getColumnIndex("country_name")
        val flagNameIndex = cursor.getColumnIndex("flag_name")

        while(cursor.moveToNext()){

            val record = FlagsModel(
                cursor.getInt(idindex),
                cursor.getString(countryNameIndex),
                cursor.getString(flagNameIndex)
            )

            recordlist.add(record)

        }

        cursor.close()

        return recordlist

    }
}