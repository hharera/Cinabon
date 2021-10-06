package com.harera.ui_components.loading

import android.app.AlertDialog
import android.content.Context
import com.harera.loading.R

class LoadingDialog(val context: Context) {
    var dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(R.layout.loading_dialog)
        .setCancelable(false)
        .create()

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.cancel()
    }
}