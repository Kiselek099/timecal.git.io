package com.example.timecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var firstOperandET:EditText
    lateinit var secondOperandET:EditText
    lateinit var plusBTN:Button
    lateinit var difBTN:Button
    lateinit var textResultTV:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firstOperandET=findViewById(R.id.firstOperandET)
        secondOperandET=findViewById(R.id.secondOperandET)
        plusBTN=findViewById(R.id.plusBTN)
        difBTN=findViewById(R.id.difBTN)
        textResultTV=findViewById(R.id.textResultTV)
        plusBTN.setOnClickListener{
            val textOne=firstOperandET.text.toString()
            val textTwo=secondOperandET.text.toString()
            textResultTV.text= addTime(textOne,textTwo)
        }
        difBTN.setOnClickListener{
            val textOne=firstOperandET.text.toString()
            val textTwo=secondOperandET.text.toString()
            textResultTV.text= subtractTime(textOne,textTwo)
        }
    }
}

fun parseTime(time: String): Int {
    var seconds = 0
    val regex = "(\\d+h)?(\\d+m)?(\\d+s)?".toRegex()
    val match = regex.matchEntire(time.trim())

    match?.groups?.let { groups ->
        groups[1]?.value?.let { hours -> // Обработка часов
            seconds += hours.dropLast(1).toInt() * 3600
        }
        groups[2]?.value?.let { minutes -> // Обработка минут
            seconds += minutes.dropLast(1).toInt() * 60
        }
        groups[3]?.value?.let { sec -> // Обработка секунд
            seconds += sec.dropLast(1).toInt()
        }
    }
    return seconds
}
fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return buildString {
        if (hours > 0) append("${hours}h")
        if (minutes > 0) append("${minutes}m")
        if (remainingSeconds > 0 || isEmpty()) append("${remainingSeconds}s")
    }
}
fun addTime(timeOne: String, timeTwo: String): String {
    val totalSeconds = parseTime(timeOne) + parseTime(timeTwo)
    return formatTime(totalSeconds)
}

fun subtractTime(timeOne: String, timeTwo: String): String {
    val totalSeconds = (parseTime(timeOne) - parseTime(timeTwo)).coerceAtLeast(0)
    return formatTime(totalSeconds)
}


