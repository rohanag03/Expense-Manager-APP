package com.example.budgettracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val labelinput = findViewById<TextInputEditText>(R.id.labelInput)
        labelinput.addTextChangedListener {
            if(it!!.count()>0)
                findViewById<TextInputLayout>(R.id.labelLayout).error = null
        }

        val amountinput = findViewById<TextInputEditText>(R.id.amountInput)
        amountinput.addTextChangedListener {
            if(it!!.count()>0)
                findViewById<TextInputLayout>(R.id.amountlayout).error = null
        }

        val addTransactionBtn = findViewById<Button>(R.id.addTransactionBtn)
        addTransactionBtn.setOnClickListener {
            val label = findViewById<TextInputEditText>(R.id.labelInput).text.toString()
            val amount = findViewById<TextInputEditText>(R.id.amountInput).text.toString().toDoubleOrNull()

            if(label.isEmpty())
                    findViewById<TextInputLayout>(R.id.labelLayout).error = "Please enter a valid label"

            if (amount==null)
                    findViewById<TextInputLayout>(R.id.amountlayout).error = "Please enter a valid amount"
        }

        findViewById<Button>(R.id.closeBtn).setOnClickListener {
            finish()
        }
        }


    }
