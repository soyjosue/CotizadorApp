package com.example.cotizador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class cotizacion_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion_activity)

        findViewById<TextView>(R.id.tvTotal).text = "EL TOTAL ES: $${intent.getStringExtra("RESULT")}"
        findViewById<TextView>(R.id.tvBrand).text = "Marca: ${intent.getStringExtra("BRAND")}"
        findViewById<TextView>(R.id.tvPlan).text = "Plan: ${intent.getStringExtra("TYPE")}"
        findViewById<TextView>(R.id.tvYear).text = "Año del Auto: ${intent.getStringExtra("YEAR")}"

        val ibBack: ImageButton = findViewById(R.id.ibBack)

        ibBack.setOnClickListener {
            finish()
        }
    }
}