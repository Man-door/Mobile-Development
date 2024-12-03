package com.dicoding.mandoor.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.mandoor.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : AppCompatEditText(context, attrs) {

    init {
        setup()
    }

    private fun setup() {
        hint = context.getString(R.string.password)

        inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validatePassword(password: String) {
        if (password.length < 8) {
            error = context.getString(R.string.password_too_short)
        } else {
            error = null
        }
    }
}