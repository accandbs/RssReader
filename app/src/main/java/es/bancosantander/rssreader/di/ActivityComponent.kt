package es.bancosantander.rssreader.di

import dagger.Component
import es.bancosantander.rssreader.arch.ActivityModule
import es.bancosantander.rssreader.base.BaseActivity
import es.bancosantander.rssreader.ui.detail.DetailActivity
import es.bancosantander.rssreader.ui.home.MainActivity

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)

    fun activity(): BaseActivity
}
