package com.rahman.umweltify.persentation.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper


fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}


fun String.isValidPassword(): Boolean {
    if (this.length < 6) return false
    if (this.filter { it.isDigit() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
    if (this.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}


fun String.validPasswordText(): String {
    if (this.isEmpty()) return "Your Password Must Not Be Empty "
    if (this.length < 6) return "Your Password Must Be More then 6 character"
    if (this.filter { it.isDigit() }.firstOrNull() == null) return "Your Password Must Have At Least One Number"
    if (this.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return "Your Password Must Have At Least One Upper Case"
    if (this.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return "Your Password Must Have At Least One Lower Case"
    if (this.filter { !it.isLetterOrDigit() }.firstOrNull() == null)return "Your Password Must Have At Least One Character"

    return ""
}

fun String.isValidEmail(): Boolean {
    if (this.isEmpty()) return false
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()) return false
    return true
}


fun String.validEmailText(): String {

    if (this.isEmpty()) return "Your Email Must Not Be Empty"
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()) return "Your Enter Invalid Email Address"

    return ""
}