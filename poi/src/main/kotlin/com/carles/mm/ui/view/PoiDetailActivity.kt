package com.carles.mm.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.carles.core.ui.view.BaseActivity
import com.carles.core.ui.view.initDefaultToolbar
import com.carles.core.ui.viewmodel.ERROR
import com.carles.core.ui.viewmodel.LOADING
import com.carles.core.ui.viewmodel.ResourceState
import com.carles.core.ui.viewmodel.SUCCESS
import com.carles.mm.PoiDetail
import com.carles.mm.R
import com.carles.mm.ui.viewmodel.PoiDetailViewModel
import kotlinx.android.synthetic.main.activity_poi_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PoiDetailActivity : BaseActivity() {

    override val layoutResourceId = R.layout.activity_poi_detail
    private val viewModel by viewModel<PoiDetailViewModel> { parametersOf(intent.getStringExtra(EXTRA_ID)) }
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observablePoiDetail.observe(this, Observer {
            it?.let { handlePoiDetail(it.state, it.data, it.message) }
        })
    }

    override fun initViews() {
        toolbar = initDefaultToolbar()
    }

    private fun handlePoiDetail(state: ResourceState, poiDetail: PoiDetail?, errorMessage: String?) {
        when (state) {
            SUCCESS -> {
                hideProgress()
                if (poiDetail != null) displayPoiDetail(poiDetail)
            }
            ERROR -> {
                hideProgress()
                showError(errorMessage)
            }
            LOADING -> showProgress()
        }
    }

    private fun displayPoiDetail(poi: PoiDetail) {
        toolbar.title = poi.title
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

    companion object {
        private const val EXTRA_ID = "poi_detail_extra_id"
        fun newIntent(context: Context, id: String) = Intent(context, PoiDetailActivity::class.java).apply { putExtra(
            EXTRA_ID, id) }
    }
}