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

        val pump= intent.getParcelableExtra<Pump>(CalculateActivity.PUMP)
        val invoice = intent.getParcelableExtra<Invoice>(CalculateActivity.INVOICE)

        invoice?.let {
            etCarInformation.setText(it.car.getListName())
            etTotalPrice.setText("price: ${it.price} liter: ${it.liter}")
        }
        pump?.let {
            etPumpInformation.setText("${it.no} Nolu pompa - Pompacı: ${it.employee.name}")
        }
    }
}