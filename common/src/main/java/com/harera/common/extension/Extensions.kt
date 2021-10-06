package com.harera.common

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.mancj.materialsearchbar.MaterialSearchBar

fun MaterialSearchBar.onSearchConfirmed(afterTextChanged: (String) -> Unit) {
    this.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
        override fun onSearchStateChanged(enabled: Boolean) {
        }

        override fun onSearchConfirmed(text: CharSequence) {
            afterTextChanged.invoke(text.toString())
        }

        override fun onButtonClicked(buttonCode: Int) {
        }
    })
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun View.focusSoftKeyboard() {
    this.requestFocus()

    val inputMethodManager = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}