package com.kevinmost.whoscalling.util

import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import android.support.annotation.IntRange
import com.kevinmost.whoscalling.App
import java.io.Serializable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TTSReader @Inject internal constructor() {
  private val tts: TextToSpeech = TextToSpeech(App.INSTANCE, null)

  public fun speak(text: CharSequence, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
    tts.speak(text, queueMode, null, null)
  }

  public fun silenceFor(millis: Long, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
    tts.playSilentUtterance(millis, queueMode, null)
  }

  public fun repeatText(text: CharSequence, numRepeats: Int = 0, delayBetweenInMillis: Long = 0) {
    if (numRepeats <= 0) {
      repeatText(text, Int.MAX_VALUE, delayBetweenInMillis)
      return
    }
    for (num in 0..numRepeats) {
      speak(text, TextToSpeech.QUEUE_ADD)
      silenceFor(delayBetweenInMillis, TextToSpeech.QUEUE_ADD)
    }
  }

  public fun setLanguage(locale: Locale) {
    tts.setLanguage(locale)
  }

  public fun setSpeechRate(@IntRange(from = 0L, to = 5L) speechRate: Int) {
    val speechRateNormalized = (speechRate / 3.33333333333333) + 0.5
    tts.setSpeechRate(speechRateNormalized.toFloat())
  }

  public fun stopSpeaking() {
    tts.stop()
  }

  // TODO: Let them set all the other params like speech speed, pitch, etc
}