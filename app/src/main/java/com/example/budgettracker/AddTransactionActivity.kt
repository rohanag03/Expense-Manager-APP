package com.example.budgettracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            val description = findViewById<TextInputEditText>(R.id.DescriptionInput).text.toString()
            val amount = findViewById<TextInputEditText>(R.id.amountInput).text.toString().toDoubleOrNull()

            if(label.isEmpty())
                    findViewById<TextInputLayout>(R.id.labelLayout).error = "Please enter a valid label"

            else if (amount==null)
                    findViewById<TextInputLayout>(R.id.amountlayout).error = "Please enter a valid amount"
            else{
                val transaction = Transaction(0, label, amount, description)
                insert(transaction)
            }
        }

        findViewById<ImageButton>(R.id.closeBtn).setOnClickListener {
            finish()
        }
        }

    private fun insert(transaction: Transaction){
        val db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

        GlobalScope.launch{
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }


    }
