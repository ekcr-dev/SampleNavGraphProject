package com.example.examplenavgraphbugproject


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class IntroViewModel @Inject constructor() : ViewModel() {

    sealed class Action {
        object OnNextClicked : Action()
        object OnSkipClicked : Action()
    }

    val actionLiveData = MutableLiveData<Action>()

}