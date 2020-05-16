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
import com.carles.core.Navigator
import com.carles.core.ui.BaseFragment
import com.carles.core.ui.ERROR
import com.carles.core.ui.LOADING
import com.carles.core.ui.ResourceState
import com.carles.core.ui.SUCCESS
import com.carles.poi.PoiDetail
import com.carles.poi.R
import com.carles.poi.ui.ErrorDialogFragment.Companion.REQUEST_CODE_RETRY
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_address_textview
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_contentview
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_description_textview
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_mail_textview
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_phone_textview
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_toolbar
import kotlinx.android.synthetic.main.fragment_poi_detail.poidetail_transport_textview
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PoiDetailFragment : BaseFragment(R.layout.fragment_poi_detail) {

    private val viewModel: PoiDetailViewModel by viewModel { parametersOf(requireArguments().getString(EXTRA_ID)) }
    private val navigate: Navigator by lazy {
        requireActivity().lifecycleScope.get<Navigator> { parametersOf(requireActivity()) }
    }

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
            ERROR -> {
                hideProgress()
                navigate.toErrorFromPoiDetail(errorMessage)
            }
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