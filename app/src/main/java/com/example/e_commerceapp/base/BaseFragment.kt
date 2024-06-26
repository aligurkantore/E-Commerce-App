package com.example.e_commerceapp.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.e_commerceapp.R
import com.example.e_commerceapp.helper.NetWorkUtils
import com.example.e_commerceapp.ui.dialogs.CustomDialog
import com.example.e_commerceapp.util.ProgressBarUtil

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    lateinit var mContext: Context

    private var _binding: VB? = null
    protected val binding: VB?
        get() = _binding

    protected abstract val viewModelClass: Class<out VM>
    lateinit var netWorkUtils: NetWorkUtils
    lateinit var progressBarUtil: ProgressBarUtil

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    abstract fun setUpListeners()

    abstract fun setUpObservers()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = getBinding(inflater, container)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        netWorkUtils = NetWorkUtils()
        progressBarUtil = ProgressBarUtil(mContext, view as ViewGroup)
        setUpListeners()
        setUpObservers()

        if (netWorkUtils.isInternetAvailable(mContext)){
            Log.d("agt", "There is an Internet connection ")
        }else {
            Log.d("agt", "No Internet connection ")
            showNoInternetDialog()
        }
    }


    private fun showNoInternetDialog(){
        CustomDialog(
            mContext,
            getString(R.string.warning),
            getString(R.string.no_internet),
            getString(R.string.ok),
            showNegativeButton = false,
            positiveButtonClickListener = {
                activity?.finishAffinity()
            }
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
