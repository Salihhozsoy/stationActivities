package com.example.stationdesignproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginlayout)

        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener() {
            val intent = Intent(this,CalculateActivity::class.java)
            startActivity(intent)
        }
    }
}