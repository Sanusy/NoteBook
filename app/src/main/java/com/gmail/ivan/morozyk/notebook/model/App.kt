package com.gmail.ivan.morozyk.notebook.model

import android.app.Application
import com.gmail.ivan.morozyk.notebook.model.data.Note
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        var idGenerator = 0
            private set
            get() {
                return field++
            }

        val noteStorage = ArrayList<Note>()

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(
            GsonConverterFactory.create()).build()
    }
}