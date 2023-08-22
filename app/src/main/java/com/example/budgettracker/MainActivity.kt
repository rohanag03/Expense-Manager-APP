package com.example.budgettracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.budgettracker.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: List<Transaction>
    private lateinit var  transactionAdapter: TransactionAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactions = arrayListOf()

        transactionAdapter = TransactionAdapter(transactions)
        linearlayoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this,
        AppDatabase::class.java,
        "transactions").build()

        binding.recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearlayoutManager

        }


        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun fetchAll(){
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()

            runOnUiThread{
                updateDashboard()
                transactionAdapter.setData(transactions)
            }
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

    override fun onResume() {
        super.onResume()
        fetchAll()
    }

}