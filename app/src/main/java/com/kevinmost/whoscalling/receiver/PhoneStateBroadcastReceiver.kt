package com.kevinmost.whoscalling.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import com.kevinmost.whoscalling.App
import com.kevinmost.whoscalling.util.TTSReader
import timber.log.Timber
import javax.inject.Inject

class PhoneStateBroadcastReceiver : BroadcastReceiver() {

  init {
    App.INSTANCE.appComponent.inject(this)
  }

  @Inject
  lateinit var ttsReader: TTSReader

  override fun onReceive(context: Context?, intent: Intent?) {
    if (context == null || intent == null) {
      return
    }
    val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
    when (state) {
      TelephonyManager.EXTRA_STATE_IDLE,
      TelephonyManager.EXTRA_STATE_OFFHOOK -> {
        ttsReader.stopSpeaking()
      }
      TelephonyManager.EXTRA_STATE_RINGING -> {
        val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        val (wasFound, name) = getContactNameFromPhoneNumber(context, phoneNumber)
        // TODO: Let user decide if we should read the number out if there's no contact found
        if (wasFound) {
          ttsReader.repeatText("Call from $name", numRepeats = 10, delayBetweenInMillis = 1000)
        }
      }
    }
  }

  private fun getContactNameFromPhoneNumber(context: Context, phoneNumber: String): Contact {
    val uri: Uri = Uri.withAppendedPath(
        ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
        Uri.encode(phoneNumber))
    val result = context.contentResolver.query(uri,
        arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
        // "null, null, null)" is the title of my band's new post-rock album
        null,
        null,
        null)
    while (result.moveToNext()) {
      val name = result.getString(0)
      return Contact(wasFound = true, name = name)
    }
    return Contact(wasFound = false, name = "")
  }

  data class Contact(
      val wasFound: Boolean,
      val name: String
  )
}

