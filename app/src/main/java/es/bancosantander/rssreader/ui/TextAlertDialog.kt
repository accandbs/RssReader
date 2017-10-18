package es.bancosantander.rssreader.ui

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.arch.Callback
import javax.inject.Inject

class TextAlertDialog
@Inject
constructor(
        private val activity: Activity
) {
    fun show(@StringRes title: Int, @StringRes text: Int, callback: Callback<String>) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(text)

        val input = EditText(activity)
        input.inputType = InputType.TYPE_TEXT_VARIATION_URI
        builder.setView(input)

        builder.setPositiveButton(R.string.add) { _, _ -> callback(input.text.toString()) }
        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }

        builder.create().show()
    }
}
