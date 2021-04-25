package com.whiteside.cafe.common

import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.whiteside.cafe.R
import com.whiteside.cafe.ui.custom.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    //TODO convert to Dependency injection
    val connection: ConnectivityManager by lazy {
        application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    var loadingDialog = LoadingDialog()

    fun handleFailure(e: Exception) {
        dismissLoading()
        showErrorToast()
        printException(e)
    }

    fun handleLoading() {
        showLoading()
    }

    fun handleSuccess() {
        dismissLoading()
    }

    private fun showErrorToast() {
        Toast.makeText(this, resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    private fun printException(e: Exception) {
        e.printStackTrace()
    }

    private fun showLoading() {
        loadingDialog.show(this)
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }
}