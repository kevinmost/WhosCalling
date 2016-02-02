package com.kevinmost.whoscalling.extension

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.widget.Toast

@ColorInt
fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
  return ContextCompat.getColor(this, colorRes)
}

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, text, duration).show()
}

fun Context.checkPermissions(vararg permissions: String) {
  for (permission in permissions) {
    ContextCompat.checkSelfPermission(this, permission)
  }
}

val Context.windowManager: WindowManager
    get() {
      return getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
