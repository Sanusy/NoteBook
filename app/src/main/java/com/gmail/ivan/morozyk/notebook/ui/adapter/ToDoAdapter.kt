package com.gmail.ivan.morozyk.notebook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.databinding.ItemToDoBinding
import com.gmail.ivan.morozyk.notebook.model.data.ToDo

class ToDoAdapter(private val markAsCompletedClicked: (ToDo) -> Unit) :
    RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {

    private val toDoList = ArrayList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ToDoHolder(
        ItemToDoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    override fun getItemCount() = toDoList.size

    fun setTODOs(toDoList: List<ToDo>) {
        this.toDoList.clear()
        this.toDoList.addAll(toDoList)
        notifyDataSetChanged()
    }

    inner class ToDoHolder(private val binding: ItemToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDo: ToDo) {
            with(binding) {

                completedButton.setOnClickListener { markAsCompletedClicked(toDo) }

                userName.text = toDo.userId.toString()
                todoContent.text = toDo.title
                completedButton.setImageResource(
                    if (toDo.completed) {
                        completedButton.isEnabled = false
                        R.drawable.icon_done
                    } else {
                        completedButton.isEnabled = true
                        R.drawable.icon_todo
                    }
                )

            }
        }
    }
}

