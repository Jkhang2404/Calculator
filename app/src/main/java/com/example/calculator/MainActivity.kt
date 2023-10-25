package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumberic = false
    var stateError = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllclearClick(view: View) {

        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastNumberic = false
        binding.resultTv.visibility = View.GONE


    }


    fun onEqualClick(view: View) {

        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString()


    }


    fun onDigitalClick(view: View) {
        if(stateError){

            binding.dataTv.text = (view as Button).text
            stateError = true

        }else{
            binding.dataTv.append((view as Button).text)
        }
        lastNumberic = true
        onEqual()

    }


    fun onOperatorClick(view: View) {
        if(!stateError && lastNumberic ){
            binding.dataTv.append((view as Button).text)
            lastNumberic = false
            onEqual()
        }

    }


    fun onClearClick(view: View) {
        binding.dataTv.text = ""
        lastNumberic = false


    }


    fun onDeleteClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)

        try{
            val lastChar = binding.dataTv.text.toString().last()

            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.dataTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error",e.toString())
        }

    }


    fun onEqual(){
        if(lastNumberic && !stateError){
            val txt = binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try{
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE

                binding.resultTv.text = result.toString()


            }catch (ex: ArithmeticException){
                Log.e("evaluate error",toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumberic = true
            }

        }
    }
}