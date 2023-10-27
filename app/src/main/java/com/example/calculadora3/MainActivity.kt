package com.example.calculadora3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var resultTextView: TextView
    val calculo = Calculo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        // Manejar clic en números del 0 al 9
        val numberButtons = arrayOf(
            findViewById<Button>(R.id.button0),
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )

        for (i in 0..9) {
            numberButtons[i].setOnClickListener { onNumberClick(i) }
        }

        // Manejar clic en botones de operación (+, -, x, /)
        val operationButtons = arrayOf(
            findViewById<Button>(R.id.buttonPlus),
            findViewById<Button>(R.id.buttonMinus),
            findViewById<Button>(R.id.buttonMultiply),
            findViewById<Button>(R.id.buttonDivide)
        )

        val operationSymbols = arrayOf("+", "-", "x", "/")

        for (i in 0 until operationButtons.size) {
            operationButtons[i].setOnClickListener { onOperationClick(operationSymbols[i]) }
        }

        // Manejar clic en botón CE (borrar)
        val ceButton = findViewById<Button>(R.id.buttonCE)
        ceButton.setOnClickListener { onClearClick() }

        // Manejar clic en botón igual (=)
        val equalsButton = findViewById<Button>(R.id.buttonEquals)
        equalsButton.setOnClickListener { onEqualsClick() }
    }

    private fun onNumberClick(number: Int) {
        calculo.setNumClicked(number)
        updateResult()
    }

    private fun onOperationClick(operation: String) {
        calculo.setOperation(operation)
        updateResult()
    }

    private fun onClearClick() {
        calculo.clear()
        updateResult()
    }

    private fun onEqualsClick() {
        if (calculo.num1 != 0.0 && calculo.num2 != 0.0 && calculo.operacion.isNotEmpty()) {
            calculo.calculate()
            updateResult()
        } else {
            // Mostrar mensaje de error si no se pueden realizar cálculos
            Toast.makeText(this, "Debe introducir 2 números y una operación para mostrar un resultado.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateResult() {
        resultTextView.text = calculo.num1.toString()
        if (calculo.operacion.isNotEmpty()) {
            resultTextView.append(" ${calculo.operacion} ")
        }
        if (calculo.num2 != 0.0) {
            resultTextView.append(calculo.num2.toString())
        }
    }
}

class Calculo {
    var num1: Double = 0.0
    var num2: Double = 0.0
    var operacion: String = ""
    var resultado: Double = 0.0

    fun setNumClicked(number: Int) {
        if (operacion.isEmpty()) {
            num1 = num1 * 10 + number
        } else {
            num2 = num2 * 10 + number
        }
    }

    fun setOperation(operation: String) {
        if (num1 != 0.0 && num2 != 0.0) {
            calculate()
        }
        operacion = operation
    }

    fun calculate() {
        when (operacion) {
            "+" -> resultado = num1 + num2
            "-" -> resultado = num1 - num2
            "x" -> resultado = num1 * num2
            "/" -> {
                if (num2 != 0.0) {
                    resultado = num1 / num2
                }
            }
        }
        num1 = resultado
        num2 = 0.0
        operacion = ""
    }

    fun clear() {
        num1 = 0.0
        num2 = 0.0
        operacion = ""
        resultado = 0.0
    }
}

