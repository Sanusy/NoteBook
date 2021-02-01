package com.gmail.ivan.morozyk.notebook.mvp.presenter

import android.util.Log
import com.gmail.ivan.morozyk.notebook.model.App
import com.gmail.ivan.morozyk.notebook.model.ToDoService
import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import com.gmail.ivan.morozyk.notebook.mvp.contract.ToDoContract
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToDoPresenter : MvpPresenter<ToDoContract.View>(), ToDoContract.Presenter {

    private val toDoService = App.retrofit.create(ToDoService::class.java)

    override fun attachView(view: ToDoContract.View?) {
        super.attachView(view)

        getToDos()
    }

    private fun getToDos() {
        toDoService.getToDos().enqueue(object : Callback<List<ToDo>> {
            override fun onResponse(call: Call<List<ToDo>>, response: Response<List<ToDo>>) {
                if (response.isSuccessful) {
                    with(viewState) {
                        if (response.body().isNullOrEmpty()) showEmpty()
                        else showTODOs(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<ToDo>>, t: Throwable) {
                Log.e(TAG, "Failed to get TODO list!", t)
                viewState.showLoadingError()
            }
        })
    }

    override fun markAsCompletedButtonClicked(toDo: ToDo) {
        toDo.completed = true
        toDoService.updateToDo(toDo.id, toDo).enqueue(object : Callback<ToDo> {
            override fun onResponse(call: Call<ToDo>, response: Response<ToDo>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: Updated successfully")
                    getToDos()
                }
            }

            override fun onFailure(call: Call<ToDo>, t: Throwable) {
                Log.e(TAG, "Failed to update ToDo", t)
                viewState.showUpdateError()
            }

        })
    }

    companion object {
        private val TAG = ToDoPresenter::class.qualifiedName
    }
}