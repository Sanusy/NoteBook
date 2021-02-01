package com.gmail.ivan.morozyk.notebook.model

import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ToDoService {

    @GET("todos")
    fun getToDos(): Call<List<ToDo>>

    @PUT("todos/{id}")
    fun updateToDo(@Path("id")toDoId: Int, @Body toDo: ToDo) : Call<ToDo>
}