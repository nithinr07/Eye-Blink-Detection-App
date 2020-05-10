package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_display_message.*
import java.util.*

class DisplayMessageActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var textSpeak: String? = null
    private var textToSend:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        buttonSpeak = this.button_tts
        buttonSpeak!!.isEnabled = false
        tts = TextToSpeech(this, this)
        buttonSpeak!!.setOnClickListener { speakOut() }
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        textSpeak = message
        when (textSpeak) {
            "1001" -> textToSend = "Codeword Sequence 1,0,0,1 activated"
            "1011" -> textToSend = "Codeword Sequence 1,0,1,1 activated"
            else -> { // Note the block
                textToSend = "Tame Impala is love"
            }
        }
        textSpeak = textToSend
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = textToSend
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@DisplayMessageActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("SendToQueue", textToSend)
        startActivity(intent)
    }

    override fun onSupportNavigateUp():Boolean {
        val intent = Intent(this@DisplayMessageActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("SendToQueue", textToSend)
        startActivity(intent)
        return true
    }

    private fun speakOut() {
        val text = textSpeak
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK)
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
