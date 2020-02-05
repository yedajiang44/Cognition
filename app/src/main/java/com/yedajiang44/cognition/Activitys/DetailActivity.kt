package com.yedajiang44.cognition.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yedajiang44.cognition.Adapters.DetailAdapter
import com.yedajiang44.cognition.Models.DetailItemModel
import com.yedajiang44.cognition.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        textToSpeech = TextToSpeech(this, this)
        val adapter = DetailAdapter()
        val json = intent.getStringExtra("data")
        adapter.setNewData(
            Gson().fromJson(
                json,
                object : TypeToken<ArrayList<DetailItemModel>>() {}.type
            )
        )
        adapter.setOnItemClickListener { _, _, position ->
            if (textToSpeech.isSpeaking) return@setOnItemClickListener
            textToSpeech.speak(
                adapter.data[position].name,
                TextToSpeech.QUEUE_FLUSH,
                null,
                UUID.randomUUID().toString()
            )
        }
        viewPager.adapter = adapter
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            //初始化tts引擎
            val result = textToSpeech.setLanguage(Locale.CHINESE)

            //不支持中文tts或中文tts引擎数据丢失
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.lostVoicePacketsOrUnsupportedVoice),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            //tts引擎初始化失败
            AlertDialog.Builder(this)
                .setMessage(resources.getString(R.string.voicePacketInitializationFailed))
                .setTitle(resources.getString(R.string.prompt))
        }
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}
