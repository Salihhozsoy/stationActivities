package com.example.stationdesignproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class PrintActivity : AppCompatActivity() {
    lateinit var etCarInformation: EditText
    lateinit var etPumpInformation:EditText
    lateinit var etTotalPrice:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        etCarInformation = findViewById(R.id.etCarInformation)
        etPumpInformation = findViewById(R.id.etPumpInformation)
        etTotalPrice = findViewById(R.id.etTotalPrice)

        val car= intent.getParcelableExtra<Car>(CalculateActivity.CAR)
        val pump= intent.getParcelableExtra<Pump>(CalculateActivity.PUMP)

        car?.let {
            etCarInformation.setText(it.getListName())
        }
        pump?.let {
            etPumpInformation.setText(it.no)
        }
    }
}