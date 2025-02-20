package com.example.lenses

import android.annotation.SuppressLint
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todayDateView: TextView = findViewById(R.id.todayDate)
        val setdate: TextView = findViewById(R.id.setdate)
        val twoWeeksLaterView: TextView = findViewById(R.id.twoWeeksLater)
        val setDateButton: Button = findViewById(R.id.setDateButton)
        val lastcock: TextView = findViewById(R.id.lastcock)
        val lastcock_button: Button = findViewById(R.id.lastcock_button)
        val lastcock_p: TextView = findViewById(R.id.lastcock_p)
        val lastcock_p_button: Button = findViewById(R.id.lastcock_p_button)

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("LensCyclePrefs", MODE_PRIVATE)


        updateTodayDate(todayDateView)

        // Загрузка сохраненной даты, если она есть
        val savedDate = sharedPreferences.getString("savedDate", null)
        if (savedDate != null) {
            setdate.text = "Дата установки: $savedDate"
            calculateTwoWeeksLater(savedDate, twoWeeksLaterView)
        }
        val savedDatec = sharedPreferences.getString("savedDate_c", null)
        if (savedDatec != null) {
            lastcock.text = "Последний раз дрочил: $savedDatec"
        }
        val savedDatecp = sharedPreferences.getString("savedDate_cp", null)
        if (savedDatecp != null) {
            lastcock_p.text = "С порно: $savedDatecp"
        }

        // Установка сегодняшней даты и сохранение треш
        setDateButton.setOnClickListener {
            val currentDate = LocalDate.now().format(formatter)
            setdate.text = "Дата установки: $currentDate"
            saveDate(currentDate)
            calculateTwoWeeksLater(currentDate, twoWeeksLaterView)
        }

        lastcock_button.setOnClickListener {
            val currentDate = LocalDate.now().format(formatter)
            lastcock.text = "Последний раз дрочил: $currentDate"
            saveDate_c(currentDate)
        }

        lastcock_p_button.setOnClickListener {
            val currentDate = LocalDate.now().format(formatter)
            lastcock_p.text = "C порно: $currentDate"
            saveDate_cp(currentDate)
            lastcock.text = "Последний раз дрочил: $currentDate"
            saveDate_c(currentDate)
        }


    }

    // Метод для обновления сегодняшней даты при каждом входе
    override fun onResume() {
        super.onResume()
        val todayDateView: TextView = findViewById(R.id.todayDate)
        updateTodayDate(todayDateView)
    }

    private fun updateTodayDate(todayDateView: TextView) {
        val today = LocalDate.now() // Получение текущей даты
        todayDateView.text = "Сегодня: ${today.format(formatter)}" // Отображение текущей даты
    }

    private fun saveDate(date: String) {
        sharedPreferences.edit().putString("savedDate", date).apply()
    }
    private fun saveDate_c(date: String) {
        sharedPreferences.edit().putString("savedDate_c", date).apply()
    }
    private fun saveDate_cp(date: String) {
        sharedPreferences.edit().putString("savedDate_cp", date).apply()
    }

    private fun calculateTwoWeeksLater(date: String, view: TextView) {
        val parsedDate = LocalDate.parse(date, formatter)
        val dateAfterTwoWeeks = parsedDate.plusWeeks(2)

        view.text = "Дата + 2 недели: ${dateAfterTwoWeeks.format(formatter)}"
    }
}

