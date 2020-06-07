package com.example.testprojectinfinite

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast

import com.example.testprojectinfinite.CustaAdapter.CustomGrid
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList
import java.util.Collections
import java.util.Random

class MainActivity : AppCompatActivity() {


    protected lateinit var dataLis: MutableList<Int>

    protected lateinit var gridView: GridView
    protected lateinit var customGrid: CustomGrid
    protected lateinit var txtselNo: TextView
    protected lateinit var txtPrevNumber: TextView
    internal val min = 1
    internal val max = 90
    protected lateinit var namesArr: Array<Int>

    var selList: MutableList<Int> = ArrayList()

    protected lateinit var txtNewGame: TextView
    internal var startPrev: Boolean? = false
    protected lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.grid)
        txtselNo = findViewById(R.id.txtselNo)
        txtPrevNumber = findViewById(R.id.txtPrevNumber)
        txtNewGame = findViewById(R.id.txtNewGame)
        saveArr = ArrayList()
        sharedPreferences = getSharedPreferences("USER", Context.MODE_PRIVATE)


        //Collections.shuffle(dataLis);
        refreshDta()


        /* customGrid=new CustomGrid(MainActivity.this,dataLis);
        gridView.setAdapter(customGrid);*/







        txtselNo.setOnClickListener {
            try {

                if (selList.size <= 0) {
                    Toast.makeText(this@MainActivity, "Winner Winner Chicken Dinner", Toast.LENGTH_LONG).show()
                    txtNewGame.visibility = View.VISIBLE
                } else {
                    if (startPrev!!) {
                        txtPrevNumber.text = LastSelectedNumber
                    }
                    namesArr = selList.toTypedArray<Int>()
                    val ik = namesArr[Random().nextInt(namesArr.size)]
                    txtselNo.text = ik.toString()
                    LastSelectedNumber = ik.toString()
                    selList.remove(ik)
                    startPrev = true

                    val position = dataLis.indexOf(ik)

                    customGrid.setSelectedPosition(position)

                    customGrid.notifyDataSetChanged()

                    //save data
                    saveArr.add(position.toString())
                    val gson = Gson()
                    val json = gson.toJson(saveArr)
                    val editor = sharedPreferences.edit()
                    editor.putString("Set", json)
                    editor.commit()

                }
            } catch (e: Exception) {
                e.message
            }
        }


        txtNewGame.setOnClickListener {
            txtNewGame.visibility = View.GONE
            refreshDta()
        }

        readData()


    }


    private fun readData() {


        val gson = Gson()
        val json = sharedPreferences.getString("Set", "")
        if (json!!.isEmpty()) {
            Toast.makeText(this@MainActivity, "There is something error", Toast.LENGTH_LONG).show()
        } else {
            val type = object : TypeToken<List<String>>() {

            }.type
            saveArr = gson.fromJson(json, type)
        }
        if (saveArr.size > 0) {

            for (data in saveArr) {
                val position = Integer.valueOf(data)
                customGrid.setSelectedPosition(position)
                customGrid.notifyDataSetChanged()
            }

        } else {
            refreshDta()
        }
    }


    fun refreshDta() {
        txtNewGame.visibility = View.GONE
        txtselNo.text = "__"
        startPrev = false
        txtPrevNumber.text = "__"
        dataLis = ArrayList()
        for (i in 1..90) {
            dataLis.add(i)
            selList.add(i)
        }

        customGrid = CustomGrid(this@MainActivity, dataLis)
        gridView.adapter = customGrid
    }

    companion object {
        var LastSelectedNumber = "__"
        protected lateinit var saveArr: ArrayList<String>
    }


}
