package com.dicoding.mandoor.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.dicoding.mandoor.R

class LoadingAdapter(private val context: Context) {
    private var dialog: Dialog? = null

    fun showLoading() {
        if (dialog == null) {
            dialog = Dialog(context).apply {
                val view: View = LayoutInflater.from(context).inflate(R.layout.loading, null)
                setContentView(view)
                setCancelable(false)
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        dialog?.show()
    }

    fun dismissLoading() {
        dialog?.dismiss()
        dialog = null
    }
}