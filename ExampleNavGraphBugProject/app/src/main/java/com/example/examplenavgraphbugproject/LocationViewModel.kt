package com.example.examplenavgraphbugproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examplenavgraphbugproject.RxUtils.applyLoadingStates
import com.example.examplenavgraphbugproject.RxUtils.applySchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.IOException
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(){

    private val disposable = CompositeDisposable()

    sealed class Command {
        object NavigateToNext : Command()
        class NavigateToSelectDr(val selectedLocationId: String) : Command()
    }

    fun navigateToNext() {
        commandLiveData.value = Command.NavigateToNext
    }

    val commandLiveData = MutableLiveData<Command>()

    var selectedLocationName: String? = null
    var selectedLocationId: String? = null
        set(value) {
            field = value
            field?.let { syncLocation(it) }
        }

    private fun syncLocation(locationId: String?) {
        disposable.add(
            repository.setLocationAndDr(locationId, null)
                .compose(applySchedulers(schedulerProvider))
                .subscribe(
                    {
                        when {
                            locationId == null -> navigateToNext()
                            else -> commandLiveData.value = Command.NavigateToSelectDr(locationId)
                        }
                    }, {
                        throw IOException("Something went wrong :(")
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}