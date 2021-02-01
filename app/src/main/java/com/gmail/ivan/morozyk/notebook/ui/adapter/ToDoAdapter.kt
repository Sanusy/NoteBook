package com.gmail.ivan.morozyk.notebook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.databinding.ItemToDoBinding
import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import com.gmail.ivan.morozyk.notebook.mvp.presenter.ToDoPresenter

class ToDoAdapter(private val presenter: ToDoPresenter) : RecyclerView.Adapter<ToDoHolder>() {

    private val toDoList = ArrayList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ToDoHolder(
        ItemToDoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), presenter
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
}

class ToDoHolder(private val binding: ItemToDoBinding, private val presenter: ToDoPresenter) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(toDo: ToDo) {
        with(binding) {

            completedButton.setOnClickListener { presenter.markAsCompletedButtonClicked(toDo) }

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