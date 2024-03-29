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
import com.example.e_commerceapp.adapter.language.LanguageAdapter
import com.example.e_commerceapp.adapter.profile.ProfileAdapter
import com.example.e_commerceapp.base.BaseFragment
import com.example.e_commerceapp.databinding.FragmentProfileBinding
import com.example.e_commerceapp.util.AppUtils
import com.example.e_commerceapp.base.BaseShared
import com.example.e_commerceapp.util.Constants.CATEGORY
import com.example.e_commerceapp.util.Constants.DE
import com.example.e_commerceapp.util.Constants.EMAIL
import com.example.e_commerceapp.util.Constants.EN
import com.example.e_commerceapp.util.Constants.FLAG
import com.example.e_commerceapp.util.Constants.FR
import com.example.e_commerceapp.util.Constants.LANGUAGE_NAME
import com.example.e_commerceapp.util.Constants.TR
import com.example.e_commerceapp.util.CustomDialog
import com.example.e_commerceapp.util.SetCategories
import com.example.e_commerceapp.util.changeLanguage
import com.example.e_commerceapp.util.goneIf
import com.example.e_commerceapp.util.navigateSafe
import com.example.e_commerceapp.util.visibleIf


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var languageAdapter: LanguageAdapter
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
        }

    }

    override fun setUpObservers() {}

    private fun setUpAdapter() {
        profileAdapter = ProfileAdapter(setCategories.setProfileCategories(mContext),
            object : ProfileAdapter.ItemClickListener {
                override fun onClick(categoryName: String, position: Int) {
                    BaseShared.saveString(mContext, CATEGORY, categoryName)
                    when (position) {
                        2 -> navigateSafe(R.id.action_profileFragment_to_cartFragment)
                        7 -> showLogoutDialog()
                        else -> navigateSafe(R.id.action_profileFragment_to_categoryDetailFragment)
                    }
                }
            })
        binding?.recyclerViewProfile?.apply {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun checkUserLoginStatus(isVisible: Boolean) {
        binding?.apply {
            recyclerViewProfile visibleIf isVisible
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

    private fun saveLanguageAndFlag(languageName: String, flag: Int){
        BaseShared.saveString(mContext,LANGUAGE_NAME,languageName)
        BaseShared.saveInt(mContext,FLAG,flag)
    }

    private fun showLanguagePopUp() {
        val languageList = setCategories.setLanguages(mContext)
        languageAdapter = LanguageAdapter(languageList) { language ->
            when (language) {
                0 -> {
                    context?.changeLanguage(EN)
                    saveLanguageAndFlag(getString(R.string.english), R.drawable.united_kingdom)
                }

                1 -> {
                    context?.changeLanguage(TR)
                    saveLanguageAndFlag(getString(R.string.turkish), R.drawable.turkey)
                }

                2 -> {
                    context?.changeLanguage(DE)
                    saveLanguageAndFlag(getString(R.string.german), R.drawable.germany)
                }

                3 -> {
                    context?.changeLanguage(FR)
                    saveLanguageAndFlag(getString(R.string.french), R.drawable.france)
                }
            }
            requireActivity().recreate()
        }

        dialog = Dialog(mContext)
        dialog?.apply {
            setContentView(R.layout.pop_up_language)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val recyclerViewLanguage = dialog?.findViewById<RecyclerView>(R.id.recycler_view_language)
        recyclerViewLanguage?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = languageAdapter
        }
        dialog?.show()
    }

    private fun updateLanguageViews() {
        val selectedLanguage = BaseShared.getString(mContext, LANGUAGE_NAME, getString(R.string.english)) ?: ""
        val selectedFlag = BaseShared.getInt(mContext, FLAG, R.drawable.united_kingdom)

        binding?.apply {
            textLanguages.text = selectedLanguage
            imageFlag.setImageResource(selectedFlag)
        }
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
            getString(R.string.exit),
            getString(R.string.log_out_dialog),
            getString(R.string.yes),
            getString(R.string.no),
            positiveButtonClickListener = {
                viewModel.auth.signOut()
                BaseShared.removeKey(mContext, EMAIL)
                navigateSafe(R.id.action_profileFragment_to_loginFragment)
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