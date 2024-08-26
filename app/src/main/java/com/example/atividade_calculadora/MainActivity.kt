package com.example.atividade_calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.atividade_calculadora.ui.theme.Atividade_calculadoraTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

const val MULTIPLICACAO = "*"
const val DIVISAO = "/"
const val SOMA = "+"
const val SUBTRACAO = "-"

const val CLEAR = "C"
const val EQUAL = "="

class CalculatorViewModel : ViewModel() {
    val tokensList = mutableStateListOf<String>()
    var result = mutableStateOf("")

    private val operators = setOf(MULTIPLICACAO, DIVISAO, SOMA, SUBTRACAO)

    fun onClickBtn(value: String) {
        val lastToken = tokensList.lastOrNull() ?: ""

        when {
            value == CLEAR -> {
                tokensList.clear()
            }

            value == EQUAL -> {
                evaluate()
            }

            // é um operador
            operators.contains(value) -> {
                if (tokensList.isNotEmpty() && !operators.contains(lastToken)) {
                    tokensList.add(value)
                }
            }

            // é um numero comum
            else -> {
                if (lastToken.isNotBlank() && !operators.contains(lastToken)) {
                    tokensList[tokensList.size - 1] = lastToken + value
                } else {
                    tokensList.add(value)
                }
            }
        }
    }

    fun applyOperator(a: Double, b: Double, op: String): Double {
        return when (op) {
            "*" -> a * b
            "/" -> a / b
            "+" -> a + b
            "-" -> a - b
            else -> throw IllegalArgumentException("Operador desconhecido: $op")
        }
    }

    fun evaluate() {
        // implementar
        result.value = "Teste"
    }

}

@Composable
fun CalculatorButton(text: String, viewModel: CalculatorViewModel) {
    Button(
        onClick = { viewModel.onClickBtn(text) },
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
    ) {
        Text(text, fontSize = 24.sp)
    }
}

@Composable
fun CalculatorApp(viewModel: CalculatorViewModel = CalculatorViewModel()) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = viewModel.tokensList.joinToString(" "),
                style = TextStyle(fontSize = 32.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = viewModel.result.value,
                style = TextStyle(fontSize = 32.sp),
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            val rows: List<List<String>> = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("C", "0", "+", "="),
            )

            for (row in rows) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (symbol in row) {
                        CalculatorButton(symbol, viewModel)
                    }
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}