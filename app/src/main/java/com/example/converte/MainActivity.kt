package com.example.converte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.example.converte.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btConvert.setOnClickListener { convertOnClick() }
        binding.btSwitch.setOnClickListener { switchOnClick() }


    }



    private fun switchOnClick(){
        val startHint = binding.teStart.hint
        val finishHint = binding.teFinish.hint
        binding.teStart.hint = finishHint
        binding.teFinish.hint = startHint

        val startText = binding.teStart.text
        val finishText = binding.teFinish.text
        binding.teStart.text = finishText
        binding.teFinish.text = startText
    }

    private fun convertOnClick(){

        if(!binding.teStart.text.isNullOrEmpty()){

            // Use hints to understand from which unit to which we are converting
            val convertedValue = convert(binding.teStart.hint.toString(),binding.teFinish.hint.toString(), binding.teStart.text.toString().toDouble())

            binding.teFinish.text = Editable.Factory.getInstance().newEditable(convertedValue.toString())
            return
        }
        else if(!binding.teFinish.text.isNullOrBlank()){

            // Use hints to understand from which unit to which we are converting
            val convertedValue = convert(binding.teFinish.hint.toString(), binding.teStart.hint.toString(), binding.teFinish.text.toString().toDouble())

            binding.teStart.text = Editable.Factory.getInstance().newEditable(convertedValue.toString())
            return
        }
    }

    // Convert from one unit of measurement to another
    private fun convert( from : String, to : String, value : Double) : Double{
        return (value * weightValue(to)) / weightValue(from)
    }

    // Use the hint to find the unit weight
    private fun weightValue(hint : String) : Double{
        return when(hint){
            getString(R.string.teaspoon) -> WEIGHT_TEASPOON
            getString(R.string.tablespoon) -> WEIGHT_TABLESPOON
            getString(R.string.cup) -> WEIGHT_CUP
            getString(R.string.pint) -> WEIGHT_PINT
            getString(R.string.fluid_ounce) -> WEIGHT_FLUID_OUNCE
            else -> -1.0
        }
    }

}