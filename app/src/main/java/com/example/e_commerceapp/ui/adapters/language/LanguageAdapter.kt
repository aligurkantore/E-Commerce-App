package com.example.e_commerceapp.ui.adapters.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.models.datamodels.profile.Language
import com.example.e_commerceapp.databinding.ItemLanguageBinding

class LanguageAdapter(
    private val languageList: List<Language>,
    private val clickLanguage: (Int) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageVH>() {

    inner class LanguageVH(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageVH {
        val view = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageVH(view)
    }

    override fun onBindViewHolder(holder: LanguageVH, position: Int) {
        with(holder.binding) {
            val languageData = languageList[position]
            imageFlag.setImageResource(languageData.flag)
            textViewLanguage.text = languageData.languageName
            linearLanguageCategory.setOnClickListener { clickLanguage.invoke(position) }
        }
    }

    override fun getItemCount(): Int = languageList.size
}