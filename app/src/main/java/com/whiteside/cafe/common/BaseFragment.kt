package com.whiteside.cafe.common

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.whiteside.cafe.R
import com.whiteside.cafe.ui.custom.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment() {
    //TODO convert to Dependency injection

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
        Toast.makeText(context, resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    private fun printException(e: Exception) {
        e.printStackTrace()
    }

    private fun showLoading() {
        loadingDialog.show(requireContext())
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }
}