package com.gmail.ivan.morozyk.notebook.mvp.presenter

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.awaitResult
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.coroutines.awaitByteArrayResponse
import com.gmail.ivan.morozyk.notebook.App
import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import com.gmail.ivan.morozyk.notebook.model.data.ToDoDto
import com.gmail.ivan.morozyk.notebook.model.mapper.ToDoDtoMapper
import com.gmail.ivan.morozyk.notebook.model.mapper.ToDoMapper
import com.gmail.ivan.morozyk.notebook.mvp.contract.ToDoContract
import kotlinx.coroutines.*
import moxy.MvpPresenter

class ToDoPresenter : MvpPresenter<ToDoContract.View>(), ToDoContract.Presenter {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun attachView(view: ToDoContract.View?) {
        super.attachView(view)

        getToDos()
    }

    private fun getToDos() {
        coroutineScope.launch {
            Fuel.get("/todos").awaitResult(ToDoDto.Deserializer()).fold({ data ->
                if (data.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        viewState.showEmpty()
                    }
                } else {
                    val toDoList = data.map { ToDoDtoMapper().map(it) }
                    withContext(Dispatchers.Main) {
                        viewState.showTODOs(toDoList)
                    }
                }

            }, {
                withContext(Dispatchers.Main) {
                    viewState.showLoadingError()
                }
            })
        }
    }

    override fun markAsCompletedButtonClicked(toDo: ToDo) {
        toDo.completed = true

        val dto = ToDoMapper().map(toDo)
        val jsonAdapter = App.moshi.adapter(ToDoDto::class.java)
        coroutineScope.launch {
            val updateStatus = Fuel.put("/todos/${dto.id}").body(jsonAdapter.toJson(dto))
                .awaitByteArrayResponse().second.isSuccessful
            if (updateStatus) {
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