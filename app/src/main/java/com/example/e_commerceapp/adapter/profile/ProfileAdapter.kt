package com.example.e_commerceapp.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.models.datamodels.profile.Category
import com.example.e_commerceapp.databinding.ItemProfileCategoryBinding

class ProfileAdapter(
    private var categoryList: List<Category>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<ProfileAdapter.ProfileVH>() {

    private var selectedPosition: Int? = null

    inner class ProfileVH(val binding: ItemProfileCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        val view =
            ItemProfileCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileVH(view)
    }

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        with(holder.binding) {
            val data = categoryList[position]
            imageCategory.setImageResource(data.categoryImage)
            textViewCategoryName.text = data.categoryName

            linearProfileCategory.setOnClickListener {
                selectedPosition = holder.adapterPosition
                selectedPosition?.let { _selected -> clickListener.onClick(data.categoryName,_selected) }
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface ItemClickListener {
        fun onClick(categoryName: String,position: Int)
    }
}