package com.example.examplenavgraphbugproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examplenavgraphbugproject.RxUtils.applyLoadingStates
import com.example.examplenavgraphbugproject.RxUtils.applySchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.IOException
import javax.inject.Inject

class SelectDrViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider,
) : ViewModel() {

    private val _locationDrsLiveData = MutableLiveData<List<Doctor>>()
    val locationDrsLiveData: LiveData<List<Doctor>> = _locationDrsLiveData

    val commandLiveData = MutableLiveData<Command>()

    sealed class Command {
        object OnDrSelected: Command()
    }

    val disposable = CompositeDisposable()

    var selectedDr: Doctor? = null
        set(value) {
            field = value
            syncData()
        }

    fun getDrs() {
        disposable.add(
            repository.getDrs()
                .compose(applySchedulers(schedulerProvider))
                .subscribe({
                    handleResult(it)
                }, {
                    throw IOException("Something went wrong :(")
                })
        )
    }

    private fun handleResult(result: List<Doctor>) {
        _locationDrsLiveData.value = result
    }

    private fun syncData() {
        disposable.add(
            repository.setDr(selectedDr)
                .compose(applySchedulers(schedulerProvider))
                .subscribe(
                    {
                    },
                    {
                        throw IOException("Something went wrong :(")

                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
