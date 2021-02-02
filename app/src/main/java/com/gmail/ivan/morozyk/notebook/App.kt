package com.gmail.ivan.morozyk.notebook

import android.app.Application
import com.gmail.ivan.morozyk.notebook.model.data.Note
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class App : Application() {

    companion object {
        var idGenerator = 0
            private set
            get() {
                return field++
            }

        val noteStorage = ArrayList<Note>()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                )
            ).build()
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}