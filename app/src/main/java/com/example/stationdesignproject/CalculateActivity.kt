package com.example.stationdesignproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CalculateActivity : AppCompatActivity() {

    companion object{
      const val LiterOrMoney ="literormoney"
    }

    lateinit var btnBuyFuel:Button
    lateinit var spCars: Spinner
    lateinit var spPumps: Spinner
    lateinit var rgLiterOrMoney:RadioGroup
    lateinit var etLiterOrMoney:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        rgLiterOrMoney = findViewById(R.id.rgLiterOrMoney)
        etLiterOrMoney = findViewById(R.id.etLiterOrMoney)
        btnBuyFuel = findViewById(R.id.btnBuyFuel)
        spCars =findViewById(R.id.spCar)
        spPumps=findViewById(R.id.spPump)


        rgLiterOrMoney.setOnCheckedChangeListener { group, checkedId ->                                   //basılan radiobuttonu tutuyor. Full butona basılmadıysa enable
            val selectedRadioButton: RadioButton = findViewById(checkedId)
            etLiterOrMoney.isEnabled = selectedRadioButton.id != R.id.rdbtnFull
        }


        val carNames = Database.cars.map { it.getListName() }                                             //cars listesini spinner'a basma
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carNames)
        spCars.adapter = adapter


/*
        spCars.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val items = Database.cars
                val selected = items.get(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("lutfen secim yap....")
            }

        }
*/

        val pumpNames = Database.pumps.map { it.name }                                                           //pump listesini spinner'a basma
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, pumpNames)
        spPumps.adapter = adapter2


        btnBuyFuel.setOnClickListener{

            if(!etLiterOrMoney.text.isNullOrEmpty()){

                val intent = Intent(this,PrintActivity::class.java)
                intent.putExtra(LiterOrMoney, etLiterOrMoney.text.toString().toDouble())
                startActivity(intent)
            }else{
                AlertDialog.Builder(this)
                    .setTitle("WARNING")
                    .setMessage("PLEASE ENTER A AMOUNT")
                    .setCancelable(false)
                    .setPositiveButton("OKEY") { dialog, which ->}
                    .create().show()
            }
        }
    }
}