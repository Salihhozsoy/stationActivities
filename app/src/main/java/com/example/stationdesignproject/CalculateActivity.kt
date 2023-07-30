package com.example.stationdesignproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
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

    var selectedRadioButton: RadioButton? = null
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


        rgLiterOrMoney.setOnCheckedChangeListener { group, checkedId ->                                   //basılan radiobuttonu tutuyor. Full butona basılmadıysa enable
            selectedRadioButton = findViewById(checkedId)
            selectedRadioButton?.let {
                etLiterOrMoney.isEnabled = it.id != R.id.rdbtnFull
            } ?: kotlin.run {
                showAlert("Warning", "Please Choose a Option")
            }
        }

        val carNames = Database.cars.map { it.getListName() }                                             //cars listesini spinner'a basma
        spCars.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carNames)

        val pumpNames = Database.pumps.map { it.no }                                                    //pump listesini spinner'a basma
        spPumps.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pumpNames)


        btnBuyFuel.setOnClickListener {

            if (!etLiterOrMoney.text.isNullOrEmpty() || rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnFull) {
                var isError: Boolean = false

                val selectedCarSpinner = Database.cars[spCars.selectedItemPosition]
                val selectedPumpSpinner = Database.pumps[spPumps.selectedItemPosition]
                val enterAmountTxt = etLiterOrMoney.text.toString()
                var enterAmount: Double = 0.0

                if (enterAmountTxt.isNullOrEmpty()) enterAmount = selectedCarSpinner.getEmptyFuel() else enterAmount = etLiterOrMoney.text.toString().toDouble()


                if (rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnLtr) {
                    if (selectedCarSpinner.capacity > (enterAmount + selectedCarSpinner.currentFuelAmount)) {
                        calculatedLiter = enterAmount
                        calculatedPrice = (enterAmount * selectedCarSpinner.fuelType.price)
                    } else {
                        isError = true
                        showAlert("Fazla Yakıt", "Depo kapasitesi aşıldı.")
                    }


                } else if (rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnMoney) {
                    if (enterAmount / selectedCarSpinner.fuelType.price < (selectedCarSpinner.capacity - selectedCarSpinner.currentFuelAmount)) {
                        calculatedLiter = enterAmount / selectedCarSpinner.fuelType.price
                        calculatedPrice = enterAmount
                    } else {
                        isError = true
                        showAlert("Fazla Para", "Deponuz o kadar almaz")
                    }


                } else if (rgLiterOrMoney.checkedRadioButtonId == R.id.rdbtnFull) {
                    calculatedPrice= selectedCarSpinner.getEmptyFuel()*selectedCarSpinner.fuelType.price
                    calculatedLiter = selectedCarSpinner.getEmptyFuel()
                }

                if (!isError) {
                    val intent = Intent(this, PrintActivity::class.java)
                    intent.putExtra(INVOICE, Invoice(2, calculatedPrice!!, calculatedLiter!!, selectedCarSpinner, Database.employee1))
                    intent.putExtra(PUMP, selectedPumpSpinner)
                    startActivity(intent)
                }
            }
        }
    }
    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message).create().show()
    }
}
