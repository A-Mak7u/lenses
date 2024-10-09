package com.example.lenses

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences: SharedPreferences
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // формат без года

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todayDateView: TextView = findViewById(R.id.todayDate)
        val setdate: TextView = findViewById(R.id.setdate)
        val twoWeeksLaterView: TextView = findViewById(R.id.twoWeeksLater)
        val setDateButton: Button = findViewById(R.id.setDateButton)

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("LensCyclePrefs", MODE_PRIVATE)

        // Отображение сегодняшней даты
        val today = LocalDate.now()
        todayDateView.text = "Сегодня: ${today.format(formatter)}"

        // Загрузка сохраненной даты, если она есть
        val savedDate = sharedPreferences.getString("savedDate", null)
        if (savedDate != null) {
            setdate.text = "Дата установки: $savedDate"
            calculateTwoWeeksLater(savedDate, twoWeeksLaterView)
        }

        // Установка сегодняшней даты и сохранение
        setDateButton.setOnClickListener {
            val currentDate = today.format(formatter)
            setdate.text = "Дата установки: $currentDate"
            saveDate(currentDate)
            calculateTwoWeeksLater(currentDate, twoWeeksLaterView)
        }


    }

    private fun saveDate(date: String) {
        sharedPreferences.edit().putString("savedDate", date).apply()
    }

    private fun calculateTwoWeeksLater(date: String, view: TextView) {
        val parsedDate = LocalDate.parse(date, formatter)
        val dateAfterTwoWeeks = parsedDate.plusWeeks(2)

        view.text = "Дата + 2 недели: ${dateAfterTwoWeeks.format(formatter)}"
    }
}

