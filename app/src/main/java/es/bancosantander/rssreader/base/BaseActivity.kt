package es.bancosantander.rssreader.base

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import es.bancosantander.rssreader.di.ActivityComponent
import es.bancosantander.rssreader.arch.ActivityModule
import es.bancosantander.rssreader.arch.AndroidApplication
import es.bancosantander.rssreader.di.ApplicationComponent
import es.bancosantander.rssreader.di.DaggerActivityComponent

open class BaseActivity : AppCompatActivity() {

    private val lifecycleRegistry = LifecycleRegistry(this)

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    lateinit var activityComponent: ActivityComponent


    fun Activity.getRootView(): ViewGroup {
        val v: ViewGroup = window.decorView.findViewById(android.R.id.content)
        return v.getChildAt(0) as ViewGroup
    }


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(ActivityModule(this))
                .build()

    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}

