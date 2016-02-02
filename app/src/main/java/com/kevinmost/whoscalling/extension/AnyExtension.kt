package com.kevinmost.whoscalling.extension

import android.content.res.Resources
import com.kevinmost.whoscalling.App
import org.jetbrains.anko.layoutInflater
import kotlin.reflect.KClass

fun Any.watchForLeaks() {
  App.INSTANCE.refWatcher.watch(this)
}

fun <T> Any.getResourceValue(id: Int): T {
  with(App.INSTANCE.resources) {
    val resourceTypeName = getResourceTypeName(id)
    return when (resourceTypeName) {
      "string" -> getString(id)
      "drawable" -> getDrawable(id)
      "anim" -> getAnimation(id)
      "layout" -> App.INSTANCE.layoutInflater.inflate(id, null)
      "bool" -> getBoolean(id)
      "dimen" -> getDimension(id)
      "integer" -> getInteger(id)
      else -> throw IllegalArgumentException(getResourceName(id) +
          " could not be matched to any of these types, it is a $resourceTypeName")
    } as T
  }
}

