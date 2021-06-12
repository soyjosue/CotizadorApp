package com.example.cotizador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var spinnerBrand: Spinner? = null
    var spinnerYear: Spinner? = null
    var currentDate:Int = 0
    var startingPrice: Float = 2000.0F
    var result: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentDate = SimpleDateFormat("yyyy").format(Date()).toInt()

        // Agregamos las marcas al SpinnerBrand
        addOpcionsBrands()
        // Agregamos los años al SpinnerYear
        addOpcionsYears()

        val btnQuote: Button = findViewById(R.id.btnQuote)

        btnQuote.setOnClickListener {
            startQuote()
        }
    }

    private fun startQuote() {

        var strEmpy = "-- Seleccione --"

        var rbBasic: RadioButton = findViewById(R.id.rbBasic)
        var rbFull: RadioButton = findViewById(R.id.rbFull)

        var brand: String = spinnerBrand?.selectedItem.toString()
        var year: String = spinnerYear?.selectedItem.toString()
        var type: String = ""

        if(typeInsurance(rbBasic) != -1) {
            type = "Basico"
        } else if(typeInsurance(rbFull) != -1) {
            type = "Completo"
        } else {
            type = ""
        }

        if(brand == strEmpy || year == strEmpy || type.isEmpty() ) {
            Toast.makeText(this, "Debes llenar todos los campos.", Toast.LENGTH_LONG).show()
            return
        }
        result = startingPrice

        val intent = Intent(this, cotizacion_activity::class.java)
        intent.putExtra("BRAND", brand)
        intent.putExtra("TYPE", type)
        intent.putExtra("YEAR", year)

        // Obtener la diferencia de años
        var yearDifference: Int = currentDate - year.toInt()

        // Por cada ano hay que restar el 3%
        result -= (yearDifference.toFloat()*3.0F)/100.0F * result

        // Americano 15
        // Asiatico 5%
        // Europeo 30%
        when(brand) {
            "Americano" -> result *= 1.15F
            "Europeo" -> result *= 1.30F
            else -> result *= 1.05F
        }

        // Basico Aumenta 20%
        // Completo 50%
        when(type) {
            "Basico" ->
                result *= 1.20F
            "Completo" ->
                result *= 1.50F
        }


        intent.putExtra("RESULT", result.toString())
        startActivity(intent)

    }

    private fun typeInsurance(rb: RadioButton): Int {
        return if(rb.isChecked) {
            rb.id
        } else {
            -1
        }
    }

    private fun addOpcionsBrands() {

        spinnerBrand = findViewById(R.id.spinnerBrand)

        ArrayAdapter.createFromResource(
            this, R.array.marcas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerBrand?.adapter = adapter
        }

    }

    private fun addOpcionsYears() {
        spinnerYear = findViewById(R.id.spinnerYear)

        // Creando Array List
        val yearArray:ArrayList<String> = ArrayList()
        yearArray.add("-- Seleccione --")

        for(i in 0..9) {
            yearArray.add(
                (currentDate - i).toString()
            )
        }

        ArrayAdapter(this, android.R.layout.simple_spinner_item,
            yearArray
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerYear?.adapter = adapter
        }


    }


}