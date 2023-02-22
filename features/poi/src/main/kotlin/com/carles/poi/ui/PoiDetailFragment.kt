package com.carles.poi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import com.carles.core.Navigator
import com.carles.core.databinding.FragmentPoiDetailBinding
import com.carles.core.ui.BaseFragment
import com.carles.core.ui.ERROR
import com.carles.core.ui.LOADING
import com.carles.core.ui.ResourceState
import com.carles.core.ui.SUCCESS
import com.carles.poi.PoiDetail
import com.carles.poi.ui.ErrorDialogFragment.Companion.REQUEST_CODE_RETRY
import org.koin.android.ext.android.get
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PoiDetailFragment : BaseFragment<FragmentPoiDetailBinding>() {

    private val viewModel: PoiDetailViewModel by viewModel { parametersOf(requireArguments().getString(EXTRA_ID)) }
    private val navigate: Navigator by lazy {
        (requireActivity() as AndroidScopeComponent).scope.get { parametersOf(requireActivity()) }
    }

    override val progress
        get() = binding.poidetailProgress.progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultListener()
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPoiDetailBinding {
        return FragmentPoiDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultToolbar(binding.poidetailToolbar.toolbar)

        viewModel.observablePoiDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                handlePoiDetail(it.state, it.data, it.message)
            }
        })
    }

    private fun handlePoiDetail(state: ResourceState, poiDetail: PoiDetail?, errorMessage: String?) {
        when (state) {
            SUCCESS -> {
                hideProgress()
                if (poiDetail != null) displayPoiDetail(poiDetail)
            }
            ERROR -> {
                hideProgress()
                navigate.toErrorFromPoiDetail(errorMessage)
            }
            LOADING -> showProgress()
        }
    }

    private fun displayPoiDetail(poi: PoiDetail) {
        (activity as AppCompatActivity).supportActionBar?.title = poi.title
        binding.poidetailContentview.visibility = VISIBLE
        binding.poidetailAddressTextview.text = poi.address
        binding.poidetailDescriptionTextview.text = poi.description

        binding.poidetailTransportTextview.text = poi.transport ?: ""
        binding.poidetailTransportTextview.visibility = if (poi.transport == null) GONE else VISIBLE
        binding.poidetailMailTextview.text = poi.email ?: ""
        binding.poidetailMailTextview.visibility = if (poi.email == null) GONE else VISIBLE
        binding.poidetailPhoneTextview.text = poi.phone ?: ""
        binding.poidetailPhoneTextview.visibility = if (poi.phone == null) GONE else VISIBLE
    }

    private fun setResultListener() {
        this.setFragmentResultListener(REQUEST_CODE_RETRY) { _, _ ->
            viewModel.retry()
        }
    }

    companion object {
        private const val EXTRA_ID = "poi_detail_extra_id"
        fun getBundle(id: String) = bundleOf(EXTRA_ID to id)
    }
}