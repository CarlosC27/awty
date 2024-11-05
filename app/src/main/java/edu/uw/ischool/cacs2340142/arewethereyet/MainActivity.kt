package edu.uw.ischool.cacs2340142.arewethereyet

import edu.uw.ischool.cacs2340142.arewethereyet.MessageReciever
import android.os.Bundle
import android.content.*
import android.app.*
import android.widget.*
import androidx.core.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var isAlarmOn = false
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val messageText: EditText = findViewById(R.id.MessageText)
        val phoneNumber: EditText = findViewById(R.id.PhoneNumber)
        val n: EditText = findViewById(R.id.AmountOfTimes)
        val myButton: Button = findViewById(R.id.StartAndStopButton)

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        messageText.addTextChangedListener { checkInputs(messageText, phoneNumber, n, myButton) }
        phoneNumber.addTextChangedListener { checkInputs(messageText, phoneNumber, n, myButton) }
        n.addTextChangedListener { checkInputs(messageText, phoneNumber, n, myButton) }

        myButton.setOnClickListener {
            if (isAlarmOn) {
                isAlarmOn = false
                myButton.text = "Start"
                pendingIntent.let { alarmManager.cancel(it) }
            } else {
                val message = messageText.text.toString()
                val phoneNum = phoneNumber.text.toString()
                val time = n.text.toString().toInt() * 60 * 1000L
                val intent = Intent(this, MessageReciever::class.java).apply {
                    putExtra("message", message)
                    putExtra("phoneNumber", phoneNum)
                }
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, time, pendingIntent)

                myButton.text = "Stop"
                isAlarmOn = true
            }
        }
    }

    private fun checkInputs(messageText: EditText, phoneNumber: EditText, n: EditText, myButton: Button) {
        val message = messageText.text.toString().trim()
        val phone = phoneNumber.text.toString().trim()
        val interval = n.text.toString().trim()
        myButton.isEnabled = message.isNotEmpty() && phone.isNotEmpty() && interval.isNotEmpty() &&
                (interval.all { it.isDigit() }) && (interval.toInt() > 0)
    }
}
