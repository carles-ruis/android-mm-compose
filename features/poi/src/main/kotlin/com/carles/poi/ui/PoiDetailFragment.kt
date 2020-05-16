package com.carles.poi.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.carles.core.ui.view.BaseFragment
import com.carles.core.ui.view.safeNavigate
import com.carles.core.ui.viewmodel.ERROR
import com.carles.core.ui.viewmodel.LOADING
import com.carles.core.ui.viewmodel.ResourceState
import com.carles.core.ui.viewmodel.SUCCESS
import com.carles.poi.PoiDetail
import com.carles.poi.R
import com.carles.poi.ui.ErrorDialogFragment.Companion.REQUEST_CODE_RETRY
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_poi_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PoiDetailFragment : BaseFragment(R.layout.fragment_poi_detail) {

    private val viewModel by viewModel<PoiDetailViewModel> { parametersOf(requireArguments().getString(EXTRA_ID)) }
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = initDefaultToolbar(poidetail_toolbar as MaterialToolbar)

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
            ERROR -> navigateToError(errorMessage)
            LOADING -> showProgress()
        }
    }

    private fun displayPoiDetail(poi: PoiDetail) {
        (activity as AppCompatActivity).supportActionBar?.title = poi.title
        poidetail_contentview.visibility = VISIBLE
        poidetail_address_textview.text = poi.address
        poidetail_description_textview.text = poi.description

        poidetail_transport_textview.text = poi.transport ?: ""
        poidetail_transport_textview.visibility = if (poi.transport == null) GONE else VISIBLE
        poidetail_mail_textview.text = poi.email ?: ""
        poidetail_mail_textview.visibility = if (poi.email == null) GONE else VISIBLE
        poidetail_phone_textview.text = poi.phone ?: ""
        poidetail_phone_textview.visibility = if (poi.phone == null) GONE else VISIBLE
    }

    private fun navigateToError(errorMessage: String?) {
        hideProgress()
        safeNavigate(
            R.id.poiDetailFragment,
            R.id.action_poiDetailFragment_to_errorDialogFragment,
            ErrorDialogFragment.getBundle(errorMessage, true)
        )
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