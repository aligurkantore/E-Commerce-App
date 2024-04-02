package com.example.e_commerceapp.util

import android.content.Context
import com.example.e_commerceapp.R
import com.example.e_commerceapp.models.datamodels.profile.Category
import com.example.e_commerceapp.models.datamodels.profile.Currency
import com.example.e_commerceapp.models.datamodels.profile.Language
import com.example.e_commerceapp.util.Constants.EUR
import com.example.e_commerceapp.util.Constants.GBP
import com.example.e_commerceapp.util.Constants.TRY
import com.example.e_commerceapp.util.Constants.USD
import javax.inject.Inject

class SetCategories @Inject constructor() {
    fun setProfileCategories(context: Context): List<Category> {
        val categoryList = mutableListOf<Category>()
        categoryList.add(
            Category(
                R.drawable.my_profile, context.getString(R.string.my_profile)
            )
        )
        categoryList.add(
            Category(
                R.drawable.order, context.getString(R.string.my_order)
            )
        )
        categoryList.add(
            Category(
                R.drawable.shopping_cart, context.getString(R.string.my_cart)
            )
        )
        categoryList.add(
            Category(
                R.drawable.score, context.getString(R.string.my_score)
            )
        )
        categoryList.add(
            Category(
                R.drawable.notification, context.getString(R.string.notification)
            )
        )
        categoryList.add(
            Category(
                R.drawable.payment, context.getString(R.string.payment_information)
            )
        )
        categoryList.add(
            Category(
                R.drawable.help, context.getString(R.string.help)
            )
        )
        categoryList.add(
                Category(
                    R.drawable.logout, context.getString(R.string.log_out)
                )
                )
        return categoryList
    }


    fun setLanguages(context: Context): List<Language>{
        val languageList = mutableListOf<Language>()
        languageList.add(
            Language(context.getString(R.string.english),R.drawable.united_kingdom)
        )
        languageList.add(
            Language(context.getString(R.string.turkish),R.drawable.turkey)
        )
        languageList.add(
            Language(context.getString(R.string.german),R.drawable.germany)
        )
        languageList.add(
            Language(context.getString(R.string.french),R.drawable.france)
        )
        return languageList
    }

    fun setCurrency(): List<Currency>{
        val currencyList = mutableListOf<Currency>()
        currencyList.add(Currency(USD))
        currencyList.add(Currency(GBP))
        currencyList.add(Currency(TRY))
        currencyList.add(Currency(EUR))
        return currencyList
    }
}