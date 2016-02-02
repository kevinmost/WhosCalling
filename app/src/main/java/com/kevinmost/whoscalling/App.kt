package com.kevinmost.whoscalling

import android.app.Application
import android.content.Context
import com.kevinmost.whoscalling.dagger.AppModule
import com.kevinmost.whoscalling.dagger.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

class App : Application() {
  companion object {
    @JvmStatic
    lateinit var INSTANCE: App
  }

  val appComponent = DaggerAppComponent
      .builder()
      .appModule(AppModule(this))
      .build()
    get

  lateinit var refWatcher: RefWatcher
    get

  override fun onCreate() {
    INSTANCE = this
    super.onCreate()
    refWatcher = LeakCanary.install(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  /**
   * A common pitfall is to rely on BuildConfig.DEBUG when working with a multi-module project;
   * if checked from a library project, BuildConfig.DEBUG is always false. Better to make the app
   * implement it itself.
   */
  fun isDebugBuildType(): Boolean = BuildConfig.DEBUG
}
