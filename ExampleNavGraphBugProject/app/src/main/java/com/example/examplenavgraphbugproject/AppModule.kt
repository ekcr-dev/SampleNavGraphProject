package com.example.examplenavgraphbugproject


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationSearchViewModel::class)
    abstract fun bindLocationSearchViewModel(viewModel: LocationSearchViewModel): ViewModel

    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    abstract fun contributesSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun contributeDrFragment(): IntroFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationFragment(): LocationFragment

    @ContributesAndroidInjector
    abstract fun contributeSelectDrFragment(): SelectDrFragment

    @ContributesAndroidInjector
    abstract fun contributeFinalFragment(): FinalFragment

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(IntroViewModel::class)
    abstract fun bindIntroViewModel(viewModel: IntroViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectDrViewModel::class)
    abstract fun bindSelectDrViewModel(viewModel: SelectDrViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GenericViewModelFactory): ViewModelProvider.Factory
}