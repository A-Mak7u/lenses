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
        val melir: TextView = findViewById(R.id.melir)
        val melir_button: Button = findViewById(R.id.melir_button)
        val parikm: TextView = findViewById(R.id.parikm)
        val parikm_button: Button = findViewById(R.id.parikm_button)

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
            melir.text = "Использовал сильвер: $savedDatec"
        }
        val savedDatecp = sharedPreferences.getString("savedDate_cp", null)
        if (savedDatecp != null) {
            parikm.text = "Подстригся я: $savedDatecp"
        }

        // Установка сегодняшней даты и сохранение треш
        setDateButton.setOnClickListener {
            val currentDate = LocalDate.now().format(formatter)
            setdate.text = "Дата установки: $currentDate"
            saveDate("savedDate", currentDate)
            calculateTwoWeeksLater(currentDate, twoWeeksLaterView)
        }

        melir_button.setOnClickListener {
            val currentDate = LocalDate.now().format(formatter)
            melir.text = "Использовал сильвер: $currentDate"
            saveDate("savedDate_c", currentDate)
        }

        parikm_button.setOnClickListener {
            val currentDate = LocalDate.now().format(formatter)
            parikm.text = "Подстригся я: $currentDate"
            saveDate("savedDate_cp", currentDate)
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

    private fun saveDate(key: String, date: String) {
        sharedPreferences.edit().putString(key, date).apply()
    }

    private fun calculateTwoWeeksLater(date: String, view: TextView) {
        val parsedDate = LocalDate.parse(date, formatter)
        val dateAfterTwoWeeks = parsedDate.plusWeeks(2)

        view.text = "Дата + 2 недели: ${dateAfterTwoWeeks.format(formatter)}"
    }
}

