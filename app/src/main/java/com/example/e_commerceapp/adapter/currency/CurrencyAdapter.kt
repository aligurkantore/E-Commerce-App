package com.example.e_commerceapp.adapter.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.databinding.ItemCurrencyBinding
import com.example.e_commerceapp.models.datamodels.profile.Currency

class CurrencyAdapter(
    private val currencyList: List<Currency>,
    private val clickCurrency: (Int) -> Unit,
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyVH>() {

    inner class CurrencyVH(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyVH {
        val view = ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: CurrencyVH, position: Int) {
        with(holder.binding) {
            val currencyData = currencyList[position]
            textViewCurrency.text = currencyData.currency
            linearCurrency.setOnClickListener { clickCurrency.invoke(position) }
        }
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }
}