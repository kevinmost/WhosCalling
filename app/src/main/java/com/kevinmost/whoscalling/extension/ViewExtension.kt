package com.kevinmost.whoscalling.extension

import android.os.Build
import android.support.v4.view.ViewCompat
import android.view.View

fun View.isAttachedToWindowCompat(): Boolean {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
    return ViewCompat.isAttachedToWindow(this)
  } else {
    return isAttachedToWindow
  }
}
