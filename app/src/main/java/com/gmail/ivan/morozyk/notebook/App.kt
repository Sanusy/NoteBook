package com.gmail.ivan.morozyk.notebook

import android.app.Application
import com.github.kittinunf.fuel.core.FuelManager
import com.gmail.ivan.morozyk.notebook.model.data.Note
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class App : Application() {

    companion object {
        var idGenerator = 0
            private set
            get() {
                return field++
            }

        val noteStorage = ArrayList<Note>()

        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    override fun onCreate() {
        super.onCreate()

        FuelManager.instance.basePath = "https://jsonplaceholder.typicode.com"
    }
}