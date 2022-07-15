package com.example.examplenavgraphbugproject


import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.MaybeTransformer
import io.reactivex.rxjava3.core.SingleTransformer

object RxUtils {


    fun <T> applySchedulers(schedulerProvider: SchedulerProvider): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
        }
    }


    fun <T> applyLoadingStates(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream
                .doOnSubscribe { }
                .doFinally { }
        }
    }

}
