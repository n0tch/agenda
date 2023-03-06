package com.gustavo.agenda.common.ui

import android.app.AlertDialog
import android.content.Context

fun Context.defaultAlert(
    title: String,
    description: String,
    positiveButtonText: String,
    positiveButtonAction: () -> Unit,
    negativeButtonText: String = "",
    negativeButtonAction: () -> Unit = {}
) = AlertDialog.Builder(this)
    .setTitle(title)
    .setMessage(description)
    .setPositiveButton(positiveButtonText){ _, _ -> positiveButtonAction() }
    .setNegativeButton(negativeButtonText) { _, _ -> negativeButtonAction()}
    .create()
    .show()