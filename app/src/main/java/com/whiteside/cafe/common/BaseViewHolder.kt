package com.whiteside.cafe.common

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.whiteside.cafe.R
import com.whiteside.cafe.ui.custom.LoadingDialog

open class BaseViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
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
        Toast.makeText(binding.root.context, binding.root.context.resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    private fun printException(e: Exception) {
        e.printStackTrace()
    }

    private fun showLoading() {
        loadingDialog.show(binding.root.context)
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }
}