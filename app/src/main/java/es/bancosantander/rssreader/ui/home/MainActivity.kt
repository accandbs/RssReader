package es.bancosantander.rssreader.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.base.BaseActivity

class MainActivity : BaseActivity() {
    @javax.inject.Inject
    lateinit var decorator: MainActivityDecorator
    @javax.inject.Inject
    lateinit var presenter: MainActivityPresenter

    lateinit var viewModel: MainViewModel

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        decorator.bind(getRootView())
        presenter.initialize(decorator, viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        decorator.dispose()
    }
}
