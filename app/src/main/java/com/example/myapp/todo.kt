package com.example.myapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class todo : Fragment(),CellClickListener {

    companion object {
        fun newInstance() = todo()
    }

    private lateinit var viewModel: TodoViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_todo, container, false)

        var addButton = root.findViewById<FloatingActionButton>(R.id.addButton)

        var clearButton = root.findViewById<FloatingActionButton>(R.id.clearButton)

        var db = AppDatabase.getDatabase(root.context)

        var progressBar = root.findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.visibility = View.GONE


        getData(db,root)

        addButton.setOnClickListener{
            var builder = AlertDialog.Builder(root.context)
            val inflater= layoutInflater
            val dialogLayout = inflater.inflate(R.layout.add_dialog,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.toDoName)

            with(builder)
            {
                setTitle("Внесение задачи")
                setPositiveButton("Ок"){
                    dialog,which ->
                    var toDo = toDoData(name = editText.text.toString(), completed = false)
                    lifecycleScope.launch(Dispatchers.IO){
                        db.toDoDao().insertAll(toDo)
                        withContext(Dispatchers.Main)
                        {
                            getData(db,root)
                        }
                    }
                    println("Данные сохранены")
                }
                setNegativeButton("Отмена")
                {
                    dialog, which -> println("cancel dialog")
                }
                setView(dialogLayout)
                show()
            }
        }

        clearButton.setOnClickListener{
            var builder = AlertDialog.Builder(root.context)
            val inflater= layoutInflater
            val dialogLayout = inflater.inflate(R.layout.alert_dialog,null)

            with(builder)
            {
                setTitle("Подтвердите действие")
                setPositiveButton("Ок"){
                        dialog,which ->
                    lifecycleScope.launch(Dispatchers.IO){
                        db.toDoDao().clearTable()
                        withContext(Dispatchers.Main)
                        {
                            getData(db,root)
                        }
                    }
                }
                setNegativeButton("Отмена")
                {
                        dialog, which -> println("cancel dialog")
                }
                setView(dialogLayout)
                show()
            }
        }


        return root

    }

    private fun getData(db: AppDatabase, root:View)
    {
        var progressBar = root.findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.visibility = View.VISIBLE
        var list: List<toDoData>

        lifecycleScope.launch(Dispatchers.IO){

            list = db.toDoDao().getAll()

            withContext(Dispatchers.Main)
            {
                var adapter = ToDoRecyclerAdapter(requireActivity(),list, this@todo)
                var toDoRecycler = root.findViewById<RecyclerView>(R.id.todoRecycler)
                toDoRecycler.adapter = adapter
                toDoRecycler.layoutManager = LinearLayoutManager(requireActivity())
                progressBar.visibility = View.GONE
            }
        }

    }

    override fun onCellClickListener(data: toDoData) {

        var db = AppDatabase.getDatabase(requireActivity())
            data.completed = data.completed != true
            db.toDoDao().update(data)

        getData(db,requireView())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        // TODO: Use the ViewModel
    }


}

interface CellClickListener {
    fun onCellClickListener(data: toDoData)
}