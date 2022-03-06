package com.example.converte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.converte.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Log.e("test",binding.rgStart.checkedRadioButtonId.toString() )
        //Log.e("test:", findRadioButton(binding.teStart.hint).toString())

        binding.btConvert.setOnClickListener { convertOnClick() }
        binding.btSwitch.setOnClickListener { switchOnClick() }


        binding.teStart.setOnClickListener{ teClick(binding.teStart) }
        binding.teFinish.setOnClickListener{ teClick(binding.teFinish) }

    }

    private fun teClick(that: TextInputEditText) {
        val thatRadioButtonIt = findRadioButton(that.hint)
        Log.e("test", thatRadioButtonIt.toString())
        if(binding.rgStart.checkedRadioButtonId != thatRadioButtonIt)
            binding.rgStart.check(thatRadioButtonIt)
    }

    private fun findRadioButton(key: CharSequence?): Int{
        return when(key){
            binding.rbTeaspoon.text -> R.id.rbTeaspoon
            binding.rbTablespoon.text -> R.id.rbTablespoon
            binding.rbCup.text -> R.id.rbCup
            binding.rbPint.text -> R.id.rbPint
            binding.rbFluidOunce.text -> R.id.rbFluidOunce
            else -> -1
        }
    }

    private fun switchOnClick() {
        val startHint = binding.teStart.hint
        val finishHint = binding.teFinish.hint
        binding.teStart.hint = finishHint
        binding.teFinish.hint = startHint

        val startText = binding.teStart.text
        val finishText = binding.teFinish.text
        binding.teStart.text = finishText
        binding.teFinish.text = startText
    }


    private fun convertOnClick() {

        if (!binding.teStart.text.isNullOrEmpty()) bindingConverting(
            binding.teStart,
            binding.teFinish
        )
        else if (!binding.teFinish.text.isNullOrEmpty()) bindingConverting(
            binding.teFinish,
            binding.teStart
        )

    }

    private fun bindingConverting(from: TextInputEditText, to: TextInputEditText) {

        // Use hints to understand from which unit to which we are converting
        val convertedValue =
            convert(from.hint.toString(), to.hint.toString(), from.text.toString().toDouble())
        to.text = Editable.Factory.getInstance().newEditable(convertedValue.toString())
    }

    // Convert from one unit of measurement to another
    private fun convert(from: String, to: String, value: Double): Double {
        return (value * weightValue(to)) / weightValue(from)
    }

    // Use the hint to find the unit weight
    private fun weightValue(hint: String): Double {
        return when (hint) {
            getString(R.string.teaspoon) -> WEIGHT_TEASPOON
            getString(R.string.tablespoon) -> WEIGHT_TABLESPOON
            getString(R.string.cup) -> WEIGHT_CUP
            getString(R.string.pint) -> WEIGHT_PINT
            getString(R.string.fluid_ounce) -> WEIGHT_FLUID_OUNCE
            else -> -1.0
        }
    }

}