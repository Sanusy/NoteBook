package com.gmail.ivan.morozyk.notebook.mvp.presenter

import com.gmail.ivan.morozyk.notebook.App
import com.gmail.ivan.morozyk.notebook.model.ToDoService
import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import com.gmail.ivan.morozyk.notebook.model.mapper.ToDoDtoMapper
import com.gmail.ivan.morozyk.notebook.model.mapper.ToDoMapper
import com.gmail.ivan.morozyk.notebook.mvp.contract.ToDoContract
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import moxy.MvpPresenter

class ToDoPresenter : MvpPresenter<ToDoContract.View>(), ToDoContract.Presenter {

    private val toDoService = App.retrofit.create(ToDoService::class.java)

    private val coroutineScope = MainScope()

    override fun attachView(view: ToDoContract.View?) {
        super.attachView(view)

        getToDos()
    }

    private fun getToDos() {

        coroutineScope.launch {
            toDoService.getToDos().let {
                if (it.isNullOrEmpty()) {
                    viewState.showEmpty()
                } else {
                    val mapper = ToDoDtoMapper()
                    viewState.showTODOs(it.map { toDoDto -> mapper.map(toDoDto) })
                }
            }
        }
    }

    override fun markAsCompletedButtonClicked(toDo: ToDo) {
        toDo.completed = true

        coroutineScope.launch {
            if (toDoService.updateToDo(toDo.id, ToDoMapper().map(toDo)).isSuccessful) {
                getToDos()
            } else {
                toDo.completed = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        coroutineScope.cancel()
    }
}