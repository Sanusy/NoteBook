package com.gmail.ivan.morozyk.notebook.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.gmail.ivan.morozyk.notebook.App
import com.squareup.moshi.JsonAdapter

data class ToDoDto(val userId: Int, val id: Int, val title: String, var completed: Boolean) {

    class Deserializer() : ResponseDeserializable<List<ToDoDto>> {
        private val jsonAdapter: JsonAdapter<Array<ToDoDto>> =
            App.moshi.adapter(Array<ToDoDto>::class.java)

        override fun deserialize(content: String): List<ToDoDto> =
            jsonAdapter.fromJson(content)!!.asList()
    }
}
