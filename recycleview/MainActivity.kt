package com.example.recycleview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleview.model.UserData
import com.example.recycleview.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<UserData>

    private lateinit var userAdapter:UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /**set List*/
        userList = ArrayList()
        /**Find Id Here*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set Adapter*/
        userAdapter = UserAdapter(this,userList)
        /**set Recycler View Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }


    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item,null)
        /**set view*/
        val addingredient1 = v.findViewById<EditText>(R.id.addingredient1)
        val addingredient2 = v.findViewById<EditText>(R.id.addingredient2)
        val addingredient3 = v.findViewById<EditText>(R.id.addingredient3)
        val addingredient4 = v.findViewById<EditText>(R.id.addingredient4)
        

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            val ingredient1 = addingredient1.text.toString()
            val ingredient2 = addingredient2.text.toString()
            val ingredient3 = addingredient3.text.toString()
            val ingredient4 = addingredient4.text.toString()
            userList.add(
                UserData(
                    "Presets Saved",
                    "Ingredients: $ingredient1, $ingredient2, $ingredient3, $ingredient4",
                    "$ingredient1, $ingredient2, $ingredient3, $ingredient4"
                )
            )


            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding Presets Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

}