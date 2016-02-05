package com.kevinmost.whoscalling.util

import android.speech.tts.TextToSpeech
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import com.kevinmost.whoscalling.App
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TTSReader @Inject internal constructor() {
  private val tts: TextToSpeech = TextToSpeech(App.INSTANCE, null)

  fun speak(text: CharSequence, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
    tts.speak(text, queueMode, null, null)
  }

  fun silenceFor(millis: Long, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
    tts.playSilentUtterance(millis, queueMode, null)
  }

  fun stopSpeaking() {
    tts.stop()
  }

  fun repeatText(text: CharSequence, numRepeats: Int = 0, delayBetweenInMillis: Long = 0) {
    if (numRepeats <= 0) {
      repeatText(text, Int.MAX_VALUE, delayBetweenInMillis)
      return
    }
    for (num in 0..numRepeats) {
      speak(text, TextToSpeech.QUEUE_ADD)
      silenceFor(delayBetweenInMillis, TextToSpeech.QUEUE_ADD)
    }
  }

  fun setLanguage(locale: Locale) {
    tts.setLanguage(locale)
  }

  fun setSpeechRate(@IntRange(from = 0L, to = 5L) speechRate: Int) {
    val speechRateNormalized = (speechRate / 3.33333333333333) + 0.5
    tts.setSpeechRate(speechRateNormalized.toFloat())
  }

  // TODO: Let them set all the other params like speech speed, pitch, etc
}