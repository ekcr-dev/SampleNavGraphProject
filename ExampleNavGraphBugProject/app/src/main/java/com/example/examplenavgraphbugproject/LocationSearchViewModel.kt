package com.example.examplenavgraphbugproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationSearchViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val locationsSearchQueryPublishSubject = PublishSubject.create<String>()

    val locationsLiveData = MutableLiveData<List<LocationItem.Location>>()


    init {
        disposable.add(
            locationsSearchQueryPublishSubject
                .debounce(200, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe { query ->
                    updateLocations(query)
                }
        )
    }

    private var locations: List<LocationItem.Location> = ArrayList()
        set(value) {
            field = value
            locationsLiveData.postValue(locations)
        }

    var searchQuery: String = ""
        set(value) {
            field = value
            locationsSearchQueryPublishSubject.onNext(value)
        }

    private fun updateLocations(query: String) {
        disposable.add(
            repository.getLocations()
                .compose(RxUtils.applySchedulers(schedulerProvider))
                .subscribe(
                    { returnedLocations -> this.locations = returnedLocations },
                    {
                        // empty array list on error
                        this.locations = ArrayList()
                    })
        )
       }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

