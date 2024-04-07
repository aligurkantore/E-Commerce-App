package com.example.e_commerceapp.ui.fragments.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.adapters.currency.CurrencyAdapter
import com.example.e_commerceapp.ui.adapters.language.LanguageAdapter
import com.example.e_commerceapp.ui.adapters.profile.ProfileAdapter
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentProfileBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.models.datamodels.profile.Currency
import com.example.e_commerceapp.models.datamodels.profile.LanguageModel
import com.example.e_commerceapp.util.Constants.CATEGORY
import com.example.e_commerceapp.util.Constants.CURRENCY
import com.example.e_commerceapp.util.Constants.DE
import com.example.e_commerceapp.util.Constants.EMAIL
import com.example.e_commerceapp.util.Constants.EN
import com.example.e_commerceapp.util.Constants.EUR
import com.example.e_commerceapp.util.Constants.FLAG
import com.example.e_commerceapp.util.Constants.FR
import com.example.e_commerceapp.util.Constants.GBP
import com.example.e_commerceapp.util.Constants.LANGUAGE
import com.example.e_commerceapp.util.Constants.LANGUAGE_NAME
import com.example.e_commerceapp.util.Constants.TR
import com.example.e_commerceapp.util.Constants.TRY
import com.example.e_commerceapp.util.Constants.USD
import com.example.e_commerceapp.ui.dialogs.CustomDialog
import com.example.e_commerceapp.util.SetCategories
import com.example.e_commerceapp.util.changeLanguage
import com.example.e_commerceapp.util.goneIf
import com.example.e_commerceapp.util.navigateSafe
import com.example.e_commerceapp.util.observeNonNull

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var languageAdapter: LanguageAdapter
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var setCategories: SetCategories
    private var isWelcomeDialogShown = false
    private var dialog: Dialog? = null

    override val viewModelClass: Class<out ProfileViewModel>
        get() = ProfileViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategories = SetCategories()
        setUpAdapter()
        setUpAppBar()
        checkUserLoginStatus(viewModel.isLoggedIn())
        updateLanguageViews()
        updateCurrencyViews()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonLogin.setOnClickListener {
                navigateSafe(R.id.action_profileFragment_to_loginFragment)
            }

            buttonRegister.setOnClickListener {
                navigateSafe(R.id.action_profileFragment_to_registerFragment)
            }

            linearSelectLanguage.setOnClickListener {
                showLanguagePopUp()
            }

            linearSelectCurrency.setOnClickListener {
                showCurrencyPopUp()
            }
        }
    }

    override fun setUpObservers() {
        viewModel.selectedCurrency.observeNonNull(viewLifecycleOwner) {
            saveCurrency(it.currency)
            binding?.textCurrency?.text = it.currency
        }
    }

    private fun setUpAdapter() {
        val allCategories = setCategories.setProfileCategories(mContext)
        val notLoggedInCustomer =
            allCategories.filterIndexed { index, _ -> listOf(2, 3, 4, 6).contains(index) }
        val isLoggedIn = viewModel.isLoggedIn()
        val categories = if (isLoggedIn) allCategories else notLoggedInCustomer

        profileAdapter = ProfileAdapter(categories,
            object : ProfileAdapter.ItemClickListener {
                override fun onClick(categoryName: String, position: Int) {
                    BaseShared.saveString(mContext, CATEGORY, categoryName)
                    val actionId = when {
                        (isLoggedIn && position == 2) || (!isLoggedIn && position == 0) -> R.id.action_profileFragment_to_cartFragment
                        isLoggedIn && position == 7 -> {
                            showLogoutDialog()
                            return
                        }

                        else -> R.id.action_profileFragment_to_categoryDetailFragment
                    }
                    navigateSafe(actionId)
                }
            })
        binding?.recyclerViewProfile?.apply {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun checkUserLoginStatus(isVisible: Boolean) {
        binding?.apply {
            buttonLogin goneIf isVisible
            buttonRegister goneIf isVisible
            val email: String? = BaseShared.getString(mContext, EMAIL, "")
            binding?.textViewName?.text = email ?: ""
            if (isVisible && !isWelcomeDialogShown) {
                showWelcomeDialog()
                isWelcomeDialogShown = true
            }
        }
    }


    private fun showLanguagePopUp() {
        val languageList = setCategories.setLanguages(mContext)
        languageAdapter = LanguageAdapter(languageList) { language ->
            when (language) {
                1 -> {
                    LanguageModel(
                        stringId = R.string.turkish,
                        drawableId = R.drawable.turkey,
                        lang = TR
                    )
                }

                2 -> {
                    LanguageModel(
                        stringId = R.string.german,
                        drawableId = R.drawable.germany,
                        lang = DE
                    )
                }

                3 -> {
                    LanguageModel(
                        stringId = R.string.french,
                        drawableId = R.drawable.france,
                        lang = FR
                    )
                }

                else -> {
                    LanguageModel(
                        stringId = R.string.english,
                        drawableId = R.drawable.united_kingdom,
                        lang = EN
                    )
                }
            }.also {
                context?.changeLanguage(it.lang)
                BaseShared.saveString(mContext, LANGUAGE, it.lang)
                saveLanguageAndFlag(getString(it.stringId), it.drawableId)
            }
            requireActivity().recreate()
        }

        setUpPopUp(languageAdapter)
    }

    private fun saveLanguageAndFlag(languageName: String, flag: Int) {
        BaseShared.saveString(mContext, LANGUAGE_NAME, languageName)
        BaseShared.saveInt(mContext, FLAG, flag)
    }

    private fun updateLanguageViews() {
        val selectedLanguage =
            BaseShared.getString(mContext, LANGUAGE_NAME, mContext.getString(R.string.english))
        val selectedFlag = BaseShared.getInt(mContext, FLAG, R.drawable.united_kingdom)

        binding?.apply {
            textLanguages.text = selectedLanguage
            imageFlag.setImageResource(selectedFlag)
        }
    }

    private fun showCurrencyPopUp() {
        val currencyList = setCategories.setCurrency()
        currencyAdapter = CurrencyAdapter(currencyList) { currency ->
            val selectedCurrency = when (currency) {
                1 -> GBP
                2 -> TRY
                3 -> EUR
                else -> USD
            }
            viewModel.setSelectedCurrency(Currency(selectedCurrency))
            dialog?.dismiss()
        }

        setUpPopUp(currencyAdapter)
    }

    private fun saveCurrency(currency: String) {
        BaseShared.saveString(mContext, CURRENCY, currency)
    }

    private fun updateCurrencyViews() {
        val selectedCurrency =
            BaseShared.getString(mContext, CURRENCY, USD)

        binding?.apply {
            textCurrency.text = selectedCurrency
        }
    }

    private fun setUpPopUp(adapter: RecyclerView.Adapter<*>) {
        dialog = Dialog(mContext)
        dialog?.apply {
            setContentView(R.layout.pop_up_language_currency)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val recyclerView = dialog?.findViewById<RecyclerView>(R.id.recycler_view_language_currency)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        dialog?.show()
    }

    private fun showWelcomeDialog() {
        CustomDialog(
            mContext,
            getString(R.string.welcome),
            getString(R.string.welcome_dialog),
            getString(R.string.ok),
            showNegativeButton = false
        ).show()
    }

    private fun showLogoutDialog() {
        CustomDialog(
            mContext,
            getString(R.string.log_out),
            getString(R.string.log_out_dialog),
            getString(R.string.yes),
            getString(R.string.no),
            positiveButtonClickListener = {
                viewModel.auth.signOut()
                BaseShared.removeKey(mContext, EMAIL)
                // navigateSafe(R.id.action_profileFragment_to_loginFragment)
                requireActivity().recreate()
            }
        ).show()
    }

    private fun setUpAppBar() {
        AppUtils.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.my_profile))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
    }
}