package com.kevinmost.whoscalling.preference

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.kevinmost.whoscalling.App
import com.kevinmost.whoscalling.R
import com.kevinmost.whoscalling.extension.getResourceValue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WhosCallingPreferences @Inject internal constructor() {

  companion object {
    private val KEY_SPEAKING_STRING = "Speaking String"
    private val KEY_SPEAK_IF_NOT_IN_CONTACTS = "Speak if not in contacts"
    private val KEY_SPEAKING_SPEED = "Speaking Speed"
  }

  private val preferences: SharedPreferences

  private var editor: SharedPreferences.Editor? = null
    get() = editor()

  init {
    preferences = PreferenceManager.getDefaultSharedPreferences(App.INSTANCE)
  }

  fun commit() {
    editor?.commit()
    editor = null
  }

  private fun editor(): SharedPreferences.Editor {
    if (editor == null) {
      editor = preferences.edit()
    }
    return editor!!
  }

  fun setSpeakingSpeed(speakingSpeed: Float,
      commitAfter: Boolean = false)
      : WhosCallingPreferences {
    editor?.putFloat(KEY_SPEAKING_SPEED, speakingSpeed)
    return maybeCommitAndReturn(commitAfter)
  }

  fun setSpeakingString(speakingString: String,
      commitAfter: Boolean = false)
      : WhosCallingPreferences {
    editor?.putString(KEY_SPEAKING_STRING, speakingString)
    return maybeCommitAndReturn(commitAfter)
  }

  fun getSpeakingString(): String? {
    return preferences.getString(KEY_SPEAKING_STRING,
        getResourceValue(R.string.default_speaking_string))
  }

  fun setSpeakIfNotInContacts(speakIfNotInContacts: Boolean,
      commitAfter: Boolean = false)
      : WhosCallingPreferences {
    editor?.putBoolean(KEY_SPEAK_IF_NOT_IN_CONTACTS, speakIfNotInContacts)
    return maybeCommitAndReturn(commitAfter)
  }

  fun getSpeakIfNotInContacts(): Boolean {
    return preferences.getBoolean(KEY_SPEAK_IF_NOT_IN_CONTACTS,
        getResourceValue(R.bool.default_speak_when_not_in_contacts))
  }

  private fun maybeCommitAndReturn(commit: Boolean): WhosCallingPreferences {
    if (commit) {
      commit()
    }
    return this
  }
}