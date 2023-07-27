package com.example.stationdesignproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner

import androidx.appcompat.app.AlertDialog

class CalculateActivity : AppCompatActivity() {

    companion object {
        const val PUMP = "pump"
        const val INVOICE = "invoice"
    }

    lateinit var btnBuyFuel: Button
    lateinit var spCars: Spinner
    lateinit var spPumps: Spinner
    lateinit var rgLiterOrMoney: RadioGroup
    lateinit var etLiterOrMoney: EditText

    //   var selectedRadioButton: RadioButton? = null
    var calculatedPrice: Double? = null
    var calculatedLiter: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        rgLiterOrMoney = findViewById(R.id.rgLiterOrMoney)
        etLiterOrMoney = findViewById(R.id.etLiterOrMoney)
        btnBuyFuel = findViewById(R.id.btnBuyFuel)
        spCars = findViewById(R.id.spCar)
        spPumps = findViewById(R.id.spPump)


        /*        rgLiterOrMoney.setOnCheckedChangeListener { group, checkedId ->                                   //basılan radiobuttonu tutuyor. Full butona basılmadıysa enable
                    selectedRadioButton = findViewById(checkedId)
                    selectedRadioButton?.let {
                        etLiterOrMoney.isEnabled = it.id != R.id.rdbtnFull
                    } ?: kotlin.run {
                        showAlert("Warning","Please Choose a Option")
                    }
                }
        */

        val carNames = Database.cars.map { it.getListName() }                                             //cars listesini spinner'a basma
        spCars.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carNames)

        val pumpNames = Database.pumps.map { it.no }                                                    //pump listesini spinner'a basma
        spPumps.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pumpNames)


        btnBuyFuel.setOnClickListener {
            if (!etLiterOrMoney.text.isNullOrEmpty()) {

                val enterAmount = etLiterOrMoney.text.toString().toDouble()

                val selectedCarSpinner = Database.cars[spCars.selectedItemPosition]
                val selectedPumpSpinner = Database.pumps[spPumps.selectedItemPosition]


                if (rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnLtr) {
                    if (selectedCarSpinner.capacity > (enterAmount + selectedCarSpinner.currentFuelAmount)) {
                        calculatedLiter = enterAmount
                        calculatedPrice = (enterAmount * selectedCarSpinner.fuelType.price)
                    } else {
                        showAlert("Fazla Yakıt", "Depo kapasitesi aşıldı.")
                    }

                } else if (rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnMoney) {
                    if (enterAmount / selectedCarSpinner.fuelType.price < selectedCarSpinner.capacity - selectedCarSpinner.currentFuelAmount) {
                        calculatedLiter = enterAmount / selectedCarSpinner.fuelType.price
                        calculatedPrice = enterAmount
                    } else {
                        showAlert("Fazla Para", "Deponuz o kadar almaz")
                    }
                } else if (rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnFull) {
                    val liter = (selectedCarSpinner.capacity - selectedCarSpinner.currentFuelAmount)
                    calculatedPrice = (liter * selectedCarSpinner.fuelType.price)
                    calculatedLiter = liter
                } else {
                    showAlert("Uyarı", "Lütfen Bir Seçenek Belirleyin")
                }

                val intent = Intent(this, PrintActivity::class.java)

                intent.putExtra(INVOICE, Invoice(2, calculatedPrice!!, calculatedLiter!!, selectedCarSpinner, Database.employee1))
                intent.putExtra(PUMP, selectedPumpSpinner)
                startActivity(intent)

            } else {
                showAlert("Warnings", "Please Enter a Amount")
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message).create().show()
    }
}
