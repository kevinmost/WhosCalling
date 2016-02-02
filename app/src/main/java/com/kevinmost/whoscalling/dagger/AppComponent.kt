package com.kevinmost.whoscalling.dagger

import com.kevinmost.whoscalling.activity.MainActivity
import com.kevinmost.whoscalling.receiver.PhoneStateBroadcastReceiver
import com.squareup.leakcanary.RefWatcher
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
  fun inject(target: MainActivity)
  fun inject(target: PhoneStateBroadcastReceiver)
}
