package com.kevinmost.whoscalling.dagger

import com.kevinmost.whoscalling.App
import com.kevinmost.whoscalling.util.PostFromAnywhereBus
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.squareup.okhttp.OkHttpClient
import com.squareup.otto.Bus
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

  @Provides
  @Singleton
  fun app(): App {
    return app
  }

  @Provides
  @Singleton
  fun bus(): Bus {
    return PostFromAnywhereBus()
  }

  @Provides
  @Singleton
  fun okHttpClient(): OkHttpClient {
    return OkHttpClient()
  }

  @Provides
  @Singleton
  fun picasso(app: App, okHttpClient: OkHttpClient): Picasso {
    return Picasso.Builder(app)
        .downloader(OkHttpDownloader(okHttpClient))
        .indicatorsEnabled(app.isDebugBuildType())
        .build()
  }
}
