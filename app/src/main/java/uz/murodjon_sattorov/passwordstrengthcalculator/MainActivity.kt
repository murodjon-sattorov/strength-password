package uz.murodjon_sattorov.passwordstrengthcalculator

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import uz.murodjon_sattorov.passwordstrengthcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private var color = R.color.weak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val passwordStrengthCalculator = PasswordStrengthCalculator()
        mainBinding.passwordInput.addTextChangedListener(passwordStrengthCalculator)

        passwordStrengthCalculator.strengthLevel.observe(this, Observer {
            displayStrengthLevel(it)
        })

        passwordStrengthCalculator.strengthColor.observe(this, Observer {
            color = it
        })

        passwordStrengthCalculator.lowerCase.observe(this, Observer {
            displayPasswordSuggestions(it, mainBinding.lowerCaseImg, mainBinding.lowerCaseTxt)
        })
        passwordStrengthCalculator.upperCase.observe(this, Observer {
            displayPasswordSuggestions(it, mainBinding.upperCaseImg, mainBinding.upperCaseTxt)
        })
        passwordStrengthCalculator.digit.observe(this, Observer {
            displayPasswordSuggestions(it, mainBinding.digitImg, mainBinding.digitTxt)
        })
        passwordStrengthCalculator.specialChar.observe(this, Observer {
            displayPasswordSuggestions(it, mainBinding.specialCharImg, mainBinding.specialCharTxt)
        })

    }

    private fun displayPasswordSuggestions(value: Int?, lowerCaseImg: ImageView, lowerCaseTxt: TextView) {
        if (value == 1){
            lowerCaseImg.setColorFilter(ContextCompat.getColor(this, R.color.very_strong))
            lowerCaseTxt.setTextColor(ContextCompat.getColor(this, R.color.very_strong))
        }else{
            lowerCaseImg.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            lowerCaseTxt.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel?) {
        mainBinding.button.isEnabled = strengthLevel == StrengthLevel.VERYSTRONG

        mainBinding.strengthLevelTxt.text = strengthLevel?.name
        mainBinding.strengthLevelTxt.setTextColor(ContextCompat.getColor(this, color))
        mainBinding.strengthLevelIndicator.setBackgroundColor(ContextCompat.getColor(this, color))
    }
}