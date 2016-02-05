package com.kevinmost.whoscalling

import android.app.Application
import android.content.Context
import com.kevinmost.whoscalling.dagger.AppModule
import com.kevinmost.whoscalling.dagger.DaggerAppComponent
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
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

  lateinit var refWatcher: RefWatcher
    get

  override fun onCreate() {
    INSTANCE = this
    super.onCreate()
    refWatcher = LeakCanary.install(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    initHawk()
  }

  private fun initHawk() {
    Hawk.init(this)
        .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
        .setLogLevel(if (BuildConfig.DEBUG) LogLevel.FULL else LogLevel.NONE)
        .setStorage(HawkBuilder.newSqliteStorage(this))
        .buildRx()
  }
}
