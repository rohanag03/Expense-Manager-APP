package com.example.budgettracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: java.util.ArrayList<Transaction>
    private lateinit var  transactionAdapter: TransactionAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactions = arrayListOf(
            Transaction("Weekend Budget", 400.00),
            Transaction("Bananas", -50.00),
            Transaction("Gasoline", -40.00),
            Transaction("Breakfast", -9.99),
            Transaction("Water Bottles", -20.00),
            Transaction("Parking", -40.00)
        )

        transactionAdapter = TransactionAdapter(transactions)
        linearlayoutManager = LinearLayoutManager(this)

        binding.recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearlayoutManager

        }

        updateDashboard()

        findViewById<FloatingActionButton>(R.id.addBtn).setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDashboard(){
        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter{ it.amount>0}.map{it.amount}.sum()
        val expenseAmount = totalAmount - budgetAmount

        binding.balance.text = "₹ %.2f".format(totalAmount)
        binding.budget.text = "₹ %.2f".format(budgetAmount)
        binding.expense.text = "₹ %.2f".format(expenseAmount)
    }

}