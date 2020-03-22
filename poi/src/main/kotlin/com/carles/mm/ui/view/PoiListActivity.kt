package com.carles.mm.ui.view

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.core.ui.view.BaseActivity
import com.carles.core.ui.viewmodel.ERROR
import com.carles.core.ui.viewmodel.LOADING
import com.carles.core.ui.viewmodel.ResourceState
import com.carles.core.ui.viewmodel.SUCCESS
import com.carles.mm.Poi
import com.carles.mm.R
import com.carles.mm.ui.viewmodel.PoiListViewModel
import kotlinx.android.synthetic.main.activity_poi_list.poilist_recyclerview
import org.koin.android.viewmodel.ext.android.viewModel

class PoiListActivity : BaseActivity() {

    override val layoutResourceId = R.layout.activity_poi_list
    private val viewModel by viewModel<PoiListViewModel>()
    private lateinit var adapter: PoiListAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observablePoiList.observe(this, Observer { it?.let { handlePoiList(it.state, it.data, it.message) } })
    }

    override fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.poilist_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        adapter = PoiListAdapter { poi -> navigateToPoiDetail(poi.id) }
        poilist_recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        poilist_recyclerview.adapter = adapter
    }

    private fun handlePoiList(state: ResourceState, poiList: List<Poi>?, errorMessage: String?) {
        when (state) {
            SUCCESS -> {
                hideProgress()
                adapter.setItems(poiList ?: emptyList())
            }
            ERROR -> {
                hideProgress()
                showError(errorMessage) { viewModel.retry() }
            }
            LOADING -> showProgress()
        }
    }

    private fun navigateToPoiDetail(id: String) = startActivity(PoiDetailActivity.newIntent(this, id))
}