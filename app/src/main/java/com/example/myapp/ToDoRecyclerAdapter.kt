package com.example.myapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.graphics.Paint
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.api.Article

class ToDoRecyclerAdapter(val context: Context, val list: List<toDoData>, private val cellClickListener: CellClickListener):RecyclerView.Adapter<ToDoRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        val tvtodo: TextView = view.findViewById(R.id.tvToDo)
    }
    val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.todo_recycleritem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtodo.text = list[position].name.toString()
        if(list[position].completed == true)
        {
            holder.tvtodo.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        val prop = list[position]
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(prop)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}