package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_display_message.*
import java.util.*

class DisplayMessageActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var textSpeak: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        buttonSpeak = this.button_tts
        buttonSpeak!!.isEnabled = false
        tts = TextToSpeech(this, this)
        buttonSpeak!!.setOnClickListener { speakOut() }
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        textSpeak = message
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }

    private fun speakOut() {
        val text = textSpeak
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }
}
