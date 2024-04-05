package com.example.e_commerceapp.ui.adapters.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ItemCategoriesBinding

class CategoryAdapter(
    private val categoryList: List<String>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedItemPosition: Int = 0

    inner class CategoryViewHolder(val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder.binding) {
            textViewCategoryName.text =
                categoryList[position].replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            val isSelected = position == selectedItemPosition
            linearLayoutItem.setBackgroundResource(if (isSelected) R.drawable.item_selected_categories else R.drawable.item_categories_background)
            textViewCategoryName.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    if (isSelected) R.color.white else R.color.black
                )
            )
            linearLayoutItem.setOnClickListener {
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = holder.adapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                clickListener.onCategoryClicked(selectedItemPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface ItemClickListener {
        fun onCategoryClicked(position: Int)
    }
}
