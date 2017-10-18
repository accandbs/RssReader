package es.bancosantander.rssreader.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.base.BaseActivity
import android.R.menu
import android.view.Menu
import android.content.Intent
import android.view.MenuItem
import es.bancosantander.rssreader.ui.settings.SettingsActivity


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent

        when (item.getItemId()) {
            R.id.feetSettings -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }
}
