package edu.uw.ischool.cacs2340142.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.telephony.PhoneNumberUtils

 class MessageReciever: BroadcastReceiver() {
     override fun onReceive(context: Context, intent: Intent) {
         val message = intent.getStringExtra("message") ?: "Are we there yet?"
         val phoneNumber = intent.getStringExtra("phoneNumber") ?: "(425) 555-1212"

         val formattedPhoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, "US") ?: phoneNumber

         Toast.makeText(context, "$formattedPhoneNumber: $message", Toast.LENGTH_SHORT).show()
     }
 }
