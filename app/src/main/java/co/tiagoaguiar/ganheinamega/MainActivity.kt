package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Aqui onde você decide o que o app deve fazer
        setContentView(R.layout.activity_main)

        //buscar os objetos e ter a referencia deles
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        // banco de dados de preferencias
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        // ou usar um valor de string padrao!
        // ou retornar null e nao mostrar nada!

        // if -> let
        /*
        if (result != null) {
            txtResult.text = "Ultima aposta: $result"
        }
         */
        result?.let {
            txtResult.text = "Ultima aposta: $it"
        }

        //eventos de touch
        // Opcao 3: mais simples possivel - bloco de codigo que sera disparado pelo onClickListener
        btnGenerate.setOnClickListener {
            //Aqui podemos adicionar nossa logica de programacao, porque será disparado depois do evento de touch do usuario

            val text = editText.text.toString()

            numberGenerator(text,txtResult)
        }
    }
    private fun numberGenerator(text: String, txtResult: TextView){
        //validar quando o campo é vazio
        //validar se o campo informado é entre 6 e 15
        if (text.isNotEmpty()){

            val qtd = text.toInt()//convert string  para inteiro

            if (qtd >= 6 && qtd <= 15){

                val numbers = mutableSetOf<Int>()
                val random = Random()

                while(true){
                    val number = random.nextInt(60) //0...59
                    numbers.add(number + 1)

                    if(numbers.size == qtd){
                        break
                    }
                }

                txtResult.text = numbers.joinToString(" - ")

            } else {
            Toast.makeText(this , "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
        }
        } else {
            Toast.makeText(this , "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
        }

    }
}