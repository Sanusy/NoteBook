package com.gmail.ivan.morozyk.notebook.model

import com.gmail.ivan.morozyk.notebook.model.data.ToDoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ToDoService {

    @GET("todos")
    suspend fun getToDos(): List<ToDoDto>

    @PUT("todos/{id}")
    suspend fun updateToDo(@Path("id") toDoId: Int, @Body toDo: ToDoDto): Response<ToDoDto>
}