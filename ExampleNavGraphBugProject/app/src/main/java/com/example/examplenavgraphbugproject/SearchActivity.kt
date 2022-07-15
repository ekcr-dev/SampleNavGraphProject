package com.example.examplenavgraphbugproject

import android.os.Bundle
import android.os.Parcelable
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.examplenavgraphbugproject.databinding.ActivityLocationSearchBinding
import kotlinx.parcelize.Parcelize
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), HasAndroidInjector {

    //TODO: something about the way this is being launched is different

    private lateinit var binding: ActivityLocationSearchBinding

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE)

        getArgs()
    }

    override fun androidInjector() = dispatchingAndroidInjector

    private fun getArgs() {
        supportFragmentManager.fragments.last() as SearchFragment
    }

    companion object {
        const val PARCEL_ARG = "parcel_arg"
    }

    @Parcelize
    data class Parcel(
        val id: String,
        val name: String
    ) : Parcelable
}