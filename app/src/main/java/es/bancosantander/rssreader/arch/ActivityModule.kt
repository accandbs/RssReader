package es.bancosantander.rssreader.arch

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import es.bancosantander.rssreader.base.BaseActivity
import es.bancosantander.rssreader.di.PerActivity
import es.bancosantander.rssreader.ui.TextAlertDialog

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @PerActivity
    internal fun activity(): BaseActivity = this.activity

    @Provides
    @PerActivity
    internal fun provideLinearLayoutManager(activity: BaseActivity) = LinearLayoutManager(activity as Context)


    @Provides
    @PerActivity
    internal fun provideTextAlertDialog(activity: BaseActivity) = TextAlertDialog(activity)

}
