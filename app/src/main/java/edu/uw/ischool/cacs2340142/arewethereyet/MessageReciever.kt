package edu.uw.ischool.cacs2340142.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.telephony.*

class MessageReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message") ?: "Are we there yet?"
        val phoneNumber = intent.getStringExtra("phoneNumber") ?: ""

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Toast.makeText(context, "SMS sent to $phoneNumber", Toast.LENGTH_SHORT).show()
    }
}

