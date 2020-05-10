package com.example.firstapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*


const val EXTRA_MESSAGE = "com.example.firstapp.MESSAGE"
class MainActivity : AppCompatActivity() {
    val TAG="Service"
    companion object {
        var msgs: Queue<String> = LinkedList<String>(listOf(" ", " ", " ", " ", " "," "," "," "," "," "))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                Log.d(TAG, token)
            })
        val listView = findViewById<ListView>(R.id.listView)
        val listItems = arrayOfNulls<String>(10)
        val message = intent.getStringExtra("SendToQueue")
        if(message != null) {
            Log.d("RcvdMsg", message)
            msgs.add(message)
            msgs.remove()
        }
        var i = listItems.size - 1
        for (msg in msgs) {
            listItems[i] = msg
            Log.d("Queue", msg)
            i -= 1
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }
}
