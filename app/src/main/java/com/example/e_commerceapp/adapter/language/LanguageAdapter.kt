package com.example.e_commerceapp.adapter.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.models.datamodels.profile.Language
import com.example.e_commerceapp.databinding.ItemLanguageBinding

class LanguageAdapter(
    private val languageList: List<Language>,
    private val clickLanguage: (Int) -> Unit,
) : RecyclerView.Adapter<LanguageAdapter.LanguageVH>() {

    inner class LanguageVH(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageVH {
        val view = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageVH(view)
    }

    override fun onBindViewHolder(holder: LanguageVH, position: Int) {
        with(holder.binding) {
            val data = languageList[position]
            imageFlag.setImageResource(data.flag)
            textViewLanguage.text = data.languageName
            linearLanguageCategory.setOnClickListener { clickLanguage.invoke(position) }
        }
    }

    override fun getItemCount(): Int {
        return languageList.size
    }
}