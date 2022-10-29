package com.harera.common.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.harera.common.R
import com.harera.common.network.ConnectionLiveData
import com.harera.common.ui.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment() {

    val connectionLiveData: ConnectionLiveData by lazy {
        ConnectionLiveData(requireContext())
    }

    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(
            requireContext()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun handleError(exception: Exception?) {
        dismissLoading()
        showErrorToast()
        exception?.printStackTrace()
    }

    fun handleLoading() {
        showLoading()
    }

    fun handleLoading(state : Boolean) {
        if(state) {
            showLoading()
        } else {
            dismissLoading()
        }
    }

    fun handleSuccess() {
        dismissLoading()
    }

    private fun showErrorToast() {
        Toast.makeText(context, resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }
    private fun showLoading() {
        context?.let { loadingDialog.show() }
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }

    fun startActivity(classPath: String) {
        val clazz = Class.forName(classPath)
        val intent = Intent(activity, clazz)
        startActivity(intent)
    }

}