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

        // aqui onde voce decide o que o app fazer...
        setContentView(R.layout.activity_main)


        // buscar os objetos e ter a referencia deles
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



        // opção 1: XML

        // opção 2: variavel que seja do tipo (objeto anonimo) View.OnClickListener (interface)
        // btnGenerate.setOnClickListener(buttonClickListener)

        // opção 3: mais simples possivel - bloco de código que será disparado pelo onClickListener
        btnGenerate.setOnClickListener {
            // aqui podemos colocar nossa logica de programação. porque será disparado depois do
            // evento de touch do usuário

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        // aqui é a falha numero 1
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt() // convert string para inteiro

        // aqui é a falha numero 2
        if (qtd < 6 || qtd > 15) {
            // deu falha
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        // aqui é o sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60) // 0...59
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply() // assincrona
        
        /* // alternativa 2
        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }
         */

        // commit -> salvar de forma sincrona (bloquear a interface)
            //       informar se teve sucesso ou nao

        // apply -> salvar de forma asincrona (nao vai bloquear a interface)
            //       nao informa se teve sucesso ou nao
    }


//    val buttonClickListener = View.OnClickListener {
//        Log.i("Teste", "botao clicado!!!")
//    }

//    val buttonClickListener = object : View.OnClickListener {
//        // quem chama o onclick é o proprio SDK do android que dispara apos o evento de touch
//        override fun onClick(v: View?) {
//            Log.i("Teste", "botao clicado!!!")
//        }
//
//    }

    // opção 1: XML
//    fun buttonClicked(view: View) {
//        Log.i("Teste", "botao clicado!!!")
//    }

}