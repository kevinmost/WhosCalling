package com.kevinmost.whoscalling.activity

import android.Manifest
import android.os.Bundle
import android.widget.SeekBar
import butterknife.OnCheckedChanged
import butterknife.OnClick
import butterknife.OnTextChanged
import com.kevinmost.whoscalling.R
import com.kevinmost.whoscalling.dagger.AppComponent
import com.kevinmost.whoscalling.extension.bindView
import com.kevinmost.whoscalling.extension.checkPermissions
import com.kevinmost.whoscalling.preference.WhosCallingPreferences
import com.kevinmost.whoscalling.util.TTSReader
import javax.inject.Inject

class MainActivity : BaseActivity() {

  @Inject
  lateinit var ttsReader: TTSReader

  @Inject
  lateinit var preferences: WhosCallingPreferences

  private val speakingSpeedSeekBar by bindView<SeekBar>(R.id.speakingSpeed)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkPermissions(Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CONTACTS)

    speakingSpeedSeekBar.progress = 5

    speakingSpeedSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
  }

  @OnCheckedChanged(R.id.speakWhenNotInContacts)
  protected fun changeSpeakWhenNotInContacts(checked: Boolean) {
    preferences.setSpeakIfNotInContacts(checked, commitAfter = true)
  }

  @OnTextChanged(R.id.speakingString)
  protected fun speakingStringChanged(speakingString: CharSequence) {
    preferences.setSpeakingString(speakingString.toString(), commitAfter = true)
  }

  override fun injectSelf(appComponent: AppComponent) {
    appComponent.inject(this)
  }

  override fun layoutRes() = R.layout.foo
}