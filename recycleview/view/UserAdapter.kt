package com.example.recycleview.view

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleview.R
import com.example.recycleview.model.UserData

class UserAdapter(val c:Context,val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        var mMenus:ImageView

        init {
            name = v.findViewById<TextView>(R.id.mTitle)
            mbNum = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus =  v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c.applicationContext,v)
            popupMenus.inflate(R.menu.show_menu)

            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val dialogView = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val name1 = dialogView.findViewById<EditText>(R.id.addingredient1)
                        val name2 = dialogView.findViewById<EditText>(R.id.addingredient2)
                        val name3= dialogView.findViewById<EditText>(R.id.addingredient3)
                        val name4 = dialogView.findViewById<EditText>(R.id.addingredient4)


                        val ingredients = position.useringredients.split(", ")
                        name1.setText(ingredients.getOrNull(0)?.trim() ?: "")
                        name2.setText(ingredients.getOrNull(1)?.trim() ?: "")
                        name3.setText(ingredients.getOrNull(2)?.trim() ?: "")
                        name4.setText(ingredients.getOrNull(3)?.trim() ?: "")





                        AlertDialog.Builder(c)
                                .setView(dialogView)
                                .setPositiveButton("ok"){
                                    dialog,_->



                                    val ingredient1 = name1.text.toString().trim()
                                    val ingredient2 = name2.text.toString().trim()
                                    val ingredient3 = name3.text.toString().trim()
                                    val ingredient4 = name4.text.toString().trim()

                                    position.userMb = "Ingredients: $ingredient1, $ingredient2, $ingredient3, $ingredient4"


                                    position.useringredients = "$ingredient1, $ingredient2, $ingredient3, $ingredient4"

                                    Log.d("UserAdapter", "Updated User Ingredients: ${position.useringredients}")

                                    notifyDataSetChanged()
                                    Toast.makeText(c,"Presets Has Been Updated",Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                                .setNegativeButton("Cancel"){
                                    dialog,_->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure you want to delete this Presets?")
                            .setPositiveButton("Yes"){
                                dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted Presets",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.userName
        holder.mbNum.text = newList.userMb
    }

    override fun getItemCount(): Int {
        return userList.size

    }
}