package com.example.examplenavgraphbugproject

import android.app.Activity
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}

val Activity.app: App
    get() = application as App

val Fragment.app: App
    get() = context!!.applicationContext as App
