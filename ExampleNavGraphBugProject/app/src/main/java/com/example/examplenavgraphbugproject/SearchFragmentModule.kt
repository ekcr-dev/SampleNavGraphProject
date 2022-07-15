package com.example.examplenavgraphbugproject

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLocationSearchFragment(): SearchFragment

}
